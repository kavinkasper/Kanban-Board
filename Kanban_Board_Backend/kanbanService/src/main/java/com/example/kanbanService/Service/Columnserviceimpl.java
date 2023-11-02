package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Boards;
import com.example.kanbanService.Domain.Columns;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Columnnotfound;
import com.example.kanbanService.exception.UserNotFoundException;
import com.example.kanbanService.repository.Kanbanrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class Columnserviceimpl implements Columnservice {
    private Kanbanrepository repos;
@Autowired
    public Columnserviceimpl(Kanbanrepository repos) {
        this.repos = repos;
    }

    @Override
    public List<Columns> getallcolumnsfromboard(String email, String board_name) throws UserNotFoundException, Boardsnotfound {
        if(repos.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        Kanban kanbandetail=repos.findById(email).get();
        List<Columns> columnsList=null;
        List<Boards> boardlist=kanbandetail.getBoards();
        for(Boards boards:boardlist){
            if(boards.getBoardname().equals(board_name)){
                columnsList=boards.getColumns();
            }
        }
        if(columnsList!=null) {
            return columnsList;
        }
        else{
            throw new Boardsnotfound();
        }
    }

    @Override
    public boolean savecolumnstoboards(String email, String board_name, Columns columns) throws UserNotFoundException, Boardsnotfound {
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }

        Kanban kanbanDetails = repos.findById(email).get();
        List<Boards> boardList = kanbanDetails.getBoards();

        Boards targetBoard = null;

        // Find the target board by name
        for (Boards bo : boardList) {
            if (bo.getBoardname().equals(board_name)) {
                targetBoard = bo;
                break;
            }
        }

        if (targetBoard == null) {
            throw new Boardsnotfound();
        }

        // Update the existing board by adding the column
        List<Columns> columnList = targetBoard.getColumns();
        if (!columnList.contains(columns)) {
            columnList.add(columns);
            targetBoard.setColumns(columnList);
        }

        // Save the updated user details
        repos.save(kanbanDetails);

        // Updating in team members
        for (Boards B : boardList) {
            if (!B.getBoardname().equals(board_name)) {
                continue;
            }

            List<String> team_memberlist = B.getTeam_members();
            for (String m : team_memberlist) {
                if (m.equals(email)) {
                    continue;
                } else {
                    Kanban teamuser = repos.findById(m).get();
                    for (Boards b : teamuser.getBoards()) {
                        if (b.getBoardname().equals(board_name)) {
                            List<Columns> clist = b.getColumns();
                            if (clist.isEmpty()) {
                                b.setColumns(Arrays.asList(columns));
                            } else if (!clist.contains(columns)) {
                                clist.add(columns);
                                b.setColumns(clist);
                            }
                            repos.save(teamuser);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean deletecolumn(String email, String board_name, String column_name)
            throws UserNotFoundException, Boardsnotfound, Columnnotfound {
        boolean value = false;
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }

        Kanban kanbandetails = repos.findById(email).get();
        List<Boards> boardlist = kanbandetails.getBoards();
        for (Boards bo : boardlist) {
            if (bo.getBoardname().equals(board_name)) {
                List<Columns> columnList = bo.getColumns();
                Columns deletingcolumn = null;
                for (Columns col : columnList) {
                    if (col.getColumn_name().equals(column_name)) {
                        deletingcolumn = col;
                        break;
                    }
                }
                if (deletingcolumn != null) {
                    columnList.remove(deletingcolumn);
                    bo.setColumns(columnList);
                    repos.save(kanbandetails);
                    value = true;

                    // Delete the column from team members' boards
                    for (String mem : bo.getTeam_members()) {
                        Kanban memberKanban = repos.findById(mem).get();
                        for (Boards memberBoard : memberKanban.getBoards()) {
                            List<Columns> memberColumns = memberBoard.getColumns();
//                            Columns memberColumnToDelete = null;
                            for (Columns memberCol : memberColumns) {
                                if (memberCol.getColumn_name().equals(column_name)) {
                                    memberColumns.remove(memberCol);
                                    memberBoard.setColumns(memberColumns);
                                    break;
                                }
                            }
                        }
                        repos.save(memberKanban);
                    }
                }
            }
        }
        if (!value) {
            throw new Columnnotfound();
        }
        return value;
    }


    @Override
    public Kanban updatecolumn(String email, String board_name, String column_name, Columns column)
            throws UserNotFoundException, Boardsnotfound, Columnnotfound {
    if(repos.findById(email).isEmpty()){
        throw new UserNotFoundException();
    }
    Kanban kanbandetails=repos.findById(email).get();
    List<Boards>boardlist=kanbandetails.getBoards();
    if(boardlist.isEmpty()){
        throw new Boardsnotfound();
    }
    for(Boards bo:boardlist){
        if(bo.getBoardname().equals(board_name)){
            List<Columns> columnsList=bo.getColumns();
            if(columnsList.isEmpty()){
                throw new Columnnotfound();
            }
            for(Columns col:columnsList){
                if(col.getColumn_name().equals(column_name)){
                    col.setColumn_name(column.getColumn_name());
                    col.setTasks(column.getTasks());
                }
            }
        }
    }
        return repos.save(kanbandetails);
    }

    @Override
    public List<String> getallcolumnnames(String email,String boardname)throws Columnnotfound {
   Kanban user= repos.findById(email).get();
   List<Boards> boardlist=user.getBoards();
        List<String> columnname=new ArrayList<>();
        for(Boards bo:boardlist){
       if(bo.getBoardname().equals(boardname)){
           List<Columns> columnlist=bo.getColumns();
           for(Columns co:columnlist){
               columnname.add(co.getColumn_name());
           }
           break;
       }
   }
        return columnname;
    }

    @Override
    public List<String> getteammembersnames(String email,String boardname) {
    Kanban user=repos.findById(email).get();
        List<String> members_list=new ArrayList<>();
    for(Boards b: user.getBoards()) {
        if (b.getBoardname().equals(boardname)) {
            members_list = b.getTeam_members();
        }
    }
        return members_list;
    }


}

