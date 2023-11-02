import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class TokenService {

  setToken(to:string){
    localStorage.setItem('token', to);
  }

  getToken(){
    return localStorage.getItem('token');
  }

  clearToken(){
    localStorage.removeItem('token');
  }

  getHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }
}


