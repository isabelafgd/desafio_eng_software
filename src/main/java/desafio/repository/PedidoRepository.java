package desafio.repository;

import desafio.model.ClienteQtdPedidos;
import desafio.model.Pedido;
import desafio.model.PedidoValor;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PedidoRepository extends MongoRepository<Pedido, String> {

    @Aggregation(pipeline = {
            "{ '$match': { 'codigoPedido': ?0 } }",
            "{ '$unwind': '$itens' }",
            "{ '$group': { '_id': '$codigoPedido', 'valorTotal': { '$sum': { '$multiply': [ '$itens.preco', '$itens.quantidade' ] } } } }",
            "{ '$project': { 'codigoPedido': '$_id', 'valorTotal': { '$divide': [{ '$trunc': { '$multiply': ['$valorTotal', 100] } }, 100] } } }"
    })
    PedidoValor calcularValorTotalPedido(Long codigoPedido);

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$codigoCliente', 'quantidadePedidos': { '$sum': 1 } } }",
            "{ '$project': { 'codigoCliente': '$_id', 'quantidadePedidos': 1 } }"
    })
    List<ClienteQtdPedidos> contarPedidosPorCliente();

    @Aggregation(pipeline = {
            "{ '$match': { 'codigoCliente': ?0 } }",
            "{ '$group': { '_id': '$codigoCliente', 'quantidadePedidos': { '$sum': 1 } } }",
            "{ '$project': { 'codigoCliente': '$_id', 'quantidadePedidos': 1 } }"
    })
    ClienteQtdPedidos contarPedidosPorCliente(Long codigoCLiente);

    @Query("{ 'codigoCliente': ?0 }")
    List<Pedido> listarPedidosPorCliente(Long codigoCliente);

    Pedido findPedidoByCodigoPedido(Long condigoPedido);

}

