import { Injectable } from '@angular/core';
import { KANBAN } from 'src/Models/Kanban';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';


@Injectable({
  providedIn: 'root'
})
export class RegisterdetailService {

  constructor(private HTTP:HttpClient,
    private token:TokenService) { }
  url="http://localhost:9000/api/v2/kanban/getusers"

  getregistereddetails():Observable<KANBAN>{
    const tok =this.token.getHeaders();
    console.log("token"+tok);
    const options = { headers: tok };
    return this.HTTP.get<KANBAN>(`${this.url}`,options);
  }

}
