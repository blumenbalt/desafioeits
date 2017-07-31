package projeto.application.restful;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import projeto.domain.entity.Papel;
import projeto.domain.entity.Usuario;
import projeto.domain.service.UsuarioService;

@RequestMapping("api/usuario")
@RestController
public class UsuarioController 
{
	
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * 
	 */
	private static final String APPLICATION_PDF = "application/pdf";
	
	
	/*------------------------------------------------------------------------
	 * 
	 * 							SERVIÇOS
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	@CrossOrigin
	@PostMapping
	public ResponseEntity<String> inserir(@RequestBody Usuario usuario) 
	{
		return this.usuarioService.insert(usuario);
	}

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	@CrossOrigin
	@PutMapping
	public ResponseEntity<String> alterar(@RequestBody Usuario usuario) 
	{
		return this.usuarioService.update(usuario);
	}
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	@CrossOrigin
	@PutMapping(value = "/senha")
	public ResponseEntity<String> alterarSenha(@RequestBody Usuario usuario) 
	{

		return this.usuarioService.updatePassword(usuario);
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
	public Page<Usuario> listarUsuario(@PathVariable int page, @PathVariable int size) 
	{
		String property = "nome";
		String order = "ASC";
		Page<Usuario> usuarios = usuarioService.listUsuarios(page, size, property, order);
		return usuarios;
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	@ResponseBody
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/{page}/{size}/{property}/{order}/{filter}", method = RequestMethod.GET)
	public Page<Usuario> listarUsuarioNome(@PathVariable int page, @PathVariable int size,
			@PathVariable String property, @PathVariable String order, @PathVariable String filter) 
	{
		Page<Usuario> usuarios = usuarioService.listUsuariosByName(page, size, property, order, filter);
		return usuarios;
	}

	/**
	 * Ativa o usuário no sistema
	 * @param id
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/on/{id}", method = RequestMethod.PATCH)
	public void ativar(@PathVariable Long id) 
	{
		this.usuarioService.activate(id);
	}

	/**
	 * Desativa o usuário no sistema
	 * @param id
	 * @return
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/off/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<String> desativar(@PathVariable Long id) 
	{
		return this.usuarioService.deactivate(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Usuario detalhar(@PathVariable Long id) 
	{
		return usuarioService.detail(id);
	}

	/**
	 * Retorna o usuário logado
	 * @return
	 */
	@GetMapping(value = "/atual")
	@CrossOrigin
	public Usuario getAtual() 
	{
		return usuarioService.getCurrent();
	}
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/ativos/{page}/{size}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> listarTodosAtivos(@PathVariable int page, @PathVariable int size) 
	{
		String property = "nome";
		String order = "ASC";
		Page<Usuario> usuarios = usuarioService.listUsuariosAtivos(page, size, property, order);
		return usuarios;
	}
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	@GetMapping(value = "/ativos/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> listarTodosAtivosPorNome(@PathVariable int page, @PathVariable int size,
			@PathVariable String property, @PathVariable String order, @PathVariable String filter) 
	{
		Page<Usuario> usuarios = usuarioService.listUsuariosAtivosByName(page, size, property, order, filter);
		return usuarios;
	}
	
	/**
	 * Lista usuários do departamento selecionado
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/departamento/{id}/{page}/{size}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> listarUsuariosPorDepartamento(@PathVariable Long id, @PathVariable int page,
			@PathVariable int size) 
	{
		String property = "nome";
		String order = "ASC";
		return usuarioService.listUsuariosByDepartamento(id, page, size, property, order);

	}
	
	/**
	 * Lista os usuários do departamento selecionado, por nome
	 * @param id
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	@GetMapping(value = "/departamento/{id}/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> listarUsuariosPorDepartamento(@PathVariable Long id, @PathVariable int page,
			@PathVariable int size, @PathVariable String property, @PathVariable String order,
			@PathVariable String filter) 
	{
		return usuarioService.listUsuariosByDepartamentoAndByName(id, page, size, property, order, filter);

	}
	
	/**
	 * Lista usuários disponíveis para vinculação, ou seja, sem departamento e ativos
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/vincular/{page}/{size}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> ListarUsuariosParaVincular(@PathVariable int page, @PathVariable int size) 
	{
		String property = "nome";
		String order = "ASC";
		return usuarioService.listUsuariosForLink(page, size, property, order);

	}
	
	/**
	 * Lista usuários disponíveis para vinculação, ou seja, sem departamento e ativos, por nome
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	@GetMapping(value = "/vincular/{page}/{size}/{property}/{order}/{filter}")
	@CrossOrigin
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Page<Usuario> ListarUsuariosParaVincular(@PathVariable int page, @PathVariable int size,
			@PathVariable String property, @PathVariable String order, @PathVariable String filter) 
	{
		return usuarioService.listUsuariosForLinkByName(page, size, property, order, filter);

	}
	
	/**
	 * 
	 * @param id
	 * @param departamentoId
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/vincular/{id}/{departamentoId}", method = RequestMethod.PATCH)
	public void vincular(@PathVariable Long id, @PathVariable Long departamentoId) 
	{
		this.usuarioService.link(id, departamentoId);
	}
	
	/**
	 * 
	 * @param id
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/desvincular/{id}", method = RequestMethod.PATCH)
	public void desvincular(@PathVariable Long id) 
	{
		this.usuarioService.unlink(id);
	}
	
	/**
	 * Atribui um papel dentro do departamento
	 * @param id
	 * @param papel
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RequestMapping(value = "/role/{id}/papel={papel}", method = RequestMethod.PATCH)
	@CrossOrigin
	public void atribuirPapel(@PathVariable Long id, @PathVariable Papel papel) 
	{
		this.usuarioService.setRole(id, papel);
	}
	
	/**
	 * Deleta arquivo
	 * @param id
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/clearFile/{id}", method = RequestMethod.DELETE)
	public void clearFile(@PathVariable Long id) 
	{
		usuarioService.clearFile(id);
	}
	
	/**
	 * 
	 * @param file
	 * @param id
	 * @return
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	@RequestMapping(value = "/uploadFile/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) 
	{
		return usuarioService.uploadFile(file, id);
	}
	
	/**
	 * 
	 * @param response
	 * @param id
	 * @throws IOException
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RequestMapping(value = "/downloadFile/{id}", method = RequestMethod.GET, produces = APPLICATION_PDF)
	public void downloadFile(HttpServletResponse response, @PathVariable Long id) throws IOException 
	{
		usuarioService.downloadFile(response, id);
	}
}
