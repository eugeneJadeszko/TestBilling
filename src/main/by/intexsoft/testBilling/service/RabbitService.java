package main.by.intexsoft.testBilling.service;

import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;

@Service
@PropertySource(value = "classpath:application.properties")
public class RabbitService {
	@Value("${rabbitmq.messagesQueue}")
	private String messageQueueName;

	@Autowired
	private RabbitAdmin admin;

	public int getMessageCount() {
		DeclareOk declareOk = admin.getRabbitTemplate().execute(new ChannelCallback<DeclareOk>() {
			@Override
			public DeclareOk doInRabbit(Channel channel) throws Exception {
				return channel.queueDeclarePassive(messageQueueName);
			}
		});
		return declareOk.getMessageCount();
	}
}
