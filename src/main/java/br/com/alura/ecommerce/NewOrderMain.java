package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(KafkaDispatcher orderDispatcher = new KafkaDispatcher<Order>()){
            try(KafkaDispatcher emailDispatcher = new KafkaDispatcher<String>()){
                for (int i = 0; i < 10; i++){

                    String key = UUID.randomUUID().toString();
                    String userId = UUID.randomUUID().toString();
                    String orderId = UUID.randomUUID().toString();
                    BigDecimal amount = new BigDecimal(Math.random() * 5000 + 1);

                    Order order = new Order(userId, orderId, amount);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", key, order);


                    String email = "Thank you for your order! We are processing your order!";
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);

                }
            }
        }
    }

}
