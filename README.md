<h1> Cliente Chat</h1>

<h4>Finalidade</h4>

Esse projeto é um trabalho da matéria de Redes de Computadores, proposto pela faculdade. O projeto tem a finalidade de colocar em prática os conceitos de comunicação TCP/IP entre um cliente e um servidor.

------------
<h4>Servidor</h4>

Esse projeto necessita de um servidor para funcionar. O projeto do servidor pode ser conferido [aqui](https://github.com/talesxavier1/Chat_Server_Socket_Java).

------------
<h4>Programas utilizados</h4>

-  [Eclipse 2019-12](https://www.eclipse.org/downloads/packages/release/2019-12 "Eclipse 2019-12")
-  [SQLiteStudio 3.3.3 ](https://sqlitestudio.pl/ "SQLiteStudio 3.3.3")

------------

<h4>Ferramentas utilizadas</h4>

-  [Java SE Development Kit 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html "Eclipse 2019-12")
-  [SQLite 3.35.5 ](https://www.sqlite.org/index.html "SQLite 3.35.5")
-  [JTattoo 1.6.13](http://www.jtattoo.net/ "JTattoo 1.6.13")

------------


#### Funcionalidades

O sistema possui as funcionalidades básicas para um cliente de chat.

- [Funcionalidades](#)
  * [Cadastro](#Cadastro)
  * [Adicionar novo contato](#Adicionar-novo-contato)
  * [Nova conversa](#Nova-conversa)
  * [Deletar uma conversa](#Deletar-uma-conversa)
  * [Mensagem de desconhecido](#Mensagem-de-desconhecido)
  * [Mensagem off-line](#Mensagem-off-line)
  * [Mensagem off-line de desconhecido](#Mensagem-off-line-de-desconhecido)
  * [Excluir contato](#Excluir-contato)

<br>

------------
#### Banco de Dados.

O banco de dados que foi usado no projeto foi o [SQLite 3.35.5](https://www.sqlite.org/index.html "SQLite 3.35.5"), um banco de dados local. Todos os dados dos clientes, contatos e conversas são armazenados no arquivo [BancoDeDados.db](https://github.com/talesxavier1/Chat_Cliente_Socket_Java/blob/main/DB/BancoDeDados.db). O banco de dados possui a estrutura abaixo.

<a href="https://imgur.com/lzDh6CN"><img src="https://i.imgur.com/lzDh6CN.png" title="source: imgur.com" /></a>

------------

##### Cadastro.

A tela de cadastro permite o cadastro das credenciais do usuário e uma foto de perfil. Caso já tenha um usuário cadastrado com o e-mail inserido, o usuário será notificado.

<a href="https://imgur.com/nblobQr"><img src="https://i.imgur.com/nblobQr.gif" title="source: imgur.com" /></a>
<br>


##### Adicionar novo contato.

É possível adicionar um contato fazendo uma pesquisando com o nome do usuário.

<a href="https://imgur.com/pN8cZfk"><img src="https://i.imgur.com/pN8cZfk.gif" title="source: imgur.com" /></a>

<br>

##### Nova conversa.

Após um contato ser adicionado, é possível iniciar uma conversa.

<a href="https://imgur.com/JAZG3vk"><img src="https://i.imgur.com/JAZG3vk.gif" title="source: imgur.com" /></a>

<br>

##### Deletar uma conversa.

É possível deletar uma conversa. Quando uma conversa é deletada, todas as mensagens são apagadas.

<a href="https://imgur.com/VBEEheS"><img src="https://i.imgur.com/VBEEheS.gif" title="source: imgur.com" /></a>

<br>

##### Mensagem de desconhecido.

É possível receber uma mensagem de um desconhecido. Nesse caso, podemos conversar normalmente com ele, adicionar ele aos contatos ou excluir a conversa. O nome e a foto do desconhecido só ficam aparentes após ele ser adicionado.

<a href="https://imgur.com/4FpOy5t"><img src="https://i.imgur.com/4FpOy5t.gif" title="source: imgur.com" /></a>

<br>

##### Mensagem off-line.

É possível receber uma mensagem de outro usuário, mesmo estando off-line.

<a href="https://imgur.com/kAmomIe"><img src="https://i.imgur.com/kAmomIe.gif" title="source: imgur.com" /></a>

<br>

##### Mensagem off-line de desconhecido.

Assim como a mensagem off-line, é possível receber uma mensagem de um desconhecido, mesmo estando off-line.

<a href="https://imgur.com/KFEOLqq"><img src="https://i.imgur.com/KFEOLqq.gif" title="source: imgur.com" /></a>

<br>

##### Excluir contato.

É possível excluir um contato da lista de contatos. Nesse caso, a conversa e as mensagens são excluídas junto com o contato.

<a href="https://imgur.com/QBYGZyJ"><img src="https://i.imgur.com/QBYGZyJ.gif" title="source: imgur.com" /></a>




