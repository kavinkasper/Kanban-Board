import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private HTTP: HttpClient,
    private token: TokenService) { }

  url = "http://localhost:9000/api/v3/kanban/getemailmsg"

  getmessagesofthatemail(): Observable<string[]> {
    const tok = this.token.getHeaders();
    const options = { headers: tok };
    return this.HTTP.get<string[]>(`${this.url}`, options);
  }

}
