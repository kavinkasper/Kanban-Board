import { Component, EventEmitter, Input, Output,Inject  } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ColumnService } from 'src/Services/column.service';
import { EmailvalueService } from 'src/Services/emailvalue.service';



@Component({
  selector: 'app-taskform',
  templateUrl: './taskform.component.html',
  styleUrls: ['./taskform.component.css']
})
export class TaskformComponent {

  constructor(private form:FormBuilder,
    private dialogRef: MatDialogRef<TaskformComponent>
    ){
    this.start.setDate(this.start.getDate())
    this.due.setDate(this.start.getDate())
  }



  @Input()
  teammembers?: string[];


  @Output() 
  formSubmit: EventEmitter<any> = new EventEmitter<any>();

  taskdetails = this.form.group({
    name: ["",Validators.required],
    description: ["",Validators.required],
    priority:["",Validators.required],
    startDate:["",Validators.required],
    dueDate:["",Validators.required],
    assigneeEmail: ["",Validators.required]
  })

  start: Date = new Date();
  due: Date = new Date();

  taskadding(){
  const formData = this.taskdetails.value;
    this.formSubmit.emit(formData);
    this.dialogRef.close();
  }


  get taskname() { return this.taskdetails.get("name"); }

  get taskdescription() { return this.taskdetails.get("description"); }

  get taskpriority() { return this.taskdetails.get("priority"); }

  get taskstartDate() { return this.taskdetails.get("startDate"); }

  get taskdueDate() { return this.taskdetails.get("dueDate"); }

  get taskassigneeEmail() { return this.taskdetails.get("assigneeEmail"); }

}
