import { MensagemService } from './mensagem/mensagem.service';
import { routing } from './app.routing';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CovalentDynamicFormsModule } from '@covalent/dynamic-forms';
import { CovalentHighlightModule } from '@covalent/highlight';
import { CovalentHttpModule } from '@covalent/http';
import { CovalentMarkdownModule } from '@covalent/markdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MdIconModule } from '@angular/material';
import { MdListModule} from '@angular/material';
import { MdSlideToggleModule } from '@angular/material';
import { MdCardModule } from '@angular/material';
import { MdTooltipModule } from '@angular/material';
import { MdButtonModule } from '@angular/material';
import { MdInputModule } from '@angular/material';
import { MdSidenavModule } from '@angular/material';
import { MdSnackBarModule } from '@angular/material';
import { MdToolbarModule, MdSelectModule, MdTabsModule, MdButtonToggleModule, MdCheckboxModule, MdDialogModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CovalentDataTableModule } from '@covalent/core';
import { CovalentSearchModule } from '@covalent/core';
import 'hammerjs';


import {
  CovalentLayoutModule, CovalentStepsModule, /*, any other modules */
  CovalentDialogsModule,
  CovalentCommonModule,
  CovalentExpansionPanelModule,
  CovalentLoadingModule,
  CovalentPagingModule,
  CovalentFileModule,
} from '@covalent/core';
import { UsuarioListComponent } from './usuario/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './usuario/usuario-form/usuario-form.component';
import { UsuarioDetailComponent } from './usuario/usuario-detail/usuario-detail.component';
import { HomeComponent } from './home/home.component';
import { DepartamentoService } from './departamento/departamento.service';
import { UsuarioService } from "app/usuario/usuario.service";
import { DepartamentoFormComponent } from './departamento/departamento-form/departamento-form.component';
import { DepartamentoListComponent } from './departamento/departamento-list/departamento-list.component';
import { DepartamentoDetailComponent } from './departamento/departamento-detail/departamento-detail.component';
import { MensagemComponent } from './mensagem/mensagem.component';
import { AuthService } from "app/auth.service";
import { AcessoNegadoComponent } from './acesso-negado/acesso-negado.component';
import { DepartamentoFormConsultaComponent } from './departamento/departamento-form-consulta/departamento-form-consulta.component';

// (optional) Additional Covalent Modules imports

@NgModule({
  declarations: [
    AppComponent,
    UsuarioListComponent,
    UsuarioFormComponent,
    UsuarioDetailComponent,
    HomeComponent,
    DepartamentoFormComponent,
    DepartamentoListComponent,
    DepartamentoDetailComponent,
    MensagemComponent,
    AcessoNegadoComponent,
    DepartamentoFormConsultaComponent,

  ],
  imports: [
    CovalentFileModule,
    MdDialogModule,
    CovalentPagingModule,
    MdCheckboxModule,
    MdButtonToggleModule,
    CovalentExpansionPanelModule,
    CovalentLoadingModule,
    ReactiveFormsModule,
    MdSelectModule,
    CovalentSearchModule,
    CovalentDataTableModule,
    MdSnackBarModule,
    MdInputModule,
    MdSlideToggleModule,
    MdCardModule,
    MdTooltipModule,
    MdSidenavModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    CovalentLayoutModule,
    CovalentStepsModule,
    MdIconModule,
    MdToolbarModule,
    // (optional) Additional Covalent Modules imports
    CovalentHttpModule.forRoot(),
    CovalentHighlightModule,
    CovalentMarkdownModule,
    CovalentDynamicFormsModule,
    MdListModule,
    MdButtonModule,
    CovalentDialogsModule,
    routing,
    MdTabsModule,
    CovalentCommonModule,
    CovalentExpansionPanelModule
  ],
  providers: [UsuarioService,DepartamentoService, MensagemService, AuthService],
  bootstrap: [AppComponent],
  entryComponents:
  [
    DepartamentoFormConsultaComponent
  ]
})
export class AppModule { }
