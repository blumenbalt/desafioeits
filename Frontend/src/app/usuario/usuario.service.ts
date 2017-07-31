import { PageRequest } from './../PageRequest';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response } from '@angular/http';
import { Usuario } from "app/usuario";

@Injectable()
export class UsuarioService
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
  url: String = '/projeto/api/usuario';


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
  usuarioAtual(): Observable<Usuario>
  {
    return this.http.get(this.url + '/atual').map(res => res.json());;
  }

  /**
   *
   * LISTAR USUARIOS
   */
  listarUsuario(page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.url + '/' + page + '/' + size).map(res => res.json());;
  }

  /**
   *
   *
   *    LISTAR USUARIOS POR NOME
   */
  listarUsuarioPorNome(page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());;
  }

  /**
   *
   *
   *   ATIVAR USUÁRIO
   */

  ativarUsuario(usuario): Observable<Response>
  {
    return this.http.patch(this.url + '/on/' + usuario.id, JSON.stringify(usuario), { headers: this.headers });
  }

  /**
   *
   *
   *    DESATIVAR USUARIO
   */
  desativarUsuario(usuario): Observable<Response>
  {
    return this.http.patch(this.url + '/off/' + usuario.id, JSON.stringify(usuario), { headers: this.headers });
  }

  /**
   *
   *
   *   DETALHAR USUARIO
   */
  detalharUsuario(id): Observable<Usuario>
  {
    return this.http.get(this.url + '/' + id).map(res => res.json());
  }

  /**
   *
   * @param usuario
   */
  formarUsuario(usuario): Observable<Response>
  // tslint:disable-next-line:one-line
  {
    if (usuario.id != undefined)
    {
      /**
       *
       *    ALTERAR USUARIO
       */
      return this.http.put(this.url + '', JSON.stringify(usuario), { headers: this.headers });
    }
    else
    {
      /**
       *    INSERIR USUARIO
       *
       */
      return this.http.post(this.url + '', JSON.stringify(usuario), { headers: this.headers });
    }

  }

  /**
   *   ALTERAR SENHA DO USUÁRIO
   */
  alterarSenha(usuario): Observable<Response>
  {
    return this.http.put(this.url + '/senha', JSON.stringify(usuario), { headers: this.headers });
  }


  /**
   *
   *  LISTAR TODOS ATIVOS
   */
  listarAtivos(page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.url + '/ativos' + '/' + page + '/' + size).map(res => res.json());
  }

  /**
   *
   * LISTAR ATIVOS POR NOME
   */
  listarAtivosPorNome(page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/ativos' + + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());
  }

  /**
   * LISTAR POR DEPARTAMENTO
   */
  listarPorDepartamento(id, page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.url + '/departamento/' + id + '/' + page + '/' + size).map(res => res.json());
  }

  /**
   *
   * LISTAR POR DEPARTAMENTO E POR NOME
   */
  listarPorDepartamentoPorNome(id, page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/departamento/' + id + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());
  }

  /**
   * LISTAR USUARIOS PARA VINCULAR
   */
  listarParaVincular(page: number, size: number): Observable<PageRequest>
  {
    return this.http.get(this.url + '/vincular' + '/' + page + '/' + size).map(res => res.json());
  }

  /**
   * LISTAR USUARIOS PARA VINCULAR POR NOME
   */
  listarParaVincularPorNome(page: number, size: number, property: String, order: String, textSearch: String): Observable<PageRequest>
  {
    return this.http.get(this.url + '/vincular' + '/' + page + '/' + size + '/' + property + '/' + order + '/' + textSearch).map(res => res.json());
  }


  vincularUsuario(usuario, departamentoId): Observable<Response>
  {
    return this.http.patch(this.url + '/vincular/' + usuario.id + '/' + departamentoId, JSON.stringify(usuario), { headers: this.headers });
  }

  desvincularUsuario(usuario): Observable<Response>
  {
    return this.http.patch(this.url + '/desvincular/' + usuario.id, JSON.stringify(usuario), { headers: this.headers });
  }

  promoverUsuario(id, papel): Observable<Response>
  {
    return this.http.patch(this.url + '/role/' + id + '/papel=' + papel, { headers: this.headers });
  }

  deletarContrato(id: Number)
  {
    return this.http.delete(this.url + '/clearFile/' + id);
  }

  downloadContrato(id: Number)
  {
    return this.http.get(this.url + '/downloadFile/' + id);
  }

  uploadContrato(file: FormData, id: Number): Observable<Response>
  {
    return this.http.post(this.url + '/uploadFile/' + id, file);
  }
}


