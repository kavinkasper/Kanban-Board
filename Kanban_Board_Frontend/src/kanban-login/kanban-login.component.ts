import { Component } from '@angular/core';
import {  AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { USER } from 'src/Models/user';
import { LoginauthentiationService } from 'src/Services/loginauthentiation.service';
import { TokenService } from 'src/Services/token.service';
import { Router } from '@angular/router';
import { EmailvalueService } from 'src/Services/emailvalue.service';
@Component({
  selector: 'app-kanban-login',
  templateUrl: './kanban-login.component.html',
  styleUrls: ['./kanban-login.component.css']
})
export class KanbanLoginComponent {
  constructor(private form:FormBuilder,
    private services:LoginauthentiationService,
    private token:TokenService,
    private route:Router,
    private emailservice:EmailvalueService){}

  Userdetails=this.form.group({
    email:["",[Validators.required, this.emailvalidation]],
    password:["",[Validators.required,Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]]
  })

  //Email Validation
  emailvalidation(email: AbstractControl) {
    const emailvalue = email.value;
    if (emailvalue.startsWith('0') || emailvalue.startsWith('1') || emailvalue.startsWith('2') || emailvalue.startsWith('3') || emailvalue.startsWith('4') || emailvalue.startsWith('5') || emailvalue.startsWith('6') || emailvalue.startsWith('7') || emailvalue.startsWith('8') || emailvalue.startsWith('9') || !emailvalue.endsWith('gmail.com')) {
      return { invalidemail: true };
    }
    else {
      return null;
    }
  }

  //Method to get Email value
  get emailvalue() { return this.Userdetails.get("email"); }

  //Method to get Password value
  get passwordvalue() { return this.Userdetails.get("password"); }

  onsubmit(){
    let userdetail:USER=this.Userdetails.value as USER;
    this.emailservice.setemail(userdetail.email);
    this.services.getuserdetails().subscribe(data=>{
      const filtereddata=data.filter(d=>d.email===userdetail.email && d.password===userdetail.password);
      console.log(filtereddata);
      if(filtereddata.length>0){
      this.services.loginauth(filtereddata[0]).subscribe(data=>{
        this.token.setToken(data);
        console.log("JWT Token: "+this.token.getToken());
        this.route.navigateByUrl("/boards");
      })
      }
    })
  }

  
}


// ,[Validators.required,Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]
