package br.unibh.sdm.backend_doasangue.persistencia;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.unibh.sdm.backend_doasangue.entidades.Doador;

@EnableScan
public interface DoadorRepository extends CrudRepository <Doador, String> {

    Iterable<Doador> findByNome(String nome);
    Iterable<Doador> findByEmailAndSenha(String email, String senha);

   
}
