import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { USER } from 'src/Models/user';

@Injectable({
  providedIn: 'root'
})
export class LoginauthentiationService {

  constructor(private HTTP:HttpClient) { }

  url="http://localhost:9000/api/v1/"

  getuserdetails():Observable<USER[]>{
    return this.HTTP.get<USER[]>(`${this.url}getallusers`);
  }

  loginauth(userdata:USER):Observable<string>{
    return this.HTTP.post(`${this.url}login`,userdata, {responseType:'text'});
  }

  gettinguseremail():Observable<string[]>{
    return this.HTTP.get<string[]>(`${this.url}getuseremail`);
  }
}
