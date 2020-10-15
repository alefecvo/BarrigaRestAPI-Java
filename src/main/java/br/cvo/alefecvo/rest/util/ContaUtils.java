package br.cvo.alefecvo.rest.util;

import static io.restassured.RestAssured.get;

public class ContaUtils {

	//Recuperar código da conta pelo nome
	public static Integer getIdContaPeloNome(String nome) {
		return get("/contas?nome=" + nome).then().extract().path("id[0]");
	}
}
