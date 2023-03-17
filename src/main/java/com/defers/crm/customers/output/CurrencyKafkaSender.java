package com.defers.crm.customers.output;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CurrencyKafkaSender implements MessageSender{
    @Autowired
    KafkaTemplate<String, Object> kafkaProducer;
    @Override
    public void send() {
        ProducerRecord<String, Object> record = new ProducerRecord("currency", "key1", "keyValue1");
        kafkaProducer.send(record);
    }
}
