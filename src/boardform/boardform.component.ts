import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { BOARDS } from 'src/Models/Boards';
import { COLUMN } from 'src/Models/Column';
import { BoardService } from 'src/Services/board.service';

@Component({
  selector: 'app-boardform',
  templateUrl: './boardform.component.html',
  styleUrls: ['./boardform.component.css']
})
export class BoardformComponent {
  constructor(private dialogRef: MatDialogRef<BoardformComponent>,
    private formBuilder: FormBuilder,
    private service: BoardService){}

    boarddetails = this.formBuilder.group({
      boardname: ["",Validators.required],
      columns: [[] as COLUMN[]],
      team_members: [[] as string[]]
    })

    get boardname() { return this.boarddetails.get("boardname"); }



    onsubmit() {
      const boarddata: BOARDS = this.boarddetails.value as BOARDS;
      this.service.Saveboards(boarddata).subscribe(data => {
        console.log("sucess");
        this.dialogRef.close(); 
      })
    }

}
