import { Router, ActivatedRoute } from '@angular/router';
import { MdSnackBar, MdDialogRef, MD_DIALOG_DATA } from '@angular/material';
import { UsuarioService } from 'app/usuario/usuario.service';
import { ITdDataTableColumn, IPageChangeEvent, ITdDataTableSortChangeEvent, TdDataTableSortingOrder } from '@covalent/core';
import { PageRequest } from './../../PageRequest';
import { Component, OnInit, Injectable, Inject } from '@angular/core';
import { DepartamentoService } from "app/departamento/departamento.service";

@Component({
  selector: 'app-departamento-form-consulta',
  templateUrl: './departamento-form-consulta.component.html',
  styleUrls: ['./departamento-form-consulta.component.css']
})
@Injectable()

export class DepartamentoFormConsultaComponent implements OnInit
{

  /*------------------------------------------------------------------------
     *
     * 							ATRIBUTOS
     *
     *-----------------------------------------------------------------------*/

  /**
   *
   */
  id;

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
  sortBy: String = "";

  /**
   *
   */
  filter: String = "";

  /**
   *
   */
  property: String = "nome";

  /**
   *
   */
  columns: ITdDataTableColumn[] =
  [
    { name: 'nome', label: 'Nome do departamento', sortable: true },
    { name: 'vincular', label: 'Vincular' },
  ];

  /*------------------------------------------------------------------------
	 *
	 * 							CONSTRUCTOR
	 *
	 *-----------------------------------------------------------------------*/
  constructor
    (
    public usuarioService: UsuarioService,
    public snackBar: MdSnackBar,
    public departamentoService: DepartamentoService,
    private router: Router,
    private activatedRouter: ActivatedRoute,
    public mdDialogRef: MdDialogRef<DepartamentoFormConsultaComponent>,
    @Inject(MD_DIALOG_DATA) public data: Number
    )
  {
    this.id = data;
    usuarioService.usuarioAtual().subscribe(usuario =>
    {
      this.usuarioAtual = usuario;
    },
      erro => console.log(erro));
    this.departamentoService.listarDepartamentoTirandoId(this.id, this.page - 1, this.size).subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    })
  }

  /*------------------------------------------------------------------------
     *
     * 							MÉTODOS
     *
     *-----------------------------------------------------------------------*/

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
   *
   * @param textSearch
   */
  filtrar(textSearch: String)
  {
    this.filter = textSearch;
    if ((textSearch == '') || (textSearch == undefined))
    {
      this.departamentoService.listarDepartamentoTirandoId(this.id, this.page - 1, this.size).subscribe(departamentos =>
      {
        this.departamentos = departamentos;
        this.total = departamentos.totalElements;
      })
    }
    else
    {
      this.departamentoService.listarDepartamentoPorNomeTirandoId(this.id, 0, this.size, this.property, this.order, this.filter)
      .subscribe(departamentos =>
      {
        this.departamentos = departamentos;
        this.total = departamentos.totalElements;
      })
    }
  }

  /**
   *
   * @param event
   */
  change(event: IPageChangeEvent): void
  {
    this.page = event.page.valueOf();
    this.size = event.pageSize.valueOf();
    this.departamentoService.listarDepartamentoTirandoId(this.id, this.page - 1, this.size).subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    })


  }

  /**
   *
   * @param sortEvent
   */
  sortEvent(sortEvent: ITdDataTableSortChangeEvent): void
  {
    this.sortBy = sortEvent.name;
    this.order = sortEvent.order === TdDataTableSortingOrder.Ascending ? 'DESC' : 'ASC';
    this.property = sortEvent.name;
    this.filter = "null";
    this.departamentoService.listarDepartamentoPorNomeTirandoId(this.id, 0, this.size, this.property, this.order, this.filter)
    .subscribe(departamentos =>
    {
      this.departamentos = departamentos;
      this.total = departamentos.totalElements;
    })

  }

  /**
   *
   * @param evento
   * @param id
   */
  vincularDepartamento(evento, id)
  {
    this.departamentoService.vincularDepartamento(this.id, id).subscribe(() =>
    {

      this.openSnackBar('Departamento vinculado', '');
      this.close();
    }),
      erro =>
      {
        console.log(erro);
        this.openSnackBar('Não foi possível vincular departamento', 'Erro');
      }
  }

  ngOnInit()
  {
  }

  /**
   *
   */
  close()
  {
    this.mdDialogRef.close();
  }


}
