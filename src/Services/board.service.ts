import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BOARDS } from 'src/Models/Boards';
import { TokenService } from './token.service';
@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(private HTTP:HttpClient,
    private token:TokenService) { }
  url="http://localhost:9000/api/v2/kanban/"


  Saveboards(board:BOARDS):Observable<BOARDS>
  {
    const tok =this.token.getHeaders();
    const options = { headers: tok };
    return this.HTTP.post<BOARDS>(`${this.url}saveboards`,board,options);
  }


  getboardnames():Observable<string[]>{
    const tok =this.token.getHeaders();
    const options = { headers: tok };
    return this.HTTP.get<string[]>(`${this.url}getboardnames`,options);
  }                                       


  deleteboards(boardname:string):Observable<boolean>{
    const tok =this.token.getHeaders();
    const options = { headers: tok };
    console.log("delete:"+options.headers.get('Authorization'));
    return this.HTTP.delete<boolean>(`${this.url}deleteboard/${boardname}`,options)
  }


getallboards():Observable<BOARDS[]>{
  const tok =this.token.getHeaders();
  const options = { headers: tok };
  return this.HTTP.get<BOARDS[]>(`${this.url}getallboards`,options);
}

updatingboard(boardname:any,boards:any):Observable<BOARDS>{
  const tok =this.token.getHeaders();
  const options = { headers: tok };
  console.log(boardname);
  console.log(boards);
  return this.HTTP.put<BOARDS>(`${this.url}updatingboard/${boardname}`,boards,options);
}

addteam_members(boardname:any,memberEmail:string):Observable<boolean>{
  const tok =this.token.getHeaders();
    const options = { headers: tok };
    console.log("add:"+options.headers.get('Authorization'));
  return this.HTTP.post<boolean>(`${this.url}addteam_members/${boardname}/${memberEmail}`,null,options)
}
}
