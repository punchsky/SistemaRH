package com.AppRH.AppRH.controllers.repository;

import com.AppRH.AppRH.models.Dependentes;
import com.AppRH.AppRH.models.Funcionario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DependentesRepository extends CrudRepository<Dependentes,String> {

    Iterable<Dependentes> findByFuncionario(Funcionario funcionario);

    //pensando no delete
    Dependentes findByCpf(String cpf);
    Dependentes findById(Long id);

    //criado para implementar busca
    List<Dependentes> findByNome(String nome);

}
