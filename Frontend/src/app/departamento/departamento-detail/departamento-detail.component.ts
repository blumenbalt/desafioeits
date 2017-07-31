import { MdSnackBar } from '@angular/material';
import { TdDialogService, ITdDataTableColumn, IPageChangeEvent } from '@covalent/core';
import { UsuarioService } from 'app/usuario/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartamentoService } from './../departamento.service';
import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { PageRequest } from "app/PageRequest";

@Component({
  selector: 'app-departamento-detail',
  templateUrl: './departamento-detail.component.html',
  styleUrls: ['./departamento-detail.component.css']
})
export class DepartamentoDetailComponent implements OnInit {
  ngOnInit(): void {

  }

  /*------------------------------------------------------------------------
	 *
	 * 							ATRIBUTOS
	 *
	 *-----------------------------------------------------------------------*/

  /**
   *
   */
  usuarios: PageRequest = new PageRequest();

  /**
   *
   */
  usuarioAtual: Object;

  /**
   *
   */
  departamento: Object = {};

  /**
   *
   */
  fadeDiv: boolean = true;

  /**
   *
   */
  fadeDivi: boolean = true;

  /**
   *
   */
  departamentoId: number;

  /**
   *
   */
  selectedValue: string;

  /**
   *
   */
  page: number = 1;

  /**
   *
   */
  size: number = 5;

  /**
   *
   */
  order: String = "ASC";

  /**
   *
   */
  property: String = "nome";

  /**
   *
   */
  total: Number;

  /**
   *
   */
  sortBy: String = "";

  /**
   *
   */
  filter: String = "";

  /**
   *
   */
  opcoes =
  [
    { value: 'todos', viewValue: 'Todos' },
    { value: 'departamento', viewValue: 'Usuário do departamento' },
    { value: 'vincular', viewValue: 'Usuário para vincular' }
  ];

  /**
   *
   */
  papeis =
  [
    { value: 'SUPERIOR', viewValue: 'Superior' },
    { value: 'COMUM', viewValue: 'Comum' }
  ];

/**
 *
 * @param usuarioService
 * @param departamentoService
 * @param router
 * @param activatedRouter
 * @param _dialogService
 * @param _viewContainerRef
 * @param snackBar
 */
  /*------------------------------------------------------------------------
	 *
	 * 							CONSTRUCTOR
	 *
	 *-----------------------------------------------------------------------*/
  constructor(
    public usuarioService: UsuarioService,
    public departamentoService: DepartamentoService,
    public router: Router,
    public activatedRouter: ActivatedRoute,
    private _dialogService: TdDialogService,
    public _viewContainerRef: ViewContainerRef,
    public snackBar: MdSnackBar, )
    {
    activatedRouter.params.subscribe(params =>
    {

      let id = params['id'];
      this.departamentoId = id;
      this.departamentoService.detalharDepartamento(id).subscribe(departamento => this.departamento = departamento, erro => console.log(erro));
    });

    this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
    {
      this.usuarios = res;
      this.total = this.usuarios.totalElements;
    }, erro =>
        console.log(erro))

    usuarioService.usuarioAtual().subscribe(usuario =>
    {
      this.usuarioAtual = usuario;
    },
      erro => console.log(erro));


  }

  /*------------------------------------------------------------------------
	 *
	 * 							MÉTODOS
	 *
	 *-----------------------------------------------------------------------*/


  /**
   *     LISTAR TODOS OS ATIVOS SEM OU COM FILTRO
   * @param textSearch
   */
  filtrarTodos(textSearch: String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
          console.log(erro))
    }
    else
    {
      this.usuarioService.listarUsuarioPorNome(0, this.size, this.property, this.order, this.filter).subscribe(res =>
      {
        this.total = this.usuarios.totalElements;
        this.usuarios = res;
      }, erro =>
          console.log(erro))
    }
  }


  /**
   *  TROCAR DE PÁGINA
   * @param event
   */
  change(event: IPageChangeEvent): void
  {
    this.page = event.page.valueOf();
    this.size = event.pageSize.valueOf();
    this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
    {
      this.usuarios = res;
      this.total = this.usuarios.totalElements;
    }, erro =>
        console.log(erro))
    this.router.navigate(['/departamentos-detalhar/' + this.departamentoId],
      { queryParams: { 'page': this.page } });

  }

  /**
   *
   * @param msg
   * @param action
   */
  openSnackBar(msg, action)
  {
    this.snackBar.open(msg, action,
      {
        duration: 3000,
      });
  }

  /**
   *   VINCULA USUÁRIO NO DEPARTAMENTO
   * @param event
   * @param usuario
   */
  vincular(event, usuario): void
  {
    this.usuarioService.vincularUsuario(usuario, this.departamentoId).subscribe(() =>
    {
      this.openSnackBar('Usuário vinculado', 'Sucesso');

      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      })

    },

      erro =>
      {
        this.openSnackBar('Não foi possível vinculado o usuário', 'Erro');
        console.log(erro);
      }
    );
  }

  /**
   *     DESVINCULAR USUÁRIO DO DEPARTAMENTO
   * @param event
   * @param usuario
   */
  desvincular(event, usuario): void
  {
    this.usuarioService.desvincularUsuario(usuario).subscribe(() =>
    {
      this.openSnackBar('Usuário desvinculado', 'Sucesso');
      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
          console.log(erro))
    },

      erro =>
      {
        this.openSnackBar('Não foi possível desvinculado o usuário', 'Erro');
        console.log(erro);
      }
    );
  }


  /**
   *   PROMOVE O USUÁRIO NO DEPARTAMENTO
   * @param id
   * @param papel
   */
  promoverUsuario(id, papel)
  {
    this.usuarioService.promoverUsuario(id, papel).subscribe(res =>
    {
      erro => console.log(erro);
      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
          console.log(erro))
    });
    this.openSnackBar('Papel usuário alterado', '');
  }


  /**
   *   FILTRA USUÁRIOS DO DEPARTAMENTO
   * @param textSearch
   */
  filtrarNoDepartamento(textSearch: String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
          console.log(erro))
    }
    else
    {
      this.usuarioService.listarPorDepartamentoPorNome(this.departamentoId, 0, this.size, this.property, this.order, this.filter).subscribe(res =>
      {
        this.total = this.usuarios.totalElements;
        this.usuarios = res;
      }, erro =>
          console.log(erro))
    }
  }

  /**
   *      FILTRA USUÁRIOS PARA VINCULAR
   * @param textSearch
   */
  filtrarParaVincular(textSearch: String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.usuarioService.listarAtivos(this.page - 1, this.size).subscribe(res =>
      {
        this.usuarios = res;
        this.total = this.usuarios.totalElements;
      }, erro =>
          console.log(erro))
    }
    else
    {
      this.usuarioService.listarParaVincularPorNome(0, this.size, this.property, this.order, this.filter).subscribe(res =>
      {
        this.total = this.usuarios.totalElements;
        this.usuarios = res;
      }, erro =>
          console.log(erro))
    }
  }



}
