# ⚖️ API de Gerenciamento de Processos Judiciais

Bem-vindo à API de Gerenciamento de Processos Judiciais. Este projeto é uma solução backend robusta, desenvolvida como parte de um teste técnico, para gerir processos e agendar audiências, implementando regras de negócio complexas, autenticação segura и uma estrutura de código limpa.

---

## ✨ Tecnologias Utilizadas

Este projeto foi construído com um conjunto de tecnologias modernas e amplamente utilizadas no ecossistema Java para o desenvolvimento de APIs RESTful.

* **Linguagem e Framework Principal:**
    * [**Java 21 (LTS)**](https://www.oracle.com/java/): Versão Long-Term Support da linguagem Java.
    * [**Spring Boot 3.3**](https://spring.io/projects/spring-boot): Framework principal que acelera o desenvolvimento de aplicações prontas para produção.
    * [**Maven 3.8+**](https://maven.apache.org/): Ferramenta para gestão de dependências e build do projeto.

* **Persistência de Dados:**
    * [**Spring Data JPA**](https://spring.io/projects/spring-data-jpa): Facilita a criação da camada de persistência de dados.
    * [**Hibernate**](https://hibernate.org/): Implementação da especificação JPA para o mapeamento objeto-relacional (ORM).
    * [**H2 Database**](https://www.h2database.com/): Banco de dados relacional em memória, ideal para desenvolvimento e testes.

* **API e Web:**
    * [**Spring Web (MVC)**](https://docs.spring.io/spring-framework/reference/web/webmvc.html): Para a criação de endpoints RESTful.
    * [**Tomcat Embutido**](https://tomcat.apache.org/): Servidor de aplicação padrão do Spring Boot.

* **Segurança:**
    * [**Spring Security 6**](https://spring.io/projects/spring-security): Para a implementação da autenticação e autorização.
    * [**JWT (JSON Web Tokens)**](https://jwt.io/): Para a criação de tokens de acesso stateless e seguros.

* **Documentação e Testes:**
    * [**Springdoc OpenAPI (Swagger)**](https://springdoc.org/): Para a geração automática de documentação interativa da API.
    * [**JUnit 5**](https://junit.org/junit5/): Framework padrão para testes unitários e de integração.
    * [**Mockito**](https://site.mockito.org/): Para a criação de mocks em testes unitários.

---

## 🚀 Como Executar a Aplicação

Para executar o projeto localmente, siga os passos abaixo.

### Pré-requisitos

* **JDK 21** ou superior instalado.
* **Apache Maven** instalado e configurado nas variáveis de ambiente.
* Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code) ou apenas a linha de comando.
* Um cliente de API como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/) (opcional, pois a API pode ser testada via Swagger).

### Passo a Passo

1.  **Clone o Repositório**
    ```bash
    git clone https://github.com/babingthon/Gerenciamento_Judicial_API
    cd management
    ```

2.  **Execute a Aplicação via Maven**
    Este é o método mais simples. Na raiz do projeto, execute o seguinte comando no seu terminal:
    ```bash
    mvn spring-boot:run
    ```
    Aguarde até que o log mostre a mensagem `Started ManagementApplication in ... seconds`. A aplicação estará a correr em `http://localhost:8080`.

3.  **Acesse as Ferramentas Embutidas**
    * **Documentação Interativa (Swagger UI)**:
        `http://localhost:8080/swagger-ui.html`
    * **Console do Banco H2**:
        `http://localhost:8080/h2-console`
        * **JDBC URL**: `jdbc:h2:mem:judicial_db`
        * **User Name**: `sa`
        * **Password**: (deixe em branco)

---

## 🔑 Autenticação e Uso da API

A API é protegida com JWT. Para aceder aos endpoints, você precisa primeiro de se autenticar.

1.  **Obter um Token de Acesso**
    Faça uma requisição `POST` para o endpoint de login. Um utilizador `admin` com a palavra-passe `password` é criado automaticamente na primeira inicialização da aplicação.

    `POST /api/v1/auth/login`

    **Corpo da Requisição (Body):**
    ```json
    {
      "username": "admin",
      "password": "password"
    }
    ```
    **Resposta:**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6..."
    }
    ```
    Copie o valor do token gerado.

2.  **Autorizar as Requisições**
    Para todos os outros endpoints, inclua o token no cabeçalho `Authorization`.

    * **Na Swagger UI**: Clique no botão **"Authorize"** no canto superior direito, cole o seu token no campo de texto com o prefixo `Bearer ` (ex: `Bearer eyJhbGci...`) e clique em "Authorize".
    * **No Postman/Insomnia/cURL**: Adicione um cabeçalho (Header) à sua requisição:
        `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6...`

---

## Endpoints da API

A documentação completa e interativa com todos os detalhes de `request` e `response` está disponível no **Swagger UI**.

### Exemplo de Requisições

* **Criar um novo processo:**
    `POST /api/v1/process`

* **Listar processos (com filtros):**
    `GET /api/v1/process?status=ACTIVE&distric=Natal`

* **Agendar uma nova audiência:**
    `POST /api/v1/audience`

* **Consultar a agenda de uma comarca:**
    `GET /api/v1/audiences/schedule?distric=Recife&day=2025-10-20`

###  Coleção do Postman

Para facilitar ainda mais os testes, uma [**coleção do Postman**](./ProcessesAndAudiences.postman_collection.json) com todos os endpoints configurados está disponível na raiz deste repositório. Importe o ficheiro `ProcessesAndAudiences.postman_collection.json` no seu Postman para começar a testar imediatamente.

---

## ✅ Executando os Testes

O projeto tem uma suíte de testes unitários e de integração para garantir a qualidade do código e o funcionamento das regras de negócio.

Para executar todos os testes, rode o seguinte comando na raiz do projeto:
```bash
mvn clean install
```
Este comando irá compilar o projeto, executar todos os testes e, se tudo passar, empacotar a aplicação num ficheiro `.jar`.

