package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Boards;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Moretanthreetask;
import com.example.kanbanService.exception.TeamMemberAlreadyExists;
import com.example.kanbanService.exception.UserNotFoundException;

import java.util.List;

public interface Boardservice {
    List<Boards> getallboards(String email) throws Boardsnotfound;

    Kanban saveboards(String emailid, Boards boards) throws Boardsnotfound, UserNotFoundException, TeamMemberAlreadyExists, Moretanthreetask;

    boolean updateboard(String email,String boardname,Boards board)throws Boardsnotfound, UserNotFoundException,Moretanthreetask,TeamMemberAlreadyExists;

    boolean Deleteboard(String email,String boardname)throws Boardsnotfound, UserNotFoundException;

    boolean addMemberToBoardByEmail(String boardName, String memberEmail,String kanbanEmail)
            throws UserNotFoundException,Boardsnotfound, Moretanthreetask, TeamMemberAlreadyExists;


    List<String> getallboardnames(String email) throws Boardsnotfound;



}