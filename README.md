<h1 align="center"> <strong> Tech Challenge - Sistema de e-commerce - FIAP Arquitetura de Sistemas em Java - 5º Fase </strong> </h1>

Este sistema de e-commerce foi desenvolvido para permitir que os usuários realizem cadastro, login, gerenciem produtos e mantenham um carrinho de compras, além de simular o processo de pagamento.
O sistema é baseado no framework Spring Boot, utiliza Spring Security para autenticação e autorização, e é construído seguindo a arquitetura de microserviços, garantindo segurança e escalabilidade.

<h2 align="center">  Tecnologias Adotadas </strong> </h2>

- **Java 17**: Versão da linguagem Java de suporte de longo prazo (LTS)
- **Spring**: Framework utilizado para a construção de aplicativos Java que agiliza nosso desenvolvimento com servidor ja embarcado da aplicação e estruturas\bibliotecas já prontas em especial para o desenvolvimento WEB com injeção e inversão de dependências.
- **Rest Template**: Classe utilitária que permite interagir com serviços RESTful a partir de uma aplicação Java usando o Framework Spring, poderiamos ter optado por Feign , WebClient ou Spring Integration, mas por já conhecermos esta classe e pelo prazo da entrega optamos por ela. 
- **Docker**: Plataforma de software que permite a criação, o envio e a execução de aplicativos em contêineres.
- **MongoDB**: Banco de dados não relacional utilizado para persistir as informações relacionadas ao carrinho, optamos por ela pela flexibilidade do relacionamento entre as demais entidades que existem dentro do carrinho.
- **MySQL**: Banco de dados relacional utilizado para armazenar outras informações do sistema, usamos na parte de estoque-produtos e achamo interessante um banco relacional para a garantia de ACID dos dados que sofrem constantes ajustes conforme os produtos são adicionados ou removidos dos carrinhos.
- **Postgres**: Outro banco de dados relacional utilizado para armazenamento SQL e no caso usamos para o cadastro de usuários, com a garantia de ACID e para exemplificarmos bem como podemos separar e desacoplar os bancos de dados adotando a arquitetura de microserviços.
- **Spring Security e JWT**: Bibliotecas responsaveis por efetuar a camada de segurança da aplicação fornecendo a autenticação e autorização dos usuários e geração e validação dos tokens. O ideal é que o Tokens tenham um tempo curto para expiração ou até mesmo sendo unico para cada transação,porém deixamos um prazo longo de 4 horas para facilitar os testes, mas o ideal que não passem de 15 minutos, conforme explicado nas aulas de Segurança.
  O JWT é composto por três partes: um cabeçalho (header) com informações sobre o tipo de token e algoritmo de criptografia, um payload (carga útil) com as informações do usuário e um tempo de expiração, e uma assinatura digital para garantir a integridade do token.
- **SonarLint** é um software detector, em tempo real, de códigos fora de padrão – que geram dificuldades de manutenção , bugs e vulnerabilidades. Foi muito importante a sua utilização para termos uma escrita correta e segura, aplicamos em todas as classes do projeto com exceção as de testes.
- **CheckStyle** é um analisador estático de código para checar se o código fonte está de acordo com as regras de codificação, este software nos ajudou a atendermos os requisitos da utilização de boas práticas em nosso código.
- **JUnit**: É um framework open-source para construção de testes automatizados em Java, essencial para o desenvolvimento utilizando os conceitos de TDD, em conjunto com o Mockito que cria Fakes para nossos testes unitários, utilizamos em algumas de  nossas classes de testes, apesar de não estar no requisito deste projeto, mas sabemos da importãncia sempre citados pelos professores.
  
<h2 align="center"> Arquitetura de Microserviços</strong> </h2>

A arquitetura de microsserviços refere-se a um estilo de arquitetura para o desenvolvimento de sistemas. Os microsserviços permitem que um aplicativo grande seja separado em partes independentes menores, com cada parte tendo sua própria responsabilidade, indo de encontro aos conceitos do Solid (especial o S) e permitindo as equipes de desenvolvimento poderem criar e atualizar novos componentes para atender às mudanças nas necessidades empresariais sem interromper a aplicação como um todo.

![Texto alternativo](doc/img/modelagem.png)

Cada microserviço interage com seu próprio banco de dados para garantir a independência e a eficiência na gestão dos dados.
Em nosso projeto temos, todos os microsserviços chamando o de segurança para validação do Token e recuperação dos dados de roles dos usuários, além do microserviço de carrinho que chama o de estoque para consulta de disponibilidade de produtos, recuperar detalhes de produtos e atualizar o estoque seja no adicionar quanto no remover produtos.

<h2 align="center">Microserviços do Sistema</strong> </h2>

### 1. MS-Seguranca

**Cadastro de usuários**

Metodo **POST**

**URL** http://localhost:8081/users

```json
{
  "login": "teste",
  "password": "123456",
  "cpf": "46181856005",
  "email": "teste@hotmail.com",
  "role": "ADMIN ou USER"
}
```
---

**Listar todos usuários**

Metodo **GET**

**URL** http://localhost:8081/users

---

**Listar usuário por ID**

Metodo **GET**

**URL** http://localhost:8081/users/{id}

---

**Autenticar**

Metodo **POST**

**URL** http://localhost:8081/auth/login
```json
{
    "login":"teste",
    "password":"123456"
}
```

