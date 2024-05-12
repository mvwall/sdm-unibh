package br.unibh.sdm.backend_doasangue.entidades;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Unidade {
    private static final String UNIDADES_STRING = "DoaSangueBackend\\src\\main\\java\\br\\unibh\\sdm\\backend_doasangue\\resources\\unidades.json";
    private String nome;
    private String municipio;
    private String endereco;

    public Unidade() {
    }

    public Unidade(String nome, String municipio, String endereco) {
        this.nome = nome;
        this.municipio = municipio;
        this.endereco = endereco;
    }

    // Getters e Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }
    

    // Método para ler o JSON e salvar uma lista de unidades
    public static List<Unidade> lerJSONListaUnidades() throws IOException, ParseException {
        List<Unidade> unidades = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(UNIDADES_STRING)) {
            
            JSONArray unidadesJson = (JSONArray) ((JSONObject) parser.parse(reader)).get("unidades_saude");

            for (Object obj : unidadesJson) {
                JSONObject jsonUnidade = (JSONObject) obj;

                String nome = (String) jsonUnidade.get("nome");
                String municipio = (String) jsonUnidade.get("municipio");
                String endereco = (String) jsonUnidade.get("endereco");

                Unidade unidade = new Unidade(nome, municipio, endereco);
                unidades.add(unidade);
            }
        }

       

        return unidades;
    }

    // Método para imprimir as informações das unidades
    public static void imprimirLista(List<Unidade> unidades) {
        for (Unidade unidade : unidades) {
            System.out.println(unidade);
        }
    }

    // Método para testar a leitura do JSON e a criação da lista de unidades
    

    @Override
    public String toString() {
        return "Unidade [nome=" + nome + ", municipio=" + municipio + ", endereco=" + endereco + "]";
    }
}