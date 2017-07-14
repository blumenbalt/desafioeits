import { PageRequest } from './../../PageRequest';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { MdSnackBar } from '@angular/material';
import { ITdDataTableColumn, TdDataTableSortingOrder, IPageChangeEvent, ITdDataTableSortChangeEvent } from '@covalent/core';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})


export class UsuarioListComponent implements OnInit {
  ngOnInit() {

  }

  //           CONSTRUCTOR E INJECTS
  usuarioAtual: Object;
  usuarios: PageRequest  = new PageRequest();
  total: Number;
  page: number = 1;
  size: number = 5;
  order: String ="ASC";
  property: String="nome";
  sortBy : String ="";
  filter : String = "";
   columns: ITdDataTableColumn[] = [
    { name: 'nome', label: 'Nome do usuário', sortable: true},
    { name: 'ativo', label:'Status', tooltip:'é possível alterar'},
    { name: 'opcao', label:'Opções', tooltip:'detalhar e editar'},
  ];

  constructor(public usuarioService: UsuarioService,
   public snackBar: MdSnackBar, private router: Router,private route: ActivatedRoute
   )
   { //        USUARIO ATUAL LOGADO
     usuarioService.usuarioAtual().subscribe(usuario =>
      {
        this.usuarioAtual = usuario;
      },
      erro => console.log(erro));

     //           LISTAGEM DE USUARIOS
        this.usuarioService.listarUsuario(this.page -1, this.size).subscribe(usuarios=>{
        this.usuarios=usuarios;
        this.total = usuarios.totalElements;
      })
    }
//             MÉTODOS
//
/**
 *
 */


    //           LISTAGEM DE USUARIOS

 listarUsuarios(textSearch : String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.usuarioService.listarUsuario(this.page -1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
        console.log(erro))
    }
    else
    {
      this.usuarioService.listarUsuarioPorNome(0 , this.size , this.property ,this.order, this.filter).subscribe(res =>
      {
        this.total = this.usuarios.totalElements;
        this.usuarios = res;
      }, erro =>
        console.log(erro))
    }
  }

    change(event: IPageChangeEvent): void
  {
       this.page = event.page.valueOf();
       this.size = event.pageSize.valueOf();
       this.usuarioService.listarUsuario(this.page -1, this.size).subscribe(usuarios=>{
        this.usuarios=usuarios;
        this.total = usuarios.totalElements;
      })
       this.router.navigate(['/usuarios'],
       {queryParams: {'page': this.page}});

  }

  sortEvent(sortEvent: ITdDataTableSortChangeEvent): void
  {
    this.sortBy = sortEvent.name;
    this.order = sortEvent.order === TdDataTableSortingOrder.Ascending ? 'DESC' : 'ASC';
    this.property= sortEvent.name;
    this.filter="null";
    this.usuarioService.listarUsuarioPorNome(this.page -1 , this.size , this.property ,this.order, this.filter ).subscribe(usuarios=>
    {
      this.usuarios = usuarios;
      this.total = usuarios.totalElements;
    })

  }


    /**
     *                 SNACKBAR
     */
          openSnackBar(msg, action)
      {
        this.snackBar.open(msg, action,
        {
          duration: 10000
        });
      }


  /**
   *          ATIVAR USUÁRIO
   */
  ativar(event, usuario): void
  {
      this.usuarioService.ativarUsuario(usuario).subscribe(() =>
      {
        this.openSnackBar('Usuário ativado', 'Sucesso');
       this.usuarioService.listarUsuario(this.page -1, this.size).subscribe(usuarios=>{
        this.usuarios=usuarios;
        this.total = usuarios.totalElements;
      })
      },

        erro =>
        {
          this.openSnackBar('Não foi possível ativar o usuário', 'Erro');
         console.log(erro);
        }
         );
   }

  /**
   *          DESATIVAR USUÁRIO
   */
  desativar(event, usuario): void
  {
      this.usuarioService.desativarUsuario(usuario).subscribe(() =>
      {
        this.openSnackBar('Usuário desativado', 'Sucesso');
         this.usuarioService.listarUsuario(this.page -1, this.size).subscribe(usuarios=>{
        this.usuarios=usuarios;
        this.total = usuarios.totalElements;
      })
      },

        erro =>
        {
          this.openSnackBar(erro._body, 'Erro');
         console.log(erro);
        }
        );
    }

}
