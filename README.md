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

# Executando Ema Server

O backend do Ema Server é composto pelos componentes:
- API do EmaServer (codigo deste repositório)
- API vulnerável ao ataque de Log4J (arquivo .jar disponibilizado na pasta log4/ deste repositório.) 
- Servidor LDAP (arquivo .jar disponibilizado na pasta log4/ deste repositório.)
- Banco de dados (configurado via docker)

## API do EmaServer

### Executando API do EmaServer utilizando docker

```shell
// Inicie seu docker
sudo service docker start
// Faz o build das imagens docker
docker-compose build
// Executa container
docker-compose up 
```

Após a execução bem sucedida dos comandos acima, acesse o swagger da aplicação (que roda na porta 80) através do endereço:
- [http://localhost:80/swagger-ui/index.html](http://localhost:80/swagger-ui/index.html)

### Executando API do EmaServer utilizando java

```shell
java -jar build\libs\emaserver-<VERSAO>-SNAPSHOT.jar
```

### Executando API do EmaServer na sua IDE (caso deseje realizar debug da aplicação)

Pré-requisito:
- Banco do ema server na porta 3306 executando em host com *database_local*.

Você pode testar através do comando de telnet.

```
telnet database_local 3306
```

Passos da configuração:
- Importe o projeto na IDE de preferência (é sugerida a instalação de plugins do gradle);
- Com o banco de dados disponível, execute o comando "gradle bootRun".

## Banco de dados

Você pode executar localmente o banco de dados usando o seguinte comando de docker.
```
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=AM3_DIGITAL_R3D_D3V_R3D3MPT1ON -e MYSQL_DATABASE=ema_local cyber-ctf-ema-server_database_local
```

## API vulnerável ao ataque de Log4J

Em construção.
<p align="right">(<a href="#top">back to top</a>)</p>

## Servidor LDAP

Em construção.
<p align="right">(<a href="#top">back to top</a>)</p>


# 💻 Compilando Ema Server

* A string de conexão é database_local:3306/ema_local, portanto se você for rodar o projeto sem ser via docker é necessário redefinir o endereço 127.0.0.1 como 'database_local'

## Pré-requisitos:

* JAVA_HOME tem que estar definida em versão JDK igual ou superior ao 11.
* Gradle 7.1 ou superior.
* Se desejar pular os testes, execute "./gradlew build -x test".

```shell
./gradlew build 
```

<p align="right">(<a href="#top">back to top</a>)</p>

# 🤝 Time responsável e contato<br>

- Time de [Cybersecurity](mailto:security@amedigital.com) da Ame Digital.
- [Paula Fernandes](mailto:paula.fernandes@amedigital.com)
- [Paulo Salgueiro](mailto:paulo.salgueiro@amedigital.com)

<p align="right">(<a href="#top">back to top</a>)</p>

# 💻 Environment
- [x] Backend

<p align="right">(<a href="#top">back to top</a>)</p>
