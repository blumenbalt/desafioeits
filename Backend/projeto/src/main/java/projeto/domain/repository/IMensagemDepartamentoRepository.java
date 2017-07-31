package projeto.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projeto.domain.entity.MensagemDepartamento;

@Repository
public interface IMensagemDepartamentoRepository extends JpaRepository<MensagemDepartamento, Long>
{

	/**
	 * 
	 * @param departamentoId
	 * @return
	 */
	List<MensagemDepartamento> findByDepartamentoId(Long departamentoId);
	
	
	/**
	 * 
	 * @param usuarioId
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u  "
				+ "FROM MensagemDepartamento u  "
				+ "WHERE departamento.id IN (  "
					+ "SELECT departamento.id "
					+ "FROM Usuario "
					+ " WHERE id = :usuarioId )")
	public Page<MensagemDepartamento> findByUsuarioId(@Param("usuarioId")Long usuarioId,Pageable pageable);
	
	/**
	 * 
	 * @param usuarioId
	 * @return
	 */
	@Query(" SELECT u "
			+ "FROM MensagemDepartamento u "
			+ "WHERE u.id IN ( "
				+ "SELECT MAX(id)  "
				+ "FROM MensagemDepartamento   "
				+ "WHERE departamento.id IN (  "
					+ "SELECT departamento.id "
					+ "FROM Usuario  "
					+ " WHERE id = :usuarioId ))")
	public MensagemDepartamento findMensagemByUsuarioId(@Param("usuarioId")Long usuarioId);
	
	
}
