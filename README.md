GIT: https://github.com/ArleiPacanaro/TechChallenge05

Data 25.03

Pendências\ Dúvidas \ Sugestões :

	Acredito que tenho 70-75% desenvolvido, mas minha maquina não esta permitindo seguir com os testes

	1) Login e Registro de usuário, usando Spring Security para autenticação e autorização.
	ms-segurança
		1.1) Esta funcionando, tenho dúvidas se seria interessante colocar, cpf e email no cadastro de usuários.
                1.2) Talvez tirar o endpoint que lista todos usuários, senão ajustar para consulta a service ao inves da repository
                1.3) acham interessante termos o delete e update de usuários?
                1.4) Mudar de Statelles para If_required?

	2) Gestao de produtos
	ms-estoque
		2.1) Não sei se a arquitetura da segurança é a adequada, nunca fiz, no caso validamos o token 2 vezes, uma com uma service interna e
                     outra chamando a API de segurança que traz os Roles

	3) Carrinho e Pagamento 
		3.1) Não fiz a parte de pagamento que simula um cartão: Enum das BANDEIRAS... e Entidade dos atributos de Cartão
		 ** Professor colocou no grupo que não precisa mais da tela
                 Podemos usar no micrssoerviços, senão ficaram 4, pensei em ficar junto com o Carrinho, o que acham?
		3.2) Mal consegui testar da timeout do banco
		3.3) Não usei os DTOs - Controller e Service, refatorar neste ponto.
                3.4) Persistir nome do usuário no carrinho


Geral:
	1) Swaggwer
	2) Melhorar tratamento das exceptions
	3) Log
	4) Documentação
	** Testes não pediram.


Requisitos:

	1) Login e Registro de usuário, usando Spring Security para autenticação e autorização.

	2) Gestão de Itens, Os usuários administradores, terão acesso a uma tela de gestão de itens, basicamente o controle de cadastros
        e manutenção de itens, bem como seus preços.

	3) Carrinho de Compras,os usuários podem adicionar e remover itens do carrinho de compras , o carrinho deve ser persistente e associado ao usuario logado

	4) Pagamentos (simulação) implementar uma tela que simule o processo de pagamento , onde os usuários possam visualizar os itens do carrinho e concluir a compra
        

Técnicos
	utilizar os conceitos de microsserviços para cada funcionalidade acima

	sistema deve lidar com as sessões de usuários

	implementar as validações necessárias para garantir a segurança e integridade dos dados

	garanta a segurança dos endpoints dos microserviços usando Spring Security
