## Projeto avaliação para vaga de desenvolvedor na DBC Company

---
### Dependências para uso na IntelliJIDEA
- Lombok
    1) Instalar o [Plugin do Lombok](https://projectlombok.org/).
    2) Habilitar processamento de anotações: Build, Execution, Deployment -> Compiler -> Annotation Processors (Enable Annotation Processing).
    3) Java 11
### Dependências Docker
- Docker
    1) Instalar o [Docker](https://docs.docker.com/get-docker/).
    2) Instalar o [docker-compose](https://docs.docker.com/compose/install/).
---
### Para acessar o banco de dados localmente
- Após rodar o comando `docker-compose up -d` para acessar o **PGAdmin**
    1) No browser acessar o [PGAdmin](http://localhost:16543).
    2) Configurar um novo server utilizando os dados de acesso
     - host: `local-postgres`.
     - user: `******`.
     - password: `*****`.
     - database: `******`.
---
### Integrações
- [HUBDEV](https://www.hubdodesenvolvedor.com.br/ "HUBDEV") - Consulta de CPF
    - necessário a data de nascimento para a consulta dos dados de CPF na receita federal
---
### Start do projeto e observações sobre as tecnologias utilizadas
- O projeto está dividido numa arquitetura de 4 módulos como as seguintes definições:
    1) `core` tem a função de conter o domínio da aplicação  
    2) `api` provê a interface de API Rest para prover a funcionalidade do domínio  
    3) `messaging` como foi solicitado demonstração de conhecimentos em mensageria foi criado esse módulo 
    de forma separada para evidenciar também o conhecimento sobre escalabilidade vertical dos produtores e 
    consumidores conforme a necessidade. 
        Utiliza o RabbitMQ também para o agendamento de uma mensagem a cada vez que uma pauta de votação é aberta,
    usando o recurso de **TTL(Time-To-Live)** do RabbitMQ.    
    4) `event` nesse módulo utiliza-se o **Apache Kafka** como tecnologia para event broker mesmo se sabendo que o RabbitMQ 
    também provê este recurso de forma parecida em suas últimas versões, entretanto, para se demonstrar também o 
    conhecimento no Apache Kafka resolvi fazer com ele o papel de notificar os interessados em um tópico sobre o
    resultado das votações. Ao se abrir uma votação é criada uma mensagem no Rabbit com o TTL setando o tempo de expiração 
    da pauta (ScheduleEntity) e enviada para uma fila usada como espera, a mesma não possui consumidores, assim quando
     o tempo é expirado a mesma é redirecionada e consumida por outra fila, onde está a lógica de consultar no banco os 
     resultados e em seguida postar outra mensagem para o kafka;
- Todos os eventos podem ser visualizados por **logs** na aplicação;    
- Start da aplicação localmente:
    1) Abra um terminal, dentro da raiz do projeto, execute: `docker-compose up -d`
    2) em seguida execute o comando `./gradlew :api:build`
    3) em seguida execute o comando `./gradlew :messaging:build`
    4) em seguida execute o comando `./gradlew :event:build`
    2) em seguida execute o comando `./gradlew :api:bootRun`
    3) em seguida, em nova aba do terminal, na raiz do projeto execute o comando `./gradlew :messaging:bootRun`
    4) em seguida, em nova aba do terminal, na raiz do projeto execute o comando `./gradlew :event:bootRun`
- Nesse formato de starter da aplicação e possível acompanhar os logs de cada modulo de forma separada, caso existam mais de 1 Java jdk ou jre instalados na máquina, pode ser necessário adicionar `-Dorg.gradle.java.home=<caminho do java 11 na maquina>` no comando para os builds ou bootRuns.
- Caso queiram executar a app apenas com um único comando execute no terminal apenas, na raiz do projeto:
 `sudo chmod -x startup.sh && sh startup.sh` em seguida digite a senha de sudo. Esse método é menos recomendado,
 sendo necessário dar kill nos processos iniciados pelo script ao final do uso do mesmo quando quiser encerrar as apps; 
- Caso queiram ver a base de dados criada seguem orientações de acesso:
    1) Configurar um novo server utilizando os dados de acesso:
     - host: `local-postgres`.
     - user: `*******`.
     - password: `******`.
     - database: `******`.
- Foi iniciado o deploy da app em um cluster **K8S(Kubernetes)** na [Digital Ocean](https://www.digitalocean.com/ "Digital Ocean"), 
  entretanto, não houve tempo hábil para a conclusão, podendo ser demonstrado o conhecimento em momento oportuno em que 
  seja solicitado.
- [Swagger](http://localhost:8080/swagger-ui.html "Swagger") - Documentação dos endpoints do serviço estão disponíveis no endereço

### Documentação
- Para a criação de uma nova **Pauta** de votação deve-se submeter uma requisição **Post**
  no endpoint local `localhost:8080/schedules` com o seguinte payload: `{
                               "title":"Pauta teste",
                               "description":"tema relevante para ser votado"                           
                           }` 
- Para abrir uma votação em uma determinada **Pauta** deve-se submeter uma requisição **PUT**
  no endpoint `http://localhost:8080/schedules/<code>/open`. Caso não seja enviado nenhum payload a mesma irá criar um
  tempo de expiração default de 1 minuto. Para enviar um tempo de expiração desejado, é necessário submeter um payload
  no seguinte formato: `{
                          "expiresTimeInMinutes": 1
                        }`
  definindo o tempo que seja desejado no payload;
- Para criar um voto é necessário enviar uma requisição do tipo **POST** para o endpoint `localhost:8080/votes` com o 
  seguinte formato:
     `{
       "scheduleCode": "5e297903-016f-44e7-bc86-0f476c51d86c",
       "option": "Sim", //Sim ou Nao
       "cpf": "99999999999",
       "birthday": "11/06/1984"  
   }`
- É possível consultar os resultados da votação no endpoint do tipo **GET** `http://localhost:8080/schedules/<code>/results`
- É possível submeter todas as requisições pelo - [Swagger](http://localhost:8080/swagger-ui.html "Swagger") 
- Somente é possível realizar votos com CPFs válidos e existentes na receita federal devido à integração com o HubDev 
  utilizada para fins de avaliação, visto que o endpoint sugerido nas orientações da mesma não está acessível. Por 
  este motivo, é necessário informar a data de aniversário corretamente também, ou o CPF não será localizado na receita federal
  e impossibilitará a votação.
- Se houverem dúvidas, estarei a disposição no email: `gleidson.ferreirasantos@gmail.com`;
