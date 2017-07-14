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
	public Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	public Usuario findByEmail(String email);
	
	public Usuario findOne(Long id);
	
	public List<Usuario> findByDepartamentoId(Long id);
	
	@Query("select u from Usuario u where u.email = :email and ativo = true")
	public Usuario findByEmailAndAtivo(@Param("email") String email);
	
	public Page<Usuario> findByAtivoTrue(Pageable pageable);
	
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByAtivoAndByNome(@Param("nome")String nome,Pageable pageable);
	
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id = :departamentoId")
	public Page<Usuario> findByDepartamentoIdAtivo(@Param ("departamentoId")Long departamentoId,Pageable pageable);
	
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id = :departamentoId AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByDepartamentoIdAndByNome(@Param ("departamentoId")Long departamentoId, @Param ("nome") String nome,Pageable pageable);
	
	
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id IS NULL")
	public Page<Usuario> findByDepartamentoNull(Pageable pageable);
	
	@Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.departamento.id IS NULL AND LOWER(u.nome) LIKE :nome")
	public Page<Usuario> findByDepartamentoNullAndByNome(@Param("nome")String nome,Pageable pageable);

	public Long countByDepartamentoId(Long id);
	
}
