# Mini Autorizador

Projeto desafio de código para VR Benefícios

## Requisitos

Para construir e executar o aplicativo, você precisa:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.8.6](https://maven.apache.org)
- [MySQL 5.7](https://dev.mysql.com/downloads/mysql/5.7.html)
- [Docker](https://www.docker.com/)

## Dependências

Há várias dependências usadas no projeto, para obter detalhes das bibliotecas e versões usadas verificar arquivo
pom.xml.

- Spring Boot
- Spring Boot Web
- Spring Test
- Spring Data JPA
- Spring Validation
- MySQL Conector
- Lombok
- Swagger Spring Fox
- MapStruct

## Construção

Existem várias maneiras de executar um aplicativo Spring Boot em sua máquina local. Uma maneira é executar o
método `main`
na classe `br.com.vr.miniautorizador.Application` na sua IDE. Alternativamente, você pode usar o
[Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```sh
$ ./mvnw spring-boot:run
```

### Construção e teste

Para construir e testar, você pode executar:

```sh
$ cd mini-autorizador
$ ./mvnw clean install
```

### Docker

Docker apoia na execução da base de dados MySQL para camada de persistência, toda a configuração necessaria para
execução
encontra-se no arquivo `docker-compose.yml`. Portanto, o aplicativo é hospedado em um contêiner docker que pode ser
acessado em http://localhost/ na porta 3306.

Podemos facilmente executar o todo com apenas um único comando:

```sh
$ docker-compose up
```  

O Docker irá puxar as imagens MySQL e Spring Boot (se nossa máquina não tiver antes). Os serviços podem ser
em segundo plano com o comando:

```sh
$ docker-compose up -d
```

Se você precisar parar e remover todos os contêineres, redes e todas as imagens usadas por qualquer serviço no
arquivo <em>docker-compose.yml</em>, use o comando:

```sh
$ docker-compose down --rmi all
```

## Execução

Após construção ddo projeto e aplicação web é acessível via

http://localhost:8080

Recursos disponíveis:

| Recurso            | Endpoint                                   | Método |
|--------------------|--------------------------------------------|--------|
| Criar cartão       | http:localhost:8080/cartoes                | POST   |
| Consultar saldo    | http:localhost:8080/cartoes/{numeroCartao} | GET    |
| Realizar transação | http:localhost:8080/transacoes             | POST   |

Os recursos também podem ser visualizados e testados na interface de documentação Swagger.

## Swagger

Documentação da API RESTful para auxiliar no consumo e testes dos recursos:

http://localhost:8080/swagger-ui/index.html

## Estrutura do projeto

No spring boot, existem diferentes formas de estruturar o projeto. Principalmente existem diferentes maneiras de
otimizar o projeto para trabalhar de forma eficiente, nesse caso foi utilizado estrutura por `features`

- **core** arquivos estruturantes de configurações, generics e auxiliares;
- **feature** ajuda na organização dos arquivos por contexto negocial;
- **shapper.** organiza arquivos relacionados a persistencia e de compartilhamento comun.

## Desenvolvedora

Angelica Leite (angelicalleite@gmail.com)