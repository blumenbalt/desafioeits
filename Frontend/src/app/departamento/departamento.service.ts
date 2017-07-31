import { PageRequest } from './../PageRequest';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class DepartamentoService
{

  /*------------------------------------------------------------------------
   *
   * 							ATRIBUTOS
   *
   *-----------------------------------------------------------------------*/
  headers: Headers;
  urlDepartamento: String = '/projeto/api/departamento';



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
   *
   *   LISTAR TODOS DEPARTAMENTOS
   */
  listarTodosDepartamentos(page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.urlDepartamento + '/' + page + '/' + size).map(res => res.json());
  }

  /**
   *
   *   LISTAR DEPARTAMENTO POR NOME
   */
  listarDepartamentoPorNome(page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.urlDepartamento + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());;
  }


  /**
   *
   *
   * LISTAR DEPARTAMENTO EXCLUINDO UM ID
   */
  listarDepartamentoTirandoId(id, page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.urlDepartamento + '/not/' + id + '/' + page + '/' + size).map(res => res.json());
  }

  /**
   *
   *        LISTAR DEPARTAMENTO POR NOME EXCLUINDO UM ID
   *
   *
   *
   */
  listarDepartamentoPorNomeTirandoId(id, page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.urlDepartamento + '/not/' + id + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());
  }

  /**
   *
   *
   * VINCULAR DEPARTAMENTO
   */
  vincularDepartamento(id, idpai): Observable<Response>
  {
    return this.http.patch(this.urlDepartamento + '/' + id + '/' + idpai, JSON.stringify(id, idpai), { headers: this.headers });
  }

  /**
   *
   *
   * DESVINCULAR DEPARTAMENTO
   */
  desvincularDepartamento(id): Observable<Response>
  {
    return this.http.patch(this.urlDepartamento + '/' + id, JSON.stringify(id), { headers: this.headers });
  }
  /**
    *
    *
    *   DETALHAR DEPARTAMENTO
    */
  detalharDepartamento(id): Observable<Response>
  {
    return this.http.get(this.urlDepartamento + '/' + id).map(res => res.json());
  }

  /**
   *
   * @param departamento
   */
  formarDepartamento(departamento): Observable<Response>
  // tslint:disable-next-line:one-line
  {
    if (departamento.id != undefined)
    {
      /**
       *
       *    ALTERAR DEPARTAMENTO
       */
      return this.http.put(this.urlDepartamento + '', JSON.stringify(departamento), { headers: this.headers });
    }
    else
    {
      /**
       *    INSERIR DEPARTAMENTO
       *
       */
      return this.http.post(this.urlDepartamento + '', JSON.stringify(departamento), { headers: this.headers });
    }


  }

  /**
   *
   *   DELETAR DEPARTAMENTO
   */
  deleteDepartamento(id): Observable<Response>
  {
    return this.http.delete(this.urlDepartamento + '/' + id);
  }

}
