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

import br.unibh.sdm.backend_doasangue.entidades.Doador;
import br.unibh.sdm.backend_doasangue.negocio.DoadorService;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "doador")
public class DoadorController {
   
    private final DoadorService doadorService;

    public DoadorController(DoadorService doadorService){
        this.doadorService=doadorService;
    }

    @GetMapping(value = "")
    public List<Doador> getDoadores(){
        return doadorService.getDoadores();
    }
    
    @GetMapping(value="{id}")
    public Doador getDoadorById(@PathVariable String id) throws Exception {
        if(!ObjectUtils.isEmpty(id)) {
           return doadorService.getDoadorById(id);
        }
        throw new Exception("Doador com código "+id+" não encontrado");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Doador createDoador(@RequestBody @NotNull Doador doador) {
         return doadorService.saveDoador(doador);
    }
    
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Doador updateDoador(@PathVariable String id, 
    		@RequestBody @NotNull Doador doador) throws Exception {
         return doadorService.saveDoador(doador);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{id}")
    public boolean updateDoadorDoador(@PathVariable String id) throws Exception {
         doadorService.deleteDoador(id);
         return true;
    }
}
