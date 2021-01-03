package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.UUID;

public class FraudeDetectorService {

    public static void main(String[] args) {

        FraudeDetectorService fraudeDetectorService = new FraudeDetectorService();

        KafkaService service = new KafkaService(FraudeDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudeDetectorService::parse);
        service.run();

    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println("-----------------------------------------");
        System.out.println("Processando new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try{
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Ordem processada");
    }

}
