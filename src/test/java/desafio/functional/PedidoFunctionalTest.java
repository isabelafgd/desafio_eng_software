//package desafio.functional;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import desafio.config.RabbitMQConfig;
//import desafio.model.ClienteQtdPedidos;
//import desafio.model.Item;
//import desafio.model.Pedido;
//import desafio.model.PedidoValor;
//import desafio.repository.PedidoRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import java.util.Arrays;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PedidoFunctionalTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private PedidoRepository pedidoRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testFullProcess() throws InterruptedException, JsonProcessingException {
//        // Simula envio de mensagem para RabbitMQ
//        Pedido rabbitPedido = new Pedido();
//        rabbitPedido.setCodigoPedido(1003L);
//        rabbitPedido.setCodigoCliente(3L);
//        Item item = new Item();
//        item.setProduto("caderno");
//        item.setQuantidade(3L);
//        item.setPreco(15.50);
//        rabbitPedido.setItens(Arrays.asList(item));
//
//        String jsonMessage = objectMapper.writeValueAsString(rabbitPedido);
//        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, jsonMessage);
//
//        // Espera o processamento da mensagem
//        Thread.sleep(2000);
//
//        // Verifica se o pedido foi salvo no banco
//        Pedido pedido = pedidoRepository.findPedidoByCodigoPedido(1003L);
//        assertThat(pedido).isNotNull();
//
//        // Verifica as consultas via API
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Pedidos por cliente
//        String url = "http://localhost:" + port + "/api/cliente/3/pedidos";
//        ResponseEntity<Pedido[]> responsePedidos = restTemplate.getForEntity(url, Pedido[].class);
//        assertThat(responsePedidos.getStatusCode().is2xxSuccessful()).isTrue();
//        assertThat(responsePedidos.getBody()).hasSize(1);
//        Pedido pedidoResponse = responsePedidos.getBody()[0];
//        assertThat(pedidoResponse.getCodigoPedido()).isEqualTo(1003L);
//        assertThat(pedidoResponse.getCodigoCliente()).isEqualTo(3L);
//        assertThat(pedidoResponse.getItens()).hasSize(1);
//        assertThat(pedidoResponse.getItens().get(0).getProduto()).isEqualTo("caderno");
//        assertThat(pedidoResponse.getItens().get(0).getPreco()).isEqualTo(15.5);
//        assertThat(pedidoResponse.getItens().get(0).getQuantidade()).isEqualTo(3L);
//
//        // Valor total do pedido
//        url = "http://localhost:" + port + "/api/pedido/1003/valor";
//        ResponseEntity<PedidoValor> responsePedidoValor = restTemplate.getForEntity(url, PedidoValor.class);
//        assertThat(responsePedidoValor.getStatusCode().is2xxSuccessful()).isTrue();
//        PedidoValor pedidoValor = responsePedidoValor.getBody();
//        assertThat(pedidoValor.getCodigoPedido()).isEqualTo(1003L);
//        assertThat(pedidoValor.getValorTotal()).isEqualTo(46.5);
//
//        // Quantidade de pedidos por cliente
//        url = "http://localhost:" + port + "/api/cliente/qtdPedidos";
//        ResponseEntity<ClienteQtdPedidos[]> responseQtdPedidos = restTemplate.getForEntity(url, ClienteQtdPedidos[].class);
//        assertThat(responseQtdPedidos.getStatusCode().is2xxSuccessful()).isTrue();
//        assertThat(responseQtdPedidos.getBody()).hasSize(1);
//        ClienteQtdPedidos clienteQtdPedidos = responseQtdPedidos.getBody()[0];
//        assertThat(clienteQtdPedidos.getCodigoCliente()).isEqualTo(3L);
//        assertThat(clienteQtdPedidos.getQuantidadePedidos()).isEqualTo(1L);
//
//        // Quantidade de pedidos de um cliente específico
//        url = "http://localhost:" + port + "/api/cliente/3/qtdPedidos";
//        ResponseEntity<ClienteQtdPedidos> responseQtdPedidosCliente = restTemplate.getForEntity(url, ClienteQtdPedidos.class);
//        assertThat(responseQtdPedidosCliente.getStatusCode().is2xxSuccessful()).isTrue();
//        clienteQtdPedidos = responseQtdPedidosCliente.getBody();
//        assertThat(clienteQtdPedidos.getCodigoCliente()).isEqualTo(3L);
//        assertThat(clienteQtdPedidos.getQuantidadePedidos()).isEqualTo(1L);
//    }
//}
