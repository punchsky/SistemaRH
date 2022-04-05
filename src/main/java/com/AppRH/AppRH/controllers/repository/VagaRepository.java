package com.AppRH.AppRH.controllers.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.AppRH.AppRH.models.Vaga;

public interface VagaRepository extends CrudRepository<Vaga, String>{
Vaga findByCodigo(long codigo);
List<Vaga> findByNome(String nome);
}
