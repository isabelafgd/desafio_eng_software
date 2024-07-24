package desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pedido")
public class Pedido {

    @MongoId
    @JsonIgnore
    private String id;
    private Long codigoPedido;
    private Long codigoCliente;
    private List<Item> itens;

}
