package com.defers.crm.customers.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CurrencyKafkaSender<K, V> implements MessageSender<K, V>{
    @Autowired
    KafkaTemplate<K, V> kafkaProducer;
    @Override
    public void send(V producedObject) {
        Message<V> message = MessageBuilder
                .withPayload(producedObject)
                .setHeader(KafkaHeaders.TOPIC, "currency")
                .build();
        kafkaProducer.send(message);
//        ProducerRecord<K, V> record = new ProducerRecord("currency", UUID.randomUUID().toString(), producedObject);
//        kafkaProducer.send(record);
    }
}
