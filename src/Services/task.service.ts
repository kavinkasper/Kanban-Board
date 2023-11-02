import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BOARDS } from 'src/Models/Boards';
import { TokenService } from './token.service';
import { TASK } from 'src/Models/Tasks';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private HTTP:HttpClient,
    private token:TokenService) { }

    url="http://localhost:9000/api/v2/kanban/task/"

    savetasks(column_name:string,task:TASK):Observable<TASK>{
      const tok =this.token.getHeaders();
      const options = { headers: tok };
      return this.HTTP.post<TASK>(`${this.url}savetask/${column_name}`,task,options);
    }

    deletetask(boardname:string,columnname:string,taskname:string):Observable<boolean>{
      const tok =this.token.getHeaders();
      const options = { headers: tok };
      return this.HTTP.delete<boolean>(`${this.url}deletetasks/${boardname}/${columnname}/${taskname}`,options);
    }

    getalltasks(column_name:string):Observable<TASK[]>{
      const tok =this.token.getHeaders();
      const options = { headers: tok };
      return this.HTTP.get<TASK[]>(`${this.url}getalltasks/${column_name}`,options);
    }

    updatetask(boardname:string,column_name:string,taskname:string,task:TASK):Observable<boolean>{
      const tok =this.token.getHeaders();
      const options = { headers: tok };
      return this.HTTP.put<boolean>(`${this.url}updatetask/${boardname}/${column_name}/${taskname}`,task,options);
    }

    draganddropupdate(boardname:string,column_name:string, tasks:TASK):Observable<boolean>{
      const tok =this.token.getHeaders();
      const options = { headers: tok };
      return this.HTTP.post<boolean>(`${this.url}updatingdraganddrop/${boardname}/${column_name}`,tasks,options);
    }

}
