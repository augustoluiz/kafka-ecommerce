package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(KafkaDispatcher kafkaDispatcher = new KafkaDispatcher()){
            for (int i = 0; i < 100; i++){

                String key = UUID.randomUUID().toString();
                String value = key + "123123,456456,789788";
                kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", key, value);


                String email = "Thank you for your order! We are processing your order!";
                kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);

            }
        }
    }

}
