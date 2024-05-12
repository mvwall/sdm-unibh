package br.unibh.sdm.backend_doasangue.negocio;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.unibh.sdm.backend_doasangue.entidades.Doacao;
import br.unibh.sdm.backend_doasangue.persistencia.DoacaoRepository;

@Service
public class DoacaoService {
    private static final double LIMITE_LITROS_DOACAO= 0.45;
    private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private final DoacaoRepository doacaoRepository;

    public DoacaoService(DoacaoRepository doacaoRepository){
        this.doacaoRepository=doacaoRepository;
    }
    
    public List<Doacao> getDoacoes(){
        if(logger.isInfoEnabled()){
            logger.info("Buscando todos os objetos");
        }
        Iterable<Doacao> lista = this.doacaoRepository.findAll();
        return IteratorUtils.toList(lista.iterator());
    }    

    public Doacao getDoacaoById(String id){
        if(logger.isInfoEnabled()){
            logger.info("Buscando Doacao com o codigo {}",id);
        }
        Optional<Doacao> retorno = this.doacaoRepository.findById(id);
        if(!retorno.isPresent()){
            throw new RuntimeException("Doador com o id "+id+" nao encontrada");
        }
        return retorno.get();
    }
    
    public List<Doacao> getDoacaoByList(){
    	if(logger.isInfoEnabled()){
            logger.info("Buscando todos os objetos");
        }
        Iterable<Doacao> lista = this.doacaoRepository.findAll();
        return IteratorUtils.toList(lista.iterator());
    }
    
    public Doacao saveDoacao(Doacao doacao){
        if(logger.isInfoEnabled()){
            logger.info("Salvando registro da doacao {}",doacao.toString());
        }
        
        if (doacao.getQuantidadeLitros() > LIMITE_LITROS_DOACAO) {
            throw new RuntimeException("Quantidade de litros excede o limite permitido");
        }
        
        return this.doacaoRepository.save(doacao);
    }
    
    public void deleteDoacao(String id){
        if(logger.isInfoEnabled()){
            logger.info("Excluindo doacao com id {}",id);
        }
        this.doacaoRepository.deleteById(id);
    }

    public boolean isDoacaoExists(Doacao doacao){
    	Optional<Doacao> retorno = this.doacaoRepository.findById(doacao.getId());

        return retorno.isPresent() ? true:  false;
    } 

    
}

