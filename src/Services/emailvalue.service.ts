import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmailvalueService {

  constructor() { }


  email:string="";

  setemail(e:string){
    sessionStorage.setItem("email",e);
  }

  getemail(){
    return sessionStorage.getItem("email");
  }

  clearemail(){
     sessionStorage.removeItem("email");
  }
}
