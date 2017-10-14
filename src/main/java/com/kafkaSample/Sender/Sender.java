package com.kafkaSample.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.kafkaSample.Model.Employee;


public class Sender {

	  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	  @Autowired
	  private KafkaTemplate<String, Object> kafkaTemplate;

	  public void send(String topic, Employee employee) {
	    LOGGER.info("sending payload='{}' to topic='{}'", employee.toString(), topic);
	    kafkaTemplate.send(topic, employee);
	  }
}