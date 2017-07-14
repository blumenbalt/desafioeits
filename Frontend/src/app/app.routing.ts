import { AcessoNegadoComponent } from './acesso-negado/acesso-negado.component';
import { MensagemComponent } from './mensagem/mensagem.component';
import { DepartamentoFormComponent } from './departamento/departamento-form/departamento-form.component';
import { DepartamentoDetailComponent } from './departamento/departamento-detail/departamento-detail.component';
import { DepartamentoListComponent } from './departamento/departamento-list/departamento-list.component';
import { UsuarioFormComponent } from './usuario/usuario-form/usuario-form.component';
import { Component, ModuleWithProviders } from '@angular/core';
import { HomeComponent } from './home/home.component';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioDetailComponent } from './usuario/usuario-detail/usuario-detail.component';
import { UsuarioListComponent } from './usuario/usuario-list/usuario-list.component';
import { AuthService } from './auth.service';



const APP_ROUTES: Routes =
  [
    { path: '', component: HomeComponent },

    { path: 'usuarios', component: UsuarioListComponent },
    { path: 'usuarios-detalhar/:id', component: UsuarioDetailComponent },
    { path: 'usuarios-novo', component: UsuarioFormComponent, canActivate: [AuthService] },
    { path: 'usuarios-alterar/:id', component: UsuarioFormComponent, canActivate: [AuthService] },

    { path: 'departamentos', component: DepartamentoListComponent},
    { path: 'departamentos-detalhar/:id', component: DepartamentoDetailComponent },
    { path: 'departamentos-novo', component: DepartamentoFormComponent },
    { path: 'departamentos-alterar/:id', component: DepartamentoFormComponent },

    { path: 'mensagens', component: MensagemComponent},
    { path: 'acesso-negado', component: AcessoNegadoComponent}


  ];
export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES, {useHash:true});
