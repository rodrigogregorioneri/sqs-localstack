package sqs.teste.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Service
public class AmazonSqsClient {

    private AmazonSQS client;

    Regions region = Regions.SA_EAST_1;

    @Value("${app.aws.sm.endpoint}")
    String env = System.getProperty("app.aws.sm.endpoint");

    private static final Logger LOG
            = Logger.getLogger(String.valueOf(AmazonSqsClient.class));

    public AmazonSqsClient() {
    }


    @PostConstruct
    private void initializeAmazonSqsClient() {
    LOG.info(env);
        if(env != null){
            if(env.contains("local")){
                AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                        Regions.SA_EAST_1.getName());
                this.client = AmazonSQSClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
            }else{
                this.client =
                        AmazonSQSClientBuilder.standard()
                                .withRegion(Region.getRegion(region).getName())
                                .build();
            }
        }
    }


    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(String endpoint, Regions awsRegion) {
        return new AwsClientBuilder.EndpointConfiguration(endpoint, awsRegion.getName());
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("fake", "fake");
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    public AmazonSQS getClient() {
        return client;
    }
}