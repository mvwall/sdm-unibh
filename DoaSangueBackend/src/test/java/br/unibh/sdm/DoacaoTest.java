package br.unibh.sdm;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


import br.unibh.sdm.backend_doasangue.entidades.Doacao;
import br.unibh.sdm.backend_doasangue.entidades.Doador;
import br.unibh.sdm.backend_doasangue.entidades.Unidade;
import br.unibh.sdm.backend_doasangue.persistencia.DoacaoRepository;
import br.unibh.sdm.backend_doasangue.persistencia.DoadorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PropertyPlaceholderAutoConfiguration.class, DoacaoTest.DynamoDBConfig.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoacaoTest {
    private static Logger LOGGER = LoggerFactory.getLogger(DoacaoTest.class);

    @Configuration
    @EnableDynamoDBRepositories(basePackageClasses = { DoadorRepository.class })
    public static class DynamoDBConfig {

        @Value("${amazon.aws.accesskey}")
        private String amazonAWSAccessKey;

        @Value("${amazon.aws.secretkey}")
        private String amazonAWSSecretKey;

        public AWSCredentialsProvider amazonAWSCredentialsProvider() {
            return new AWSStaticCredentialsProvider(amazonAWSCredentials());
        }

        @Bean
        public AWSCredentials amazonAWSCredentials() {
            return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
        }

        @Bean
        public AmazonDynamoDB amazonDynamoDB() {
            return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
                    .withRegion(Regions.US_EAST_1).build();
        }
    }

    @Autowired
    private DoacaoRepository repository;

    @Test
    public void teste1CriarDoacoes() throws ParseException, IOException, org.json.simple.parser.ParseException  {
        LOGGER.info("Criando doações...");

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        // Criar novos doadores
        Doador doador1 = new Doador("Wallysson", "AB", "wallysson@mail.com", df.parse("01/07/1998"),"1234");
        Unidade unidade = new Unidade("Upa", "Belo Horizonte", "barão homen de mello");
        // Salvar no repositório
        Doacao d1 = new Doacao(doador1.getId(), unidade.getNome(), 5);
        repository.save(d1);

        // Verificar se foram salvos corretamente
        assertNotNull(d1);
    }

    @Test
public void teste2ExcluirDoacoes() {
    LOGGER.info("Excluindo doações...");

    // Encontrar todas as doações
    List<Doacao> doacoes = (List<Doacao>) repository.findAll();

    // Excluir cada doação encontrada
    for (Doacao doacao : doacoes) {
        LOGGER.info("Excluindo Doacao id = {}", doacao.getId());
        repository.delete(doacao);
    }
}

    }
