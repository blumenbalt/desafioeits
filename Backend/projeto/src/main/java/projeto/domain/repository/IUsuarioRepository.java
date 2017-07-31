package projeto.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projeto.domain.entity.Usuario;


@Repository
public interface IUsuarioRepository extends JpaRepository <Usuario, Long>
{	
	/**
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	public Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	/**
	 * 
	 * @param email
	 * @return
	 */
	public Usuario findByEmail(String email);
	
	/**
	 * 
	 */
	public Usuario findOne(Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Usuario> findByDepartamentoId(Long id);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	@Query("select u from Usuario u where u.email = :email and ativo = true")
	public Usuario findByEmailAndAtivo(@Param("email") String email);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Usuario> findByAtivoTrue(Pageable pageable);
	
	/**
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByAtivoAndByNome(@Param("nome")String nome,Pageable pageable);
	
	/**
	 * 
	 * @param departamentoId
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id = :departamentoId")
	public Page<Usuario> findByDepartamentoIdAtivo(@Param ("departamentoId")Long departamentoId,Pageable pageable);
	
	/**
	 * 
	 * @param departamentoId
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id = :departamentoId AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByDepartamentoIdAndByNome(@Param ("departamentoId")Long departamentoId, @Param ("nome") String nome,Pageable pageable);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id IS NULL")
	public Page<Usuario> findByDepartamentoNull(Pageable pageable);
	
	/**
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id IS NULL AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByDepartamentoNullAndByNome(@Param("nome")String nome,Pageable pageable);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Long countByDepartamentoId(Long id);
	
	/**
	 * 
	 * @param filter
	 * @param id
	 * @return
	 */
	@Query("select u from Usuario u where (u.filePath = :pFilter) and (u.id <> :id)")
	public List<Usuario> findFilesEquals(@Param("pFilter") String filter, @Param("id") Long id);
	
}
