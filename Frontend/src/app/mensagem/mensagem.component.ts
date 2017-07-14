import { PageRequest } from './../PageRequest';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Usuario } from 'app/usuario';
import { UsuarioService } from 'app/usuario/usuario.service';
import { MdSnackBar } from '@angular/material';
import { ITdDataTableColumn, TdDialogService, IPageChangeEvent } from '@covalent/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MensagemService } from './mensagem.service';
import { Component, OnInit, ViewContainerRef } from '@angular/core';

@Component({
  selector: 'app-mensagem',
  templateUrl: './mensagem.component.html',
  styleUrls: ['./mensagem.component.css']
})
export class MensagemComponent
  {
  id;
   fadeDiv: boolean = true;
  usuarioAtual: Usuario;
  mensagens: PageRequest  = new PageRequest();
  total: Number;
  page: number = 1;
  size: number = 5;
  order: String ="DESC";
  property: String="nome";
  sortBy : String ="";
  filter : String = "";
  constructor(

   public usuarioService: UsuarioService,
   private _dialogService: TdDialogService,
   public _viewContainerRef: ViewContainerRef,
   public snackBar: MdSnackBar,
   private mensagemService : MensagemService,
   private router: Router,
   private activatedRouter: ActivatedRoute)
  {
          this.mensagemService.listarMensagens(this.page -1, this.size, this.order).subscribe(mensagens =>
      {
        this.mensagens = mensagens;
        this.total = this.mensagens.totalElements;
      })

      usuarioService.usuarioAtual().subscribe(usuario =>
      {
        this.usuarioAtual = usuario;
      },
      erro => console.log(erro));



  }

  openSnackBar(msg, action)
  {
    this.snackBar.open(msg, action,
    {
      duration: 3000,
    });
  }

  fade(): void
  {
    this.fadeDiv = !this.fadeDiv;
  }

  openConfirm(id)
  {
    this._dialogService.openConfirm(
        {
            message: 'Deseja deletar esta mensagem a esse departamento ?',
            disableClose: false,
            viewContainerRef: this._viewContainerRef,
            title: 'Deletar mensagem',
            cancelButton: 'Não',
            acceptButton: 'Sim',
        }).
        afterClosed().subscribe((accept: boolean) =>
        {
          if (accept)
          {
               this.mensagemService.deleteDepartamento(id).subscribe(() =>
              {
                 this.openSnackBar('Mensagem deletada' , '');
                 this.mensagemService.listarMensagens(this.page -1, this.size, this.order).subscribe(mensagens =>
                 {
                  this.mensagens = mensagens;
                  this.total = this.mensagens.totalElements;
                 },
              erro =>
              {
                console.log(erro);
                this.openSnackBar(erro._body , 'Erro!');
              });
              })
          }
          else
          {

          }
        });
  }

   openPrompt(): void
   {
    this._dialogService.openPrompt({
      message: 'Digite uma nova mensagem ao departamento. Até 144 caracteres',
      disableClose:false,
      viewContainerRef: this._viewContainerRef,
      title: 'Nova mensagem',
      value: 'Digite aqui',
      cancelButton: 'Cancel',
      acceptButton: 'Enviar',
    }).afterClosed().subscribe((newValue: string) => {
      if (newValue.length <= 144 && newValue.length > 0)
      {
        let mensagens = {departamento:{id:this.id}, mensagem:{texto:null} };
        mensagens.mensagem.texto = newValue;
        this.mensagemService.novaMensagem(mensagens).subscribe(() =>
        {
          this.openSnackBar('Mensagem enviada','Sucesso');
         this.mensagemService.listarMensagens(this.page -1, this.size, this.order).subscribe(mensagens =>
                 {
                  this.mensagens = mensagens;
                  this.total = this.mensagens.totalElements;
                 },
              erro =>
              {
                console.log(erro);
                this.openSnackBar(erro._body , 'Erro!');
              });
        });

      } else {
        this.openSnackBar('Mensagem somente até 144 caracteres','erro');
        this.openPrompt();
      }
    });
  }
   change(event: IPageChangeEvent): void
  {
       this.page = event.page.valueOf();
       this.size = event.pageSize.valueOf();
       this.mensagemService.listarMensagens(this.page -1, this.size, this.order).subscribe(mensagens =>
                 {
                  this.mensagens = mensagens;
                  this.total = this.mensagens.totalElements;
                 },
              erro =>
              {
                console.log(erro);
                this.openSnackBar(erro._body , 'Erro!');
              });
       this.router.navigate(['/mensagens'],
       {queryParams: {'page': this.page}});

  }
  }

