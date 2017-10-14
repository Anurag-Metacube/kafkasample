package com.kafkaSample.Receiver;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;


public class Receiver {
	
  private String name;	

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public Receiver(String name) {
	  this.name = name;
  }
 
  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "${kafka.topic.json}")
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    LOGGER.info(name + " received payload='{}'", consumerRecord.value().toString());    
    latch.countDown();
  }
}