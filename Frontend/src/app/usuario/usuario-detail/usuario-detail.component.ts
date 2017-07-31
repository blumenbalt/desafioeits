import { DepartamentoService } from './../../departamento/departamento.service';
import { UsuarioService } from 'app/usuario/usuario.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-usuario-detail',
  templateUrl: './usuario-detail.component.html',
  styleUrls: ['./usuario-detail.component.css']
})
export class UsuarioDetailComponent   {

  /*------------------------------------------------------------------------
     *
     * 							ATRIBUTOS
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   */
  usuario : Object = {};

  /**
   *
   */
  departamentos: Object []=[];

  /**
   *
   */
  usuarioAtual: Object;

  /*------------------------------------------------------------------------
     *
     * 							CONSTRUCTOR
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   * @param departamentoService
   * @param usuarioService
   * @param router
   * @param activatedRouter
   */
  constructor(
    public departamentoService: DepartamentoService,
    public usuarioService: UsuarioService,
    public router: Router,
    public activatedRouter: ActivatedRoute)
  {
    activatedRouter.params.subscribe(params =>
    {

          let id = params['id'];
          //   DETALHA O USUÁRIO
          this.usuarioService.detalharUsuario(id).subscribe( usuario =>
           this.usuario = usuario, erro => console.log(erro));


      usuarioService.usuarioAtual().subscribe(usuario =>
      {
        this.usuarioAtual = usuario;
      },
      erro => console.log(erro));
    });
   }

}
