import { MensagemService } from './../mensagem/mensagem.service';
import { UsuarioService } from 'app/usuario/usuario.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    /*------------------------------------------------------------------------
     *
     * 							ATRIBUTOS
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   */
  usuarioAtual: Object;

  /**
   *
   */
  mensagem : Object = {};

    /*------------------------------------------------------------------------
     *
     * 							CONSTRUCTOR
     *
     *-----------------------------------------------------------------------*/
  constructor(public usuarioService:UsuarioService, public mensagemService:MensagemService) {

      usuarioService.usuarioAtual().subscribe(usuario =>
      {
        this.usuarioAtual = usuario;
      },
      erro => console.log(erro));

      mensagemService.listarMensagem().subscribe(mensagem =>
      {
        this.mensagem = mensagem;
      },
      erro => console.log(erro));

   }

  ngOnInit() {
  }

}
