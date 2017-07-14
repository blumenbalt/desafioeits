import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MdSnackBar } from '@angular/material';



import { UsuarioService } from './../usuario.service';
import { TdLoadingService, LoadingMode, LoadingType } from '@covalent/core';
@Component({
  selector: 'app-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent  {
  usuario : Object = {};
  perfis =
          [
            {value: 'ROLE_ADMINISTRADOR', viewValue: 'Administrador'},
            {value: 'ROLE_COLABORADOR', viewValue: 'Colaborador'}
          ];
  fadeDiv: boolean = true;

  /**
   *
   *
   *
   *
   *
   */

  constructor(
    public usuarioService: UsuarioService,
    public snackBar: MdSnackBar,
    public router: Router,
    public activatedRouter: ActivatedRoute,
    private _loadingService: TdLoadingService)
  {
    activatedRouter.params.subscribe(params => {

          const id = params['id'];

          this.usuarioService.detalharUsuario(id).subscribe( usuario => this.usuario = usuario, erro => console.log(erro));
        this._loadingService.create({
        name: 'Loading',
        mode: LoadingMode.Indeterminate,
        type: LoadingType.Circular,
      });
    });
  }

  fade(): void
  {
  this.fadeDiv = !this.fadeDiv;
  }
  /**
     *                 SNACKBAR
     */
          openSnackBar(msg, action)
      {
        this.snackBar.open(msg, action,
        {
          duration: 3000
        });
      }
      /**
       *
       *      INSERIR USUARIO
       */
  inserirUsuario(event)
          {
            this._loadingService.register('Loading');
            setTimeout(() =>
            {
            this._loadingService.resolve('Loading');
            }, 1000000);
           this.usuarioService.formarUsuario(this.usuario).subscribe(() =>
            {
              setTimeout(() =>
              {
                this._loadingService.resolve('Loading');
              }, 0);
              this.router.navigate(['/usuarios']);
              this.openSnackBar('Usuário salvo ', 'Sucesso');
            },
            erro =>
            {
              setTimeout(() =>
              {
                this._loadingService.resolve('Loading');
              }, 0);
              console.log(erro);
              this.openSnackBar(erro._body, 'Erro');
            })
          }

        /**
         *
         *
         */


    mudarSenha(usuario)
    {
      usuario;
      this.usuarioService.alterarSenha(usuario).subscribe(sucess =>
      {
        this.openSnackBar(Object(sucess)._body, '');
      },
      erro =>
      this.openSnackBar(erro._body,'Erro')
      );
    }



}
