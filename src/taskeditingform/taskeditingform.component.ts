import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { TASK } from 'src/Models/Tasks';

@Component({
  selector: 'app-taskeditingform',
  templateUrl: './taskeditingform.component.html',
  styleUrls: ['./taskeditingform.component.css']
})
export class TaskeditingformComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<TaskeditingformComponent>){}

  taskform:TASK={
    name:"",
    description:"",
    priority:"",
    startDate:"",
    dueDate:"",
    assigneeEmail:""
  }

  @Input()
  taskdetails?:TASK

  ngOnInit() {
    if (this.taskdetails) {
      this.taskform={
        name:this.taskdetails.name,
        description: this.taskdetails.description,
        priority:this.taskdetails.priority,
        startDate:this.taskdetails.startDate,
        dueDate:this.taskdetails.dueDate,
        assigneeEmail:this.taskdetails.assigneeEmail
      }
    }
  }

  @Output() 
  formSubmit: EventEmitter<any> = new EventEmitter<any>();

  onSubmit(editTaskForm:any){
    console.log(editTaskForm);
    this.formSubmit.emit(editTaskForm);
    this.dialogRef.close();
  }

}
