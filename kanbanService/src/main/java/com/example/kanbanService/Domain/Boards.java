package com.example.kanbanService.Domain;

import org.springframework.data.annotation.Id;
import java.util.List;

public class Boards {
    @Id
    private String boardname;
    private List<Columns> columns;
    private List<String> team_members;

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public List<Columns> getColumns() {
        return columns;
    }

    public void setColumns(List<Columns> columns) {
        this.columns = columns;
    }

    public List<String> getTeam_members() {
        return team_members;
    }

    public void setTeam_members(List<String> team_members) {
        this.team_members = team_members;
    }

    @Override
    public String toString() {
        return "Boards{" +
                "boardname='" + boardname + '\'' +
                ", columns=" + columns +
                ", team_members=" + team_members +
                '}';
    }
}
