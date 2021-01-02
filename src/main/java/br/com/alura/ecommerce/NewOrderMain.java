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

        for (int i = 0; i < 100; i++){
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());

            //Chave + Valor
            String key = UUID.randomUUID().toString();
            String value = key + "123123,456456,789788";
            String email = "Thank you for your order! We are processing your order!";

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", key, value);
            ProducerRecord<String, String> emailRecord = new ProducerRecord<String, String>("ECOMMERCE_SEND_EMAIL", key, email);

            Callback callback = (data, ex) -> {
                if(ex != null){
                    ex.printStackTrace();
                    return;
                }
                System.out.println("Sucesso, enviando... " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/timestamp "+data.timestamp());
            };

            producer.send(record, callback).get();
            producer.send(emailRecord, callback).get();
        }

    }

    private static Properties properties(){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

}
