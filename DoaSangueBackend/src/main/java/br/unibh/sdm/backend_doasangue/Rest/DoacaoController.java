package br.unibh.sdm.backend_doasangue.Rest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.unibh.sdm.backend_doasangue.entidades.Doacao;
import br.unibh.sdm.backend_doasangue.negocio.DoacaoService;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "doacao")
public class DoacaoController {
   

    
    private final DoacaoService doacaoService;

    public DoacaoController(DoacaoService doacaoService){
        this.doacaoService=doacaoService;
    }
     
    @GetMapping(value = "")
    public List<Doacao> getDoacaos(){
        return doacaoService.getDoacoes();
    }
    
    @GetMapping(value="{id}")
    public Doacao getDoacaoById(@PathVariable String id) throws Exception{
        if(!ObjectUtils.isEmpty(id)){
           return doacaoService.getDoacaoById(id);
        }
        throw new Exception("Doacao com codigo "+id+" nao encontrada");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Doacao createDoacao(@RequestBody @NotNull Doacao doacao) throws Exception {
         return doacaoService.saveDoacao(doacao);
    }
    
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Doacao updateDoacao(@PathVariable String id, 
    		@RequestBody @NotNull Doacao doacao) throws Exception {
         return doacaoService.saveDoacao(doacao);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{id}")
    public boolean updateDoacao(@PathVariable String id) throws Exception {
         doacaoService.deleteDoacao(id);
         return true;
    }
    
}