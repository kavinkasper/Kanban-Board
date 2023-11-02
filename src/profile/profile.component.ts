import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { KANBAN } from 'src/Models/Kanban';
import { RegisterdetailService } from 'src/Services/registerdetail.service';
import { UserService } from 'src/Services/user.service';
import { TokenService } from 'src/Services/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {
  constructor(
    private serviceS: RegisterdetailService, 
    private userservice:UserService,
    ) {}

  details: KANBAN = {
    email: "",
    username: "",
    password: "",
    confirmpassword: "",
    mobilenumber: 0,
    boards: []
  };


  ngOnInit() {
    this.serviceS.getregistereddetails().subscribe(data => {
      this.details = data;
    });
  }
    
  onSubmit(form:KANBAN) {
    this.userservice.updateuser(form).subscribe(data=>{
      console.log(data);
      console.log("UPDATED");

    })
  }

  formchangingvalue:boolean=false;

  changeboolean(){
    this.formchangingvalue=true;
  }


  
}

