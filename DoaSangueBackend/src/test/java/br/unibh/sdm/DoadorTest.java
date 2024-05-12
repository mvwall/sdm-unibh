package br.unibh.sdm;
import static org.junit.Assert.assertNotNull;
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

import br.unibh.sdm.backend_doasangue.entidades.Doador;
import br.unibh.sdm.backend_doasangue.persistencia.DoadorRepository;

/**
 * Classe de testes para entidade Doador
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PropertyPlaceholderAutoConfiguration.class, DoadorTest.DynamoDBConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoadorTest {
    private static Logger LOGGER = LoggerFactory.getLogger(DoadorTest.class);
	

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
	private DoadorRepository repository;

	@Test
	public void teste1Criacao() throws ParseException {
		LOGGER.info("Criando objetos...");
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        Doador d1 =new Doador("Wallysson","AB","wallysson@mail.com",df.parse("01/07/1998"),"1234");
				repository.save(d1);
		// Criar novos doadores
		Doador doador1 = new Doador("João", "O+", "joao@mail.com", df.parse("01/01/1980"),"1234");
		Doador doador2 = new Doador("Maria", "A-", "maria@mail.com", df.parse("15/06/1990"),"1234");
		Doador doador3 = new Doador("Pedro", "B+", "pedro@mail.com", df.parse("10/05/1975"),"1234");
		Doador doador4 = new Doador("Ana", "AB-", "ana@mail.com", df.parse("20/11/1985"),"1234");
		Doador doador5 = new Doador("Mariana", "A+", "mariana@mail.com", df.parse("05/09/1995"),"1234");

		// Salvar no repositório
		repository.save(doador1);
		repository.save(doador2);
		repository.save(doador3);
		repository.save(doador4);
		repository.save(doador5);

		// Verificar se foram salvos corretamente
		assertNotNull(doador1.getId());
		assertNotNull(doador2.getId());
		assertNotNull(doador3.getId());
		assertNotNull(doador4.getId());
		assertNotNull(doador5.getId());

        Iterable<Doador> lista = repository.findAll();
		assertNotNull(lista.iterator());
		for (Doador doador : lista) {
			LOGGER.info(doador.toString());
		}
		
	}
	
	@Test
	public void teste2Exclusao()  {
		
		LOGGER.info("Excluindo objetos...");

        // Encontrar todos os doadores"
        List<Doador> doadores = (List<Doador>) repository.findAll();

        // Excluir cada doador encontrado
        for (Doador doador : doadores) {
            LOGGER.info("Excluindo Doador id = {}", doador.getId());
            repository.delete(doador);        }

        
    }
	
}

    

