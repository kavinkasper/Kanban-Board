import { Component, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-erromsg',
  templateUrl: './erromsg.component.html',
  styleUrls: ['./erromsg.component.css']
})
export class ErromsgComponent {

  constructor(private dialogRef: MatDialogRef<ErromsgComponent>,
    private route:Router){}

  @Input()
  message?:string;


  register(){
    this.route.navigateByUrl("/register")
    this.dialogRef.close();

  }



  

}
