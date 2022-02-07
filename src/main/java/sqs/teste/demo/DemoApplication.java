package sqs.teste.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.net.URI;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		SqsClient client = SqsClient.builder()
				.region(Region.SA_EAST_1)
				.endpointOverride(URI.create("http://localhost:4566"))
				.build();

		var queueUrl = "http://localhost:4566/000000000000/terraform-example-queue.fifo";

		ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.waitTimeSeconds(5)
				.maxNumberOfMessages(5)
				.build();

		while (true) {
			client.receiveMessage(receiveMessageRequest)
					.messages()
					.forEach( message -> {
						System.out.println(message.body());
					});
		}


	}

}
