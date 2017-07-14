import { PageRequest } from './../PageRequest';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class MensagemService
{
    headers: Headers;
    url: String = '/projeto/mensagemdepartamento';

     constructor(public http: Http)
  {
    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
  }

   listarMensagens(page: number, size: number, order: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/' +  page + '/' + size + '/' + order).map(res => res.json());
  }

  listarMensagem():Observable<Response>
  {
    return this.http.get(this.url + '/home').map(res => res.json());
  }

  /**
   *
   *   DELETAR MENSAGEM
   */
  deleteDepartamento(id): Observable<Response>
  {
     return this.http.delete(this.url + '/' + id);
  }

  novaMensagem(mensagem): Observable<Response>
  {
    return this.http.post(this.url + '', JSON.stringify(mensagem), {headers:this.headers});
  }

}

