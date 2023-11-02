import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { TASK } from 'src/Models/Tasks';

@Component({
  selector: 'app-columnform',
  templateUrl: './columnform.component.html',
  styleUrls: ['./columnform.component.css']
})
export class ColumnformComponent {
  constructor( private form:FormBuilder,
    private dialogRef: MatDialogRef<ColumnformComponent>){}

  columndetails = this.form.group({
    column_name: ["",Validators.required],
    tasks: [[] as TASK[]]
  })

  @Output() 
  columnformsubmit: EventEmitter<any> = new EventEmitter<any>();

  savecolumn(){
    const formData=this.columndetails.value;
    this.columnformsubmit.emit(formData);
    this.dialogRef.close();
  }

  get columnname(){
    return this.columndetails.get("column_name")
  }

}
