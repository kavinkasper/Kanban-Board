import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from './token.service';
import { COLUMN } from 'src/Models/Column';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';

@Injectable({
  providedIn: 'root'
})
export class ColumnService {

  constructor(
    private HTTP:HttpClient,
    private token:TokenService
  ) { }

  url="http://localhost:9000/api/v2/kanban/column/"

  savecolumn(boardname:string,columns:COLUMN):Observable<boolean>{
    let token=this.token.getHeaders();
    let options={headers:token};
    return this.HTTP.post<boolean>(`${this.url}addcolumns/${boardname}`,columns,options);
  }

  getcolumnnames(boardname:string):Observable<string[]>{
    let token=this.token.getHeaders();
    let options={headers:token};
    return this.HTTP.get<string[]>(`${this.url}getcolumnname/${boardname}`,options);
  }

  deletecolumns(boardname:string,column_name:string):Observable<boolean>{
    let token=this.token.getHeaders();
    let options={headers:token};
    return this.HTTP.delete<boolean>(`${this.url}deletecloumn/${boardname}/${column_name}`,options)
  }


  getteammembers(boardname:string):Observable<string[]>{
    let token=this.token.getHeaders();
    let options={headers:token};
    return this.HTTP.get<string[]>(`${this.url}getteammembersnames/${boardname}`,options);
  }


  getallcolumns(boardname:string):Observable<COLUMN[]>{
    let token=this.token.getHeaders();
    let options={headers:token};
    return this.HTTP.get<COLUMN[]>(`${this.url}getcolumns/${boardname}`,options);
  }
}
