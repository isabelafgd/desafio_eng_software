package desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteQtdPedidos {

    private Long codigoCliente;
    private Long quantidadePedidos;
}
