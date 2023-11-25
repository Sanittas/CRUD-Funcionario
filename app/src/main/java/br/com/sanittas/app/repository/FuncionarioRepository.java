package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Query("SELECT f FROM Funcionario f WHERE f.idEmpresa.id = ?1")
    List<Funcionario> findAllWithIdEmpresa(Integer idEmpresa);
}
