import { AuthService } from 'app/auth.service';
import { Usuario } from 'app/usuario';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MdSnackBar } from '@angular/material';



import { UsuarioService } from './../usuario.service';
import { TdLoadingService, LoadingMode, LoadingType, TdFileService } from '@covalent/core';
@Component({
  selector: 'app-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent
{

    /*------------------------------------------------------------------------
     *
     * 							ATRIBUTOS
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   */
  usuario: Usuario = new Usuario();

  /**
   *
   */
  @ViewChild('fileInput') inputEl: ElementRef;
  file: File;

  /**
   *
   */
  filePath: String = null;

  /**
   *
   */
  fadeDiv: boolean = true;

  /**
   *
   */
  perfis =
  [
    { value: 'ROLE_ADMINISTRADOR', viewValue: 'Administrador' },
    { value: 'ROLE_COLABORADOR', viewValue: 'Colaborador' }
  ];

  /**
   *
   *
   *
   *
   *
   */
  /*------------------------------------------------------------------------
     *
     * 							CONSTRUCTOR
     *
     *-----------------------------------------------------------------------*/
  constructor(
    private fileUploadService: TdFileService,
    public usuarioService: UsuarioService,
    public snackBar: MdSnackBar,
    public router: Router,
    private authService: AuthService,
    public activatedRouter: ActivatedRoute,
    private _loadingService: TdLoadingService)
  {
    activatedRouter.params.subscribe(params =>
    {

      const id = params['id'];

      this.usuarioService.detalharUsuario(id).subscribe(usuario =>
        this.usuario = usuario,
        erro => console.log(erro));
      this._loadingService.create({
        name: 'Loading',
        mode: LoadingMode.Indeterminate,
        type: LoadingType.Circular,
      });
    });
  }

  /*------------------------------------------------------------------------
     *
     * 							MÉTODOS
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   */
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
   */
  selectEvent(file: File, id: Number): void
  {
    let formData = new FormData();
    formData.append('file', file);
    this.usuarioService.uploadContrato(formData, id).subscribe(() =>
    {
    }, erro => this.openSnackBar(erro._body, 'Erro!'));

  }

  /**
   *
   */
  clearFile()
  {
    this.file = null;
    this.filePath = this.usuario.filePath;
    this.usuario.filePath = null;
  }

  /**
   *
   */
  saveEquipment(usuario: Usuario)
  {
    if (this.filePath)
    {
      if (this.usuario.filePath == null)
      {
        this.usuarioService.deletarContrato(this.usuario.id).subscribe(result =>
        {
        }, erro => console.log(erro));
      }
    }
    if (this.file)
    {
      this.usuario.filePath = this.file.name;
    }
    this._loadingService.register('Loading');
    setTimeout(() =>
    {
      this._loadingService.resolve('Loading');
    }, 1000000);
    this.usuarioService.formarUsuario(this.usuario).subscribe(() =>
    {
      if (this.usuario.id === this.authService.usuarioAtual.id)
      {
        if(this.usuario.perfil !== this.authService.usuarioAtual.perfil)
        {
            window.location.assign("/projeto/logout");
            alert('você mudou sua permissão, entre novamente');

        }
      }
      if (this.file)
      {
        this.selectEvent(this.file, usuario.id);
      }
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
      });
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
        this.openSnackBar(erro._body, 'Erro')
    );
  }



}
