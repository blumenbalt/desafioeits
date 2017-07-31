package projeto.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import projeto.config.IntegrationTests;
import projeto.domain.entity.Perfil;
import projeto.domain.entity.Usuario;

@Sql({"dataset/departamento/departamento_data.sql",
		"dataset/usuario/usuario_data.sql"})
public class UsuarioServiceTest extends IntegrationTests
{
		
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	
		@Autowired
		 UsuarioService usuarioService;
		
		/*------------------------------------------------------------------------
		 * 
		 * 							SERVIÇOS
		 * 
		 *-----------------------------------------------------------------------*/
		
		/**
		 * 
		 */
		@Test(expected = AuthenticationCredentialsNotFoundException.class)
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void testeAutenticarFalha() 
		{
			Usuario usuario = new Usuario();
			usuarioService.insert(usuario);
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void inserirPassar()
		{
			Usuario usuario = new Usuario();
			usuario.setNome("nome teste");
			usuario.setEmail("email@teste.com");
			usuario.setSenha("admin");
			usuario.setPerfil(Perfil.ROLE_ADMINISTRADOR);
			usuario.setAtivo();
			usuario.setPapel(null);
			usuario.setDepartamento(null);
			usuarioService.insert(usuario);
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test(expected = IllegalArgumentException.class)
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void inserirNaoPassar()
		{
			Usuario usuario = new Usuario();
			usuario.setEmail("emailaleatorio@email.com");
			usuarioService.insert(usuario);
			
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void desativarNaoPassar()
		{
			usuarioService.deactivate(new Long(1));	
			Assert.assertTrue(usuarioService.detail(new Long(1)).isAtivo());	
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void desativarPassar()
		{
			usuarioService.deactivate(new Long(2));	
			Assert.assertFalse(usuarioService.detail(new Long(2)).isAtivo());	
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void detalharPassar()
		{
			Usuario usuario = usuarioService.detail(new Long(2));
			Assert.assertNotNull(usuario);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void detalharNaoPassar()
		{
			Usuario usuario = usuarioService.detail(new Long(7));
			Assert.assertNull(usuario);
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void atualizarPassar()
		{
			Usuario usuario = usuarioService.detail(new Long(1));
			usuario.setNome("teste1");
			usuarioService.update(usuario);
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test(expected = IllegalArgumentException.class)
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void atualizarNaoPassar()
		{
			Usuario usuario = usuarioService.detail(new Long(1));
			usuario.setNome(null);
			usuarioService.update(usuario);
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void vincularPassar()
		{
			usuarioService.link(new Long (2), new Long (1));
		}
		
		/**
		 * 
		 */
		@WithUserDetails("admin@admin.com")
		@Test(expected = IllegalArgumentException.class)
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void vincularNaoPassar()
		{
			usuarioService.link(new Long (2), new Long (5));
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar1Usuario()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByName(0, 1, "nome", "ASC", "ad");
			Assert.assertEquals(usuarios.getContent().get(0).getEmail(),"admin@admin.com");
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar3Usuarios()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByName(0, 5, "nome", "ASC", "user");
			Assert.assertEquals(usuarios.getContent().size(), 3);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar0Usuarios()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByName(0, 5, "nome", "ASC", "zzz");
			Assert.assertEquals(usuarios.getContent().isEmpty(), true);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listarTodosUsuarios()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByName(0, 5, "nome", "ASC", "");
			Assert.assertEquals(usuarios.getContent().size(), 4);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listarTodosUsuariosAtivos()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosAtivos(0, 5, "nome", "ASC");
			Assert.assertEquals(usuarios.getContent().size(), 3);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar1UsuarioAtivo()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosAtivosByName(0, 5, "nome", "ASC", "ad");
			Assert.assertEquals(usuarios.getContent().get(0).getEmail(), "admin@admin.com");
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar2UsuarioAtivo()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosAtivosByName(0, 5, "nome", "ASC", "user");
			Assert.assertEquals(usuarios.getContent().size(),2);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar0UsuarioAtivo()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosAtivosByName(0, 5, "nome", "ASC", "4");
			Assert.assertEquals(usuarios.getContent().isEmpty(),true);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listarUsuariosDepartamento()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByDepartamentoAndByName(new Long (1), 0, 5, "nome", "ASC", "");
			Assert.assertEquals(usuarios.getContent().size(),2);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar1UsuarioDepartamento()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByDepartamentoAndByName(new Long (1), 0, 5, "nome", "ASC", "3");
			Assert.assertEquals(usuarios.getContent().get(0).getNome(),"user03");
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar0UsuarioDepartamento()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosByDepartamentoAndByName(new Long (2), 0, 5, "nome", "ASC", "");
			Assert.assertEquals(usuarios.getContent().isEmpty(), true);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listarUsuariosParaVincular()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosForLinkByName(0, 5, "nome", "ASC", "");
			Assert.assertEquals(usuarios.getContent().size(), 1);
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar1UsuarioParaVincular()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosForLinkByName(0, 5, "nome", "ASC", "2");
			Assert.assertEquals(usuarios.getContent().get(0).getEmail(), "user02@email.com");
		}
		
		/**
		 * 
		 */
		@Test
		@Sql({
			"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql"
		})
		public void listar0UsuarioParaVincular()
		{
			Page<Usuario> usuarios = usuarioService.listUsuariosForLinkByName(0, 5, "nome", "ASC", "ad");
			Assert.assertEquals(usuarios.getContent().isEmpty(), true);
		}
}

