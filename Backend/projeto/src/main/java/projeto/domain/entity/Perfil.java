package projeto.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority
{
	/**
	 * 
	 *  ROLES
	 */
	
	ROLE_COLABORADOR,
	ROLE_ADMINISTRADOR;

	@Override
	public String getAuthority() 
	{
		
		return this.name();
	}
}
