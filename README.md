# Ema Server - Red Dev Redemption
![GitHub repo size](https://img.shields.io/static/v1?label=RedTeam&message=CTF&color=red)
![GitHub repo size](https://img.shields.io/badge/Tech-Docker-green)
![GitHub repo size](https://img.shields.io/badge/Tech-Java-green)
![GitHub repo size](https://img.shields.io/badge/Tech-Gradle-green)

> Projeto de backend utilizado no CTF da Ame Digital.


<p align="right">(<a href="#top">back to top</a>)</p>

## 💻 Tecnologias

* Java 11
* Gradle 7.1
* Docker

<p align="right">(<a href="#top">back to top</a>)</p>

## Executando projeto com docker

```shell
// Inicie seu docker
sudo service docker start
// Faz o build das imagens docker
docker-compose build
// Executa container
docker-compose up 
```

Após a execução bem sucedida dos comandos acima, acesse o swagger da aplicação (que roda na porta 8082) através do endereço:

[http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

## Executando projeto com java

Pré-requisito:
- Banco do ema server na porta 3306 executando em host com *database_local*.

```
telnet database_local 3306
```

Você pode executar localmente o banco de dados usando o seguinte comando de docker.
```
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=AM3_DIGITAL_R3D_D3V_R3D3MPT1ON -e MYSQL_DATABASE=ema_local cyber-ctf-ema-server_database_local
```

<p align="right">(<a href="#top">back to top</a>)</p>

## 💻 Compilando o repositório

* A string de conexão é database_local:3306/ema_local, portanto se você for rodar o projeto sem ser via docker é necessário redefinir o endereço 127.0.0.1 como 'database_local'

### Pré-requisitos:

* JAVA_HOME tem que estar definida em versão JDK igual ou superior ao 11.
* Gradle 7.1 ou superior.
* Se desejar pular os testes, execute "./gradlew build -x test".

```shell
./gradlew build 
java -jar build\libs\emaserver-<VERSAL>-SNAPSHOT.jar
```

<p align="right">(<a href="#top">back to top</a>)</p>

## 🤝 Time responsável e contato<br>

- Time de [Cybersecurity](mailto:security@amedigital.com) da Ame Digital.
- [Paula Fernandes](mailto:paula.fernandes@amedigital.com)
- [Paulo Salgueiro](mailto:paulo.salgueiro@amedigital.com)

<p align="right">(<a href="#top">back to top</a>)</p>

## 💻 Environment
- [x] Backend
- [x] Internal
- [x] Hosted

<p align="right">(<a href="#top">back to top</a>)</p>
