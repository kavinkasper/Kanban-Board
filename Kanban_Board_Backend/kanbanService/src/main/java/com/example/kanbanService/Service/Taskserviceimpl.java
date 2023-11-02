package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Boards;
import com.example.kanbanService.Domain.Columns;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Domain.Tasks;
import com.example.kanbanService.exception.*;
import com.example.kanbanService.repository.Kanbanrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Taskserviceimpl implements Taskservice {
    private Kanbanrepository repos;

    @Autowired
    public Taskserviceimpl(Kanbanrepository repos) {
        this.repos = repos;
    }

    @Override
    public Tasks getTaskByTaskName(String email, String boardname, String columnname, String taskname)
            throws UserNotFoundException, Tasknotfound {
        Kanban kanbanValue = repos.findById(email).orElse(null);

        if (kanbanValue == null) {
            throw new UserNotFoundException();
        }

        for (Boards board : kanbanValue.getBoards()) {
            if (boardname.equals(board.getBoardname())) {
                for (Columns column : board.getColumns()) {
                    if (columnname.equals(column.getColumn_name())) {
                        for (Tasks task : column.getTasks()) {
                            if (taskname.equals(task.getName())) {
                                return task;
                            }
                        }
                    }
                }
            }
        }

        throw new Tasknotfound();
    }

    @Override
    public Kanban savetasktocolumn(String email, String columnname, Tasks task)
            throws UserNotFoundException, Columnnotfound, Taskalreadyexists, Moretanthreetask {
        Kanban kanbanvalue = repos.findById(email).orElseThrow(UserNotFoundException::new);

        int count = 0;
        boolean isTeamMember = false;

        for (Boards board : kanbanvalue.getBoards()) {
            for (Columns column : board.getColumns()) {
                if (column.getColumn_name().equals(columnname)) {
                    List<Tasks> taskList = column.getTasks();
                    if (taskList == null) {
                        taskList = new ArrayList<>();
                    }

                    for (Tasks existingtask : taskList) {
                        if (existingtask.getName().equals(task.getName())) {
                            throw new Taskalreadyexists();
                        }
                    }

                    if (board.getTeam_members().contains(email)) {
                        isTeamMember = true;
                    }

                    if (isTeamMember) {
                        taskList.add(task);
                        column.setTasks(taskList);
                    }
                }
                List<Tasks> tasklist = column.getTasks();
                for (Tasks t : tasklist) {
                    if (t.getAssigneeEmail().equals(task.getAssigneeEmail())) {
                        count = count + 1;
                    }
                }
                System.out.println("count: " + count);
                if (count > 3) {
                    throw new Moretanthreetask();
                }
            }
        }
        repos.save(kanbanvalue);

        if (isTeamMember) {
            List<Boards> blist = kanbanvalue.getBoards();
            for (Boards b : blist) {
                // Update tasks for team members
                for (String mem : b.getTeam_members()) {
                    if (mem.equals(email)) {
                        continue;
                    } else {
                        Kanban memberKanban = repos.findById(mem).orElse(null);
                        if (memberKanban != null) {
                            for (Boards memberBoard : memberKanban.getBoards()) {
                                for (Columns memberColumn : memberBoard.getColumns()) {
                                    if (memberColumn.getColumn_name().equals(columnname)) {
                                        List<Tasks> taskList = memberColumn.getTasks();
                                        if (taskList == null) {
                                            taskList = new ArrayList<>();
                                        }

                                        // Check task already exists
                                        for (Tasks existingtask : taskList) {
                                            if (existingtask.getName().equals(task.getName())) {
                                                throw new Taskalreadyexists();
                                            }
                                        }

                                        // Add the task for the team member
                                        taskList.add(task);
                                        memberColumn.setTasks(taskList);
                                        break;
                                    }
                                }
                            }
                            repos.save(memberKanban);
                        }
                    }
                }
            }
        }
        return kanbanvalue;
    }

    @Override
    public boolean deletetaskfromcolumn(String email, String boardname, String columnname, String taskname)
            throws UserNotFoundException, Tasknotfound, Columnnotfound {
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }

        Kanban kanban = repos.findById(email).get();
        List<Boards> boards = kanban.getBoards();

        for (Boards board : boards) {
            if (board.getBoardname().equals(boardname)) {
                List<Columns> columns = board.getColumns();
                for (Columns column : columns) {
                    if (column.getColumn_name().equals(columnname)) {
                        List<Tasks> tasks = column.getTasks();
                        Tasks taskToDelete = null;
                        for (Tasks task : tasks) {
                            if (task.getName().equals(taskname)) {
                                taskToDelete = task;
                                break;
                            }
                        }

                        if (taskToDelete != null) {
                            tasks.remove(taskToDelete);
                            column.setTasks(tasks);
                            repos.save(kanban);

                            // Delete the task from team members' boards
                            for (String mem : board.getTeam_members()) {
                                Kanban memberKanban = repos.findById(mem).get();
                                for (Boards memberBoard : memberKanban.getBoards()) {
                                    for (Columns memberColumn : memberBoard.getColumns()) {
                                        if (memberColumn.getColumn_name().equals(columnname)) {
                                            List<Tasks> memberTasks = memberColumn.getTasks();
//                                                Tasks memberTaskToDelete = null;
                                            for (Tasks memberTask : memberTasks) {
                                                if (memberTask.getName().equals(taskname)) {
                                                    memberTasks.remove(memberTask);
                                                    memberColumn.setTasks(memberTasks);
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                    repos.save(memberKanban);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean UpdateTask(String email, String boardName, String ColumnName, String TaskName, Tasks task)
            throws UserNotFoundException, Tasknotfound, Columnnotfound {
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }

        Kanban kanbandetail = repos.findById(email).get();
        List<Boards> boardlist = kanbandetail.getBoards();
        Boards board = null;

        for (Boards boards : boardlist) {
            if (boards.getBoardname().equals(boardName)) {
                board = boards;
                List<Columns> columnlist = boards.getColumns();

                for (Columns columns : columnlist) {
                    if (columns.getColumn_name().equals(ColumnName)) {
                        List<Tasks> tasklist = columns.getTasks();

                        for (Tasks existingTask : tasklist) {
                            if (existingTask.getName().equals(TaskName)) {

                                existingTask.setName(task.getName());
                                existingTask.setDescription(task.getDescription());
                                existingTask.setPriority(task.getPriority());
                                existingTask.setDueDate(task.getDueDate());
                                existingTask.setStartDate(task.getStartDate());
                                existingTask.setAssigneeEmail(task.getAssigneeEmail());


                                repos.save(kanbandetail);
                            }
                        }
                    }
                }
            }
        }

        if (board != null) {

            for (String teamMemberEmail : board.getTeam_members()) {
                if (!teamMemberEmail.equals(email)) {
                    Kanban teamMember = repos.findById(teamMemberEmail).get();

                    for (Boards blist : teamMember.getBoards()) {
                        if (blist.getBoardname().equals(boardName)) {
                            List<Columns> clist = blist.getColumns();

                            for (Columns columns : clist) {
                                if (columns.getColumn_name().equals(ColumnName)) {
                                    List<Tasks> tlist = columns.getTasks();

                                    for (Tasks existingTask : tlist) {
                                        if (existingTask.getName().equals(TaskName)) {

                                            existingTask.setName(task.getName());
                                            existingTask.setDescription(task.getDescription());
                                            existingTask.setPriority(task.getPriority());
                                            existingTask.setDueDate(task.getDueDate());
                                            existingTask.setStartDate(task.getStartDate());
                                            existingTask.setAssigneeEmail(task.getAssigneeEmail());

                                            repos.save(teamMember);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        throw new Tasknotfound();
    }

    // not used yet
    @Override
    public Kanban addtasktousercolumn(String column_name, Tasks task)
            throws UserNotFoundException, Columnnotfound, Moretanthreetask {
        Kanban assigneeUser = repos.findById(task.getAssigneeEmail()).get();
        int count = 0;

        if (repos.findById(task.getAssigneeEmail()).isEmpty()) {
            throw new UserNotFoundException();
        }

        for (Boards board : assigneeUser.getBoards()) {
            for (Columns column : board.getColumns()) {
                if (column.getColumn_name().equals(column_name)) {
                    List<Tasks> taskList = column.getTasks();
                    for (Tasks t : taskList) {
                        if (t.getAssigneeEmail().equals(task.getAssigneeEmail())) {
                            count = count + 1;
                        }
                    }
                    System.out.println("COUNT: " + count);
                    if (count > 3) {
                        taskList.add(task);
                        column.setTasks(taskList);
                        break;
                    } else {
                        throw new Moretanthreetask();
                    }
                }
            }
        }

        repos.save(assigneeUser);

        return assigneeUser;
    }

    @Override
    public List<Tasks> getalltasks(String email, String column_name) {
        Kanban userdetail = repos.findById(email).get();
        List<Boards> boardsList = userdetail.getBoards();
        List<Tasks> tasklist = new ArrayList<>();
        for (Boards b : boardsList) {
            List<Columns> columnsList = b.getColumns();
            for (Columns c : columnsList) {
                if (c.getColumn_name().equals(column_name)) {
                    tasklist = c.getTasks();
                    break;
                }
            }
        }
        return tasklist;
    }

    @Override
    public boolean updatingtaskbydraganddrop(String email, String boardname, String toColumn, Tasks tasks) {
        Kanban userdetails = repos.findById(email).orElse(null);
        if (userdetails == null) {
            System.out.println("User not found");
            return false;
        }

        Columns sourceColumn = null;
        Columns destinationColumn = null;

        // Find the source and destination columns
        for (Boards b : userdetails.getBoards()) {
            if (b.getBoardname().equals(boardname)) {
                for (Columns c : b.getColumns()) {
                    if (c.getColumn_name().equals(toColumn)) {
                        destinationColumn = c;
                    }
                    List<Tasks> tlist = c.getTasks();
                    for (Tasks t : tlist) {
                        if (t.getName().equals(tasks.getName())) {
                            sourceColumn = c;
                            break;
                        }
                    }
                    if (sourceColumn != null && destinationColumn != null) {
                        break;
                    }
                }
            }
        }

        if (destinationColumn == null || sourceColumn == null) {
            System.out.println("Source or destination column not found");
            return false;
        }

        List<Tasks> sourceTasks = sourceColumn.getTasks();
        sourceTasks.removeIf(t -> t.getName().equals(tasks.getName()));

        List<Tasks> destinationTasks = destinationColumn.getTasks();
        destinationTasks.add(tasks);

        repos.save(userdetails);

        // Update tasks for team members
        List<Boards> b1list = userdetails.getBoards();
        for (Boards b1 : b1list) {
            if (b1.getBoardname().equals(boardname)) {
                for (String teamMemberEmail : b1.getTeam_members()) {
                    if (!teamMemberEmail.equals(email)) {
                        Kanban teamMember = repos.findById(teamMemberEmail).orElse(null);
                        if (teamMember != null) {
                            updateTasksForTeamMember(teamMember, boardname, toColumn, tasks);
                        }
                    }
                }
            }
        }

        return true;
    }


    private void updateTasksForTeamMember(Kanban teamMember, String boardname, String toColumn, Tasks tasks) {
        Columns sourceColumn = null;
        Columns destinationColumn = null;

        for (Boards b : teamMember.getBoards()) {
            if (b.getBoardname().equals(boardname)) {
                for (Columns c : b.getColumns()) {
                    if (c.getColumn_name().equals(toColumn)) {
                        destinationColumn = c;
                    }
                    List<Tasks> tlist = c.getTasks();
                    for (Tasks t : tlist) {
                        if (t.getName().equals(tasks.getName())) {
                            sourceColumn = c;
                            break;
                        }
                    }
                    if (sourceColumn != null && destinationColumn != null) {
                        break;
                    }
                }
            }
        }

        if (destinationColumn == null || sourceColumn == null) {
            System.out.println("Source or destination column not found");
        }

        List<Tasks> sourceTasks = sourceColumn.getTasks();
        sourceTasks.removeIf(t -> t.getName().equals(tasks.getName()));

        List<Tasks> destinationTasks = destinationColumn.getTasks();
        destinationTasks.add(tasks);

        repos.save(teamMember);

    }


}
