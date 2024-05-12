package br.unibh.sdm.backend_doasangue.negocio;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import br.unibh.sdm.backend_doasangue.entidades.Doador;
import br.unibh.sdm.backend_doasangue.persistencia.DoadorRepository;


@Service
public class DoadorService {
    private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private final DoadorRepository doadorRepository;

    public DoadorService(DoadorRepository doadorRepository){
        this.doadorRepository=doadorRepository;
    }
    
    public List<Doador> getDoadores(){
        if(logger.isInfoEnabled()){
            logger.info("Buscando todos os objetos");
        }
        Iterable<Doador> lista = this.doadorRepository.findAll();
        return IteratorUtils.toList(lista.iterator());
    }  
    
    

    public Doador getDoadorById(String id){
        if(logger.isInfoEnabled()){
            logger.info("Buscando doador com o codigo {}",id);
        }
        Optional<Doador> retorno = this.doadorRepository.findById(id);
        if(!retorno.isPresent()){
            throw new RuntimeException("Doador com o id "+id+" nao encontrada");
        }
        return retorno.get();
    }
    
    public List<Doador> getDoadorByNome(String nome){
    	if(logger.isInfoEnabled()){
            logger.info("Buscando todos os objetos");
        }
        Iterable<Doador> lista = this.doadorRepository.findByNome(nome);
        if (lista == null) {
        	return new ArrayList<Doador>();
        }
        return IteratorUtils.toList(lista.iterator());
    }
    
    public Doador saveDoador(Doador doador){
        if(logger.isInfoEnabled()){
            logger.info("Salvando registro do doador {}",doador.toString());
        }
        return this.doadorRepository.save(doador);
    }
    
    public void deleteDoador(String id){
        if(logger.isInfoEnabled()){
            logger.info("Excluindo doador com id {}",id);
        }
        this.doadorRepository.deleteById(id);
    }

    public boolean isDoadorExists(Doador doador){
    	Optional<Doador> retorno = this.doadorRepository.findById(doador.getNome());
        return retorno.isPresent() ? true:  false;
    }
    public boolean verificarCredenciais(String email, String senha) {
        Iterable<Doador> doadorOptional = doadorRepository.findByEmailAndSenha(email, senha);       
        return doadorOptional.iterator().hasNext();
    }
    
}
