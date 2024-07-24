package desafio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.model.ClienteQtdPedidos;
import desafio.model.Pedido;
import desafio.model.PedidoValor;
import desafio.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void processarMensagem(String mensagem) throws JsonProcessingException {
        Pedido pedido = objectMapper.readValue(mensagem, Pedido.class);
        pedidoRepository.save(pedido);
    }

    public PedidoValor calcularValorTotalPedido(Long codigoPedido){
        PedidoValor pedidoValor = pedidoRepository.calcularValorTotalPedido(codigoPedido);
        return pedidoValor;
    }

    public Object contarPedidosPorCliente(Long codigoCliente){
        if(codigoCliente == null){
            List<ClienteQtdPedidos> clienteQtdPedidos = pedidoRepository.contarPedidosPorCliente();
            return clienteQtdPedidos;
        }else{
            ClienteQtdPedidos clienteQtdPedidos = pedidoRepository.contarPedidosPorCliente(codigoCliente);
            return clienteQtdPedidos;
        }
    }

    public List<Pedido> listarPedidosPorCliente(Long codigoCliente){
        List<Pedido> pedidos = pedidoRepository.listarPedidosPorCliente(codigoCliente);
        return pedidos;
    }
}
