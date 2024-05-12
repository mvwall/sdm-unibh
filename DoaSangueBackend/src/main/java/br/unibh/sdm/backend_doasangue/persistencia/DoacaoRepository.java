package br.unibh.sdm.backend_doasangue.persistencia;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.unibh.sdm.backend_doasangue.entidades.Doacao;


@EnableScan
public interface DoacaoRepository extends CrudRepository<Doacao, String> {

    
}
