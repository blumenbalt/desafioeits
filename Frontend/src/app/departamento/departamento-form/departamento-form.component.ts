import { DepartamentoFormConsultaComponent } from './../departamento-form-consulta/departamento-form-consulta.component';
import { UsuarioService } from 'app/usuario/usuario.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MdSnackBar, MdDialogRef, MdDialog } from '@angular/material';
import { DepartamentoService } from './../departamento.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-departamento-form',
  templateUrl: './departamento-form.component.html',
  styleUrls: ['./departamento-form.component.css']
})
export class DepartamentoFormComponent {
  usuarioAtual: Object;
  departamento: Object = {};
  departamentos: Object[] = [];
  fadeDiv: boolean = true;
  id;
  nome;
  descricao;
  dialogRef: MdDialogRef<DepartamentoFormConsultaComponent>
  /**
   *
   *
   *
   *
   *
   */

  // tslint:disable-next-line:max-line-length
  constructor(public dialog: MdDialog,
   public usuarioService: UsuarioService,
    public departamentoService: DepartamentoService,
     public snackBar: MdSnackBar,
      public router: Router,
       public activatedRouter: ActivatedRoute) {
    activatedRouter.params.subscribe(params => {

      this.id = params['id'];

      this.departamentoService.detalharDepartamento(this.id).subscribe(departamento => this.departamento = departamento, erro => console.log(erro));

    });
    usuarioService.usuarioAtual().subscribe(usuario =>
      {
        this.usuarioAtual = usuario;
      },
      erro => console.log(erro));

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
   *      INSERIR departamento
   */
  inserirdepartamento(event)
  {

    this.departamentoService.formarDepartamento(this.departamento).subscribe(() => {
      this.router.navigate(['/departamentos']);
      this.openSnackBar('Departamento salvo ', 'Sucesso');
    },
      erro => {
        console.log(erro);
        this.openSnackBar(erro._body, 'Erro');
      });
  }

  dialogDepartamentos()
  {
    let dialog = this.dialog.open(DepartamentoFormConsultaComponent,
      {
        data: this.id,
        height: '480px',
        width: '800px',
      }
    )

    dialog.afterClosed().subscribe( ( result ) => {
      this.departamentoService.detalharDepartamento(this.id).subscribe(departamento => this.departamento = departamento, erro => console.log(erro));
    })
  }

  desvincularDepartamento(evento)
  {
    this.departamentoService.desvincularDepartamento(this.id).subscribe(()=>
    {
      this.openSnackBar('Departamento desvinculado','');
      this.departamentoService.detalharDepartamento(this.id).subscribe(departamento => this.departamento = departamento, erro => console.log(erro));
    }),
    erro =>
    {
      console.log(erro);
      this.openSnackBar('Não foi possível desvincular departamento', 'Erro');
    }
  }




}

