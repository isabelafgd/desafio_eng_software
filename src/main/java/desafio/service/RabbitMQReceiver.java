package desafio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import desafio.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @Autowired
    private PedidoService pedidoService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receberMensagem(String mensagem, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("Recebendo mensagem do RabbitMQ: {}", mensagem);
        try {
            pedidoService.processarMensagem(mensagem);
            channel.basicAck(tag, false);
        } catch (JsonProcessingException e){
            LOG.error("Mensagem fora do padrão: {}. Removendo mensagem da fila.", e.getMessage());
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ioException){
                LOG.error("Erro ao remover mensagem da fila: {}", ioException.getMessage());
            }
        } catch (Exception e){
            LOG.info("Erro interno: {}. Retornando mensagem para fila.", e.getMessage());
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException){
                LOG.error("Erro ao retornar mensagem para fila: {}", ioException.getMessage());
            }
        }
    }

}
