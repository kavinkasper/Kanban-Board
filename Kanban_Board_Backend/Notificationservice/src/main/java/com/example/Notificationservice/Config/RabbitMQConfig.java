package com.example.Notificationservice.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private final String que="kavinque";

    private final String exc="kavinexchange";

    private final String key="kavinroutingkey";


    @Bean
    public Queue queue(){
        return new Queue(que);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exc);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(key);
    }


}

