package projeto.domain.entity;

import org.junit.Assert;
import org.junit.Test;

import projeto.domain.entity.Perfil;
import projeto.domain.entity.Usuario;

public class UsuarioTeste 
{

	/*------------------------------------------------------------------------
	 * 
	 * 							TESTES
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	public void getAuthoritiesPassar()
	{
		final Usuario usuario = new Usuario();
		usuario.setPerfil( Perfil.ROLE_ADMINISTRADOR  );
		
		Assert.assertNotNull( usuario.getAuthorities() );
		Assert.assertTrue( usuario.getAuthorities().contains( Perfil.ROLE_ADMINISTRADOR ) );
	}
	
	/**
	 * 
	 */
	@Test
	public void validarSenhaPassar()
	{
		final Usuario usuario = new Usuario();
		usuario.setSenha("12345678");
		usuario.setConfirmarSenha("12345678");
		Assert.assertTrue(usuario.validar());
	}
	
	/**
	 * 
	 */
	@Test
	public void validarSenhaNaoPassar()
	{
		final Usuario usuario = new Usuario();
		usuario.setSenha("12345678");
		usuario.setConfirmarSenha("87654321");
		Assert.assertFalse(usuario.validar());
	}
	
	/**
	 * 
	 */
	@Test
	public void departamentoNulo()
	{
		final Usuario usuario = new Usuario();
		usuario.unsetDepartamento();
		Assert.assertTrue(usuario.getDepartamento() == null);
	}
}
