package desafio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SQSReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(SQSReceiver.class);

    @Autowired
    private PedidoService pedidoService;

    @SqsListener(value = "${cloud.aws.sqs.queue}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receberMensagem(String mensagem, Acknowledgment acknowledgment) {
        LOG.info("Recebendo mensagem do SQS: {}", mensagem);
        try {
            pedidoService.processarMensagem(mensagem);
            acknowledgment.acknowledge().get();
        } catch (JsonProcessingException e){
            LOG.error("Mensagem fora do padrao: {}. Removendo mensagem da fila.", e.getMessage());
            try {
                acknowledgment.acknowledge().get();
            }catch (InterruptedException | ExecutionException executionException){
                LOG.error("Erro ao remover mensagem da fila: {}", executionException.getMessage());
            }
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Erro ao remover mensagem da fila: {}", e.getMessage());
        } catch (Exception e){
            LOG.info("Erro interno: {}. Retornando mensagem para fila.", e.getMessage());
        }
    }
}
