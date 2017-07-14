package projeto.application.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projeto.domain.entity.Papel;
import projeto.domain.entity.Usuario;
import projeto.domain.service.UsuarioService;

@RequestMapping("usuario")
@RestController
public class UsuarioController
{
	/**
	 * importa o serviços do usuário
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/** 
	 * insere um usuário no sistema
	 * 
	 */
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	@CrossOrigin
	@PostMapping
	public  ResponseEntity<String> inserir (@RequestBody Usuario usuario)
	{
		return this.usuarioService.insert(usuario);
	}
	
	/**
	 * 
	 * altera um usuário no sistema
	 */
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	@CrossOrigin
	@PutMapping
	public ResponseEntity<String> alterar (@RequestBody Usuario usuario)
	{
		return this.usuarioService.update(usuario);
	}
	
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	@CrossOrigin
	@PutMapping(value="/senha")
	public ResponseEntity<String> alterarSenha (@RequestBody Usuario usuario)
	{
		
		return this.usuarioService.updatePassword(usuario);
	}
	
	/**
	 * lista todos os usuários
	 */
	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/{page}/{size}", method= RequestMethod.GET)
	public Page<Usuario> listarUsuarioNome(@PathVariable int page, @PathVariable int size)
	{
		String property = "nome";
		String order = "ASC";
		Page<Usuario> usuarios = usuarioService.listUsuarios(page, size, property, order);
		return usuarios;
	}
	
	

	/**
	 * lista os usuários por um filtro selecionado
	 */
	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/{page}/{size}/{property}/{order}/{filter}", method= RequestMethod.GET)
	public Page<Usuario> listarUsuarioNome(@PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order,
		     @PathVariable String filter)
	{
		Page<Usuario> usuarios = usuarioService.listUsuariosByName(page, size, property, order, filter);
		return usuarios;
	}
	/**
	 * 
	 *ativa um usuário
	 */
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/on/{id}", method= RequestMethod.PATCH)
	public void ativar(@PathVariable Long id)
	{
		this.usuarioService.activate(id);
	}
	
	/**
	 * desativa um usuário
	 */
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/off/{id}", method= RequestMethod.PATCH)
	public ResponseEntity<String> desativar(@PathVariable Long id)
	{
		return this.usuarioService.deactivate(id);
	}
	
	/**
	 * 
	 * detalha o usuário
	 */
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/{id}", method= RequestMethod.GET)
	public Usuario detalhar(@PathVariable Long id)
	{
		return usuarioService.detail(id);
	}
	
	/**
	 * pega o usuário logado no sistema
	 * 
	 */
	@GetMapping(value="/atual")
	@CrossOrigin
	public Usuario getAtual()
	{
		return usuarioService.getCurrent();
	}
	
	@GetMapping(value="/ativos/{page}/{size}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> listarTodosAtivos(@PathVariable int page, @PathVariable int size)
	{
	String property = "nome";
	String order = "ASC";
	Page<Usuario> usuarios = usuarioService.listUsuariosAtivos(page, size, property, order);
	return usuarios;
	}
	
	@GetMapping(value="/ativos/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> listarTodosAtivosPorNome(@PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order, @PathVariable String filter
		     )
	{
		 Page <Usuario> usuarios = usuarioService.listUsuariosAtivosByName(page, size, property, order, filter);
		 return usuarios;
	}
	
	
	@GetMapping(value="/departamento/{id}/{page}/{size}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> listarUsuariosPorDepartamento(@PathVariable Long id, @PathVariable int page, @PathVariable int size)
	{
		String property = "nome";
		String order = "ASC";
		return usuarioService.listUsuariosByDepartamento(id, page, size, property, order);
		
	}
	
	@GetMapping(value="/departamento/{id}/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> listarUsuariosPorDepartamento(@PathVariable Long id, @PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order, @PathVariable String filter)
	{
		return usuarioService.listUsuariosByDepartamentoAndByName(id,page, size, property, order, filter);
		
	}
	
	@GetMapping(value="/vincular/{page}/{size}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> ListarUsuariosParaVincular(@PathVariable int page, @PathVariable int size)
	{
		String property = "nome";
		String order = "ASC";
		return usuarioService.listUsuariosForLink(page, size, property, order);
		
	}
	
	
	@GetMapping(value="/vincular/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	public Page <Usuario> ListarUsuariosParaVincular(@PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order, @PathVariable String filter)
	{
		return usuarioService.listUsuariosForLinkByName (page, size, property, order, filter );
		
	}
	
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/vincular/{id}/{departamentoId}", method= RequestMethod.PATCH)
	public void vincular(@PathVariable Long id, @PathVariable Long departamentoId)
	{
		this.usuarioService.link(id,departamentoId);
	}
	
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@CrossOrigin
	@RequestMapping(value="/desvincular/{id}", method= RequestMethod.PATCH)
	public void  desvincular(@PathVariable Long id)
	{
		this.usuarioService.unlink(id);
	}
	
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@RequestMapping(value="/role/{id}/papel={papel}", method= RequestMethod.PATCH)
	@CrossOrigin
	public void atribuirPapel(@PathVariable Long id, @PathVariable Papel papel)
	{
		this.usuarioService.setRole(id,papel);
	}
}
