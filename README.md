# Sistema de E-Commerce com Microserviços

## Visão Geral

Este sistema de e-commerce foi desenvolvido para permitir que os usuários realizem cadastro, login, gerenciem itens e mantenham um carrinho de compras, além de simular o processo de checkout. O sistema é baseado no framework Spring Boot, utiliza Spring Security para autenticação e autorização, e é construído seguindo a arquitetura de microserviços, garantindo segurança e escalabilidade.

## Arquitetura de Microserviços

O sistema é composto por vários microserviços, cada um responsável por um conjunto de funções relacionadas:

* **MS-Segurança**: Gerencia o registro de usuários e a autenticação/autorização.
* **MS-Estoque**: Responsável pela gestão de itens e seus preços.
* **MS-Carrinho**: Permite aos usuários adicionar e remover itens do carrinho de compras, mantendo o estado do carrinho persistente e associado ao usuário.

Cada microserviço interage com seu próprio banco de dados para garantir a independência e a eficiência na gestão dos dados.
![Texto alternativo](doc/img/system.jpeg)

## Requisitos Técnicos

* Framework: Spring Boot.
* Segurança: Spring Security.
* Arquitetura: Microserviços.
* Banco de Dados: Independente para cada microserviço (Postgres, MySQL, Mongo).
* Documentação: Instruções de instalação e uso.

## Funcionalidades

### Login e Registro de Usuário

* Registro de usuários com validação de dados.
* Autenticação e autorização com Spring Security.

### Gestão de Itens

* Tela de administração para gestão de itens.
* CRUD de produtos no MS-Estoque.
* Atualização de estoque após compras.

### Carrinho de Compras e Pagamento

* Adição e remoção de itens do carrinho.
* Persistência do estado do carrinho.
* Simulação do processo de pagamento.

## Instruções de Instalação e Uso

// TODO : DO this part

## Endpoints

// TODO : DO this part

## Membros do time 

### Grupo 29:

* William Kaminski - RM 430025
* Diogo Henrique Valente - RM 348497
* Igor Pereira Rocha Oliveira - RM 349895
* Arlei Pacanaro Lepiani - RM 350113
* Matheus Sena - RM 348505
