package main.by.intexsoft.testBilling.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class RabbitMqConfig {

	@Value("${rabbitmq.host}")
	private String hostName;

	@Value("${rabbitmq.messagesExchange:test}")
	private String exchange;

	/**
	 * Setting connection from RabbitMQ
	 *
	 * @return {@link ConnectionFactory}
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(hostName);
	}

	@Bean
	public RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setExchange(exchange);
		return rabbitTemplate;
	}

	@Bean(name = "rabbitListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory listenerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		return factory;
	}
}