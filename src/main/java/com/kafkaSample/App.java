package com.kafkaSample;

import java.util.Properties;
import org.apache.curator.test.TestingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kafkaSample.Model.Employee;
import com.kafkaSample.Sender.Sender;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;


@RestController
@SpringBootApplication
public class App
{
    
	@Value("${kafka.topic.json}")
	private String jsonTopic;

	@Autowired private Sender sender;
	

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
        startServer();
    }
    
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    
    @RequestMapping("/sender")
    String sender() {
    	
		Employee employee = new Employee();
		employee.setId(1);
		employee.setName("Test");
		
    		sender.send(jsonTopic, employee);
    		return "Message Sent !!";
    }
    
    private static void startServer() throws Exception { 
    		TestingServer zkServer = new TestingServer();
        Properties props = new Properties(); 
        props.setProperty("hostname", "localhost"); 
        props.setProperty("port", "9092"); 
        props.setProperty("brokerid", "2"); 
        props.setProperty("log.flush.interval", "0"); 
        props.setProperty("log.default.flush.scheduler.interval.ms", "1"); 
        props.put("zookeeper.connect", zkServer.getConnectString());
        props.put("num.partitions", 2);

        KafkaServerStartable server = new KafkaServerStartable(new KafkaConfig(props));
        server.startup();
    } 

}