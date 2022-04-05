package com.AppRH.AppRH.controllers.repository;


import com.AppRH.AppRH.models.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String> {
    // metodo do jpa
       Funcionario findById(Long id);

       //busca
       Funcionario findByNome(String nome);
}
