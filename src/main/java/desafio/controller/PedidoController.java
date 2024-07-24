package desafio.controller;

import desafio.model.ClienteQtdPedidos;
import desafio.model.Pedido;
import desafio.model.PedidoValor;
import desafio.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(value = "/pedido/{codigoPedido}/valor")
    public ResponseEntity<?> consultarValorTotalPedido (@PathVariable Long codigoPedido) {
        try {
            PedidoValor pedidoValor = pedidoService.calcularValorTotalPedido(codigoPedido);
            if (pedidoValor == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(pedidoValor);
            }
        }catch (Exception e){
            LOG.error("Erro interno: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cliente/qtdPedidos")
    public ResponseEntity<?> consultarQtdPedidosPorCliente () {
        try {
            List<ClienteQtdPedidos> clienteQtdPedidos = (List<ClienteQtdPedidos>) pedidoService.contarPedidosPorCliente(null);
            if (clienteQtdPedidos == null || clienteQtdPedidos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(clienteQtdPedidos);
            }
        }catch (Exception e){
            LOG.error("Erro interno: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cliente/{codigoCliente}/qtdPedidos")
    public ResponseEntity<?> consultarQtdPedidosPorCliente (@PathVariable Long codigoCliente) {
        try {
            ClienteQtdPedidos clienteQtdPedidos = (ClienteQtdPedidos) pedidoService.contarPedidosPorCliente(codigoCliente);
            if (clienteQtdPedidos == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(clienteQtdPedidos);
            }
        }catch (Exception e){
            LOG.error("Erro interno: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cliente/{codigoCliente}/pedidos")
    public ResponseEntity<?> consultarPedidosPorCliente (@PathVariable Long codigoCliente) {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(codigoCliente);
            if (pedidos == null || pedidos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(pedidos);
            }
        }catch (Exception e){
            LOG.error("Erro interno: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
