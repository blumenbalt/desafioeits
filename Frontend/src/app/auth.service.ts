import { Usuario } from './usuario';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UsuarioService } from 'app/usuario/usuario.service';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router } from "@angular/router";


@Injectable()
export class AuthService implements CanActivate
{
  usuarioAtual : Usuario;

  constructor ( public usuarioService: UsuarioService, public router: Router)
  {
    usuarioService.usuarioAtual().subscribe(usuario =>
    {
      this.usuarioAtual = usuario;
    },
    erro => console.log(erro));
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | Observable<boolean>
  {
    if ( this.usuarioAtual.perfil == 'ROLE_ADMINISTRADOR' && this.usuarioAtual.ativo == true )
    {
      return true;
    }
      this.router.navigate(['/acesso-negado']);

      return false;
  }
}
