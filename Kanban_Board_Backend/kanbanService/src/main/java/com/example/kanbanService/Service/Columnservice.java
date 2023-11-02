package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Columns;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Columnnotfound;
import com.example.kanbanService.exception.UserNotFoundException;

import java.util.List;

public interface Columnservice {
    List<Columns> getallcolumnsfromboard(String email,String board_name)throws UserNotFoundException, Boardsnotfound;

    boolean savecolumnstoboards(String email,String board_name,Columns columns)throws UserNotFoundException, Boardsnotfound;

    boolean deletecolumn(String email,String board_name,String column_name)throws UserNotFoundException, Boardsnotfound, Columnnotfound;

    Kanban updatecolumn(String email,String board_name,String column_name,Columns column)
            throws UserNotFoundException,Boardsnotfound,Columnnotfound;

    List<String> getallcolumnnames(String email,String boardname)throws Columnnotfound;

    List<String> getteammembersnames(String email,String boardname);
}
