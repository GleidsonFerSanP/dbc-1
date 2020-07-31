## Projeto avaliação para vaga de desenvolvedor na DBC Company
Para o cliente **Sicred**.

[Jenkins](https://www.jenkins.io/) | [Sonar](https://www.sonarqube.org/)
---
### Dependências para uso na IntelliJIDEA
- Lombok
    1) Instalar o [Plugin do Lombok](https://projectlombok.org/).
    2) Habilitar processamento de anotações: Build, Execution, Deployment -> Compiler -> Annotation Processors (Enable Annotation Processing).
### Dependências para rodar a aplicação em Docker
- Docker
    1) Instalar o [Docker](https://docs.docker.com/get-docker/).
    2) Instalar o [docker-compose](https://docs.docker.com/compose/install/).
    3) Rodar o comando no terminal `docker-compose up -d`
---
### Para acessar o banco de dados localmente
- PGAdmin
    1) No browser acessar o [PGAdmin](http://localhost:16543).
    2) Configurar um novo server utilizando os dados de acesso
     - host: `local-postgres`.
     - user: `sicred`.
     - password: `sicred`.
     - database: `sicred`.
---
### Integrações
- [HUBDEV](hubdodesenvolvedor.com.br "HUBDEV") - Consulta de CPF
---
### Documentação
Documentação dos endpoints do serviço estão disponíveis no endereço `http://localhost:8080/swagger-ui.html`
