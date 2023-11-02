package com.example.Notificationservice.Config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig1 {
    private final String que1="kavinqueee";
    private final String key1="kavinroutingkey1";

    private final String exc="kavinexchange";


    @Bean
    public Queue queue1(){
        return new Queue(que1);
    }

    @Bean
    public TopicExchange exchange1(){
        return new TopicExchange(exc);
    }


    @Bean
    public Binding bindings(){
        return BindingBuilder
                .bind(queue1())
                .to(exchange1())
                .with(key1);
    }

}
