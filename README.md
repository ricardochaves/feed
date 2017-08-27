# Feed

## Executando 

Primeiro clone o projeto.

`git clone https://github.com/ricardochaves/feed.git`


### Localmente
Você precisa o ambiente java configurado corretamente.

Execute no terminal `mvn exec:java ` na raiz do projeto.


### Docker

#### Gere uma imagem, execute na raiz do projeto (Diretório do Dockerfile).

`docker build -t <name> .`

Agora rode a imagem.

`docker run -it -p 90:8080 <name>`

Acesse localhost na porta 90.

Detalhe para o docker no `-it`, a imagem gerada por esse `Dockerfile` é para desenvolvimento e por isso se faz nescessário o `-it`. 

#### Você ainda pode usar a imagem do [hub.docker](https://hub.docker.com/r/ricardobchaves6/globo-feed/):

`docker run -it -p 90:8080 ricardobchaves6/globo-feed`

## Pontos expostos
Você poderá ver duas URLs *(Se estiver rodando com docker mude a porta para a porta exposta no docker host)*
- http://localhost:8080/myapp/feed
- http://localhost:8080/myapp/feedauth

A primeira URL é aberta a qualquer usuário e irá retornar o json **no formato correto** do feed. 

A segunda URL somente poderá ser acessada com um token de autenticação.

## Autenticação
Optei por uma segurança com JWT, gosto muito da parte de microservices e foi uma escolha feito por um gosto pessoal.

Dentro de `src.main.java.com.feedglobo.jwt` vocês podem ver todas as classes usadas no processo de criação e validação do token.

Basicamente eu criei uma anotação que leva a um filtro que é executado antes de qualquer metodo onde essa anotação esteja presente, esse filtro utiliza as classes do pacote jwt para verificar se o token é valido ou não, caso não seja ele retorna 401.

Não existe banco de dados nem cadastro de usuários, apenas coloquei um usuário admin com senha admin hard code mesmo.

Para se conseguir um token basta executar o seguinte post:

```
curl -d "username=admin&password=admin" -X POST http://localhost:8080/myapp/token

```

Após conseguir um token acesse o link `http://localhost:8080/myapp/feedauth` com o token no header `Authorization` da seguinte forma:

```
curl -H "Authorization: JWT <token>" http://localhost:8080/myapp/feedauth

```
## Testes
Para os teste eu fiz uma pequena cobertura, mas seguindo o principio utlizado você conseguir cobrir a solução totalmente.

### Injeção de dependência
Você pode ver a aplicação de injeção de dependencia na classe `FeedResource`:

```
	@Inject   
	public FeedResource(IXMLConvert converte) {
		this.converte = converte;
	}
```

Onde a anotação `@Inject` diz par o framework que ele deve injetar a Interface `IXMLConvert` no caso dela não existir na chamada do construtor.

Obviamente isso não funciona se não informamos ao mecanismo de injeção de dependência o que deve ser criado para essa Inteface. Isso foi feito na classe `Main`:

```
        // aqui é registrado a injeção de dependêcia.
        rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(XMLConvert.class).to(IXMLConvert.class);
            }
        });
```

A rotina de teste que se beneficia desse sistema de injeção de dependência pode ser visto na classe `MyResourceTest` dentro de `src.test.java.com.feedglobo`:

```
    /**
     * Testando o Resource apenas, com a injeção de depência usada aqui
     * é possível testar o metodo sem precisar baixar xml nem nada do tipo.
     */
    @Test
    public void testFeedResource() throws Exception {
    	//Preparando
    	FeedResource feed = new FeedResource(new BlankXMLConvert());

    	//Executando
     	String retorno = feed.getIt();
     	
     	//Verificando
    	assertEquals(retorno.toString(), "{'status':'sucesso'}");
    	
    }
```

O código acima você pode ver a criação de uma classe `BlankXMLConvert` que nada mais é do que uma casca feita apenas para testar o metodo `getIt()`, a classe é passada para o contrutor do `FeedResource` e o mesmo não vai fazer a injeção de depencia da classe `XMLConvert`.



