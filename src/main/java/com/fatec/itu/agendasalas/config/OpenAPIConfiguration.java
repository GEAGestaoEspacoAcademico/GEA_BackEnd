package com.fatec.itu.agendasalas.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    server.setUrl("http://localhost:8080");
    server.setDescription("Desenvolvimento");

    Contact contact = new Contact();
    contact.setName(
        "Equipe: Beatriz Camargo, Henrique Andrade, Henrique Carvalho, Leonardo Boff, Lucas Morais e Walinson Pereira");
    contact.setUrl("https://github.com/orgs/GEAGestaoEspacoAcademico/teams/back-end");

    Info information = new Info()
        .title("Documentação da  API do GEA (Gestor de Espaços Acadêmicos)").version("1.0")
        .description("Essa API expõe todos os endpoints da aplicação.").contact(contact);

    return new OpenAPI().info(information).servers(List.of(server));
  }
}
