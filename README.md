<h1 align="center">
  Texo IT Desafio Backend
</h1>

Projeto elaborado para solucionar o desafio de processo seletivo.

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Batch](https://spring.io/projects/spring-batch)
- [Spring Doc](https://springdoc.org/)
- [H2](https://www.h2database.com/html/main.html)

## Banco de Dados

- Acessar banco de dados em `http://localhost:8080/h2-console`
- JDBC URL: jdbc:h2:mem:teste
- User Name: teste
- Password: teste

- Os dados fornecidos serão inseridos na tabela movie [SELECT * FROM MOVIE]

## Práticas adotadas

- API REST
- Consulta com Spring Data JPA
- Injeção de Dependências
- Geração automática do Swagger com a OpenAPI 3

## Como Executar

- Clonar repositório git: []()
```
git clone 
```
- Executar build da aplicação Spring Boot
```
./mvnw clean package 
```
- Executar a aplicação Spring Boot
```
java -jar target/texoit-desafio-backend-0.0.1-SNAPSHOT.jar 
```
- Acessar aplicação em `http://localhost:8080/api/movies/producers-by-biggest-range-and-smallest-range-from-year-winner-consecutive`.

## API Endpoint Desafio

- Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta httpie:

- http :8080/api/movies/producers-by-biggest-range-and-smallest-range-from-year-winner-consecutive
```
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Sun, 14 Apr 2024 10:53:46 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "max": [
        {
            "followingWin": 2015,
            "interval": 13,
            "previousWin": 2002,
            "producer": "Matthew Vaughn"
        }
    ],
    "min": [
        {
            "followingWin": 1991,
            "interval": 1,
            "previousWin": 1990,
            "producer": "Joel Silver"
        }
    ]
}
```
## Documentação API

- Acessar documentação em `http://localhost:8080/swagger-ui/index.html`
