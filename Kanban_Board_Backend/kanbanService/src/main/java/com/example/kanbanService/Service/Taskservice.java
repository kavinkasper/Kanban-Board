package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Domain.Tasks;
import com.example.kanbanService.exception.*;

import java.util.List;

public interface Taskservice {
    Tasks getTaskByTaskName(String email,String boardname,String columnname,String taskname)
            throws UserNotFoundException, Tasknotfound ;

    Kanban savetasktocolumn(String email, String columnname, Tasks task)
            throws UserNotFoundException,Columnnotfound, Taskalreadyexists,Moretanthreetask;

    boolean deletetaskfromcolumn(String email,String boardname,String columnname,String taskname)
            throws UserNotFoundException, Tasknotfound, Columnnotfound;

    boolean UpdateTask(String email,String boardName,String ColumnName,String TaskName,Tasks task)
            throws UserNotFoundException, Tasknotfound, Columnnotfound;

    Kanban addtasktousercolumn(String column_name,Tasks task)throws UserNotFoundException,Columnnotfound,Moretanthreetask;

    List<Tasks> getalltasks(String email, String column_name);

    boolean updatingtaskbydraganddrop(String email,String boardname, String column_name,Tasks tasks);

}
