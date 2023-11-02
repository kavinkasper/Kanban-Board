import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-teammembers',
  templateUrl: './teammembers.component.html',
  styleUrls: ['./teammembers.component.css']
})
export class TeammembersComponent {

  constructor(private dialogRef: MatDialogRef<TeammembersComponent>){}

  membername:string="";

  @Output()
  searching: EventEmitter<string> = new EventEmitter<string>();

  Addtheteam_member(){
    console.log("member"+ this.membername)
    this.searching.emit(this.membername);
    this.dialogRef.close();
  }



}