**/login**  é necessário informar o Login e a senha cadastrados anteriormente. Caso essas informações estejam corretas será gerato um TOKEN atraves da biblioteca JWT que terá validade de 4 horas e será necessários para utilzar o restante do sistema.

---
**Validar**

Metodo **GET**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8081/auth/validate

**/validade** é um endpoint criado para ser chamado pelos outros microserviços para realizar a validação do token informado.

---

### 2. MS-Estoque
**Para utlizar os serviços presente no ms-estoque já é necessário estar logado no sistema(Possuir um TOKEN valido).**

**Criar produto**

Metodo **POST**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto
```json
{
    "id": "1",
    "nome": "TV 22",
    "descricao": "TV LG 22 polegadas",
    "quantidade_estoque": "102",
    "preco": "435.99"
}
```
---
**Listar todos produtos**

Metodo **GET**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto

---

**Listar produto por ID**

Metodo **GET**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto/{id}

---

**Deletar produto**

Metodo **DELETE**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto/{id}

---

**Editar produto**

Metodo **PUT**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto

```json
{
    "id": "1",
    "nome": "TV 22",
    "descricao": "TV LG 22 polegadas",
    "quantidade_estoque": "102",
    "preco": "599.99"
}
```
---

**Atualizar estoque**

Metodo **PUT**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8082/produto/atualizar/estoque/{produtoId}/{quantidade}

**/atualizar/estoque** Endpoint que será chamado pelos outros microserviços para alterar a quantidade de protudos

---

### 3. MS-Carrinho

**Para utlizar os serviços presente no ms-carrinho já é necessário estar logado no sistema(Possuir um TOKEN valido).**

**Criar carrinho**

Metodo **POST**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho?login=

---

**Adicionar produto no carrinho**

Metodo **POST**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho/adicionarproduto/{login}

```json
{
    "id":"1",
    "quantidade":1
}
```
---

**Remover produto no carrinho**

Metodo **DELETE**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho/removerproduto/{login}

```json
1
```
---
**Listar todos carrinhos**

Metodo **GET**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho

---

**Listar carrinho por ID**

Metodo **GET**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho/{id}

---

**Pagamento do carrinho**

Após esta ação o status fica false e o carrinho fechado, desta forma abrindo um novo carrinho pelo login, este não será selecionado para adicionar, remover produtos e pagar e não poderá mais sofrer ajustes. Mas poderá ser aberto um novo carrinho pelo Login com outro ID de carrinho.

Metodo **POST**

Headers **Authorization Bearer {token}**

**URL** http://localhost:8083/carrinho/pagamento/efetuar/{login}

```json
{
  "cartao": "1234567890123456",
  "validade": "12/26",
  "bandeira": "VISA",
  "qtdParcelas": 3,
  "nome":"Joao Silva",
  "cvv":"254"

}
```
---

Exemplo de como fica o reterno do carrinho , após pagamento.

```json
{
    "id": "6601f961fbe6956e6519f9ff",
    "loginCliente": "teste23",
    "itensPedido": [
        {
            "id": 2,
            "nome": "TV 75",
            "descricao": "TV LG 75 polegadas",
            "quantidade": 1,
            "preco": 4115.99
        }
    ],
    "dataCompra": "2024-03-25T19:34:17.8845729",
    "valorTotal": 4115.99,
    "status": false,
    "pagamento": {
        "cartao": "1234567890123456",
        "validade": "12/26",
        "bandeira": "VISA",
        "nome": "Joao Silva",
        "cvv": "254",
        "qtdParcelas": 3
    }
}
```
---

<h2 align="center"> Como Executar o Projeto</strong> </h2>

Siga as etapas abaixo para executar o projeto em sua máquina local:

1. **Clone o Repositório: git clone https://github.com/ArleiPacanaro/Fase05.git
2. **Importe o Projeto na sua IDE:**

Abra o projeto em sua IDE favorita (por exemplo, IntelliJ IDEA, Eclipse) e importe-o como um projeto Maven.

3.**Execute o Docker Compose:**

Certifique-se de ter o Docker instalado em sua máquina. Navegue até o diretório raiz do projeto clonado e execute o seguinte comando: **docker compose up -d** para iniciar os contêineres do MongoDB, MySQL e Postgres.

4.**Inicie os Serviços:**

A partir da raiz do projeto altere para a pasta *ms-seguranca* e execute o seguinte comando **mvn spring-boot:run** após abra outra linha de comando e navegue para a pasta *ms-estoque* e execute novamente o comando **mvn spring-boot:run** após abra outra linha de comando e navegue para a pasta *ms-carrinho* e execute o comando **mvn spring-boot:run** pela ultima vez.

Após seguir essas etapas, o sistema estará em execução em sua máquina local e você poderá acessá-lo através dos endpoints especificados na documentação. Certifique-se de verificar se todos os serviços estão em execução corretamente antes de utilizar o sistema.

<h2 align="center"> Testes</strong> </h2>

Existe a pasta com as Collections do Postman caso não queria seguir os exemplos acima.

<h2 align="center"> Integrantes do Grupo – Grupo 39-29 </strong> </h2>

- Arlei Pacanaro Lepiani - RM 350113
- Igor Pereira Rocha Oliveira - RM 349895
- William Kaminski - RM 430025
- Diogo Henrique Valente - RM 348497
- Matheus Sena - RM 348505
