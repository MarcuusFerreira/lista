# Projeto da disciplina de Desenvolvimento Desktop - 2023/1

## Exercício de uma Lista de Compras Facilitada
_Uma ideia para solucionar o problema de ter que ficar sempre refazendo a lista de compras do mercado._

_Cada cliente possui nome, CPF, Data de nascimento, Usuário, Senha, CEP e listas de mercado._ 
_A Lista por sua vez é composta de Produtos._
_Cada Produto possui Nome, Setor, Marca, Nome do Produto, sua Unidade de Medida e sua Quantidade ou Peso._

### Camada Model
###### Modelagem E-R

1. Tabela CLIENTE:
2. Tabela LISTA:
3. Tabela PRODUTO:

###### Entidades

##TODO criar uma interface BaseDAO

1. Cliente: id(Integer), rua(String), numero(String), cep(String), cidade(String), estado(String) (<span><strong>&#10003;</strong></span>)
2. Lista:  id(Integer), nome(String), cpf(String), endereco(Endereco) (<span><strong>&#10003;</strong></span>)
3. Produto: id(Integer), ddd(String), numero(String), ativo(boolean), movel(boolean), dono(Cliente - pode ser null) (<span><strong>&#10003;</strong></span>)

###### Data Access Objects (DAOs)
###### Todo DAO conterá os seguintes métodos: 
* inserir(Entidade novoObjeto)
* atualizar(Entidade objetoParaAtualizar)
* excluir(int id)
* consultarPorId(int id) 
* consultarTodos()
* consultarComSeletor(Seletor seletor)


1. ClienteDAO (<span><strong>&#10003;</strong></span>)
2. ListaDAO  (<span><strong>&#10003;</strong></span>)
3. ProdutoDAO (<span><strong>&#10003;</strong></span>)

###### Business Objects (BOs)
* Classes que encapsulam as **regras de negócio** do sistema


1. ClienteBO:  (i) não deixar excluir cliente que possua telefone associado  (<span><strong>&#10003;</strong></span>), (ii) não deixar cadastrar cliente com CPF já usado  (<span><strong>&#10003;</strong></span>), (iii) não deixar cadastrar cliente sem endereço válido (<span><strong>&#10003;</strong></span>)

2. ListaBO: (i) não deixar excluir endereço que possua cliente associado (<span><strong>&#10003;</strong></span>), (ii) consultar CEP (TODO chamar ViaCep)

3. ProdutoBO: (i) manter a consistência entre "ativo" e o telefone possuir ou não um cliente associado (<span><strong>&#10003;</strong></span>)

### Camada Controller
##### Classes responsáveis por: 

* Receber dados ou objetos da camada de view

* Realizar validações

* Controlar o fluxo de telas

* Chamar a camada de model para persistências ou consultas de dados

* Classes Controller, Service ou Servlet (varia conforme a arquitetura)


1. EnderecoBO: validar campos obrigatórios antes de inserir/atualizar (<span><strong>&#10003;</strong></span>)

2. ClienteBO: validar campos obrigatórios antes de inserir/atualizar (<span><strong>&#10003;</strong></span>)

3. TelefoneBO: validar campos obrigatórios antes de inserir/atualizar

### Camada View
##### Camada com as classes/componentes responsáveis pela apresentação dos dados para o usuário

###### Tipos de telas mais comuns:

1.Tela Login:.
Com campos para inserir o Login e senha. E botões para Entrar ou se Cadastrar

2.Tela Principal:.
Com uma mensagem de Boas Vindas e contará com MenuBar para Navegação;

3.Tela de cadastro/atualização: campos (geralmente) organizados como um formulário

* PainelCadastroCliente
* PainelCadastroListas
* PainelCadastroProdutosAdministrador

3.Tela de listagem: (geralmente) uma tabela ou cards mostrando todos os itens buscados. Dispõe de opções para editar ou excluir um item selecionado

* PainelMostrarClientes 
* PainelMostrarListas 
* PainelMostrarProdutos 
