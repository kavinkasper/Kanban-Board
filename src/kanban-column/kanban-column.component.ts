
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { COLUMN } from 'src/Models/Column';
import { TASK } from 'src/Models/Tasks';
import { BoardService } from 'src/Services/board.service';
import { ColumnService } from 'src/Services/column.service';
import { TaskService } from 'src/Services/task.service';
import { ColumnformComponent } from 'src/columnform/columnform.component';
import { TaskformComponent } from 'src/taskform/taskform.component';
import { CdkDragDrop, moveItemInArray, transferArrayItem  } from '@angular/cdk/drag-drop';
import { TaskeditingformComponent } from 'src/taskeditingform/taskeditingform.component';
import { EmailvalueService } from 'src/Services/emailvalue.service';



@Component({
  selector: 'app-kanban-column',
  templateUrl: './kanban-column.component.html',
  styleUrls: ['./kanban-column.component.css'
  ]
})
export class KanbanColumnComponent implements OnInit {
  constructor(private route: ActivatedRoute,
    private columnservice: ColumnService,
    private dialog: MatDialog,
    private taskservice: TaskService,
    private Boardservice: BoardService,
    private router: Router,
    private emailservice:EmailvalueService
  ) { }
  boardname: string = "";
  columns: COLUMN[] = [];
  team_members: string[] = [];
  ngOnInit() {
    this.route.params.subscribe(data => {
      this.boardname = data["id"];
      this.columnservice.getallcolumns(this.boardname).subscribe(data => {
        console.log(data);
        this.columns = data;
      })
      this.columnservice.getteammembers(this.boardname).subscribe(data => {
        const adminemail=this.emailservice.getemail();
        const filteredteam_memberslist=data.filter(p=>p!=="kavinselvan005@gmail.com");
        this.team_members = filteredteam_memberslist;
      })
    });
    this.boardnametocolumn()
  }

   Admin:any=this.emailservice.getemail();
   AdminEmail:string="kavinselvan005@gmail.com";

  columnTasks: { [key: string]: TASK[] } = {};
  Columnnames: string[] = [];

  // boardnametocolumn() {
  //   this.columnservice.getcolumnnames(this.boardname).subscribe(data => {
  //     if (data.length > 0) {
  //       this.Columnnames = data;
  //       this.Columnnames.forEach(columnName => {
  //         this.taskservice.getalltasks(columnName).subscribe(data => {
  //           console.log(data);
  //           this.columnTasks[columnName] = [...data];
  //         });
  //       });
  //     }
  //   })
  // }


  boardnametocolumn() {
    this.columnservice.getcolumnnames(this.boardname).subscribe(data => {
      if (data.length > 0) {
        this.Columnnames = data;
        this.Columnnames.forEach(columnName => {
          this.taskservice.getalltasks(columnName).subscribe(data => {
            console.log(data);
  
            data.sort(function (taskA, taskB) {
              var priorityOrder = ['HIGH', 'MEDIUM', 'LOW']; 
  
              var priorityA = priorityOrder.indexOf(taskA.priority);  
              var priorityB = priorityOrder.indexOf(taskB.priority);   
  
              if (priorityA !== priorityB) {  
                return priorityA - priorityB;        
              } else {
                var dueDateA:any = new Date(taskA.dueDate);  
                var dueDateB:any = new Date(taskB.dueDate);  
                return dueDateA - dueDateB;
              }
            });
  
            this.columnTasks[columnName] = [...data];
          });
        });
      }
    });
  }
  
  addcolumn() {
    const dialogRef = this.dialog.open(ColumnformComponent);
    dialogRef.componentInstance.columnformsubmit.subscribe(data => {
      console.log(data);
      const col_name=data.column_name;
      const filtername=this.Columnnames.filter(p=>p===col_name);
      if(filtername.length===0){
      this.columnservice.savecolumn(this.boardname, data).subscribe(data => {
        console.log("added column");
        window.location.reload()
      })
    }
    else{
      alert("Column Name is already present");
    }
    })
  }

  deletetask(taskname: string, columnname: string) {
    this.taskservice.deletetask(this.boardname, columnname, taskname).subscribe(data => {
      console.log("successssssss");
      window.location.reload();
    })
  }

  taskadd(column_name: string) {

    const dialogRef = this.dialog.open(TaskformComponent);
    dialogRef.componentInstance.teammembers = this.team_members;

    dialogRef.componentInstance.formSubmit.subscribe((formData) => {
      this.taskservice.savetasks(column_name, formData).subscribe(
        (data) => {
          console.log("SUCCESS PARAM");
          window.location.reload();
        },
        (error) => {
          alert("Sorry the assignee is already doing three Tasks you cannot give more Tasks");
        }
      );

    })
  }

  deleteboard() {
    this.Boardservice.deleteboards(this.boardname).subscribe(data => {
      console.log("sucess");
      this.router.navigateByUrl("/boards");
    })
  }

  deletecolumn(colname: string) {
    this.columnservice.deletecolumns(this.boardname, colname).subscribe(data => {
      console.log("sucess kavin ");
      window.location.reload();
    })
  }

  onTaskDrop(event: CdkDragDrop<TASK[]>, column: string) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log(event.item.data);
      console.log(column);
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
      this.taskservice.draganddropupdate(this.boardname, column, event.item.data).subscribe(data => {
        console.log(this.boardname);
        console.log("drageed and dropped");
      })
    }
  }

  getPriorityColor(priority: string): string {
    switch (priority) {
      case 'HIGH':
        return ' #ff0000';
      case 'MEDIUM':
        return '#009933';
      case 'LOW':
        return ' #ffcc00';
      default:
        return 'transparent';
    }
  }

  taskdetails(taskdetails: any, cname: string) {
    console.log(taskdetails);
    const dialogRef = this.dialog.open(TaskeditingformComponent);
    dialogRef.componentInstance.taskdetails = taskdetails;

    dialogRef.componentInstance.formSubmit.subscribe((formData) => {
      console.log("update" + formData.value);
      this.taskservice.updatetask(this.boardname, cname, taskdetails.name, formData.value).subscribe(data => {
        console.log("updated TASK");
        window.location.reload();
      })
    })
  }



}



