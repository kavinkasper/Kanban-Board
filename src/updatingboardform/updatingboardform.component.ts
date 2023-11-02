import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { COLUMN } from 'src/Models/Column';

@Component({
  selector: 'app-updatingboardform',
  templateUrl: './updatingboardform.component.html',
  styleUrls: ['./updatingboardform.component.css']
})
export class UpdatingboardformComponent {

  constructor( private form:FormBuilder,
    private dialogRef: MatDialogRef<UpdatingboardformComponent>){}

  boarddetails=this.form.group({
    boardname: ["",Validators.required,,Validators.minLength(2)],
    columns: [[] as COLUMN[]],
    team_members: [[] as string[]]
  })

  get boardname() { return this.boarddetails.get("boardname"); }


  @Output() 
  boardform: EventEmitter<any> = new EventEmitter<any>();

  updateboard(){
    const formData=this.boarddetails.value;
    console.log(formData);
    this.boardform.emit(formData);
    this.dialogRef.close();
  }

}
