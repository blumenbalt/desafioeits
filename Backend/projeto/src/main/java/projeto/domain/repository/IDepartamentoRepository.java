package projeto.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projeto.domain.entity.Departamento;



@Repository
public interface IDepartamentoRepository extends JpaRepository <Departamento, Long>
{
	/**
	 * 
	 * @param nome
	 * @return
	 */
	public Departamento findByNome (String nome);
	
	
	@Query("SELECT u FROM Departamento u WHERE LOWER (u.nome) LIKE :filtro OR (u.descricao) LIKE :filtro")
	public Page<Departamento> findByNomeOrDescricaoContainingIgnoreCase(@Param("filtro")String filtro, Pageable pageable);
	
	public Page<Departamento> findByIdNot(Long id, Pageable pageable);
	
	
	@Query("SELECT u FROM Departamento u WHERE LOWER (u.nome) LIKE :nome AND NOT u.id = :departamentoId")
	public Page<Departamento> findByNomeContainingIdNot(@Param("departamentoId") Long departamentoId,
			@Param("nome") String nome, Pageable pageable);
	
	@Query("SELECT u FROM Departamento u WHERE u.departamentoPai.id = :id")
	public List<Departamento> findByDepartamento(@Param("id")Long id);
}
