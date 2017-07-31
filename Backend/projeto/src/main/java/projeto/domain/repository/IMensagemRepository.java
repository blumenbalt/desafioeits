package projeto.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.domain.entity.Mensagem;

@Repository
public interface IMensagemRepository extends JpaRepository<Mensagem, Long>
{	
}
