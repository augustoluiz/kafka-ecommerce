package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class FraudeDetectorService {

    public static void main(String[] args) {
        KafkaConsumer consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if(!records.isEmpty()){
                System.out.println(records.count()+ " registros encontrados...");
                records.forEach((record) -> {
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
                });
            }
        }
    }

    public static Properties properties(){
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudeDetectorService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudeDetectorService.class.getSimpleName() +"-"+UUID.randomUUID());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

}
