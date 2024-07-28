package org.example.video;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class S3EventHandler implements RequestHandler<S3Event, Boolean> {
    private static final AmazonS3 s3Client = AmazonS3Client.builder()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();

    @Override
    public Boolean handleRequest(S3Event input, Context context) {
        final LambdaLogger lambdaLogger = context.getLogger();

        if (input.getRecords().isEmpty()) {
            lambdaLogger.log("No records found");
            return false;
        }
        for (S3EventNotification.S3EventNotificationRecord record : input.getRecords()) {
            String bucketName = record.getS3().getBucket().getName();
            String objectKey = record.getS3().getObject().getKey();
            S3Object s3Object = s3Client.getObject(bucketName, objectKey);
            S3ObjectInputStream inpustream = s3Object.getObjectContent();
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(
                    inpustream, StandardCharsets.UTF_8
            ))) {
                br.lines().skip(1)
                        .forEach(line -> lambdaLogger.log(line + "\n"));
            } catch (IOException e) {
                lambdaLogger.log("Error occured in lambda: " + e.getMessage());
                return false;
            }
        }
        return null;
    }
}
