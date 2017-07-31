package projeto.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import projeto.config.IntegrationTests;
import projeto.domain.entity.Departamento;
import projeto.domain.entity.Mensagem;
import projeto.domain.entity.MensagemDepartamento;
import projeto.domain.entity.Usuario;
import projeto.domain.repository.IMensagemRepository;


@Sql({"dataset/mensagem/mensagem_data.sql",
	"dataset/departamento/departamento_data.sql",
		"dataset/usuario/usuario_data.sql",
		"dataset/mensagemDepartamento/mensagemDepartamento_data.sql",})
public class DepartamentoServiceTest extends IntegrationTests
{
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	
	@Autowired
	 UsuarioService usuarioService;
	
	@Autowired
	DepartamentoService departamentoService;
	
	@Autowired
	IMensagemRepository mensagemRepository;
	
	
	/*------------------------------------------------------------------------
	 * 
	 * 							TESTES
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 	
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test
	public void inserirPassar()
	{
		Departamento departamento = new Departamento ();
		departamento.setNome("departamento");
		departamento.setDescricao("sdgdfgdfg");
		departamentoService.insert(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test(expected = IllegalArgumentException.class)
	public void inserirNaoPassar()
	{
		Departamento departamento = new Departamento ();
		departamento.setNome("departamento financeiro");
		departamento.setDescricao("ASDGSDGSDG");
		departamentoService.insert(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test(expected = IllegalArgumentException.class)
	public void inserirNaoPassarNulo()
	{
		Departamento departamento = new Departamento ();
		departamentoService.insert(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test
	public void alterarPassar()
	{
		Departamento departamento = departamentoService.detail(new Long (1));
		departamento.setNome("sdfsdf sdfsdf");
		departamentoService.update(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test(expected = IllegalArgumentException.class)
	public void alterarNaoPassar()
	{
		Departamento departamento = departamentoService.detail(new Long (1));
		departamento.setNome("departamento financeiro");
		departamentoService.update(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test(expected = IllegalArgumentException.class)
	public void alterarNaoPassarNulo()
	{
		Departamento departamento = departamentoService.detail(new Long (1));
		departamentoService.update(departamento);
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test
	public void detalharPassar()
	{
		departamentoService.detail(new Long (1));
	}
	
	/**
	 * 
	 */
	@Sql({
		"dataset/departamento/departamento_data.sql"
	})
	@Test(expected = IllegalArgumentException.class)
	public void detalharNaoPassar()
	{
		departamentoService.detail(new Long (9));
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
	public void deletarPassar()
	{
		departamentoService.delete(new Long (3));
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
	public void deletarNaoPassar()
	{
		departamentoService.delete(new Long (10));
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
		departamentoService.link(new Long (1), new Long (2));
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
		departamentoService.link(new Long (1), new Long (5));
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
	public void vincularNaoPassarNulo()
	{
		departamentoService.link(new Long (1), null);
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
	public void desvincularPassar()
	{
		departamentoService.unlink(new Long (1));
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
	public void desvincularNaoPassar()
	{
		departamentoService.unlink(new Long (4));
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void inserirMensagemPassar()
	{
		
		MensagemDepartamento mensagemDepartamento = new MensagemDepartamento();
		Departamento departamento = departamentoService.detail(new Long (1));
		Usuario usuario = usuarioService.detail(new Long (1));
		Mensagem mensagem = mensagemRepository.findOne(new Long(1));
		mensagemDepartamento.setUsuario(usuario);
		mensagemDepartamento.setMensagem(mensagem);
		mensagemDepartamento.setDepartamento(departamento);
		departamentoService.insertMessageDepartament(mensagemDepartamento);
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test(expected = IllegalArgumentException.class)
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void inserirMensagemNaoPassar()
	{
		
		MensagemDepartamento mensagemDepartamento = new MensagemDepartamento();
		Departamento departamento = departamentoService.detail(new Long (7));
		Usuario usuario = usuarioService.detail(new Long (7));
		Mensagem mensagem = mensagemRepository.findOne(new Long(7));
		mensagemDepartamento.setUsuario(usuario);
		mensagemDepartamento.setMensagem(mensagem);
		mensagemDepartamento.setDepartamento(departamento);
		departamentoService.insertMessageDepartament(mensagemDepartamento);
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test(expected = IllegalArgumentException.class)
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void inserirMensagemNaoPassarNulo()
	{
		
		MensagemDepartamento mensagemDepartamento = new MensagemDepartamento();
		mensagemDepartamento.setUsuario(usuarioService.detail(new Long (7)));
		mensagemDepartamento.setMensagem(mensagemRepository.findOne(new Long(7)));
		mensagemDepartamento.setDepartamento(departamentoService.detail(new Long (7)));
		departamentoService.insertMessageDepartament(mensagemDepartamento);
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void mostrarMensagem()
	{
		MensagemDepartamento mensagem = departamentoService.showMessage();
		Assert.assertEquals(mensagem.getId(), new Long(5));
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void deletarMensagemPassar()
	{
		departamentoService.deleteMessageDepartament(new Long (1));
	}
	
	/**
	 * 
	 */
	@WithUserDetails("admin@admin.com")
	@Test(expected = IllegalArgumentException.class)
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void deletarMensagemNaoPassar()
	{
		departamentoService.deleteMessageDepartament(new Long (7));
	}
	

	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listarDepartamentos()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByName(0, 5, "nome", "ASC", "");
		Assert.assertEquals(departamentos.getContent().size(), 3);
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listar2Departamentos()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByName(0, 5, "nome", "ASC", "adm");
		Assert.assertEquals(departamentos.getContent().get(0).getNome(), "departamento dos administradores");
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listar0Departamentos()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByName(0, 5, "nome", "ASC", "zzzzz");
		Assert.assertEquals(departamentos.getContent().isEmpty(), true);
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listarDepartamentosMenosSelecionado()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByNameNotId(new Long(1), 0, 5, "nome", "ASC", "");
		Assert.assertEquals(departamentos.getContent().size(), 2);
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listar1DepartamentoMenosSelecionado()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByNameNotId(new Long(1), 0, 5, "nome", "ASC", "sup");
		Assert.assertEquals(departamentos.getContent().get(0).getNome(), "departamento suporte");
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"dataset/mensagem/mensagem_data.sql",
		"dataset/departamento/departamento_data.sql",
			"dataset/usuario/usuario_data.sql",
			"dataset/mensagemDepartamento/mensagemDepartamento_data.sql"
	})
	public void listar0DepartamentoMenosSelecionado()
	{
		Page <Departamento> departamentos = departamentoService.listDepartamentosByNameNotId(new Long(1), 0, 5, "nome", "ASC", "xcvxcv");
		Assert.assertEquals(departamentos.getContent().isEmpty(), true);
	}
	

}
