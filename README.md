# Projeto FEIFood - Programação orientada a objetos

**Nome:** João Vitor Alves Escudeiro 24.224.010-9

## 1. Início

O projeto **FEIFood** tem como objetivo principal a criação de uma plataforma de pedidos de alimentos, o projeto foi desenvolvido em Java utilizando o IDE NetBeans e a biblioteca Swing JFrame.

Para a interface, o usuário pode realizar as seguintes funções:

*   **Cadastro e Login de Usuário:** Gerenciamento de contas de usuário.
*   **Busca e Seleção de Alimentos:** Buscar alimentos e adicioná-los ao pedido.
*   **Gerenciamento de pedidos:** Finalizar, avaliar ou excluir pedidos.

Foi utilizado o **PostgreSQL** para fazer o gerenciamento do banco de dados e manter as informações após o fechamento do programa.

## 2. Arquitetura do projeto

As diversas funcionalidades foram separadas em pacotes diferentes, isso foi feito para facilitar a manutenção e a compreensão do código, a estrutura seguiu o padrão Model-View-Controller (MVC), complementado por uma camada de Data Access Object (DAO) para a comunicação com o banco de dados.

### Estrutura dos pacotes:

*   **`model`:** Modelo de Dados, contém as classes que representam as entidades de negócio e a lógica de dados. `Usuario`, `Alimento` (Abstrata), `Bebida`, `Comida`, `Pedido`, `PedidoItem`.

O pacote `model` implementa os conceitos de POO:

**Herança e Polimorfismo:** A classe abstrata `Alimento` define a estrutura básica de um item (ID, nome, preço, descrição, estabelecimento). As classes `Comida` e `Bebida` herdam de `Alimento`, adicionando atributos específicos, exemplo `vegetariana` em `Comida`, `alcoolica` em `Bebida`.
**Interfaces:** A interface `Imposto_Alcool` é implementada pela classe `Bebida`, demonstrando a aplicação de regras de negócio específicas, com o cálculo de imposto de 25% sobre bebidas alcoólicas.
**Gerenciamento de Pedidos:** As classes `Pedido`, `PedidoItem` e `PedidoAtual` gerenciam o ciclo de vida de um pedido, desde a seleção dos itens até a finalização.

*   **`view`:** Interface do Usuário, contém as classes `JFrame` que definem a interface gráfica e a interação visual com o usuário. `Inicio`, `LoginUsuario`, `CadastroUsuario`, `Menu`, `BuscarAlimento`, `MeuPedidos`.
*   **`controller`:** Controlador, contém a lógica de negócio, mediando a interação entre a View e o Model. `ControleLogin`, `ControleCadastro`, `ControleMenu`.
*   **`dao`:** Acesso a Dados, responsável por toda a comunicação com o banco de dados. `Conexao`, `UsuarioDAO`, `AlimentoDAO`, `PedidoDAO`.

## 3. Funcionalidades

O sistema simula o fluxo completo de um usuário em uma plataforma de delivery, desde o acesso inicial até a conclusão de um pedido.

### Gerenciamento de usuários:

*   **Início (`Inicio.java`):** Tela inicial do programa, nela o usuário tem a opção de logar ou se cadastrar.
*   **Cadastro de Usuário (`CadastroUsuario.java`):** Nesta tela o usuário entra com os seus dados, e o `ControleCadastro` e `UsuarioDAO` garantem que essas informações sejam salvas no banco de dados.
*   **Login de Usuário (`LoginUsuario.java`):** Nesta tela, o usuário entra com seu usuário e senha, e é autenticado, sendo levado ao menu principal do programa.

### Busca e seleção de alimentos:

Depois do login, o usuário tem 3 opções, Buscar alimentos, Meus pedidos e Finalizar pedido.

*   **Busca de Alimentos (`BuscarAlimento.java`):** Nessa tela, o usuário pode buscar pelo alimento que deseja, e assim, adicioná-lo ao pedido. Esta busca é feita através do `AlimentoDAO`, que interage com o banco de dados. A lógica de adição e o cálculo de subtotais são gerenciados pelas classes `PedidoAtual` e `PedidoItem`.

### Finalização do pedido:

*   **Vizualização do pedido (`MeusPedidos.java`):** Nesta tela, o usuário consegue ver os alimentos que foram adicionados anteriormente ao pedido na tela de buscar alimentos, e o valor total dele.
*   **Finalização:** Ao finalizar o pedido no menu, ele é registrado no bando de dados através do `PedidoDAO`, que associa ao usuário logado.

## 4. Interface do usuário

A interface do programa foi feita utilizando os **JFrames** do NetBeans, e seus componentes, ou seja, `JtextField`, `JButton` e `JTable`. Esses itens nos deixam criar uma interface clara e funcional.


## 5. Persistência de dados

*   **Conexão (`Conexao.java`):** Esta classe tem como objetivo fazer a conexão com o banco de dados PostgreSQL. As credenciais de acesso estão no próprio código.
*   **DAOS:** Classes como `UsuarioDAO`, `AlimentoDAO` e `PedidoDAO` encapsulam a lógica SQL para operações de CRUD, para que a camada de `controller` não precise conhecer os detalhes da implementação do banco de dados.

## 6. Conclusão

A partir do desenvolvimento deste projeto foram aplicados com sucesso vários conceitos de desenvolvimento de software e programação orientada a objetos, incluindo arquitetura MVC e persistência de dados com PostgreSQL. A estrutura atual do projeto, com a separação de packages, permite futuras expansões.


