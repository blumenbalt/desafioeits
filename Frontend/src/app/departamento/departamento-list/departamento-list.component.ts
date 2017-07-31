import { PageRequest } from './../../PageRequest';
import { UsuarioService } from 'app/usuario/usuario.service';
import { MdSnackBar } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartamentoService } from './../departamento.service';
import { ITdDataTableColumn, TdDialogService, IPageChangeEvent, ITdDataTableSortChangeEvent, TdDataTableSortingOrder } from '@covalent/core';
import { Component, OnInit, ViewContainerRef } from '@angular/core';

@Component({
  selector: 'app-departamento-list',
  templateUrl: './departamento-list.component.html',
  styleUrls: ['./departamento-list.component.css']
})
export class DepartamentoListComponent implements OnInit
{



  ngOnInit()
  {
  }

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
  departamentos: PageRequest = new PageRequest();

  /**
   *
   */
  total: Number;

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
  sortBy: String = "";

  /**
   *
   */
  filter: String = "";

  /**
   *
   */
  columns: ITdDataTableColumn[] = [
    { name: 'nome', label: 'Nome do departamento', sortable: true },
    { name: 'descricao', label: 'Descrição', sortable: true },
    { name: 'quantidadeDeUsuarios', label: 'Quantidade de Usuários' },
    { name: 'opcao', label: 'Opções', tooltip: 'detalhar e editar' },
  ];

  /*------------------------------------------------------------------------
     *
     * 							CONSTRUTOR
     *
     *-----------------------------------------------------------------------*/
  constructor(
    public usuarioService: UsuarioService,
    private _dialogService: TdDialogService,
    public _viewContainerRef: ViewContainerRef,
    public snackBar: MdSnackBar,
    public departamentoService: DepartamentoService,
    private router: Router,
    private route: ActivatedRoute)
  {
    usuarioService.usuarioAtual().subscribe(usuario =>
    {
      this.usuarioAtual = usuario;
    },
      erro => console.log(erro));

    //           LISTAGEM DE DEPARTAMENTOS
    this.departamentoService.listarTodosDepartamentos(this.page - 1, this.size).subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    })
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
   *   DELETAR DEPARTAMENTO
   * @param id
   */
  openConfirm(id): void
  {
    this._dialogService.openConfirm(
      {
        message: 'Deseja excluir o departamento ?',
        disableClose: false,
        viewContainerRef: this._viewContainerRef,
        title: 'Excluir departamento',
        cancelButton: 'Não',
        acceptButton: 'Sim',
      }).
      afterClosed().subscribe((accept: boolean) =>
      {
        if (accept)
        {
          this.departamentoService.deleteDepartamento(id).subscribe(() =>
          {
            this.departamentoService.listarTodosDepartamentos(this.page - 1, this.size).subscribe(departamentos =>
            {
              this.departamentos = departamentos;
              this.total = departamentos.totalElements;
            })
            this.openSnackBar('Departamento deletado', 'Sucesso!');
          },
            erro =>
            {
              console.log(erro);
              this.openSnackBar('Erro ao deletar o departamento', 'Erro!');
            }
          );
        }
        else
        {

        }
      });
  }

  /**
   *  LISTAR DEPARTAMENTOS E FILTRAR POR NOMES
   * @param textSearch
   */
  filtrar(textSearch: String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.departamentoService.listarTodosDepartamentos(this.page - 1, this.size).subscribe(departamentos =>
      {
        this.departamentos = departamentos;
        this.total = departamentos.totalElements;
      })
    }
    else
    {
      this.departamentoService.listarDepartamentoPorNome(0, this.size, this.property, this.order, this.filter).subscribe(departamentos =>
      {
        this.departamentos = departamentos;
        this.total = departamentos.totalElements;
      }, erro =>
          console.log(erro))
    }
  }


  /**
   *  MUDAR PÁGINA
   * @param event
   */
  change(event: IPageChangeEvent): void
  {
    this.page = event.page.valueOf();
    this.size = event.pageSize.valueOf();
    this.departamentoService.listarTodosDepartamentos(this.page - 1, this.size).subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    })
    this.router.navigate(['/departamentos'],
      { queryParams: { 'page': this.page } });

  }


  /**
   * MUDAR ORDEM DE ORDENAÇÃO
   * @param sortEvent
   */
  sortEvent(sortEvent: ITdDataTableSortChangeEvent): void
  {
    this.sortBy = sortEvent.name;
    this.order = sortEvent.order === TdDataTableSortingOrder.Ascending ? 'DESC' : 'ASC';
    this.property = sortEvent.name;
    this.filter = "null";
    this.departamentoService.listarDepartamentoPorNome(this.page - 1, this.size, this.property, this.order, this.filter).subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    }, erro =>
        console.log(erro))

  }

}
