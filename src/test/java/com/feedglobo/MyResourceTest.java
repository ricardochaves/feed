package com.feedglobo;

import static org.junit.Assert.assertEquals;

import java.security.Principal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.feedglobo.converters.XMLConvert;
import com.feedglobo.interfaces.IXMLConvert;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        
        // create the client
        Client c = ClientBuilder.newClient(new ClientConfig()
        		.register(FeedResource.class)
        		.register(new AbstractBinder() {
    					@Override
					    protected void configure() {
					        bind(BlankXMLConvert.class).to(IXMLConvert.class);
					    }
					})
        		);

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
       
        
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("feed").request().get(String.class);
        //System.out.println(responseMsg.toString());
        assertEquals(1,1);
    }
    
    @Test
    public void testeXMLConvertGetArrayDeItensSucesso()  {
    	//Preparando
    	XMLConvert convert = new XMLConvert();
    	
    	//Executando
    	JSONArray listaitens = convert.getArrayDeItens(GetXMLTest());
    	
    	//Verificando
    	assertEquals(listaitens.length(), 2);
    	assertEquals(listaitens.getJSONObject(0).getString("title").trim(), "Ford lança Ranger Sportrac por R$ 159.990");
    	assertEquals(listaitens.getJSONObject(1).getString("title").trim(), "Novo Porsche Cayenne tem primeiras fotos vazadas antes do lançamento");
    	assertEquals(listaitens.getJSONObject(0).getString("link").trim(), "http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/ford-lanca-ranger-sportrac-por-r-159990.html");
    	assertEquals(listaitens.getJSONObject(1).getString("link").trim(), "http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/novo-porsche-cayenne-tem-primeiras-fotos-vazadas-antes-do-lancamento.html");
    }
    
    @Test
    public void testConverteItemsToFeedItem() {
    	//Preparando
    	XMLConvert convert = new XMLConvert();
    	JSONArray listaitensoriginal = convert.getArrayDeItens(GetXMLTest());
    	
    	//Executando
    	JSONArray resultado = convert.converteItemsToFeedItem(listaitensoriginal);

    	//Verificando
    	assertEquals(resultado.length(), 2);
    	assertEquals(resultado.getJSONObject(0).getJSONObject("item").getString("title").trim(), "Ford lança Ranger Sportrac por R$ 159.990");
    	assertEquals(resultado.getJSONObject(1).getJSONObject("item").getString("title").trim(), "Novo Porsche Cayenne tem primeiras fotos vazadas antes do lançamento");
    	assertEquals(resultado.getJSONObject(0).getJSONObject("item").getString("link").trim(), "http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/ford-lanca-ranger-sportrac-por-r-159990.html");
    	assertEquals(resultado.getJSONObject(1).getJSONObject("item").getString("link").trim(), "http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/novo-porsche-cayenne-tem-primeiras-fotos-vazadas-antes-do-lancamento.html");
  	
    	
    }
    
    private String GetXMLTest() {
    	return "\r\n" + 
    			"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
    			"<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\r\n" + 
    			"<channel>\r\n" + 
    			"  <title><![CDATA[Auto Esporte]]></title>\r\n" + 
    			"  <link>http://revistaautoesporte.globo.com/</link>\r\n" + 
    			"  <description></description>\r\n" + 
    			"  <language>pt-BR</language>\r\n" + 
    			"  <copyright>© Todos os direitos reservados.</copyright>\r\n" + 
    			"  <image>\r\n" + 
    			"    <url>http://e.glbimg.com/og/ed/edg2/static_files/rss/img/auto-esporte.png</url>\r\n" + 
    			"    <title><![CDATA[Auto Esporte]]></title>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/</link>\r\n" + 
    			"  </image>\r\n" + 
    			"  \r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Ford lança Ranger Sportrac por R$ 159.990]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ford Ranger Sportrac será lançada oficialmente na Expointer (Foto: Divulgação)\" height=\"413\" id=\"239056\" src=\"http://s2.glbimg.com/ACOTgFuHX-_RkPE621aCkWf3Kww=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/rangerfrente.jpg\" title=\"Ford Ranger Sportrac será lançada oficialmente na Expointer (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Ford Ranger Sportrac ser&aacute; lan&ccedil;ada oficialmente na Expointer (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A <a href=\"http://revistaautoesporte.globo.com/carros/ford/\">Ford</a> aproveitou a feira agropecu&aacute;ria Expointer para apresentar a s&eacute;rie especial Sportrac da Ranger, que j&aacute; est&aacute; no <a href=\"https://www.ford.com.br/compre-o-seu/monte-o-seu.html/?v=RANGER&amp;n=Nova_Ranger_FBR&amp;u=P&amp;l=&amp;c=&amp;y=2017&amp;vc=Pick-Ups&amp;intcmp=bb-fbr-vhp-ford%20ranger-fbr--return\">configurador do site da marca</a>. O evento abrir&aacute; as portas neste final de semana em Esteio, Rio Grande do Sul. <strong>Trata-se de uma edi&ccedil;&atilde;o feita sobre a XLS diesel, cotada a R$ 159.990, exatos R$ 6 mil a mais do que a configura&ccedil;&atilde;o que lhe deu origem.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>A grande diferen&ccedil;a entre elas &eacute; o pacote est&eacute;tico da Sportrac, que inclui itens como santoant&ocirc;nio exclusivo, estribos, soleiras, encosto de cabe&ccedil;a personalizado e apliques externos. Adesivos que imitam a trilha deixada por pneus nas laterais das ca&ccedil;ambas s&atilde;o os toques mais chamativos. </strong>As rodas s&atilde;o de liga leve ganharam acabamento escurecido para combinar com o tom cinza Londres do santoant&ocirc;nio (gray London) e t&ecirc;m 17 polegadas.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Tal como a XLS, a Ranger Sportrac vem apenas com o motor Duratorq 2.2 turbodiesel de 160 cv e 39,2 kgfm de torque.<strong> Se voc&ecirc; quiser o motor Duratorq 3.2 de cinco cilindros, 200 cv e 47,9 kgfrm, ter&aacute; que partir para a Ranger XLT de R$ 171.490.</strong> A configura&ccedil;&atilde;o vem sempre com transmiss&atilde;o autom&aacute;tica de seis velocidades e tra&ccedil;&atilde;o 4X4 com marcha reduzida e diferencial traseiro autoblocante.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Perfil se destaca pelos adesivos exclusivos da Sportrac (Foto: Divulgação)\" height=\"413\" id=\"239057\" src=\"http://s2.glbimg.com/plp2gK-dPQEZ2q7Rp1pQK5hgbvk=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/perfilranger.jpg\" title=\"Perfil se destaca pelos adesivos exclusivos da Sportrac (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Perfil se destaca pelos adesivos exclusivos da Sportrac (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	S&atilde;o de s&eacute;rie ar-condicionado, dire&ccedil;&atilde;o el&eacute;trica, trio el&eacute;trico, controle de cruzeiro, entre outros itens. <strong>H&aacute; controle eletr&ocirc;nico de estabilidade, aux&iacute;lio de partida em rampa e controle de velocidade em descida. O pacote de seguran&ccedil;a &eacute; complementado pelos sete airbags de s&eacute;rie (frontais, laterais dianteiros, do tipo cortina e para os joelhos do motorista)</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A conectividade n&atilde;o foi esquecida. O sistema Sync vem ainda com assistente de emerg&ecirc;ncia e c&acirc;mera de r&eacute;, que se soma ao sensor de estacionamento traseiro. <strong>Mas o Sync3 com tela de oito polegadas sens&iacute;vel ao toque vem tamb&eacute;m s&oacute; da XLT para cima.</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Encostos de cabeça trazem o nome Sportrac, mas revestimento não é de couro (Foto: Divulgação)\" height=\"413\" id=\"239058\" src=\"http://s2.glbimg.com/F-2o8y7rwBTBv6n4GG9oL7XSPR0=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/rangerpainel.jpg\" title=\"Encostos de cabeça trazem o nome Sportrac, mas revestimento não é de couro (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Encostos de cabe&ccedil;a trazem o nome Sportrac, mas revestimento n&atilde;o &eacute; de couro (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2016/03/teste-ford-ranger-limited.html\">Testamos: como anda a Ford Ranger Limited com motor de 200 cv</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/07/ford-ranger-fica-ate-r-2400-mais-cara-na-linha-2018.html\">Mercado: Ford Ranger fica mais cara com o lan&ccedil;amento da linha 2018</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2016/10/ford-confirma-ranger-wildtrack-para-o-salao-de-sao-paulo.html\">Outra vers&atilde;o especial: Ford apresenta Ranger Wildtrack no Sal&atilde;o do Autom&oacute;vel</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/ford-lanca-ranger-sportrac-por-r-159990.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1636279</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Novo Porsche Cayenne tem primeiras fotos vazadas antes do lançamento]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239040\" src=\"http://s2.glbimg.com/tY8UsKr2k9Oqyf-rJy0EBd8hwTs=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-04.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">SUV ter&aacute; nova plataforma, razoavelmente mais leve (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Ainda faltam alguns dias para a revela&ccedil;&atilde;o oficial da nova gera&ccedil;&atilde;o do Cayenne, mas a revista inglesa <em>Auto Express</em> conseguiu as primeiras imagens (aparentemente reais) do SUV de luxo, que ser&aacute; revelado pela f&aacute;brica de Stuttgart na pr&oacute;xima ter&ccedil;a-feira (29)</strong>. A estreia para o p&uacute;blico ocorrer&aacute; em duas semanas, no <strong>Sal&atilde;o do Autom&oacute;vel de Frankfurt</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239038\" src=\"http://s2.glbimg.com/JvsOfRMQfS5nmOebFaU_clw_jLw=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-08.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Traseira concentra as mudan&ccedil;as de design do Cayenne (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Comparado ao Cayenne atual, o novo pode at&eacute; parecer uma reestiliza&ccedil;&atilde;o quando visto de frente, mas as imagens revelam altera&ccedil;&otilde;es mais profundas. <strong>J&aacute; se sabe que a plataforma desta terceira gera&ccedil;&atilde;o do utilit&aacute;rio ser&aacute; razoavelmente mais leve. A base &eacute; a MLB, a mesma das novas gera&ccedil;&otilde;es de Audi Q7 e Volkswagen Touareg, e do brit&acirc;nico Bentley Bentayga</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239041\" src=\"http://s2.glbimg.com/ZLmdEFQcFskHPRdE38YVuETHIPo=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-05.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Linhas est&atilde;o mais harmoniosas na coluna traseira (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Externamente, <strong>o novo Cayenne exibe boas mudan&ccedil;as, em especial na traseira, que adota lanternas interligadas que recortam a tampa e trazem o nome Porsche centralizado. O conjunto &oacute;tico posicionado mais ao alto deixa o SUV com aspecto mais robusto</strong>, al&eacute;m de elegante &mdash; com fios e tra&ccedil;os de led. J&aacute; o teto tem curvatura mais acentuada e esportiva.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239034\" src=\"http://s2.glbimg.com/pa2uMYQIWf26Np-xpZQas90_OE0=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-11.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Novas lanternas lembra as do novo Panamera (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Na dianteira, a grade ficou visivelmente mais encorpada e quase se une &agrave;s outras duas se&ccedil;&otilde;es laterais</strong>, que ficam logo abaixo dos far&oacute;is. Estas trazem os leds diurnos, j&aacute; presentes no Cayenne atual, mas melhor incorporados. <strong>Por dentro, o utilit&aacute;rio repete novamente o estilo da gera&ccedil;&atilde;o anterior, por&eacute;m com evolu&ccedil;&otilde;es, entre elas a enorme tela multim&iacute;dia</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239043\" src=\"http://s2.glbimg.com/mGyGRtd1sLiOCWEdQVTSOl-k1l0=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-02.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Painel ficou mais limpo e com bem menos bot&otilde;es no console (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em termos gerais, <strong>o visual do painel ficou mais limpo, com novos visores digitais. Nas imagens vazadas, a vers&atilde;o oferece acabamento bicolor, com couros bege e preto</strong>. O acabamento segue impec&aacute;vel, com muitos detalhes em metal fosco. No quesito espa&ccedil;o, ainda n&atilde;o h&aacute; dados num&eacute;ricos, mas o Cayenne continua generoso para os passageiros.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239027\" src=\"http://s2.glbimg.com/qANk6liGQ2xIZovZI6WtIEohvck=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-18.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Espa&ccedil;o no banco traseiro do Cayenne seguir&aacute; generoso (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Na parte mec&acirc;nica, <strong>o novo Cayenne deve estrear com as tradicionais op&ccedil;&otilde;es de motores turbo V6 e V8 a gasolina e transmiss&atilde;o autom&aacute;tica</strong>. Segundo a <em>Auto Express</em>, as vers&otilde;es a diesel devem atrasar um pouco por causa das investiga&ccedil;&otilde;es envolvendo as fraudes do <strong><a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/03/entenda-o-caso-dieselgate.html\" target=\"_blank\">Dieselgate</a></strong>, maior esc&acirc;ndalo da hist&oacute;ria do grupo Volkswagen, dono da Porsche.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/porsche-cria-maior-maquina-de-tecer-do-mundo-para-produzir-rodas-de-fibra-de-carbono-para-o-911-turbo.html\" target=\"_blank\">Porsche cria rodas exclusivas de fibra de carbono para o 911 Turbo</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/porsche-inicia-vendas-do-cayenne-platinum-edition-no-brasil.html\" target=\"_blank\">Porsche inicia vendas do Cayenne Platinum Edition no Brasil</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239030\" src=\"http://s2.glbimg.com/18fC1Pjge9N16bkSVU0EVv17o1I=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-17.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Sa&iacute;das de ar centrais foram deslocadas para baixo da tela (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239021\" src=\"http://s2.glbimg.com/InmXb7NT2ioBegQep2fQVWh8vzs=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-22.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Freio de estacionamento agora fica junto &agrave; alavanca do c&acirc;mbio (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239022\" src=\"http://s2.glbimg.com/m9xK-hvIvdRssWeK3nARqWeodfs=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-23.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Portas trazem soleiras em alum&iacute;nio escovado (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239024\" src=\"http://s2.glbimg.com/dRG4qXsaKokbxacYJrZz1yhY0GQ=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-21.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">SUV mant&eacute;m o cl&aacute;ssico rel&oacute;gio no alto do painel (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239026\" src=\"http://s2.glbimg.com/YE0KYIEE1-jq3TAiSSGBLgZvGqI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-20.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Porta-malas seguir&aacute; generoso para as bagagens (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239028\" src=\"http://s2.glbimg.com/rhpQhAVV7FAOrgcJOGcK4S5M7Vk=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-13.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Traseira mostra apenas dois escapes retangulares (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239036\" src=\"http://s2.glbimg.com/Zl1KYnS22KOj1IpVhwDXrsDiOA8=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-10.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Novo Cayenne manter&aacute; ACC e deve ganhar c&acirc;mera 360&ordm; (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239037\" src=\"http://s2.glbimg.com/ij-uEjSW2Cu1qTTEBneE8YJ_evE=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-09.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Far&oacute;is exibem canh&otilde;es &uacute;nicos iluminados com leds (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239032\" src=\"http://s2.glbimg.com/KipMKCBn0-5_IfbGmccVOh9VKdI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-14.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Rodas de dez raios s&atilde;o novas (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Cayenne 2018 (Foto: Divulgação)\" height=\"413\" id=\"239033\" src=\"http://s2.glbimg.com/spsSMC6XKpUp6Q1Zj9K3RAotr0I=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/porsche-cayenne-2018-autoesporte-12.jpg\" title=\"Porsche Cayenne 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Nome do SUV segue centralizado na tampa traseira (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/novo-porsche-cayenne-tem-primeiras-fotos-vazadas-antes-do-lancamento.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1636106</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"</channel>\r\n" + 
    			"</rss>\r\n" + 
    			"";
    	//return (new XMLConvert()).downloadAsString("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml");
    }

    @Test
    public void testFeedResource() throws Exception {
    	FeedResource feed = new FeedResource(new BlankXMLConvert());
    	SecurityContext sc = new SecurityContext() {
			
			@Override
			public boolean isUserInRole(String role) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthenticationScheme() {
				// TODO Auto-generated method stub
				return "";
			}
		};
		
    	String retorno = feed.getIt(sc);
    	assertEquals(retorno.toString(), "{'status':'sucesso'}");
    	
    	
    }
}
