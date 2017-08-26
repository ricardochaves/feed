package com.feedglobo;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.feedglobo.converters.XMLConvert;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

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
        assertEquals("a", "a");
    }
    
    @Test
    public void testeXMLConvertGetArrayDeItensSucesso()  {
    	//Preparando
    	XMLConvert convert = new XMLConvert();
    	
    	//Executando
    	JSONArray listaitens = convert.getArrayDeItens(GetXMLTest());
    	
    	//Verificando
    	assertEquals(listaitens.length(), 19);
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
    	assertEquals(resultado.length(), 19);
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
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Podcast Central Eletrônica #05: quando e como os carros vão voar?]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"PAL-V Liberty (Foto: Divulgação)\" height=\"413\" id=\"207260\" src=\"http://s2.glbimg.com/S3MrGRjziNOrGqKwJpJHT0AqwbQ=/620x413/e.glbimg.com/og/ed/f/original/2017/02/20/palv_liberty_-4.jpg\" title=\"PAL-V Liberty (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">PAL-V Liberty (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Tantas empresas est&atilde;o cravando que v&atilde;o desenvolver um carro voador que chegou a hora de discutirmos: quem vai fazer o carro voar? Ou melhor: o carro vai, mesmo, voar algum dia?</strong> Sim, o sonho dos anos 1950 e 1960 voltou a ser sonhado hoje em dia - e discutido no Podcast Central Eletr&ocirc;nica #05. Alberto Cataldi e Guilherme Blanco Muniz tamb&eacute;m falam sobre outras previs&otilde;es feitas h&aacute; 50 anos que n&atilde;o deram t&atilde;o certo assim... &Eacute; o assunto do quadro <strong>Futurologia</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em <strong>A Semana de Elon Musk</strong>, novidade de &uacute;ltima hora: o <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/caminhao-eletrico-da-tesla-deve-ser-capaz-de-percorrer-ate-500-km-sem-recarga.html\">caminh&atilde;o aut&ocirc;nomo da Tesla j&aacute; tem data para aparecer</a>. Entenda qual ser&aacute; a proposta da novidade e acompanhe os &uacute;ltimos medos do chef&atilde;o da Tesla quando o assunto &eacute; intelig&ecirc;ncia artificial.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	No quadro <strong>Sem As M&atilde;os</strong>, falamos sobre o medo que a popula&ccedil;&atilde;o ainda tem de entrar em um carro aut&ocirc;nomo. E como isso pode ser resolvido.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em<strong> As &Uacute;ltimas</strong>: os <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/empresario-quer-criar-empresa-de-taxis-fluviais-na-franca.html\">taxis fluviais</a> que est&atilde;o sendo criados na Fran&ccedil;a, os <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/audi-desenvolve-com-chinesa-hanergy-veiculo-com-placas-solares-no-teto.html\">Audi com placas solares no teto</a>, o <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/uber-tem-prejuizo-de-us-645-miloes-no-segundo-trimestre.html\">preju&iacute;zo um pouco menor da Uber</a> e <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/o-que-acontece-se-eu-apertar-o-freio-de-estacionamento-em-alta-velocidade.html\">o que acontece se acionar o freio de estacionamento eletr&ocirc;nico em movimento</a>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Ou&ccedil;a e assine o Central Eletr&ocirc;nica:</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	No <a href=\"https://soundcloud.com/autoesporte/\">SoundCloud</a></p>\r\n" + 
    			"<p>\r\n" + 
    			"	No <a href=\"https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewPodcast?id=1269607854\">iTunes</a></p>\r\n" + 
    			"<p>\r\n" + 
    			"	Ou <a href=\"http://feeds.soundcloud.com/users/soundcloud:users:15166583/sounds.rss\">siga via RSS</a></p>\r\n" + 
    			"<p>\r\n" + 
    			"	E deixe seu coment&aacute;rio nesta p&aacute;gina, nas nossas redes sociais ou pelo email <a href=\"mailto:autoesporte@edglobo.com.br?subject=Podcast%20Central%20Eletr%C3%B4nica\">autoesporte@edglobo.com.br</a></p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>D&ecirc; play e ou&ccedil;a:</strong></p>\r\n" + 
    			"<div class=\"youtube componente_materia\">\r\n" + 
    			"	<iframe frameborder=\"no\" height=\"204\" scrolling=\"no\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/339490184&amp;color=ff5500&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true\" width=\"620\"></iframe></div>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-04-carros-autonomos-cavalos-e-um-delorean-voador.html\">PODCAST CENTRAL ELETR&Ocirc;NICA #04: CARROS AUT&Ocirc;NOMOS, CAVALOS E UM DELOREAN VOADOR</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-03-uber-versus-o-mundo-e-historia-do-motorista-fantasiado-de-banco.html\">Central Eletr&ocirc;nica #03: Uber versus o mundo e a hist&oacute;ria do motorista fantasiado de banco</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-02-tesla-model-3-no-asfalto-hyperloop-debaixo-da-terra-e-volocopter-no-ceu.html\">Central Eletr&ocirc;nica #02: TESLA MODEL 3 NO ASFALTO, HYPERLOOP DEBAIXO DA TERRA E VOLOCOPTER NO C&Eacute;U</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/07/podcast-central-eletronica-01-telinha-multimidia-nao-pode-ser-mais-ou-menos.html\">Central Eletr&ocirc;nica #01: Telinha multim&iacute;dia n&atilde;o pode ser mais ou menos</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-05-quando-e-como-os-carros-vao-voar.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1636105</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Frenagem automática e o futuro do automóvel no programa Auto Esporte]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"video componente_materia\" height=\"360\" id=\"6104012\" style=\"background: url(https://s01.video.glbimg.com/640x360/6104012.jpg) no-repeat top center / 640px 360px; width:640px !important; height:360px !important;\" width=\"640\">\r\n" + 
    			"	<div style=\"background: url(http://e.glbimg.com/og/ed/edg2/static_files/materia/img/placeholder_video_transparente.png) no-repeat top center;\">\r\n" + 
    			"		&nbsp;</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	Nesta semana, no programa Auto Esporte, voc&ecirc; confere os detalhes da frenagem autom&aacute;tica, tecnologia que salva vidas e poder&aacute; se tornar obrigat&oacute;ria em breve no mundo. Tamb&eacute;m conhece mais sobre o futuro do autom&oacute;vel com um conceito urbano e sabe mais sobre o novo diagn&oacute;stico que identifica os poss&iacute;veis problemas do seu carro a partir dos ru&iacute;dos.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Frenagem automática no programa Auto Esporte (Foto: Reprodução)\" height=\"413\" id=\"239015\" src=\"http://s2.glbimg.com/uT37XZ6WFYrm75SBwVeSxkHl36U=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/chamada.jpg\" title=\"Frenagem automática no programa Auto Esporte (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Frenagem autom&aacute;tica no programa Auto Esporte (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/frenagem-automatica-e-o-futuro-do-automovel-no-programa-auto-esporte.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1635949</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Fotos do novo Suzuki Jimny vazam na internet]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Novo Suzuki Jimny vaza em imagens de apresentação (Foto: Reprodução)\" height=\"413\" id=\"239000\" src=\"http://s2.glbimg.com/P4-SNycMhh8EB6kMEReO-bEdrQ8=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/suzukifrwente.jpg\" title=\"Novo Suzuki Jimny vaza em imagens de apresentação (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Novo Suzuki Jimny vaza em imagens de apresenta&ccedil;&atilde;o (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A terceira gera&ccedil;&atilde;o do <a href=\"http://revistaautoesporte.globo.com/carros/suzuki/\">Suzuki</a> Jimny foi lan&ccedil;ada em 1998, ou seja, est&aacute; mais do que na hora do jipinho ser renovado completamente. E n&atilde;o &eacute; que as primeira imagens da nova encarna&ccedil;&atilde;o vazaram na internet? <strong>As fotos de uma apresenta&ccedil;&atilde;o privada mostram que o Jimny voltar&aacute; a ter um estilo retr&ocirc;, bem parecido com o primeiro modelo de 1970. O lan&ccedil;amento &eacute; esperado para o Sal&atilde;o de T&oacute;quio, cuja abertura ser&aacute; no pr&oacute;ximo dia 27 de outubro.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	A marca d&#39;&aacute;gua das fotos exibe os dizeres Suzuki Design e objetos &agrave; frente da c&acirc;mera obstru&iacute;ram um pouco a vis&atilde;o, contudo, d&aacute; para ver bastante coisa. <strong>A come&ccedil;ar pelas formas retil&iacute;neas, j&aacute; entregue pelos flagras dos prot&oacute;tipos publicadas pela imprensa internacional.</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Perfil indica que não ficaram painéis de carroceria do antecessor, é um novo Jimny! (Foto: Reprodução)\" height=\"413\" id=\"239001\" src=\"http://s2.glbimg.com/Yh13FxdxHS4DltKXYx78RRPoJaU=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/perfilsuzuji.jpg\" title=\"Perfil indica que não ficaram painéis de carroceria do antecessor, é um novo Jimny! (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Perfil indica que n&atilde;o ficaram pain&eacute;is de carroceria do antecessor, &eacute; um novo Jimny! (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Aqueles que lembram da chegada oficial da Suzuki ao Brasil tamb&eacute;m n&atilde;o deixar&atilde;o de reconhecer toques do jipinho Samurai na nova gera&ccedil;&atilde;o. <strong>A frente mais achatada &eacute; um exemplo, tal como as vidros quadradinhos t&iacute;picos da configura&ccedil;&atilde;o fechada do antepassado.&nbsp;</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	Outro toque &agrave; moda antiga &eacute; o rebaixamento da linha dos vidros nas portas dianteiras, uma maneira de melhorar a visibilidade dos retrovisores externos e tamb&eacute;m dar uma m&atilde;o nas manobras lentas do fora de estrada.<strong> O estepe pendurado na tampa de abertura lateral libera espa&ccedil;o no porta-malas ex&iacute;guo, enquanto as lanternas incrustadas no para-choque traseiro poderia ter sido retirada de um dos Jimny anteriores.</strong> At&eacute; as bordas das soldas no teto permanecem l&aacute;.&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Tampa de abertura lateral e estepe pendurado são pontos que não mudarão (Foto: Reprodução)\" height=\"413\" id=\"239002\" src=\"http://s2.glbimg.com/5_AtDt8UioLmDSBPGhJVDQ7A_1I=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/traseirajimny.jpg\" title=\"Tampa de abertura lateral e estepe pendurado são pontos que não mudarão (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Tampa de abertura lateral e estepe pendurado s&atilde;o pontos que n&atilde;o mudar&atilde;o (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O tamanho n&atilde;o deve fugir tanto do porte subcompacto do atual Jimny, dotado de apenas 3,68 metros de comprimento e diminutos 2,25 m de entre-eixos. </strong>As dist&acirc;ncia entre as rodas parece ser maior no jipinho vazado, elas parecem ficar mais pr&oacute;ximas das extremidades. Isso garantir&aacute; &acirc;ngulos de entrada e sa&iacute;da generosos.&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Da mesma maneira, a constru&ccedil;&atilde;o continuar&aacute; a ser tradicional. &Agrave; maneira dos jipes mais parrudos, o Jimny seguir&aacute; o m&eacute;todo de cabine sobre chassi. Ainda n&atilde;o sabemos se a suspens&atilde;o continuar&aacute; a ser por eixo r&iacute;gido nas duas pontas, sistema que garante robustez e ampla articula&ccedil;&atilde;o, contudo, n&atilde;o deixa de ser saltitante ao passar por irregularidades. <strong>Pelas fotos, achamos que o esquema ser&aacute; mantido.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O que d&aacute; para ver &eacute; que h&aacute; uma alavanca secund&aacute;ria de transmiss&atilde;o no console.</strong> Ou seja, nada de bot&atilde;o comandando o 4X4 ou reduzida - atual brasileiro usa bot&otilde;es. A tra&ccedil;&atilde;o nas situa&ccedil;&otilde;es normais continuar&aacute; a ser traseira. <strong>A Suzuki j&aacute; tem o Vitara e o S-Cross como crossovers, ent&atilde;o n&atilde;o h&aacute; necessidade de desagradar os f&atilde;s do offroad extremo.</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Painel mistura presente (central multimídia) ao passado, exemplo dos instrumentos em copos (Foto: Reprodução)\" height=\"413\" id=\"239003\" src=\"http://s2.glbimg.com/Q9BuktaMclqOdnHvAByE9HEVwYM=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/painesuzuki.jpg\" title=\"Painel mistura presente (central multimídia) ao passado, exemplo dos instrumentos em copos (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Painel mistura presente (central multim&iacute;dia) ao passado, exemplo dos instrumentos em copos (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O jeit&atilde;o parrudo n&atilde;o abdica dos confortos modernos. As imagens entregam que o carro ter&aacute; op&ccedil;&atilde;o de c&acirc;mbio autom&aacute;tico e central multim&iacute;dia sens&iacute;vel ao toque, posicionada bem no topo do painel. </strong>Sem falar no volante multifuncional, &agrave; direita por se tratar de um carro para o mercado japon&ecirc;s. Os comandos do ar-condicionado, sa&iacute;das de ar e teclas abaixo dela s&atilde;o totalmente diferentes, mas n&atilde;o abdicam da aura retr&ocirc; em toques como o quadro de instrumentos com &ldquo;copos&rdquo; bem fundos. Uma barra &agrave; frente do passageiro permitir&aacute; que ele se segure enquanto o carrinho avan&ccedil;a no fora de estrada.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Sem falar que a Suzuki n&atilde;o esqueceu dos toques hipsters. Tal como outros funcars, pequenos modelos criados para divers&atilde;o, o Jimny ter&aacute; v&aacute;rias op&ccedil;&otilde;es de personaliza&ccedil;&atilde;o. Voc&ecirc; poder&aacute; mudar o tom da grade, dos apliques do para-choque, cap&ocirc; e teto. A<strong> imagem abaixo exibe algumas das configura&ccedil;&otilde;es poss&iacute;veis.&nbsp;</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"O novo Jimny terá várias opções de personalização (Foto: Reprodução)\" height=\"413\" id=\"239004\" src=\"http://s2.glbimg.com/AGbVW2DuISmAizkQRouevFktyWI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/25/jimnypersonalicao.jpg\" title=\"O novo Jimny terá várias opções de personalização (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">O novo Jimny ter&aacute; v&aacute;rias op&ccedil;&otilde;es de personaliza&ccedil;&atilde;o (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O melhor pode estar debaixo do cap&ocirc; envolvente: um novo motor turbinado. A atual gera&ccedil;&atilde;o fabricada no Brasil &eacute; movida por um 1.3 de 85 cv e 11,2 kgfm de torque. </strong>Embora n&atilde;o atrapalhe tanto na terra e em trilhas, com a ajuda do c&acirc;mbio curtinho, o motorzinho garante apenas desempenho de 1.0 no asfalto. Um turbinado pouco menor do que o 1.4 BoosterJet usado no Vitara e fam&iacute;lia poderia dar muito torque em baixa e deixar o pequeno com desempenho de gente grande.&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>No mercado japon&ecirc;s, motores de pequena litragem s&atilde;o estimulados gra&ccedil;as &agrave; legisla&ccedil;&atilde;o de mini carros (kei cars).</strong> Podemos esperar at&eacute; pelo novo tricil&iacute;ndrico de 660 cm&sup3;, de 81 cv e 10,9 kgfm a 3.400 rpm e tamb&eacute;m por uma op&ccedil;&atilde;o pouco maior para os outros mercados, provavelmente com 1 litro.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Seja como for, o novo Jimny ser&aacute; um retorno &agrave;s origens do jipinho, lan&ccedil;ado originalmente em 1968 pela japonesa Hope Motor Company como ON 360. </strong>O pequeno fabricante foi adquirido pela Suzuki logo em seguida ao lan&ccedil;amento e foi reprojetado para se tornar o LJ10 em 1970, considerado o primeiro Jimny. O pr&oacute;prio fabricante n&atilde;o esquece da linhagem e trouxe um LJ20 1975 para o &uacute;ltimo Sal&atilde;o do Autom&oacute;vel (foto abaixo).</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Suzuki Jimny LJ20 de 1975 (Foto: Marcos Camargo/Autoesporte)\" height=\"413\" id=\"192418\" src=\"http://s2.glbimg.com/1P-9B0apfr5xPj_YdkGiW4E6BU8=/620x413/e.glbimg.com/og/ed/f/original/2016/11/10/mcam5376.jpg\" title=\"Suzuki Jimny LJ20 de 1975 (Foto: Marcos Camargo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Suzuki Jimny LJ20 de 1975 (Foto: Marcos Camargo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2013/02/testamos-o-suzuki-jimny-nacional.html\">Teste completo: como se saiu o Suzuki Jimny nacional na pista de testes e no offroad</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2016/10/teste-suzuki-vitara-4sport-14-turbo.html\">Testamos: Suzuki Vitara ganha motor turbo e passa a andar forte como um Golf 1.4 TSI</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			&quot;<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/04/suzuki-baixa-o-preco-do-vitara-4all-em-ate-r-4400.html\">Promo&ccedil;&atilde;o&quot;: pre&ccedil;os do Suzuki Vitara est&atilde;o mais baixos, confira as mudan&ccedil;as</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/fotos-do-novo-suzuki-jimny-vazam-na-internet.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1635686</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[O que acontece se eu engatar a ré com o carro em movimento?]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Câmbio manual do Ford Focus 1.6 SE Plus (Foto: Leo Sposito / Autoesporte)\" height=\"413\" id=\"152721\" src=\"http://s2.glbimg.com/im7aLIB5wjA2LwBfb2tBc0qrLlU=/620x413/e.glbimg.com/og/ed/f/original/2016/03/10/cambio-manual-ford-focus.jpg\" title=\"Câmbio manual do Ford Focus 1.6 SE Plus (Foto: Leo Sposito / Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">C&acirc;mbio manual do Ford Focus 1.6 (Foto: Leo Sposito / Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	Talvez voc&ecirc; nunca tenha pensado no assunto, talvez morra de curiosidade para descobrir. Foi pensando nisso que decidimos revelar:<strong> o que acontece se engatarmos a marcha r&eacute; em alta velocidade?</strong> Conversamos com Nilton Monteiro, diretor adjunto da AEA (Associa&ccedil;&atilde;o Brasileira de engenharia Automotiva, para descobrir.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Voc&ecirc; s&oacute; ir&aacute; conseguir engatar a r&eacute; de fato em alguns carros autom&aacute;ticos e em baix&iacute;ssimas velocidades. </strong>A rea&ccedil;&atilde;o ser&aacute; muito brusca e o carro ir&aacute; parar. Em altas velocidades, um sistema de prote&ccedil;&atilde;o evita que a transmiss&atilde;o aceite esse comando. Alguns ve&iacute;culos possuem inclusive trava na manopla.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	E se o carro for manual? <strong>Neste caso, o motorista n&atilde;o consegue engatar a marcha r&eacute; porque a engrenagem est&aacute; virando no sentido ao contr&aacute;rio e n&atilde;o &ldquo;encaixa&rdquo;</strong>. Prepare-se para ouvir os dentes arranhando uns nos outros. <strong>Se mesmo assim o motorista for&ccedil;ar, a caixa de c&acirc;mbio pode estourar. </strong>Os preju&iacute;zos ser&atilde;o grandes!</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/o-que-acontece-se-eu-apertar-o-freio-de-estacionamento-em-alta-velocidade.html\">O que acontece se eu apertar o freio de estacionamento em alta velocidade</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Servico/noticia/2014/02/cambio-automatico-saiba-fazer-manutencao.html\">C&Acirc;MBIO AUTOM&Aacute;TICO: SAIBA FAZER A MANUTEN&Ccedil;&Atilde;O</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2016/09/autoesporte-cbn-saiba-mais-sobre-os-diferentes-tipos-de-cambio.html\">AUTOESPORTE CBN: SAIBA MAIS SOBRE OS DIFERENTES TIPOS DE C&Acirc;MBIO</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/o-que-acontece-se-eu-engatar-re-com-o-carro-em-movimento.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1631656</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Volkswagen vai produzir “Kombi do futuro” a partir de 2022]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238904\" src=\"http://s2.glbimg.com/fMWvhUVfjUJZF9zQzAKqyBdqT9k=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-02.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">As Kombis do futuro e do passado juntas (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Kombi &eacute; o exemplo perfeito de ve&iacute;culo eterno e imut&aacute;vel. Podem se passar d&eacute;cadas, e a carism&aacute;tica van seguir&aacute; cultuada, com seus tra&ccedil;os imortais. Mas, tal como o Fusca, a Kombi tamb&eacute;m ter&aacute; uma sucessora. <strong>A Volkswagen confirmou nos &uacute;ltimos dias que ir&aacute; produzir o conceito ID Buzz, revelado no &uacute;ltimo Sal&atilde;o de Detroit (EUA), a partir de 2022</strong>. Apesar de extremamente futurista, o modelo tem forte inspira&ccedil;&atilde;o na ic&ocirc;nica van do passado.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238903\" src=\"http://s2.glbimg.com/R3ZWl01Gr3l8Ltq69_07RIMG_DM=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-03.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Apesar da inspira&ccedil;&atilde;o, ID Buzz tem estilo pr&oacute;pria (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Mas que fique claro desde j&aacute;: a van do futuro n&atilde;o ser&aacute; como a original &mdash; cuja primeira unidade foi produzida no Brasil em 2 de setembro de 1957, h&aacute; 60 anos. <strong>Externamente, o conceito ID Buzz apresenta estilo retr&ocirc; conectado &agrave; Kombi, mas de design igualmente original e cheio de personalidade</strong>. Afora o visual e o formato &ldquo;p&atilde;o de forma&rdquo;, n&atilde;o h&aacute; qualquer semelhan&ccedil;a entre os modelos, especialmente na mec&acirc;nica, que ser&aacute; el&eacute;trica.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238899\" src=\"http://s2.glbimg.com/RE-mAjYk6GAk7_2WNVijAlPAKTI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-06.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Painel do conceito n&atilde;o tem bot&otilde;es e traz um gnomo flutuante (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Volkswagen ainda n&atilde;o revelou todos os detalhes da sucessora &quot;sci-fi&quot; da Kombi, mas os dados preliminares, aplicados ao conceito, j&aacute; impressionam. <strong>A van mostrada em Detroit tem tra&ccedil;&atilde;o integral e usa dois motores el&eacute;tricos, um em cada eixo, capazes de gerar, juntos, pot&ecirc;ncia combinada de robustos 374 cv. Segundo a montadora, a acelera&ccedil;&atilde;o de 0 a 100 km/h leva 5 segundos, com m&aacute;xima de 160 km. A autonomia das baterias &eacute; de 434 km</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238900\" src=\"http://s2.glbimg.com/khBm7764HvFE3VYOYd9bNwby5h4=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-07.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">ID Buzz tem ampla &aacute;rea envidra&ccedil;ada e cabine generosa (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Por falar nelas (as baterias), <strong>a van ID Buzz nascer&aacute; da plataforma MEB, criada especialmente para os futuros ve&iacute;culos movidos apenas a eletricidade. A estrutura foi desenvolvida para acomodar as baterias no assoalho, liberando espa&ccedil;o na cabine e favorecendo outros aspectos, como o centro de gravidade</strong>. O resultado pode ser visto no interior da Kombi futurista, que &eacute; bastante amplo e modul&aacute;vel para seis passageiros.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238898\" src=\"http://s2.glbimg.com/wC-bOCAi0XiUGqyPBJd_INuKEcw=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-08.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Bancos correm em trilhos e giram sob o assoalho de madeira (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Nas imagens, &eacute; poss&iacute;vel ver trilhos no elegante assoalho de madeira, por onde correm os assentos. <strong>A van permite dobrar e girar os bancos dianteiros, permitindo que os ocupantes possam se sentar uns de frente para os outros. O console entre os bancos dianteiros tamb&eacute;m &eacute; m&oacute;vel e traz uma enorme tela touch, que re&uacute;ne todos os controles e fun&ccedil;&otilde;es do ve&iacute;culo</strong>. O visor &eacute; como um tablet e pode ser recolhido quando fora de uso.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238902\" src=\"http://s2.glbimg.com/5XOERsPTH8LTfHjmrM54JLGZJtg=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-04.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Basta um toque para o volante se recolher no modo aut&ocirc;nomo (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Esta configura&ccedil;&atilde;o ao estilo &quot;sala de estar&quot; s&oacute; &eacute; poss&iacute;vel porque <strong>a van ter&aacute; o modo ID Pilot, de condu&ccedil;&atilde;o totalmente aut&ocirc;noma. A Volks acredita que a tecnologia (de n&iacute;vel 5, a mais avan&ccedil;ada) deve estar dispon&iacute;vel at&eacute; 2025</strong>. Para acionar o sistema aut&ocirc;nomo, basta um leve empurr&atilde;o para o volante ser recolhido, ficando totalmente acoplado ao painel. A partir da&iacute;, o motorista pode deixar a dire&ccedil;&atilde;o por conta do pr&oacute;prio ve&iacute;culo, e curtir o passeio a bordo.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238905\" src=\"http://s2.glbimg.com/mvCxwYbLKFhoBpd_IVION21EN28=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-01.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Nome &quot;Buzz&quot; brinca com o termo &quot;Bus&quot; e o motor el&eacute;trico (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	At&eacute; a ilumina&ccedil;&atilde;o ambiente &eacute; ajustada &mdash; luzes brancas adotam tons mais quentes, para os passageiros relaxarem. O modo ID Pilot &eacute; desligado com um simples toque no volante de formato retangular, que emerge novamente. <strong>A van futurista ser&aacute; o segundo modelo 100% el&eacute;trico da VW, que projeta vender mais de 1 milh&atilde;o de ve&iacute;culos el&eacute;tricos at&eacute; 2025, tornando-se l&iacute;der mundial no segmento</strong>. &Eacute; um projeto ambicioso que estreia em 2020, com a vers&atilde;o final do ID Concept, revelado no Sal&atilde;o de Paris de 2016 como o &quot;Fusca do futuro&quot;.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/01/volkswagen-mostra-kombi-eletrica-o-segundo-conceito-da-familia-id.html\" target=\"_blank\">Volkswagen mostra Kombi el&eacute;trica, o segundo conceito da fam&iacute;lia ID</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/06/fas-da-kombi-fazem-viagem-da-malasia-ate-alemanha.html\" target=\"_blank\">F&atilde;s da Kombi fazem viagem da Mal&aacute;sia at&eacute; a Alemanha</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238901\" src=\"http://s2.glbimg.com/kRRd6DcGEdHyrAF323atTKSnXKk=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-05.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">ID Buzz ser&aacute; o segundo modelo 100% el&eacute;trico da Volks (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238896\" src=\"http://s2.glbimg.com/tZbITpBo0VWkMRi3eQOjYbWSrd8=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-09.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Autonomia de mais de 400 km &eacute; um ponto alto (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen ID Buzz (Foto: Divulgação)\" height=\"413\" id=\"238897\" src=\"http://s2.glbimg.com/movbU4SNoZJR0e6oIr4nHiP2IZk=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/volkswagen-id-buzz-concept-autoesporte-10.jpg\" title=\"Volkswagen ID Buzz (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Plataforna MEB prev&ecirc; baterias sob o assoalho (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-vai-produzir-kombi-do-futuro-partir-de-2022.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1634863</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Direto de 2002: o que achamos do primeiro Volkswagen Polo 1.0]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Logo depois do lançamento, o 1.0 estava disponível em várias versões: básica, Plus, Comfortline e até Sportline (Foto:  Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238913\" src=\"http://s2.glbimg.com/FDtIJDwlPb3rq-52UkMv7Xs4oyI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polofrente.jpg\" title=\"Logo depois do lançamento, o 1.0 estava disponível em várias versões: básica, Plus, Comfortline e até Sportline (Foto:  Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Logo depois do lan&ccedil;amento, o 1.0 estava dispon&iacute;vel em v&aacute;rias vers&otilde;es: b&aacute;sica, Plus, Comfortline e at&eacute; Sportline (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O novo Volkswagen Polo est&aacute; sendo apresentado aos pouquinhos e ainda n&atilde;o temos a confirma&ccedil;&atilde;o oficial de todos os motores. <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-revela-o-que-muda-no-visual-do-polo-nacional.html\">Contudo, o modelo mais barato deve vir apenas com o 1.0 MPI aspirado de tr&ecirc;s cilindros</a>, basicamente o mesmo propulsor que equipa o up! e outros VW. <strong>Ser&aacute; que ele vai repetir a hist&oacute;ria do antigo Polo 1.0, lan&ccedil;ado em agosto de 2002?</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	Para quem n&atilde;o se lembra, o modelo 1.0 chegou depois dos 1.6 e 2.0, introduzidos no m&ecirc;s de maio daquele ano, mas tinha o mesmo grau de requinte e constru&ccedil;&atilde;o esmerada. Contudo, foi lan&ccedil;ado na mesma semana em que o IPI (Imposto sobre Produto Industrializado) dos carros com motores acima de 1.0 foi achatado: os 1 litro passaram a pagar 9% e os demais 16%. <strong>A diferen&ccedil;a de pre&ccedil;o planejada inicialmente entre o Polo popular e o 1.6 era grande - R$ 25 mil contra R$ 29 mil -, mas em pouco tempo ele era vendido por apenas R$ 600 a menos</strong>. Nem mesmo uma vers&atilde;o mais depenada deu conta de transformar a configura&ccedil;&atilde;o em um sucesso.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O atual n&atilde;o deve repetir a mesma sina, <a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2017/08/primeiras-impressoes-volkswagen-polo-10-tsi.html\">ainda mais porque ter&aacute; at&eacute; op&ccedil;&atilde;o TSI</a>.&nbsp;Mesmo o aspirado promete ser mais esperto. No up!, o propulsor EA 211 gera at&eacute; 82 cv e 10,4 kgfm, contra os 79 cv e 9,7 kgfm do antigo Polo 1 litro, que tinha 3 cv a mais que o EA 111 aplicado no Gol. <strong>Mesmo tendo tamb&eacute;m quatro v&aacute;lvulas por cilindro, o novo tricil&iacute;ndrico tem atrito reduzido e tamb&eacute;m &eacute; mais esperto gra&ccedil;as alguns recursos, exemplo do comando de v&aacute;lvulas vari&aacute;vel na admiss&atilde;o, que o ajuda a entregar a for&ccedil;a m&aacute;xima a apenas 3.000 rpm, contra 4.500 giros do antecessor de 2002</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Isso n&atilde;o ajudou no desempenho do primeiro Polo popular, cujo f&ocirc;lego antes dos 3.000 giros mostrava um &quot;certo marasmo&quot;, ainda que o peso de 1.073 kg nem fosse t&atilde;o elevado. O que pegava eram as rela&ccedil;&otilde;es desfavor&aacute;veis de peso/pot&ecirc;ncia e peso/torque, nem as marchas extremamente curtas corrigiram a defici&ecirc;ncia. Foram algumas das impress&otilde;es do jornalista Hairton Ponciano Voz no teste publicado na edi&ccedil;&atilde;o de agosto de 2002 da&nbsp;<strong>Autoesporte</strong>. Confira a mat&eacute;ria logo abaixo.</p>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Despojado s&oacute; no desempenho</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	O conforto &eacute; de carro de luxo: ao acionar o controle remoto, a porta destrava-se e a luz interna acende. Quando o veloc&iacute;metro chega a 20 km/h, o pino da porta desce suavemente. E, assim como desce, volta a subir no momento em que a chave &eacute; retirada da igni&ccedil;&atilde;o.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Inscrição 16V era o único detalhe que entregava a versão; as rodas aro 15 eram opcionais (Foto:  Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238910\" src=\"http://s2.glbimg.com/A4l_yRX2CLuJOJNMjfjutXqIVEI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polotraseira.jpg\" title=\"Inscrição 16V era o único detalhe que entregava a versão; as rodas aro 15 eram opcionais (Foto:  Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Inscri&ccedil;&atilde;o 16V era o &uacute;nico detalhe que entregava a vers&atilde;o; as rodas aro 15 eram opcionais (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Se o motorista n&atilde;o estiver bem acomodado, pode regular n&atilde;o s&oacute; o banco (que tem ajuste de altura), mas tamb&eacute;m o volante, em altura e profundidade. Com os far&oacute;is acesos, o painel ganha uma suave colora&ccedil;&atilde;o azul, de bom gosto. <strong>O ar-condicionado tem painel de controle digital, e todo o interior &eacute; revestido com um material suave ao toque, normalmente encontrado s&oacute; em carros de categoria superior.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Tudo, ou melhor, quase tudo nesse carro agrada. O problema &eacute; que o tempo que voc&ecirc; levou para chegar at&eacute; a esta parte do texto (cerca de 35 segundos) &eacute; menos do que o Polo 1.0 gasta para vencer os primeiros 1.000 metros, partindo da imobilidade.</strong> Voc&ecirc; at&eacute; pode apelar para leitura din&acirc;mica e baixar este tempo, mas o Polo &#39;popular&#39;, n&atilde;o.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Ele s&oacute; fecha o primeiro quil&ocirc;metro em 37,9 segundos quando a largada &eacute; feita com o motor &#39;cheio&#39;, e com as marchas trocadas no limite de rota&ccedil;&atilde;o do motor. <strong>Outro n&uacute;mero que combina pouco com um carro t&atilde;o bonito &eacute; o que mostra a acelera&ccedil;&atilde;o de 0 a 100 km/h: 17 segundos, ou o tempo necess&aacute;rio para chegar mais ou menos &agrave; metade do primeiro par&aacute;grafo</strong>. Fa&ccedil;a voc&ecirc; mesmo o teste &ndash; o de leitura, digo.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"O desenho dessa geração do Polo perduraria até 2015 com algumas alterações (Foto: Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238909\" src=\"http://s2.glbimg.com/zlgam29o4YInjoJjL8Fj3l8zdy4=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/poloaberto.jpg\" title=\"O desenho dessa geração do Polo perduraria até 2015 com algumas alterações (Foto: Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">O desenho dessa gera&ccedil;&atilde;o do Polo perduraria at&eacute; 2015 com algumas altera&ccedil;&otilde;es (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O desempenho proporcionado pelo motor 1.0 de 16 v&aacute;lvulas e 79 cavalos (a novidade deste Polo) &eacute;, no fim, o que menos agrada no simp&aacute;tico hatch, previsto para chegar &agrave;s lojas em meados de agosto. <strong>N&atilde;o por falta de pot&ecirc;ncia, mas sim porque o carro &eacute; pesado, resultado do bom n&iacute;vel de acabamento e equipamentos. O Polo 1.0 pesa 1.073 kg, o que resulta numa rela&ccedil;&atilde;o peso/pot&ecirc;ncia de 13,6 kg/cv. N&atilde;o &eacute; t&atilde;o ruim (o Mille Fire, de 805 kg e 55 cv, tem rela&ccedil;&atilde;o pior, de 14,6 kg), mas a sensa&ccedil;&atilde;o &eacute; que o Polo se arrasta mais. </strong>Por qu&ecirc;? Porque o motor de 16 v&aacute;lvulas mostra certo marasmo abaixo de 3 mil rpm, mesmo com as adapta&ccedil;&otilde;es feitas exclusivamente para o Polo.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Quem n&atilde;o quiser ficar nervoso ao volante do carro (como eu) tem de trocar marchas sempre na faixa de 4 mil giros (o torque m&aacute;ximo, de 9,7 kgfm, aparece a 4.500 rpm), e n&atilde;o pode ter pregui&ccedil;a de reduzir.</strong> O ru&iacute;do aumenta um pouco, mas a&iacute; pelo menos o Polo deslancha.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Painel tinha acabamento macio e nível de construção de carro de luxo (Foto: Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238908\" src=\"http://s2.glbimg.com/YyoDQe9IJLf4MVvF54xRjCoUag0=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/painelpolo.jpg\" title=\"Painel tinha acabamento macio e nível de construção de carro de luxo (Foto: Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Painel tinha acabamento macio e n&iacute;vel de constru&ccedil;&atilde;o de carro de luxo (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Outro sintoma do carro &eacute; que a 120 km/h em quinta marcha, por exemplo, o conta-giros aponta para cerca de 4.500 rpm, rota&ccedil;&atilde;o j&aacute; relativamente alta para uma velocidade de cruzeiro. Isso &eacute; o resultado da ado&ccedil;&atilde;o de rela&ccedil;&otilde;es mais curtas, solu&ccedil;&atilde;o dada pela engenharia para melhorar as rea&ccedil;&otilde;es do carro.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Volkswagen quis fazer um carro 1.0 mais sofisticado, e n&atilde;o um ve&iacute;culo &#39;pelado&#39;. Caso contr&aacute;rio, ele poderia tirar mercado n&atilde;o s&oacute; de Fiesta, Palio e Corsa, mas tamb&eacute;m do Gol. Para evitar a briga interna, a montadora fez um carro com o mesmo padr&atilde;o de equipamentos do Polo com motoriza&ccedil;&atilde;o superior (1.6 e 2.0). <strong>A &uacute;nica diferen&ccedil;a &eacute; a inscri&ccedil;&atilde;o &#39;16V&#39; na traseira. Assim, n&atilde;o espere pre&ccedil;o baixo. Embora a montadora n&atilde;o tenha tocado em assunto t&atilde;o espinhoso at&eacute; o final de julho, estima-se que o Polo 1.0 n&atilde;o v&aacute; custar muito abaixo de R$ 25 mil.</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porta-copos retrátil era um toque refinado do interior do Polo (Foto:  Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238911\" src=\"http://s2.glbimg.com/FN0DfqDZ69z3BDxA1s3pqLMQggg=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/portacopo.jpg\" title=\"Porta-copos retrátil era um toque refinado do interior do Polo (Foto:  Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Porta-copos retr&aacute;til era um toque refinado do interior do Polo (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O motor &eacute; o do Gol, mas com algumas mudan&ccedil;as. Al&eacute;m das altera&ccedil;&otilde;es para instal&aacute;-lo na transversal (no Gol ele &eacute; longitudinal), o coletor de admiss&atilde;o cresceu em di&acirc;metro e comprimento, e o corpo da borboleta tamb&eacute;m ficou maior. Al&eacute;m disso, o propulsor alcan&ccedil;a maior rota&ccedil;&atilde;o, o que tamb&eacute;m favorece o aumento de pot&ecirc;ncia. <strong>No Polo 1.0, a pot&ecirc;ncia m&aacute;xima aparece a 6.500 rpm (6.000 no Gol). Com todas as mexidas, o motor EA 111 foi de 76 cv no Gol para 79 cv no Polo. Ainda assim, insuficiente para deixar a gente muito feliz ao volante.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	A unidade testada por Autoesporte veio at&eacute; com freios ABS, mas o sistema andou dando uns sustos: quando se freia sobre piso irregular, o dispositivo libera press&atilde;o de frenagem se a roda p&aacute;ra de girar (o que ocorre quando est&aacute; saltitando sobre alguma imperfei&ccedil;&atilde;o). E a sensa&ccedil;&atilde;o de pisar no freio e o carro continuar andando n&atilde;o &eacute; das melhores. Discos e tambores t&ecirc;m o mesmo di&acirc;metro dos do Polo 1.6. <strong>A suspens&atilde;o agrada: levemente firme, transmite sensa&ccedil;&atilde;o de seguran&ccedil;a nas curvas, e &eacute; confort&aacute;vel.</strong></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ar-condicionado digital e rádio CD player integrados ao painel davam requinte (Foto: Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238907\" src=\"http://s2.glbimg.com/ngolWwerrF63nN_4k6HOwp7njvA=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/ardigital.jpg\" title=\"Ar-condicionado digital e rádio CD player integrados ao painel davam requinte (Foto: Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Ar-condicionado digital e r&aacute;dio CD player integrados ao painel davam requinte (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Se n&atilde;o empolga tanto no acelerador, o Polo tem outros atrativos. O primeiro &eacute; mesmo visual: por fora e por dentro, o carro tem estilo agrad&aacute;vel e simp&aacute;tico.</strong> &Eacute; bem mais s&oacute;brio que Corsa e Fiesta, por&eacute;m muito mais elegante. N&atilde;o h&aacute; como n&atilde;o associar o conjunto de far&oacute;is e lanternas, ovalados, &agrave; frente dos Mercedes.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Por dentro o carro tamb&eacute;m agrada bastante. Como nas outras vers&otilde;es de motoriza&ccedil;&atilde;o, o volante de quatro raios &eacute; belo<strong>. A dire&ccedil;&atilde;o eletro-hidr&aacute;ulica, precisa, &eacute; item de s&eacute;rie, e o modelo veio com dois airbags (opcionais). Dependendo da vers&atilde;o de acabamento, o Polo 16V pode vir com ilumina&ccedil;&atilde;o nos espelhos dos quebra-s&oacute;is e computador de bordo. </strong>H&aacute; ainda porta-copos no painel e no console central, uma gaveta sob o banco do motorista e um porta-objetos at&eacute; sobre o painel.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Quebra-sol é equipado com espelho iluminado (Foto: Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238906\" src=\"http://s2.glbimg.com/9fEbH7t5I-17uUcOCrkfVjaNk7k=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/luzesteto.jpg\" title=\"Quebra-sol é equipado com espelho iluminado (Foto: Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Quebra-sol &eacute; equipado com espelho iluminado (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Volkswagen preferiu enfatizar que desde o in&iacute;cio de desenvolvimento do Polo 1.0 (h&aacute; cerca de 18 meses, ou bem antes da chegada das vers&otilde;es 1.6 e 2.0 ao mercado), a id&eacute;ia era oferecer o mesmo n&iacute;vel de equipamentos dos modelos de maior cilindrada (da&iacute; o maior peso; da&iacute; o desempenho morninho).</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Esse objetivo certamente foi conseguido. <strong>Para se ter uma id&eacute;ia, at&eacute; aquela prosaica vareta que segura o cap&ocirc; mesmo em ve&iacute;culos bem mais caros foi substitu&iacute;da por um amortecedor, que levanta sozinho a tampa do motor. </strong>Pena que os engenheiros n&atilde;o aproveitaram a facilidade para instalar tamb&eacute;m uma turbina&hellip;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Motor 1.0 era pouco mais potente do que o mesmo utilizado no Gol e as marchas mais curtas que no Polo 1.6 (Foto: Oswaldo Palermo/Autoesporte)\" height=\"413\" id=\"238912\" src=\"http://s2.glbimg.com/oFVOWQ2yqUs2gcqr400b3Qk-aMo=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/motorumlitro.jpg\" title=\"Motor 1.0 era pouco mais potente do que o mesmo utilizado no Gol e as marchas mais curtas que no Polo 1.6 (Foto: Oswaldo Palermo/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Motor 1.0 era pouco mais potente do que o mesmo utilizado no Gol e as marchas mais curtas que no Polo 1.6 (Foto: Oswaldo Palermo/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Ficha t&eacute;cnica</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	Motor: Dianteiro, transversal, 4 cil. em linha, 1.0, 16V, comando duplo, inje&ccedil;&atilde;o eletr&ocirc;nica de gasolina<br />\r\n" + 
    			"	Pot&ecirc;ncia: 79 cv a 6.500 rpm<br />\r\n" + 
    			"	Torque: 9,7 kgfm a 4.500 rpm<br />\r\n" + 
    			"	C&acirc;mbio: Manual de cinco velocidades, tra&ccedil;&atilde;o dianteira<br />\r\n" + 
    			"	Dire&ccedil;&atilde;o: Eletro-hidr&aacute;ulica<br />\r\n" + 
    			"	Suspens&atilde;o: Independente, McPherson na dianteira e eixo de tor&ccedil;&atilde;o na traseira<br />\r\n" + 
    			"	Freios: Discos ventilados na dianteira e tambores na traseira<br />\r\n" + 
    			"	Pneus: 185/60 R 14<br />\r\n" + 
    			"	Comprimento: 3,89 m<br />\r\n" + 
    			"	Largura: 1,65 m<br />\r\n" + 
    			"	Altura: 1,48 m<br />\r\n" + 
    			"	Entre-eixos: 2,46 m<br />\r\n" + 
    			"	Capacidade do tanque: 45 litros<br />\r\n" + 
    			"	Peso: 1.073 kg<br />\r\n" + 
    			"	Porta-malas: 270 litros</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2017/08/primeiras-impressoes-volkswagen-polo-10-tsi.html\">Teste: o que achamos do Volkswagen Polo 1.0 TSI no nosso primeiro contato</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-revela-o-que-muda-no-visual-do-polo-nacional.html\">Aos poucos: Volkswagen agora revela o que mudar&aacute; no visual do novo Polo nacional</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-golf-sportsvan-e-atualizado-na-europa.html\">L&aacute; fora: Volkswagen Golf Sportvan &eacute; atualizado nos mercados europeus</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/direto-de-2002-o-que-achamos-do-primeiro-volkswagen-polo-10.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1632265</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Por que o túnel central é mais elevado em alguns carros?]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Tunel central pode atrapalhar quem senta no meio do banco traseiro (Foto: Ivan Carneiro/Autoesporte)\" height=\"413\" id=\"http://edg2.admin.globoi.com/photo/7e8b2901-860a-42bb-b1fc-2916c4ff481f\" src=\"http://s2.glbimg.com/KeSgksqP-Ue3u81KZ-k9KdRwhgY=/e.glbimg.com/og/ed/f/original/2017/08/25/tunel.jpg\" title=\"Tunel central pode atrapalhar quem senta no meio do banco traseiro (Foto: Ivan Carneiro/Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Tunel central pode atrapalhar quem senta no meio do banco traseiro (Foto: Ivan Carneiro/Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	Se voc&ecirc; j&aacute; sentou no banco de tr&aacute;s de um carro, com certeza j&aacute; lutou para n&atilde;o ser o passageiro do meio. Esse assento indesejado &eacute; sempre mais apertado e menos c&ocirc;modo do que os outros lugares do ve&iacute;culo. <strong>Um dos motivos &eacute; a falta de espa&ccedil;o para as pernas, provocada principalmente pelo t&uacute;nel central.</strong> E o que &eacute; isso?</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O t&uacute;nel central &eacute; uma eleva&ccedil;&atilde;o no piso da &aacute;rea traseira central do carro.</strong> Dependendo do modelo do ve&iacute;culo, ele pode ser mais ou menos elevado - determinando se cabem, de verdade, cinco pessoas no carro ou apenas quatro. Marcio Azuma, diretor de seguran&ccedil;a veicular da AEA, explica que <strong>a eleva&ccedil;&atilde;o &eacute; mais recorrente em carros 4x4 ou com tra&ccedil;&atilde;o traseira e motor dianteiro</strong>. Nesses casos, o eixo card&atilde; (que d&aacute; a for&ccedil;a pra rodar as rodas do eixo traseiro) ocupa o espa&ccedil;o do t&uacute;nel central. Ainda assim, <strong>nos SUVs, a eleva&ccedil;&atilde;o n&atilde;o precisa ser t&atilde;o grande</strong>, j&aacute; que o pr&oacute;prio carro j&aacute; &eacute; mais alto.<br />\r\n" + 
    			"	<br />\r\n" + 
    			"	Quando se trata de <strong>motores com tra&ccedil;&atilde;o dianteira, a tubula&ccedil;&atilde;o de escapamento e o cabo de sistema de freio ocupam o espa&ccedil;o do t&uacute;nel central</strong>. Al&eacute;m disso, essas pe&ccedil;as ficam protegidas, j&aacute; que est&atilde;o embutidas em rela&ccedil;&atilde;o ao assoalho.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O t&uacute;nel central tamb&eacute;m contribui para a estrutura do carro.</strong> &ldquo;Ele ganha rigidez sem muito peso&rdquo;, comenta M&aacute;rcio. Nos casos em que n&atilde;o existe eleva&ccedil;&atilde;o, o ve&iacute;culo necessita de um refor&ccedil;o adicional, uma estrutura que compense no padr&atilde;o m&iacute;nimo de rigidez necess&aacute;ria. Isso adiciona um peso, material - e custo - a mais na produ&ccedil;&atilde;o do carro. Ainda assim, para alguns engenheiros, a se&ccedil;&atilde;o n&atilde;o tem uma participa&ccedil;&atilde;o t&atilde;o grande na rigidez do ve&iacute;culo. Marco Colosio, diretor do 10&ordm; Simp&oacute;sio SAE BRASIL de Novos Materiais e Aplica&ccedil;&otilde;es na Mobilidade, conta que existem outros suportes internos que d&atilde;o rigidez, al&eacute;m do t&uacute;nel. &ldquo;<strong>Em ve&iacute;culos el&eacute;tricos e h&iacute;bridos o foco &eacute; remover o t&uacute;nel central</strong>, j&aacute; que n&atilde;o precisa desse espa&ccedil;o para passar o eixo card&atilde; nem a tubula&ccedil;&atilde;o de escapamento&rdquo;, ele explica.</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/por-que-o-tunel-central-e-mais-elevado-em-alguns-carros.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1631003</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Caminhão elétrico da Tesla deve ser capaz de percorrer até 500 km sem recarga]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Tesla revela primeira imagem de futuro caminhão elétrico (Foto: Reprodução)\" height=\"413\" id=\"218616\" src=\"http://s2.glbimg.com/AJZFr2hiEL0ehTzKdaoAkslBfSg=/620x413/e.glbimg.com/og/ed/f/original/2017/05/02/caminhao_tesla2.jpg\" title=\"Tesla revela primeira imagem de futuro caminhão elétrico (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Tesla revela primeira imagem de futuro caminh&atilde;o el&eacute;trico (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	A Tesla planeja <strong>revelar no m&ecirc;s que vem um caminh&atilde;o el&eacute;trico de longo alcance com capacidade para rodar de 322 a 483 quil&ocirc;metros</strong>, a <em>Reuters</em> tomou conhecimento, um sinal de que a montadora de ve&iacute;culos el&eacute;tricos est&aacute; se direcionando para o transporte regional para sua entrada no mercado de frete comercial.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O presidente-executivo Elon Musk <strong>prometeu divulgar um prot&oacute;tipo do Tesla Semi Truck em setembro em uma tentativa de expandir o mercado da companhia al&eacute;m dos carros de luxo</strong>. O empres&aacute;rio atraiu a ind&uacute;stria de transporte rodovi&aacute;rio com a perspectiva de um ve&iacute;culo pesado movido a bateria que pode competir com os convencionais alimentados a diesel, que podem viajar at&eacute; 1.610 quil&ocirc;metros com um &uacute;nico tanque de combust&iacute;vel.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Scott Perry, um executivo da operadora de frotas Ryder System, disse que se encontrou com autoridades da Tesla no in&iacute;cio do ano para discutir essa tecnologia. Ele disse que os esfor&ccedil;os da empresa est&atilde;o centrados em <strong>uma grande plataforma el&eacute;trica conhecida como &quot;cabine do dia&quot;, sem espa&ccedil;o de descanso</strong>, capaz de viajar cerca de 322 a 483 quil&ocirc;metros com uma carga &uacute;til sem recarregar.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em resposta a e-mail enviado pela <em>Reuters</em> pedindo coment&aacute;rios, a Tesla respondeu: &quot;<strong>A pol&iacute;tica da Tesla &eacute; de sempre se recusar a comentar sobre especula&ccedil;&otilde;es, sejam elas verdadeiras ou n&atilde;o, pois isso seria tolice. Tolice!</strong>&quot;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O plano da empresa, que poderia mudar a maneira como os caminh&otilde;es s&atilde;o desenvolvidos, &eacute; consistente com o que pesquisadores de bateria afirmam ser poss&iacute;vel com a tecnologia atual. <strong>A Tesla n&atilde;o disse publicamente o qu&atilde;o longe o caminh&atilde;o el&eacute;trico pode viajar, quanto custar&aacute; e quanta carga transportar&aacute;</strong>. Mas Musk reconheceu que a companhia se encontrou em particular com potenciais compradores para discutir suas necessidades.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Aproximadamente <strong>30% dos empregos de transporte por caminh&atilde;o dos EUA s&atilde;o viagens regionais de 161 a 322 quil&ocirc;metros</strong>, de acordo com Sandeep Kar, diretor de estrat&eacute;gia da Fleet Complete, que rastreia e analisa o movimento de caminh&otilde;es.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O interesse em caminh&otilde;es el&eacute;tricos &eacute; alto para as empresas de transporte que procuram reduzir suas emiss&otilde;es de poluentes e custos operacionais. Mas a tecnologia atual para alimentar caminh&otilde;es em todo os Estados Unidos n&atilde;o &eacute; simples, os especialistas dizem que as baterias necess&aacute;rias seriam t&atilde;o grandes e pesadas que haveria pouco espa&ccedil;o para a carga.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/tesla-desenvolve-tecnologia-autonoma-para-caminhoes-e-quer-testa-los-em-nevada.html\">TESLA DESENVOLVE TECNOLOGIA AUT&Ocirc;NOMA PARA CAMINH&Otilde;ES E QUER TEST&Aacute;-LOS EM NEVADA</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/07/tesla-model-3-e-lancado-oficialmente-nos-eua.html\">TESLA MODEL 3 &Eacute; LAN&Ccedil;ADO OFICIALMENTE NOS EUA</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-02-tesla-model-3-no-asfalto-hyperloop-debaixo-da-terra-e-volocopter-no-ceu.html\">Conhe&ccedil;a o grande plano da Tesla no podcast Central Eletr&ocirc;nica</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/caminhao-eletrico-da-tesla-deve-ser-capaz-de-percorrer-ate-500-km-sem-recarga.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1634560</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[INOVAÇÃO # 19: SISTEMAS DE COMANDOS POR VOZ]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Apple desenvolve sistema de reconhecimento de voz para motoristas (Foto: Divulgação)\" height=\"413\" id=\"15475\" src=\"http://s2.glbimg.com/2kVtCN-GMvdWqMVvKacPDllHRn8=/620x413/e.glbimg.com/og/ed/f/original/2013/05/02/eyes_free.jpg\" title=\"Apple desenvolve sistema de reconhecimento de voz para motoristas (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Apple desenvolve sistema de reconhecimento de voz para motoristas (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O transporte de pessoas est&aacute; passando por sua maior revolu&ccedil;&atilde;o desde que os ve&iacute;culos come&ccedil;aram a se beneficiar dos motores de combust&atilde;o interna. Diante disso, a <strong>Autoesporte </strong>criou a <strong>Nova Era da Mobilidade</strong>, uma s&eacute;rie que, durante tr&ecirc;s meses, vai mostrar 50 inova&ccedil;&otilde;es que v&ecirc;m tornando a rela&ccedil;&atilde;o entre as pessoas e os carros, e deles com as ruas e estradas, mais confort&aacute;vel, conveniente, segura e econ&ocirc;mica.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Conhe&ccedil;a a 19&ordf; inova&ccedil;&atilde;o abaixo e veja a s&eacute;rie completa <a href=\"http://revistaautoesporte.globo.com/Especiais/novaera/\">aqui</a>:<br />\r\n" + 
    			"	<br />\r\n" + 
    			"	<strong>Sistemas de comando de voz</strong><br />\r\n" + 
    			"	<br />\r\n" + 
    			"	Imagine acionar o navegador do carro apenas falando com ele, ou ajustar a temperatura do ar-condicionado sem ter de mexer em nenhum controle. Isso j&aacute; &eacute; poss&iacute;vel atrav&eacute;s dos comandos acionados por voz. <strong>O equipamento, presente at&eacute; em compactos nacionais, permite fazer tudo isso, assim como procurar e reproduzir m&uacute;sicas, responder mensagens de texto etc</strong>. Os sistemas mais sofisticados permitem tamb&eacute;m realizar buscas pela internet (conectada ao carro pelo celular), assim como verificar os compromissos na agenda do dia, entre outras fun&ccedil;&otilde;es. <a href=\"http://revistaautoesporte.globo.com/carros/ford/new-fiesta/\">Ford Fiesta</a> e <a href=\"http://revistaautoesporte.globo.com/carros/nissan/versa/\">Nissan Versa</a> s&atilde;o modelos nacionais que contam com o recurso.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>Mais sobre tecnologia</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Especiais/novaera/noticia/2017/07/inovacao-03-ar-condicionado-com-comando-de-duas-zonas.html\">INOVA&Ccedil;&Atilde;O # 03: AR-CONDICIONADO COM COMANDO DE DUAS ZONAS</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Especiais/novaera/noticia/2017/07/inovacao-04-assistente-de-partida-em-rampas.html\">INOVA&Ccedil;&Atilde;O # 04: ASSISTENTE DE PARTIDA EM RAMPAS</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Especiais/novaera/noticia/2017/08/inovacao-11-sensores-de-chuva-e-crepuscular.html\">INOVA&Ccedil;&Atilde;O # 11: SENSORES DE CHUVA E CREPUSCULAR</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<br />\r\n" + 
    			"	<span style=\"color: rgb(204, 0, 0); font-family: Georgia; font-size: 1.67em; font-weight: bold; letter-spacing: -0.02em;\">Ford My Connection (comando de voz)</span></p>\r\n" + 
    			"<div class=\"youtube componente_materia\">\r\n" + 
    			"	<object height=\"400\" width=\"620\"><param name=\"movie\" value=\"http://www.youtube.com/v/NYVSPIFoSis\" /><param name=\"allowFullScreen\" value=\"true\" /><param name=\"allowscriptaccess\" value=\"always\" /><embed allowfullscreen=\"true\" allowscriptaccess=\"always\" height=\"400\" src=\"http://www.youtube.com/v/NYVSPIFoSis\" type=\"application/x-shockwave-flash\" width=\"620\"></embed></object></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Especiais/novaera/noticia/2017/08/inovacao-19-sistemas-de-comandos-por-voz.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1585061</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Que tal alugar um carrão por um dia direto com o dono?]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche 911 Carrera conversível 1987 pode ser alugado por US$ 330 (Foto: Reprodução)\" height=\"413\" id=\"238868\" src=\"http://s2.glbimg.com/M6udrgLGxn4VbTVAVzIlEZH-8YI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/911aluguel.jpg\" title=\"Porsche 911 Carrera conversível 1987 pode ser alugado por US$ 330 (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Porsche 911 Carrera convers&iacute;vel 1987 pode ser alugado por US$ 330 (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A seguradora Hagerty criou um servi&ccedil;o de compartilhamento de carros entre pessoas f&iacute;sicas, o DriveShare. At&eacute; a&iacute;, nenhuma novidade. Mas a plataforma deles tem algo de bem diferente: ela &eacute; voltada ao mercado de autom&oacute;veis e ve&iacute;culos cl&aacute;ssicos. &Eacute; a chance de pegar &ldquo;emprestado&rdquo; aquele antigo que voc&ecirc; gostaria de dirigir, mas n&atilde;o gostaria de pagar uma grana preta por isso. A ideia &eacute; interessante, pena que est&aacute; dispon&iacute;vel por enquanto apenas nos Estados Unidos.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Trata-se de um servi&ccedil;o de carshare bem parecido com o praticado pelo antigo Fleety, que chegou a operar no Brasil, mas fechou as portas no in&iacute;cio deste ano. Voc&ecirc; escolhe o carro que quer, tira suas d&uacute;vidas a respeito dele e detalhes, cria um cadastro e marca uma loca&ccedil;&atilde;o para retirar o ve&iacute;culo e devolv&ecirc;-lo. Alguns donos topam levar seus ve&iacute;culos at&eacute; voc&ecirc; em um raio m&aacute;ximo (pode chegar a mais de 100 km) e a maioria deles est&aacute; no estado da Calif&oacute;rnia. Por enquanto, s&atilde;o 151 carros no acervo.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ford A 1931 é alugado como a oportunidade de entrar em uma cápsula do tempo (Foto: Reprodução)\" height=\"400\" id=\"http://edg2.admin.globoi.com/photo/85a048d6-a848-43dc-a25f-44176b7a3271\" src=\"http://s2.glbimg.com/v7DbdTW7RKjmge3FRUZZvdiX7d8=/e.glbimg.com/og/ed/f/original/2017/08/24/forda.jpg\" title=\"Ford A 1931 é alugado como a oportunidade de entrar em uma cápsula do tempo (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Ford A 1931 &eacute; alugado como a oportunidade de entrar em uma c&aacute;psula do tempo (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Ou seja, funciona como se fosse um AirBnB de carros interessantes. Voc&ecirc; pode escolher caracter&iacute;sticas t&eacute;cnicas, tais como ar-condicionado ou dire&ccedil;&atilde;o assistida, e tamb&eacute;m dividir os modelos por g&ecirc;nero (hot rod, antique, etc). D&aacute; para notar logo de cara que os valores s&atilde;o bem superiores aos pedidos por um autom&oacute;vel em uma locadora convencional.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Ter um cl&aacute;ssico pode gerar despesas enormes e nem sempre o uso ser&aacute; cotidiano. Ou seja, mais ou menos como &eacute; o c&aacute;lculo que muitos fazem na hora de comprar um barco ou construir uma piscina.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Você pode se sentir em De volta para o futuro por US$ 800 por dia (Foto: Reprodução)\" height=\"350\" id=\"238862\" src=\"http://s2.glbimg.com/Q12EN0XL8jI2nDjFoIVjidM--Dw=/e.glbimg.com/og/ed/f/original/2017/08/24/delorean.jpg\" title=\"Você pode se sentir em De volta para o futuro por US$ 800 por dia (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Voc&ecirc; pode se sentir em De volta para o futuro por US$ 800 por dia (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Por sua vez, os propriet&aacute;rios t&ecirc;m controle total de cada aluguel, e podem definir qual &eacute; a quilometragem m&aacute;xima a ser rodada, valor do dep&oacute;sito e da di&aacute;ria. E por que eles fariam isso com carros t&atilde;o valiosos? A Hagerty oferece uma ap&oacute;lice de seguro de at&eacute; US$ 1 milh&atilde;o. Segundo a empresa, a vantagem &eacute; poder gerar para o locat&aacute;rio uma renda extra para comprar pe&ccedil;as, restaurar ou adquirir um novo carro. Lembrando que ainda h&aacute; uma taxa de servi&ccedil;o (10% do aluguel).&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&Eacute; divertido dar uma percorrida no acervo do site. H&aacute; um carro para cada tipo de ocasi&atilde;o, voc&ecirc; pode lembrar de De volta para o futuro em um DeLorean DMC-12 (US$ 800) ou dar uma de patr&atilde;o no banco traseiro do&nbsp; Rolls-Royce Phantom 2013 (US$ 525). Se voc&ecirc; quer viajar no tempo de fato, um <a href=\"http://revistaautoesporte.globo.com/carros/ford/\">Ford</a> Modelo A Tudor 1931 &eacute; um dos antigos mais originais e cobra US$ 179/dia. O propriet&aacute;rio j&aacute; avisa logo: &quot;esse carro n&atilde;o anda como um ve&iacute;culo moderno. &Eacute; uma c&aacute;psula do tempo da experi&ecirc;ncia de dirigir em 1931&quot;.&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"O Edsel se tornou colecionável depois de personificar o maior fracasso da Ford (Foto: Reprodução)\" height=\"400\" id=\"http://edg2.admin.globoi.com/photo/c48951c4-3e86-489c-bd99-60f0e92ec540\" src=\"http://s2.glbimg.com/lciD1TXDay7iS6aiith1z1dpFtI=/e.glbimg.com/og/ed/f/original/2017/08/24/edsel.jpg\" title=\"O Edsel se tornou colecionável depois de personificar o maior fracasso da Ford (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">O Edsel se tornou colecion&aacute;vel depois de personificar o maior fracasso da Ford (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	D&aacute; at&eacute; experimentar por 200 d&oacute;lares a di&aacute;ria um dos mais conhecidos fracassos da ind&uacute;stria automotiva, o Edsel, marca criada pela Ford nos anos 50. O Ranger 1959, contudo, pode ser alugado apenas com chofer. Que pena!</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A grande maioria talvez ache mais interessante alugar um superesportivo. Mesmo carr&otilde;es que j&aacute; foram substitu&iacute;dos como o&nbsp;<a href=\"http://revistaautoesporte.globo.com/carros/lamborghini/\">Lamborghini</a> Gallardo ainda est&atilde;o cotados a mais de 1.000 d&oacute;lares/dia. Estamos falando de R$ 3,1 mil por dia, sem taxas.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Voc&ecirc; pode apelar para uma op&ccedil;&atilde;o mais barata, exemplo do <a href=\"http://revistaautoesporte.globo.com/carros/audi/\">Audi</a> R8, equipado com o mesmo V10 do supercarro italiano e dispon&iacute;vel por 60% do valor. Um <a href=\"http://revistaautoesporte.globo.com/carros/chevrolet/\">Chevrolet</a> Corvette da gera&ccedil;&atilde;o atual tamb&eacute;m pode ser uma alternativa em conta e vai de US$ 400 a US$ 750 (Z06), dependendo da vers&atilde;o. Ou at&eacute; optar por uma uma <a href=\"http://revistaautoesporte.globo.com/carros/ferrari/\">Ferrari</a> menos cotada. D&aacute; para alugar uma 308 igualzinha a utilizada por Magnum na s&eacute;rie de TV dos anos 80 (US$ 650) ou escolher uma California mais atual (US$ 808).</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Van H da Citroën é alugada apenas para eventos, um truckfood mais hipster seria impossível (Foto: Reprodução)\" height=\"400\" id=\"http://edg2.admin.globoi.com/photo/47fff7b3-54cf-49b0-b102-c93d30939319\" src=\"http://s2.glbimg.com/_mNCSIYHqNvwSqLaCbZjHoJisSc=/e.glbimg.com/og/ed/f/original/2017/08/24/vancitroen.jpg\" title=\"Van H da Citroën é alugada apenas para eventos, um truckfood mais hipster seria impossível (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Van H da Citro&euml;n &eacute; alugada apenas para eventos, um truckfood mais hipster seria imposs&iacute;vel (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	N&atilde;o d&aacute; para deixar de se surpreender com a valoriza&ccedil;&atilde;o de carros incomuns, especialmente se eles forem para outros usos al&eacute;m de rodar. Um dos mais curiosos &eacute; o food truck feito sobre uma van H da <a href=\"http://revistaautoesporte.globo.com/carros/citroen/\">Citro&euml;n</a>, alugada somente para eventos por US$ 600 por ocasi&atilde;o. Quem curte acampar vai curtir a <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/\">Volkswagen</a> Kombi camper (US$ 400). &nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Alguns modelos raros nos Estados Unidos tamb&eacute;m s&atilde;o mais caros, exemplo do <a href=\"http://revistaautoesporte.globo.com/carros/land-rover/\">Land Rover Defender 110</a> com pinta de carro militar, dispon&iacute;vel por US$ 199. Outro utilit&aacute;rio de caserna &eacute; o Steyr Puch Pinzgauer 712M, descrito pelo propriet&aacute;rio como um &quot;caminh&atilde;o antigo barulhento, desconfort&aacute;vel e fedorento que &eacute; absolutamente incr&iacute;vel&quot;! Utilizado originalmente pelas for&ccedil;as militares da Su&iacute;&ccedil;a, o exemplar leva 14 pessoas e pode ser locado por US$ 349.<span style=\"font-size: 1.26em; letter-spacing: -0.02em;\">&nbsp;</span></p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Steyr Puch Pinzgauer 712M é descrito de maneira sincera pelo dono (Foto: Reprodução)\" height=\"400\" id=\"238866\" src=\"http://s2.glbimg.com/B4nfFgGR40VN73EKXiSPTwIOdsU=/e.glbimg.com/og/ed/f/original/2017/08/24/caminhaozinho.jpg\" title=\"Steyr Puch Pinzgauer 712M é descrito de maneira sincera pelo dono (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Steyr Puch Pinzgauer 712M &eacute; descrito de maneira sincera pelo dono (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Nem todos s&atilde;o exorbitantes. Esportivos mais comuns como a primeira gera&ccedil;&atilde;o do <a href=\"http://revistaautoesporte.globo.com/carros/porsche/\">Porsche</a> Boxster de 1997 pode ser alugada por 99 d&oacute;lares, metade do pedido por um 944 Turbo. Ele pode estar com os far&oacute;is amarelados e a cor teve mais vi&ccedil;o h&aacute; 20 anos atr&aacute;s, contudo, o dono deixa voc&ecirc; rodar at&eacute; 300 milhas por dia - cerca de 500 km. A maioria limita a quilometragem a 166 km (100 mi).</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Porsche Boxster pode ser alugado por pouco mais de 100 dólares/dia (Foto: Reprodução)\" height=\"400\" id=\"http://edg2.admin.globoi.com/photo/8598566f-cea9-41b0-952b-b1ae5fe1195b\" src=\"http://s2.glbimg.com/rdOEjOo5MqHFnALbtkXNdeVAzYU=/e.glbimg.com/og/ed/f/original/2017/08/24/boxster.jpg\" title=\"Porsche Boxster pode ser alugado por pouco mais de 100 dólares/dia (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Porsche Boxster pode ser alugado por pouco mais de 100 d&oacute;lares/dia (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<br />\r\n" + 
    			"	H&aacute; quem supervalorize seus modelos. Um <a href=\"http://revistaautoesporte.globo.com/carros/mercedes/\">Mercedes-Benz</a> CLK 320 Cabriolet 2000 n&atilde;o deixa de ter o seu charme, no entanto, a di&aacute;ria de US$ 275 d&oacute;lares (incluindo a taxa) pedida pelo propriet&aacute;rio d&aacute; para alugar um Porsche 911 menos valorizado dos anos 80 ou mais atual.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/uber-tem-prejuizo-de-us-645-miloes-no-segundo-trimestre.html\">Tecnologia: &Uuml;ber tem preju&iacute;zo superior a R$ 1,8 bilh&atilde;o no primeiro semestre de 2017</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/mercedes-benz-1929-ganha-concurso-de-pebble-beach.html\">Cl&aacute;ssicos: Mercedes-Benz de 1929 ganha o pr&ecirc;mio principal em Pebble Beach</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2016/05/que-tal-um-delorean-que-nunca-teve-um-dono.html\">Zerinho: que tal voc&ecirc; comprar um DeLorean DMC-12 nunca antes licenciado?</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/que-tal-alugar-um-carrao-por-um-dia-direto-com-o-dono.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1631258</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Felipe Massa quer continuar na F1 em 2018]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Este foi o segundo pódio de Massa no ano (Foto: Ivan Carneiro/ Autoesporte)\" height=\"413\" id=\"86507\" src=\"http://s2.glbimg.com/iE4-QivmclYtOZQuOZtOfbzI0m8=/620x413/e.glbimg.com/og/ed/f/original/2014/11/09/segundo-podio-no-ano.jpg\" title=\"Este foi o segundo pódio de Massa no ano (Foto: Ivan Carneiro/ Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Este foi o segundo p&oacute;dio de Massa no ano (Foto: Ivan Carneiro/ Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O brasileiro Felipe Massa, que havia anunciado a aposentadoria ao fim de 2016, mas voltou &agrave; F&oacute;rmula 1 em 2017 para substituir Valtteri Bottas na Williams, <strong>n&atilde;o descartou em declara&ccedil;&otilde;es nesta quinta-feira (24) continuar pilotando na categoria na pr&oacute;xima temporada</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&quot;Estou satisfeito com a maneira que pilotei na primeira metade da temporada. <strong>N&atilde;o vejo motivo para n&atilde;o continuar se tiver a oportunidade</strong>&quot;, afirmou Massa, em coletiva de imprensa antes do Grande Pr&ecirc;mio da B&eacute;lgica.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Ausente do GP da Hungria, em final de julho, devido a vertigens causadas por problemas no ouvido,<strong> o veterano de 36 anos foi libertado para competir na B&eacute;lgica, depois de ser examinado por m&eacute;dicos da Federa&ccedil;&atilde;o Internacional do Autom&oacute;vel (FIA)</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&quot;Minha cabe&ccedil;a estava girando. E n&atilde;o s&oacute; no carro, mas quando eu deitava tamb&eacute;m. O problema &eacute; que aconteceu num fim de semana de corrida.<strong> Eu n&atilde;o estava em condi&ccedil;&otilde;es de correr, mas agora me sinto bem</strong>&quot;, explicou o brasileiro.</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/felipe-massa-quer-continuar-na-f1-em-2018.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1631422</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Empresário quer criar empresa de táxis fluviais na França]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Protótipo de táxi fluvial da SeaBubbles durante apresentação em Saint-Tropez, França  (Foto: Philippe Laurenson /Reuters)\" height=\"413\" id=\"238849\" src=\"http://s2.glbimg.com/dOJOMLavFXJ2EPWQNTU8ziaaK1M=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/taxi-fluvial.jpg\" title=\"Protótipo de táxi fluvial da SeaBubbles durante apresentação em Saint-Tropez, França  (Foto: Philippe Laurenson /Reuters)\" width=\"620\" /><label class=\"foto-legenda\">Prot&oacute;tipo de t&aacute;xi fluvial da SeaBubbles durante apresenta&ccedil;&atilde;o em Saint-Tropez, Fran&ccedil;a (Foto: Philippe Laurenson /Reuters)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	O iatista franc&ecirc;s Alain Thebault quer utilizar o modelo de um barco que usou para quebrar um recorde mundial de velocidade em 2009 em <strong>base para um servi&ccedil;o de t&aacute;xi limpo e r&aacute;pido para as vias naveg&aacute;veis das principais cidades do mundo</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O SeaBubble ganhou o apoio de investidores privados </strong>- Thebault espera arrecadar entre 50 milh&otilde;es e 100 milh&otilde;es de euros at&eacute; o fim de setembro.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O presidente da Fran&ccedil;a, Emmanuel Macron, quer criar uma &quot;na&ccedil;&atilde;o de startups&quot;</strong>, at&eacute; defendeu a ideia quando era ministro da Economia. Seu gabinete n&atilde;o respondeu aos pedidos de coment&aacute;rios questionando-o se ainda apoia o projeto.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A SeaBubbles enfrenta obst&aacute;culos regulat&oacute;rios espec&iacute;ficos, sem falar em ter que convencer as autoridades parisienses a elevar o limite de velocidade do rio Sena.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Mas assim como outras startups, <strong>Thebault teme que a sua empresa seja prejudicada pela burocracia administrativa</strong> se a ideia decolar e precisar crescer rapidamente.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&quot;&Eacute; uma estrada cheia de obst&aacute;culos para duas aves marinhas como Anders (Bringdal) e eu&quot;, disse ele sobre seu parceiro comercial, um campe&atilde;o sueco de windsurf. &quot;Se ficar muito complicado ... iremos para onde &eacute; mais f&aacute;cil&quot;.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O prot&oacute;tipo SeaBubbles preserva sua bateria ao se elevar acima do n&iacute;vel da &aacute;gua em alta velocidade</strong>. A prefeita de Paris, Anne Hidalgo, apoiou a ideia com um passeio no rio Sena em junho.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Mas a SeaBubbles s&oacute; tem chance de operar em Paris se as autoridades elevarem o limite de velocidade do rio Sena para que a embarca&ccedil;&atilde;o possa ir r&aacute;pido o suficiente para sair da &aacute;gua, um pedido que at&eacute; agora foi rejeitado. O gabinete da prefeita de Paris n&atilde;o respondeu a um pedido de coment&aacute;rios sobre o projeto, inclusive se ela achava que o limite de velocidade de Paris deveria ser alterado.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Fran&ccedil;a ocupa o 21&ordm; lugar no relat&oacute;rio de competitividade do F&oacute;rum Econ&ocirc;mico Mundial deste ano, com base na sofistica&ccedil;&atilde;o empresarial, na tecnologia e na prepara&ccedil;&atilde;o para a inova&ccedil;&atilde;o.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Mas ficou na 115&ordf; posi&ccedil;&atilde;o entre 138 pa&iacute;ses em termos de &quot;peso da regulamenta&ccedil;&atilde;o governamental&quot; e det&eacute;m a 129&ordf; posi&ccedil;&atilde;o para pr&aacute;ticas de contrata&ccedil;&atilde;o e demiss&atilde;o, com base em dados de 2015.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	As leis trabalhistas restritivas est&atilde;o no topo da lista de reclama&ccedil;&otilde;es dos investidores. Qualquer empresa sediada na Fran&ccedil;a com 50 funcion&aacute;rios ou mais tem que criar um conselho de trabalhadores, organizar elei&ccedil;&otilde;es sindicais e passar por regulamenta&ccedil;&otilde;es estruturadas frequentemente com os trabalhadores. Redund&acirc;ncias e demiss&otilde;es podem ser caras.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Os investidores afirmam que as startups n&atilde;o est&atilde;o sendo prejudicadas por preocupa&ccedil;&otilde;es financeiras, mais sim pela burocracia e leis trabalhistas que s&atilde;o projetadas para proteger os funcion&aacute;rios, mas que podem ser pesadas e dispendiosas para as empresas.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	&quot;<strong>N&atilde;o &eacute; uma quest&atilde;o de dinheiro. H&aacute; muito, muito dinheiro</strong>&quot;, disse Romain Lavault, s&oacute;cio da Partech Ventures, um fundo de capital de risco que tamb&eacute;m investiu na SeaBubbles.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Cerca de US$ 2,03 bilh&otilde;es foram investidos no primeiro semestre do ano em compara&ccedil;&atilde;o com 2,1 bilh&otilde;es de d&oacute;lares em todo o ano de 2016. Isso faz da Fran&ccedil;a o segundo local com melhores financiamentos para startups de tecnologia, depois do Reino Unido, disse a CB Insights.</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/empresario-quer-criar-empresa-de-taxis-fluviais-na-franca.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1631365</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Volkswagen revela o que muda no visual do Polo nacional]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)\" height=\"413\" id=\"238752\" src=\"http://s2.glbimg.com/xAUIceIT_UV8bRFghCB9eUgFzZI=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polo.jpg\" title=\"Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	N&atilde;o foi desta vez que a Volkswagen revelou o visual final do <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/polo/\">Polo</a> nacional. Mas a marca realizou um evento em sua f&aacute;brica, em S&atilde;o Bernardo do Campo (SP), para d<strong>etalhar as mudan&ccedil;as que o modelo brasileiro ter&aacute; em rela&ccedil;&atilde;o ao europeu</strong>. O m&aacute;ximo que p&ocirc;de ser visto da nova gera&ccedil;&atilde;o do hatch foi uma vers&atilde;o de argila em tamanho real, utilizada pelo departamento de design da VW no desenvolvimento.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O nosso Polo ser&aacute; ligeiramente diferente do <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/07/tudo-o-que-ja-sabemos-sobre-o-novo-volkswagen-polo-brasileiro.html\">que vinha sendo mostrado at&eacute; agora</a>. <strong>O para-choque dianteiro, por exemplo, foi redesenhado. </strong>&quot;O carro se saiu muito bem nas cl&iacute;nicas, mas o mercado brasileiro precisava de um pouco mais de esportividade&quot;, afirma Jos&eacute; Carlos Pavone, gerente executivo de design da Volkswagen Am&eacute;rica do Sul. Segundo ele, as mudan&ccedil;as foram inspiradas no <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/06/volkswagen-arteon-o-que-estao-dizendo-por-ai.html\">Arteon</a>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Curiosamente, o visual do para-choque segue um pouco a linha adotada na vers&atilde;o flagrada na &Aacute;frica do Sul, <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/03/novo-volkswagen-polo-e-flagrado-sem-disfarces.html\">conforme publicamos em mar&ccedil;o</a>. Apenas os vincos s&atilde;o mais encorpados no modelo em clay. O hatch flagrado por l&aacute; tamb&eacute;m tem at&eacute; o prolongamento do cap&ocirc;, que invade um pouco a grade e est&aacute; no carro em argila (clay). Um toque introduzido pelo Arteon. Os far&oacute;is s&atilde;o mais simples e se integram a um filete na grade, algo que deve estar no nacional.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Novo Volkswagen Polo é flagrado sem disfarces (Foto: Reprodução)\" height=\"400\" id=\"211123\" src=\"http://s2.glbimg.com/h3nkYO06dUQMCeS7pxJmfw6leEc=/e.glbimg.com/og/ed/f/original/2017/03/17/plodianteira.jpg\" title=\"Novo Volkswagen Polo é flagrado sem disfarces (Foto: Reprodução)\" width=\"620\" /><label class=\"foto-legenda\">Novo Volkswagen Polo &eacute; flagrado sem disfarces (Foto: Reprodu&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	Por dentro, uma perda. <strong>O Polo n&atilde;o ter&aacute; op&ccedil;&atilde;o de painel colorido no Brasil.</strong> &quot;As pesquisas que fizemos mostraram que o mercado brasileiro preza por contraste e inser&ccedil;&atilde;o de cor moderada&quot;, diz Jonas Silva, gestor de Color e Trim. Mas ele explica que &eacute; estudada, sim, a oferta &quot;pontual&quot; de interior colorido no futuro.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A suspens&atilde;o tamb&eacute;m ser&aacute; elevada em 20 mil&iacute;metros, para se adequar ao pavimento daqui.</p>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Gol, Fox e up! continuam</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	A chegada do Polo n&atilde;o ir&aacute; eliminar nenhum dos muitos modelos at&eacute; R$ 100 mil que a montadora j&aacute; oferece no pa&iacute;s. <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/gol/\">Gol </a>e <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/up/\">up!</a> seguir&atilde;o como op&ccedil;&otilde;es de entrada, seguidos pelo Fox. Na sequ&ecirc;ncia, vir&aacute; o <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/polo/\">Polo</a>, antes do caro hatch m&eacute;dio <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/golf/\">Golf</a>. <strong>O objetivo do Polo &eacute; rivalizar com os bem-vendidos <a href=\"http://revistaautoesporte.globo.com/carros/hyundai/hb20/\">Hyundai HB20</a>, <a href=\"http://revistaautoesporte.globo.com/carros/chevrolet/onix/\">Chevrolet Onix</a> e com o rec&eacute;m-lan&ccedil;ado <a href=\"http://revistaautoesporte.globo.com/carros/fiat/argo/\">Fiat Argo</a>.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	Os pre&ccedil;os ainda n&atilde;o foram divulgados, mas <strong>o CEO da Volkswagen do Brasil, David Powels, garante que a vers&atilde;o de entrada ter&aacute; pre&ccedil;os abaixo dos R$ 50 mil. </strong>Esta configura&ccedil;&atilde;o dever&aacute; ser equipada com um motor 1.0 flex aspirado.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>As vers&otilde;es intermedi&aacute;rias do Polo, que devem concentrar a maior parte das vendas, ter&atilde;o como diferencial o motor 1.0 turbo com inje&ccedil;&atilde;o direta (TSI) de 128 cv e 20,4 kgfm. </strong>Trata-se do mesmo motor que equipa o compacto <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen/up/\">up!</a>, por&eacute;m retrabalhado para entregar maior pot&ecirc;ncia e o mesmo torque do <a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2016/09/teste-volkswagen-golf-10-tsi.html\">1.0 TSI usado pelo Golf</a>. Acoplado a este motor estar&aacute; sempre um c&acirc;mbio autom&aacute;tico de seis velocidades.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Por fim, as vers&otilde;es mais caras poder&atilde;o contar com um motor 1.6 flex aspirado, mas esta configura&ccedil;&atilde;o e a com motor 1.0 aspirado ainda n&atilde;o est&atilde;o confirmadas oficialmente.<strong> Os nomes das configura&ccedil;&otilde;es seguir&atilde;o o padr&atilde;o da marca, por&eacute;m sem a tradicional nomenclatura Trendline para os modelos de entrada</strong>: ficar&aacute; Polo, Polo Comfortline e Polo Highline.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Analises/noticia/2017/08/primeiras-impressoes-volkswagen-polo-10-tsi.html\">PRIMEIRAS IMPRESS&Otilde;ES: VOLKSWAGEN POLO 1.0 TSI</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-virtus-o-seda-do-polo-aparece-completamente-sem-camuflagem.html\">VOLKSWAGEN VIRTUS, O SED&Atilde; DO POLO, APARECE COMPLETAMENTE SEM CAMUFLAGEM</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/06/quiz-novo-polo-golf-ou-gol.html\">Quiz: Novo Polo, Gol ou Golf?</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Seguran&ccedil;a</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	Conforme<strong>&nbsp;</strong>antecipamos, <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-polo-nacional-tera-quatro-airbags-de-serie-em-busca-de-nota-maxima-de-seguranca.html\">o Polo nacional ser&aacute; vendido de s&eacute;rie com quatro airbags e recebeu refor&ccedil;os na estrutura</a>. O objetivo &eacute; garantir que o modelo conquiste cinco estrelas no teste de seguran&ccedil;a do Latin NCAP. Apesar disso, <strong>os controles eletr&ocirc;nicos de estabilidade ser&atilde;o ofertados como opcional nas vers&otilde;es mais baratas e ser&atilde;o item de s&eacute;rie apenas na configura&ccedil;&atilde;o turbo</strong>.&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Al&eacute;m dos airbags laterias, o Polo vir&aacute; de f&aacute;brica com top tether, um sistema extra de ancoragem que evita rota&ccedil;&otilde;es das cadeirinhas. Cintos de tr&ecirc;s pontos, ajuste de altura dos cintos e apoios de cabe&ccedil;a ser&atilde;o para todos, de s&eacute;rie.&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	De acordo com Antonio Carnielli Jr, engenheiro de carroceria e seguran&ccedil;a da Volkswagen, o Polo foi construido com&nbsp;50% de a&ccedil;os de alta resist&ecirc;ncia. Segundo o executivo, o compacto ainda &quot;<strong>foi projetado para ter o m&aacute;ximo de seguran&ccedil;a para pedestres&quot;</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)\" height=\"413\" id=\"238717\" src=\"http://s2.glbimg.com/2bTAvkltwLlUcbO3SsfSyo1Hb_A=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/a1c12650-aabb-4aff-afba-5114de9301c7.jpg\" title=\"Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Teste de colis&atilde;o lateral: Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	Para garantir que o modelo seja testado logo e com o objetivo de utilizar um poss&iacute;vel bom resultado como estrat&eacute;gia de marketing,<strong> a montadora ir&aacute; patrocinar o primeiro teste de colis&atilde;o do carro</strong>. Isso n&atilde;o significa, por&eacute;m, que a iniciativa influenciar&aacute; no resultado da avalia&ccedil;&atilde;o. O Latin NCAP aceita que montadoras invistam no teste para que o &oacute;rg&atilde;o compre uma unidade, por&eacute;m o carro &eacute; escolhido pelo instituto.&nbsp;&quot;<strong>Nota m&aacute;xima em seguran&ccedil;a n&atilde;o &eacute; op&ccedil;&atilde;o, &eacute; princ&iacute;pio</strong>&quot;, afirma Gustavo Schmidt, vice-presidente de vendas e marketing da fabricante.</p>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Itens de s&eacute;rie</div>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	Todas as vers&otilde;es do compacto ser&atilde;o equipadas com <strong>dire&ccedil;&atilde;o el&eacute;trica, freios a disco nas rodas dianteiras e a tambor nas traseiras, al&eacute;m de suspens&atilde;o dianteira McPherson e traseira por eixo de tor&ccedil;&atilde;o.</strong> Haver&aacute; ainda bancos bipartidos e pneus de baixa resist&ecirc;ncia com rodas que variam entre 15, 16 e 17 polegadas.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Tamb&eacute;m j&aacute; adiantamos que<strong> o Polo ter&aacute; itens mais refinados, mas a montadora ainda n&atilde;o confirma se eles ser&atilde;o ofertados como itens de s&eacute;rie ou opcionais</strong>. S&atilde;o eles: sensor de luz e chuva, volante multifuncional, teto solar, controle de velocidade de cruzeiro, ar-condicionado digital, tr&ecirc;s entradas USB, sistema start/stop, c&acirc;mera de r&eacute;, rebatimento dos retrovisores externos e sensores de estacionamento dianteiro e traseiro.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	O Polo tamb&eacute;m ser&aacute; o respons&aacute;vel por inaugurar o in&eacute;dito quadro de instrumentos digital em modelos nacionais da Volkswagen. A tecnologia usa uma tela colorida de 10,2 polegadas para substituir o quadro de instrumentos anal&oacute;gico tradicional.<strong> Este item, por&eacute;m, ser&aacute; um opcional apenas da vers&atilde;o topo de linha.</strong></p>\r\n" + 
    			"<div class=\"componente_materia\">\r\n" + 
    			"	<div class=\"intertitulo\">\r\n" + 
    			"		Veja como ser&atilde;o os bancos e as rodas das tr&ecirc;s vers&otilde;es do Polo</div>\r\n" + 
    			"</div>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen Polo - versão de entrada (Foto: Guilherme Blanco Muniz / Autoesporte)\" height=\"413\" id=\"238745\" src=\"http://s2.glbimg.com/aR05WvaNUmmawX8JeUEbOeQHfek=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polo_de_entrada.jpg\" title=\"Volkswagen Polo - versão de entrada (Foto: Guilherme Blanco Muniz / Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Volkswagen Polo (Foto: Guilherme Blanco Muniz / Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen Polo Comfortline (Foto: Guilherme Blanco Muniz / Autoesporte)\" height=\"413\" id=\"238748\" src=\"http://s2.glbimg.com/lztEt8S0P4Hfd1O7Eby0a35k8tE=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polo_comfortline.jpg\" title=\"Volkswagen Polo Comfortline (Foto: Guilherme Blanco Muniz / Autoesporte)\" width=\"620\" /><label class=\"foto-legenda\">Volkswagen Polo Comfortline (Foto: Guilherme Blanco Muniz / Autoesporte)</label></div>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Volkswagen Polo Highline (Foto: G)\" height=\"413\" id=\"238750\" src=\"http://s2.glbimg.com/tfnefnVbT1Vp1oY7pioLhotaHvQ=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/polo_highline.jpg\" title=\"Volkswagen Polo Highline (Foto: G)\" width=\"620\" /><label class=\"foto-legenda\">Volkswagen Polo Highline (Foto: Guilherme Blanco Muniz / Autoesporte)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/volkswagen-revela-o-que-muda-no-visual-do-polo-nacional.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1627157</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Uber tem prejuízo de US$ 645 milhões no segundo trimestre]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Sede da Uber em São Francisco (EUA) (Foto: JUSTIN SULLIVAN / AFP)\" height=\"400\" id=\"220771\" src=\"http://s2.glbimg.com/SMpNZpiiA0gJR2Eo0rEiZTvyq60=/e.glbimg.com/og/ed/f/original/2017/05/15/uber.jpg\" title=\"Sede da Uber em São Francisco (EUA) (Foto: JUSTIN SULLIVAN / AFP)\" width=\"620\" /><label class=\"foto-legenda\">Sede da Uber em S&atilde;o Francisco (EUA) (Foto: JUSTIN SULLIVAN / AFP)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	A <a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/04/uber-conheca-historia-e-polemicas-da-empresa-de-transporte.html\">Uber</a> reduziu seu preju&iacute;zo em 9% no segundo trimestre e aumentou o volume de corridas, disse a empresa nesta quarta-feira (23), <strong>mas ainda est&aacute; longe ser lucrativa</strong>. A Uber informou preju&iacute;zo l&iacute;quido de US$ 645 milh&otilde;es, abaixo dos US$ 708 milh&otilde;es do primeiro trimestre e dos US$ 991 milh&otilde;es nos &uacute;ltimos tr&ecirc;s messes de 2016.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O movimento sinaliza os esfor&ccedil;os da empresa para reduzir gastos com subs&iacute;dios para motoristas e clientes</strong>, e outras pr&aacute;ticas competitivas, &agrave; medida que aumenta a concorr&ecirc;ncia em mercados dif&iacute;ceis como o sul da &Aacute;sia.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A companhia disse que <strong>os pedidos de corridas para o per&iacute;odo atingiram US$ 8,7 milh&otilde;es</strong>, alta de 17% ante o trimestre anterior. O n&uacute;mero de viagens globais no aplicativo aumentou 150% em rela&ccedil;&atilde;o ao ano anterior, com forte crescimento em mercados em desenvolvimento.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A receita l&iacute;quida ajustada atingiu US$ 1,75 bilh&atilde;o, ante US$ 1,5 bilh&otilde;es no primeiro trimestre.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Desde 2010, o Uber captou mais de US$ 15 bilh&otilde;es de investidores, o que a permitiu que operar com preju&iacute;zo</strong>. A companhia disse que tem US$ 6,6 bilh&otilde;es em caixa, abaixo dos US$ 7,2 bilh&otilde;es no primeiro trimestre.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/preco-real-de-corridas-do-uber-entra-em-questao-com-investidores.html\">PRE&Ccedil;O REAL DE CORRIDAS DO UBER ENTRA EM QUEST&Atilde;O COM INVESTIDORES</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/04/uber-conheca-historia-e-polemicas-da-empresa-de-transporte.html\">UBER: CONHE&Ccedil;A A HIST&Oacute;RIA E POL&Ecirc;MICAS DA EMPRESA DE TRANSPORTE</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Podcast/noticia/2017/08/podcast-central-eletronica-03-uber-versus-o-mundo-e-historia-do-motorista-fantasiado-de-banco.html\">OU&Ccedil;A: AS POL&Ecirc;MICAS DA UBER NO PODCAST CENTRAL ELETR&Ocirc;NICA</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/uber-tem-prejuizo-de-us-645-miloes-no-segundo-trimestre.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1629911</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Superguia com automáticos de até R$ 70 mil na revista Autoesporte de setembro]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Autoesporte de setembro (Foto: Autoesporte)\" height=\"413\" id=\"238637\" src=\"http://s2.glbimg.com/qYNc4Nrbftcyms4fOJWbSD9R3nE=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/capa_autoesporte_628_2.jpg\" title=\"Autoesporte de setembro (Foto: Autoesporte)\" width=\"620\" /></div>\r\n" + 
    			"<p>\r\n" + 
    			"	Voc&ecirc; sabia que mais de 40% dos carros compactos vendidos no Brasil em 2017 s&atilde;o autom&aacute;ticos? A procura por modelos sem pedal da embreagem cresce ano ap&oacute;s ano. <strong>Para ajudar voc&ecirc; na compra do 0 km, testamos todos os ve&iacute;culos autom&aacute;ticos &agrave; venda no pa&iacute;s com pre&ccedil;o de at&eacute; R$ 70 mil.</strong> Nosso superguia vai ajudar a descobrir qual modelo &eacute; a op&ccedil;&atilde;o perfeita para voc&ecirc;.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Autoesporte ainda revela os planos ambiciosos da Volkswagen para o Brasil com proje&ccedil;&otilde;es do novo sed&atilde; compacto Virtus e entrevista com o presidente da montadora alem&atilde;, David Powels.&nbsp;<strong>Na revista, voc&ecirc; descobre as armas que a Volkswagen prepara para os pr&oacute;ximos anos nas disputas de SUVs, hatches, sed&atilde;s e picapes.</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O principal duelo da edi&ccedil;&atilde;o &eacute; o embate entre as novas gera&ccedil;&otilde;es de Volvo XC60 e Audi Q5, que se enfrentam em um comparativo de SUVs m&eacute;dios de luxo.</strong> A revista ainda coloca o novo Fiat Argo contra os usados Fiat Bravo e Hyundai i30 e o novo Ford EcoSport enfrenta uma aventura pelas margens do rio S&atilde;o Francisco.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Confira outros destaques da edi&ccedil;&atilde;o de setembro</strong></p>\r\n" + 
    			"<p>\r\n" + 
    			"	- Rolls-Royce Phantom<br />\r\n" + 
    			"	- Volkswagen up! Pepper<br />\r\n" + 
    			"	- Porsche Panamera H&iacute;brido<br />\r\n" + 
    			"	- Mitsubishi Eclipse Cross<br />\r\n" + 
    			"	- Land Rover Velar<br />\r\n" + 
    			"	- Mercedes-AMG E63<br />\r\n" + 
    			"	- Chevrolet S10 turbodiesel<br />\r\n" + 
    			"	- Mercedes-Benz GLA</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Se n&atilde;o quiser ir at&eacute; a banca comprar a sua Autoesporte, n&atilde;o tem problema! <strong>Voc&ecirc; j&aacute; pode ter acesso ao conte&uacute;do completo da revista no seu celular atrav&eacute;s do nosso novo aplicativo.</strong> O download pode ser feito gratuitamente para Android e iOS. L&aacute;, voc&ecirc; tamb&eacute;m ter&aacute; acesso &agrave;s not&iacute;cias e conte&uacute;dos do site, al&eacute;m de mat&eacute;rias exclusivas.</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/busca/click?q=produto&amp;p=0&amp;r=1503529801441&amp;u=http%3A%2F%2Frevistaautoesporte.globo.com%2FNoticias%2Fnoticia%2F2017%2F08%2Fvolkswagen-revela-t-roc-modelo-que-vem-para-o-brasil.html&amp;t=informacional&amp;d=false&amp;f=false&amp;ss=&amp;o=&amp;cat=\">Volkswagen revela T-Roc, modelo que vem para o Brasil</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/busca/click?q=produto&amp;p=4&amp;r=1503529801443&amp;u=http%3A%2F%2Frevistaautoesporte.globo.com%2FNoticias%2Fnoticia%2F2017%2F08%2Fvolkswagen-estuda-importar-o-suv-atlas-para-o-brasil.html&amp;t=informacional&amp;d=false&amp;f=false&amp;ss=&amp;o=&amp;cat=\">Volkswagen estuda importar o SUV Atlas para o Brasil</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/superguia-com-automaticos-de-ate-r-70-mil-na-revista-autoesporte-de-setembro.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1626438</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Audi desenvolve com chinesa Hanergy veículo com placas solares no teto]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Logo da Audi (Foto: Reuters)\" height=\"413\" id=\"238714\" src=\"http://s2.glbimg.com/u1IaFITUHDyX73gRdtXhFNRhM7U=/620x413/e.glbimg.com/og/ed/f/original/2017/08/24/audi.jpg\" title=\"Logo da Audi (Foto: Reuters)\" width=\"620\" /><label class=\"foto-legenda\">Logo da Audi (Foto: Reuters)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	A <a href=\"http://revistaautoesporte.globo.com/carros/audi\">Audi</a>, principal gerador de lucro para a <a href=\"http://revistaautoesporte.globo.com/carros/volkswagen\">Volkswagen</a>, disse nesta quarta-feira (23) que est&aacute; trabalhando com a chinesa Hanergy Thin Film Power para <strong>integrar c&eacute;lulas solares em teto de vidro panor&acirc;mico no pr&oacute;ximo ve&iacute;culo el&eacute;trico da montadora</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A Alta Devices, unidade da empresa de c&eacute;lulas solares Hanergy, ir&aacute; projetar o teto do ve&iacute;culo integrado &agrave; energia solar que eventualmente ajudar&aacute; a aumentar a gama de ve&iacute;culos el&eacute;tricos, <strong>alimentando sistemas el&eacute;tricos internos, como ar-condicionado e outros componentes</strong>, declarou a Audi.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>O prot&oacute;tipo do ve&iacute;culo ser&aacute; constru&iacute;do at&eacute; o fim de 2017</strong>, acrescentou a montadora sem dar detalhes sobre investimentos ou estimativa de prazo para produ&ccedil;&atilde;o em massa.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em comunicado nesta quarta-feira (23), <strong>a Audi disse que planeja desenvolver tr&ecirc;s modelos el&eacute;tricos movidos a bateria at&eacute; 2020 e pretende cobrir um ter&ccedil;o de seus ve&iacute;culos com transmiss&atilde;o totalmente el&eacute;trica at&eacute; 2025</strong>.</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Em uma fase posterior, a montadora informou que espera poder usar energia solar para recarregar a bateria por tra&ccedil;&atilde;o.</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/audi-desenvolve-com-chinesa-hanergy-veiculo-com-placas-solares-no-teto.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1629781</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"\r\n" + 
    			"  <item>\r\n" + 
    			"    <title><![CDATA[Ferrari apresenta o Portofino, seu novo supercarro de entrada]]></title>\r\n" + 
    			"    <description><![CDATA[<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238674\" src=\"http://s2.glbimg.com/8u7yyknslrmR48VLMPklK2CARk8=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-05.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Portofino &eacute; o nome de uma charmosa cidade da costa italiana (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>A Ferrari revelou as primeiras imagens de seu novo supercarro, o Portofino, que ser&aacute; apresentado oficialmente em duas semanas, na abertura do Sal&atilde;o do Autom&oacute;vel de Frankfurt (Alemanha)</strong>. O cup&ecirc;-convers&iacute;vel ser&aacute; o superesportivo de entrada da marca italiana, para o lugar do j&aacute; veterano California, lan&ccedil;ado em 2009 e renovado pela &uacute;ltima vez em 2015. E traz muitas evolu&ccedil;&otilde;es, para misturar conforto, precis&atilde;o e desempenho brutal.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238675\" src=\"http://s2.glbimg.com/U8sbzoYo5gkI_R_L16dpQGP4Bow=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-04.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Traseira do supercarro tem menos volume e &eacute; mais elegante (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Visualmente, <strong>o Portofino lembra bastante o elegante e suntuoso cup&ecirc; de quatro lugares GTC4 Lusso. A dianteira tem alguns elementos em comum, mas h&aacute; outras particularidades que se destacam &mdash; como as sa&iacute;das de ar atr&aacute;s das rodas dianteiras, que recortam o para-lamas</strong> e d&atilde;o um ar mais agressivo, com cavidades que se prolongam pelas portas. Na compara&ccedil;&atilde;o com o antecessor, a traseira parece mais harmoniosa, com menos volume.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238676\" src=\"http://s2.glbimg.com/sqi0P_GzTA3SYF8TsP1RJktiSNQ=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-02.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Sa&iacute;das de ar recortam para-lamas dianteiros (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	Segundo a f&aacute;brica de Maranello, <strong>o chassi do Portofino &eacute; nov&iacute;ssimo, mais leve e r&iacute;gido que o do California. J&aacute; a mec&acirc;nica &eacute; a mesma do antecessor, por&eacute;m com ajustes que elevaram pot&ecirc;ncia em 40 cavalos &mdash; foram inclu&iacute;dos novos pist&otilde;es e bielas, e o coletor de admiss&atilde;o foi redesenhado. O 3.9 V8 biturbo despeja 600 cv a 7.500 rpm e um torque truculento de 77,5 kgfm entre 3.000 rpm e 5.250 rpm &mdash; 0,5 a mais que antes. A acelera&ccedil;&atilde;o de 0-100 km/h apenas 3,5 segundos e m&aacute;xima atinge incr&iacute;veis 320 km/h</strong>.</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238672\" src=\"http://s2.glbimg.com/SeCtySR8gkGj1dhBrR7-D27KiLk=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-06.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Cabine tem acabamento primoroso, com muito metal fosco (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	<strong>Outra importante evolu&ccedil;&atilde;o &eacute; a dire&ccedil;&atilde;o el&eacute;trica em vez de hidr&aacute;ulica &mdash; segundo a marca, as respostas est&atilde;o 7% mais diretas. Na cabine, a principal novidade &eacute; a central multim&iacute;dia com uma generosa tela touch de 10,2 polegadas</strong>. A Ferrari n&atilde;o divulgou todos os detalhes, mas &eacute; poss&iacute;vel ver outro visor touch &agrave; frente do banco do passageiro, com &iacute;cones para operar o equipamento. <strong>H&aacute; ainda itens interessantes, como ajustes el&eacute;tricos com 18 posi&ccedil;&otilde;es para os bancos dianteiros e ar-condicionado capaz de ajustar a temperatura mesmo com o teto aberto</strong>.&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238673\" src=\"http://s2.glbimg.com/l-OOkOB0to_Wyc6zDY9VvDpanKQ=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-07.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Portofino tem quatro lugares e promete elevado conforto (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<p>\r\n" + 
    			"	A escolha do nome vem do ber&ccedil;o da marca. Portofino &eacute; uma cidade do noroeste da It&aacute;lia, que a Ferrari alega ser uma das mais belas e charmosas de sua terra natal. Bem apropriado, n&atilde;o?</p>\r\n" + 
    			"<div class=\"saibamais componente_materia expandido\">\r\n" + 
    			"	<strong>saiba mais</strong>\r\n" + 
    			"	<ul>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/04/ferrari-cria-cadeira-de-escritorio-inspirada-em-carros-de-competicao.html\" target=\"_blank\">Ferrari cria cadeira de escrit&oacute;rio inspirada em carros de competi&ccedil;&atilde;o</a></li>\r\n" + 
    			"		<li>\r\n" + 
    			"			<a href=\"http://revistaautoesporte.globo.com/Noticias/noticia/2017/02/ferrari-milionaria-e-achada-dentro-de-apartamento.html\" target=\"_blank\">Ferrari milion&aacute;ria &eacute; achada dentro de apartamento</a></li>\r\n" + 
    			"	</ul>\r\n" + 
    			"</div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238677\" src=\"http://s2.glbimg.com/y_uVExFZfS9qiVFKbMS-QPL0noA=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-01.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Novo modelo de entrada lembra o topo de linha GTC4 Lusso (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"<div class=\"foto componente_materia midia-largura-620\">\r\n" + 
    			"	<img alt=\"Ferrari Portofino 2018 (Foto: Divulgação)\" height=\"413\" id=\"238670\" src=\"http://s2.glbimg.com/JiJfomCpxQSTMNmoZbDvXASA7zc=/620x413/e.glbimg.com/og/ed/f/original/2017/08/23/ferrari-portofino-2018-autoesporte-08.jpg\" title=\"Ferrari Portofino 2018 (Foto: Divulgação)\" width=\"620\" /><label class=\"foto-legenda\">Marca revelar&aacute; detalhes do modelo no Sal&atilde;o de Frankfurt (Foto: Divulga&ccedil;&atilde;o)</label></div>\r\n" + 
    			"<p>\r\n" + 
    			"	&nbsp;</p>\r\n" + 
    			"]]></description>\r\n" + 
    			"    <link>http://revistaautoesporte.globo.com/Noticias/noticia/2017/08/ferrari-apresenta-o-portofino-seu-novo-supercarro-de-entrada.html</link>\r\n" + 
    			"    <dc:creator>Da redação de Auto Esporte</dc:creator>\r\n" + 
    			"    <guid>1629546</guid>\r\n" + 
    			"  </item>\r\n" + 
    			"\r\n" + 
    			"</channel>\r\n" + 
    			"</rss>\r\n" + 
    			"";
    	//return (new XMLConvert()).downloadAsString("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml");
    }
}
