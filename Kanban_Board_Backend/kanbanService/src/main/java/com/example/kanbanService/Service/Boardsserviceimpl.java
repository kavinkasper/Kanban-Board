package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Boards;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Moretanthreetask;
import com.example.kanbanService.exception.TeamMemberAlreadyExists;
import com.example.kanbanService.exception.UserNotFoundException;
import com.example.kanbanService.repository.Kanbanrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Boardsserviceimpl implements Boardservice {
    private Kanbanrepository repos;


    @Autowired
    public Boardsserviceimpl(Kanbanrepository repos) {
        this.repos = repos;
    }

    @Override
    public List<Boards> getallboards(String email) throws Boardsnotfound {
        Kanban userdetail = repos.findById(email).get();
        if (userdetail.getBoards().isEmpty()) {
            throw new Boardsnotfound();
        }
        return userdetail.getBoards();
    }

    @Override
    public Kanban saveboards(String emailid, Boards boards)
            throws Boardsnotfound, UserNotFoundException, TeamMemberAlreadyExists, Moretanthreetask {
        System.out.println("Email ID: " + emailid);
        if (repos.findById(emailid).isEmpty()) {
            throw new UserNotFoundException();
        }
        Kanban userData = repos.findById(emailid).get();
        if (userData.getBoards() == null ) {
            userData.setBoards(Arrays.asList(boards));
        } else {
            List<Boards> boardList = userData.getBoards();
            boardList.add(boards);
            userData.setBoards(boardList);
        }
        Kanban savekanban=repos.save(userData);
        return savekanban;
    }


    @Override
    public boolean updateboard(String email, String boardname, Boards updatedBoard)
            throws Boardsnotfound, UserNotFoundException, Moretanthreetask, TeamMemberAlreadyExists {
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        Kanban userdetails = repos.findById(email).get();
        List<Boards> blist = null;

        if (userdetails.getBoards().isEmpty()) {
            throw new Boardsnotfound();
        } else {
            List<Boards> boardsList = userdetails.getBoards();
            boolean boardFound = false;

            for (Boards board : boardsList) {
                if (board.getBoardname().equals(boardname)) {
                    board.setBoardname(updatedBoard.getBoardname());
//                    board.setColumns(updatedBoard.getColumns());
                    if (updatedBoard.getTeam_members() != null) {
                        for (String memberEmail : updatedBoard.getTeam_members()) {
                            if (!board.getTeam_members().contains(memberEmail)) {
                                board.getTeam_members().add(memberEmail);
                            }
                        }
                    }
                    boardFound = true;
//                    break;
                }
                for (String mem : board.getTeam_members()) {
                    Kanban user1 = repos.findById(mem).get();
                    blist = user1.getBoards();
                    for (Boards b : blist) {
                        if (b.getBoardname().equals(boardname)) {
                            b.setBoardname(updatedBoard.getBoardname());
//                            b.setColumns(updatedBoard.getColumns());
                            if (updatedBoard.getTeam_members() != null) {
                                for (String memberEmail : updatedBoard.getTeam_members()) {
                                    if (!b.getTeam_members().contains(memberEmail)) {
                                        b.getTeam_members().add(memberEmail);
                                    }
                                }
                            }
                        }
                    }
                    user1.setBoards(blist);
                    repos.save(user1);
                }
            }

            if (boardFound) {
                userdetails.setBoards(boardsList);
                repos.save(userdetails);
                return boardFound;
            } else {
                throw new Boardsnotfound();
            }
        }
    }

//    @Override
//    public boolean Deleteboard(String email, String boardname) throws Boardsnotfound, UserNotFoundException {
//        boolean value = false;
//        if (repos.findById(email).isEmpty()) {
//            throw new UserNotFoundException();
//        }
//        Kanban user = repos.findById(email).get();
//        Kanban user1 = null;
//
//        if (user.getBoards() == null || user.getBoards().isEmpty()) {
//            throw new Boardsnotfound();
//        }
//
//        List<Boards> boardlist = user.getBoards();
//        Boards boardToDelete = null;
//        List<Boards> boardlist1 = new ArrayList<>();
//        Boards boardToDelete1 = null;
//
//        for (Boards bo : boardlist) {
//            if (bo.getBoardname().equals(boardname)) {
//                boardToDelete = bo;
//                List<String> memberlist = bo.getTeam_members();
//                if (!memberlist.isEmpty()) {
//                    for (String mem : memberlist) {
//                        if (mem.equals(email)) {
//                            continue;
//                        } else {
//                            user1 = repos.findById(mem).get();
//                            boardlist1 = user1.getBoards();
//                            for (Boards b : boardlist1) {
//                                if (b.getBoardname().equals(boardname)) {
//                                    boardToDelete1 = b;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//        }
//
//        if (boardToDelete != null) {
//            boardlist.remove(boardToDelete);
//            user.setBoards(boardlist);
//            repos.save(user);
//
//            if (!boardToDelete.getTeam_members().isEmpty() && boardToDelete1 != null) {
//                boardlist1.remove(boardToDelete1);
//                user1.setBoards(boardlist1);
//                repos.save(user1);
//            }
//
//            value = true;
//        }
//
//        return value;
//    }

    @Override
    public boolean Deleteboard(String email, String boardname) throws Boardsnotfound, UserNotFoundException {
        boolean value = false;
        if (repos.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        Kanban user = repos.findById(email).get();

        if (user.getBoards() == null || user.getBoards().isEmpty()) {
            throw new Boardsnotfound();
        }

        List<Boards> boardlist = user.getBoards();
        Boards boardToDelete = null;

        List<String> membersToUpdate = new ArrayList<>();

        for (Boards bo : boardlist) {
            if (bo.getBoardname().equals(boardname)) {
                boardToDelete = bo;

                // Add all team members except the current user
                for (String mem : bo.getTeam_members()) {
                    if (!mem.equals(email)) {
                        membersToUpdate.add(mem);
                    }
                }
                break;
            }
        }

        if (boardToDelete != null) {
            boardlist.remove(boardToDelete);
            user.setBoards(boardlist);
            repos.save(user);

            // Update the boards
            for (String memberEmail : membersToUpdate) {
                Optional<Kanban> memberOptional = repos.findById(memberEmail);
                if (memberOptional.isPresent()) {
                    Kanban member = memberOptional.get();
                    List<Boards> memberBoards = member.getBoards();
                    memberBoards.removeIf(b -> b.getBoardname().equals(boardname));
                    member.setBoards(memberBoards);
                    repos.save(member);
                }
            }

            value = true;
        }

        return value;
    }

//    public boolean addMemberToBoardByEmail( String boardName, String memberEmail,String kanbanEmail)
//            throws UserNotFoundException, Boardsnotfound, TeamMemberAlreadyExists {
//        // Find the Kanban by email
//        Optional<Kanban> kanbanOptional = repos.findById(kanbanEmail);
//        if (kanbanOptional.isEmpty()) {
//            throw new UserNotFoundException();
//        }
//        Kanban kanbandetail = kanbanOptional.get();
//
//        // Find the board by boardName
//        Boards boardd = findBoardByName(kanbandetail, boardName);
//        if (boardd == null) {
//            throw new Boardsnotfound();
//        }
//            if (boardd.getTeam_members().contains(memberEmail)) {
//                throw new TeamMemberAlreadyExists();
//            }
//            if (!boardd.getTeam_members().contains(memberEmail)) {
//                boardd.getTeam_members().add(memberEmail);
//            }
//            if (!boardd.getTeam_members().contains(kanbanEmail)) {
//                boardd.getTeam_members().add(kanbanEmail);
//            }
//
//
//        repos.save(kanbandetail);
//
//        // Update the user's profile to include the board
//        Optional<Kanban> userkanbanOptional = repos.findById(memberEmail);
//        if (userkanbanOptional.isEmpty()) {
//            System.out.println("userrrrrr");
//            throw new UserNotFoundException();
//        }
//        Kanban userkanban = userkanbanOptional.get();
//        userkanban.getBoards().add(boardd);
//        repos.save(userkanban);
//
//        return true;
//    }

    public boolean addMemberToBoardByEmail(String boardName, String memberEmail, String kanbanEmail)
            throws UserNotFoundException, Boardsnotfound, TeamMemberAlreadyExists {

        Optional<Kanban> kanbanOptional = repos.findById(kanbanEmail);
        if (kanbanOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        Kanban kanbandetail = kanbanOptional.get();

        Boards boardd = findBoardByName(kanbandetail, boardName);
        if (boardd == null) {
            throw new Boardsnotfound();
        }

        List<String> teamMembers = boardd.getTeam_members();

        if (teamMembers.contains(memberEmail)) {
            throw new TeamMemberAlreadyExists();
        }

        teamMembers.add(memberEmail);

        if (!teamMembers.contains(kanbanEmail)) {
            teamMembers.add(kanbanEmail);
        }

        repos.save(kanbandetail);

        // Update the user's profile to include the board
        Optional<Kanban> userkanbanOptional = repos.findById(memberEmail);
        if (userkanbanOptional.isEmpty()) {
            System.out.println("userrrrrr");
            throw new UserNotFoundException();
        }
        Kanban userkanban = userkanbanOptional.get();
        userkanban.getBoards().add(boardd);

        for (String member : teamMembers) {
            if (!member.equals(memberEmail) && !member.equals(kanbanEmail)) {
                Optional<Kanban> existingMemberOptional = repos.findById(member);
                Kanban existingMember=null;
                if (existingMemberOptional.isPresent()) {
                    existingMember = existingMemberOptional.get();
                    List<Boards> blist=existingMember.getBoards();
                    Iterator<Boards> iterator = blist.iterator();
                    while (iterator.hasNext()) {
                        Boards b = iterator.next();
                        if (b.getBoardname().equals(boardName)) {
                            iterator.remove();
                        }
                    }

                    existingMember.getBoards().add(boardd);
                }
                repos.save(existingMember);
            }
        }
        repos.save(userkanban);
        return true;
    }




    private Boards findBoardByName(Kanban kanban, String boardName) {
    List<Boards> boardList = kanban.getBoards();
    for (Boards board : boardList) {
        if (board.getBoardname().equals(boardName)) {
            return board;
        }
    }
    return null;
}

    @Override
    public List<String> getallboardnames(String email) throws Boardsnotfound {
        List<Boards> boardsList=repos.findById(email).get().getBoards();
        List<String> boardname=new ArrayList<>();
        for(Boards b:boardsList){
            boardname.add(b.getBoardname());
        }
        if(!(boardname.isEmpty()))
        return boardname;
        else
            throw new Boardsnotfound();
    }
}





