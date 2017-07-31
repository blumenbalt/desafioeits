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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projeto.domain.entity.MensagemDepartamento;
import projeto.domain.service.DepartamentoService;

@RestController
@RequestMapping("api/mensagemdepartamento")
public class MensagemDepartamentoController 
{
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	@Autowired
	private DepartamentoService departamentoService;

	/*------------------------------------------------------------------------
	 * 
	 * 							SERVIÇOS
	 * 
	 *-----------------------------------------------------------------------*/

	/**
	 * 
	 * @param mensagemDepartamento
	 */
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	@PostMapping
	@CrossOrigin
	public void inserir(@RequestBody MensagemDepartamento mensagemDepartamento) 
	{
		this.departamentoService.insertMessageDepartament(mensagemDepartamento);
	}
	
	/**
	 * Lista todas as mensagens do departamento do usuário
	 * @param page
	 * @param size
	 * @param order
	 * @return
	 */
	@GetMapping(value = "/{page}/{size}/{order}")
	@ResponseBody
	@Produces({ "application/xml", "application/json" })
	@CrossOrigin
	public Page<MensagemDepartamento> listaMensagemPorUsuario(@PathVariable int page, @PathVariable int size,
			@PathVariable String order) 
	{
		String property = "id";
		return departamentoService.listAllByUserId(page, size, property, order);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String> deletar(@PathVariable Long id) 
	{
		return this.departamentoService.deleteMessageDepartament(id);
	}
	
	/**
	 * Retorna a última mensagem enviada ao departamento do usuário
	 * @return
	 */
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@GetMapping(value = "/home")
	@CrossOrigin
	public MensagemDepartamento mostrarMensagem() 
	{
		return this.departamentoService.showMessage();
	}

}
