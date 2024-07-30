package org.example.errorHandling.destination;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.LambdaDestinationEvent;

public class DestinationLmbda {
    public void handle(LambdaDestinationEvent event, Context context){
        final LambdaLogger logger = context.getLogger();
        logger.log("Lambda Destination event received");
        logger.log("\nDestination event detials:" + event.getRequestPayload().toString());
    }
}
