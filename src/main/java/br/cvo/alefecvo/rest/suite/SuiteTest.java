package br.cvo.alefecvo.rest.suite;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.cvo.alefecvo.rest.core.BaseTest;
import br.cvo.alefecvo.rest.test.AutenticacaoTest;
import br.cvo.alefecvo.rest.test.ContaTest;
import br.cvo.alefecvo.rest.test.MovimentacaoTest;
import br.cvo.alefecvo.rest.test.SaldoTest;

@RunWith(Suite.class)
@SuiteClasses({
	ContaTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AutenticacaoTest.class
})
public class SuiteTest extends BaseTest{

	//Fazer autenticação e recuperar token
	@BeforeClass
	public static void login() {
		//Fazendo autenticação e extraindo token
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "alefe@cardozo");
		login.put("senha", "123456");
		
		//Definindo a estrutura do teste
		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
		;
		
		//Adicionando token no head para ser usado em todos os testes
		requestSpecification.header("Authorization","JWT " + TOKEN);
		//Resetando banco de dados
		get("/reset").then().statusCode(200);
	}
}
