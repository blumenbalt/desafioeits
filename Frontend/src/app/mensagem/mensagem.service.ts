import { PageRequest } from './../PageRequest';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class MensagemService
{
  /*------------------------------------------------------------------------
   *
   * 							ATRIBUTOS
   *
   *-----------------------------------------------------------------------*/

  /**
   *
   */
  headers: Headers;

  /**
   *
   */
  url: String = '/projeto/api/mensagemdepartamento';


  /*------------------------------------------------------------------------
     *
     * 							CONSTRUCTOR
     *
     *-----------------------------------------------------------------------*/
  constructor(public http: Http)
  {
    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
  }


  /*------------------------------------------------------------------------
     *
     * 							MÉTODOS
     *
     *-----------------------------------------------------------------------*/


  /**
   *                 LISTA TODAS AS MENSAGENS ENVIADA AO DEPARTAMENTO
   * @param page
   * @param size
   * @param order
   */
  listarMensagens(page: number, size: number, order: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/' + page + '/' + size + '/' + order).map(res => res.json());
  }

  /**
   * MOSTRA ULTIMA MENSAGEM ENVIADA AO DEPARTAMENTO DO USUÁRIO
   */
  listarMensagem(): Observable<Response>
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

  /**
   *             ENVIA UMA MENSAGEM PARA OS USUÁRIOS DO DEPARTAMENTO
   * @param mensagem
   */
  novaMensagem(mensagem): Observable<Response>
  {
    return this.http.post(this.url + '', JSON.stringify(mensagem), { headers: this.headers });
  }

}

