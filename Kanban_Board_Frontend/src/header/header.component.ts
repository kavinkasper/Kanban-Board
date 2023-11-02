import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EmailvalueService } from 'src/Services/emailvalue.service';
import { NotificationService } from 'src/Services/notification.service';
import { RegisterdetailService } from 'src/Services/registerdetail.service';
import { TokenService } from 'src/Services/token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private token:TokenService,
    private route:Router,
    private notificationservice:NotificationService,
    private serviceS: RegisterdetailService,
    private emailservice:EmailvalueService
    ){
      this.notificationservice.getmessagesofthatemail().subscribe(data=>{
        console.log(data);
        if(data)
        this.messages=data;
      })
      this.serviceS.getregistereddetails().subscribe(data=>{
        this.UserName=data.username;
      })
          this.Adminemail=this.emailservice.getemail();
          if(this.Adminemail ==="kavinselvan005@gmail.com"){
            this.registerboolean=true;
          }
    }

    Adminemail:any="";

    registerboolean:boolean=false;

    messages:string[]=[];

    UserName:string=""

  log(){
    this.token.clearToken();
    this.emailservice.clearemail();
    this.route.navigateByUrl("");
  }

  board(){
    this.route.navigateByUrl("/boards");
  }

}
