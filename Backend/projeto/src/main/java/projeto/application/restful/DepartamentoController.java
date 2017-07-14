package projeto.application.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projeto.domain.entity.Departamento;
import projeto.domain.service.DepartamentoService;

@RequestMapping("departamento")
@RestController
public class DepartamentoController {
	/**
	 * exportando serviços do departamento
	 */
	@Autowired
	private DepartamentoService departamentoService;
	
	/**
	 * insere um departamento no sistema
	 * 
	 * 
	 */
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	@PostMapping
	@CrossOrigin
	public ResponseEntity<String> inserir (@RequestBody Departamento departamento)
	{
		return this.departamentoService.insert(departamento);
	}
	
	/**
	 * 
	 * altera um departamento no sistema
	 */
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	@PutMapping
	@CrossOrigin
	public ResponseEntity<String> alterar (@RequestBody Departamento departamento)
	{
		return this.departamentoService.update(departamento);
	}
	


	
	/**
	 * 
	 * pesquisar departamento por um filtro
	 */

	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@GetMapping(value="/{page}/{size}/{property}/{order}/{filter}")	
	@CrossOrigin
	public Page<Departamento> listarDepartamentoNome(@PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order,
		     @PathVariable String filter)
	{

		Page<Departamento> departamento= departamentoService.listDepartamentosByName(page, size, property, order, filter);
		return departamento;
	}
	/**
	 * 
	 *Listar todos os departamentos
	 */
	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@GetMapping(value="/{page}/{size}")
	@CrossOrigin
	public Page<Departamento> listarDepartamento(@PathVariable int page, @PathVariable int size)
	{
		String property = "nome";
		String order = "ASC";
		Page<Departamento> departamento= departamentoService.listAll(page, size, property, order);
		return departamento;
		
	}
	
	/**
	 * 
	 * Lista departamento, menos o id passado
	 */
	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@GetMapping(value="/not/{id}/{page}/{size}")	
	@CrossOrigin
	public Page<Departamento> listarDepartamentoTirandoId(@PathVariable Long id, @PathVariable int page, @PathVariable int size)
	{

		String property = "nome";
		String order = "ASC";
		return departamentoService.listDepartamentosByNotId(id, page, size, property, order);
	}
	
	/**
	 * 
	 * Pesquisar numa lista de departamentos, usando o filtro e tirando o id passado
	 * 
	 */
	@ResponseBody
	@Produces({"application/xml", "application/json"})
	@GetMapping(value="/not/{id}/{page}/{size}/{property}/{order}/{filter}")	
	@CrossOrigin
	public Page<Departamento> listarDepartamentoTirandoId(@PathVariable Long id, @PathVariable int page, @PathVariable int size,
		     @PathVariable String property, @PathVariable String order,
		     @PathVariable String filter)
	{
		return departamentoService.listDepartamentosByNameNotId(id, page, size, property, order, filter );
	}
	
	/**
	 * 
	 * Deleta um departamento
	 */
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	@CrossOrigin
	public void deletar(@PathVariable Long id)
	{
		this.departamentoService.delete(id);
	}

	/**
	 * 
	 * Detalha um departamento
	 */
	@Produces({"application/xml", "application/json"})
	@RequestMapping(value="/{id}", method= RequestMethod.GET)
	@CrossOrigin
	public Departamento detalhar(@PathVariable Long id)
	{
		return departamentoService.detail(id);
	}
	
	/**
	 * 
	 * 
	 * Vincula departamento
	 */
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@PatchMapping(value="/{id}/{idPai}")
	@CrossOrigin
	public void vincularDepartamento (@PathVariable Long id,@PathVariable Long idPai)
	{
		this.departamentoService.link(id, idPai);
	}
	
	/**
	 * 
	 * 
	 * Desvincula departamento
	 * 	 
	 */
	@Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
	@PatchMapping(value="/{id}")
	@CrossOrigin
	public void desvincularDepartamento (@PathVariable Long id)
	{
		this.departamentoService.unlink(id);
	}
	
}
