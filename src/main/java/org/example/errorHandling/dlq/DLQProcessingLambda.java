package org.example.errorHandling.dlq;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class DLQProcessingLambda {
    public void handle(SNSEvent snsEvent, Context context){
        final LambdaLogger logger = context.getLogger();
        snsEvent.getRecords().forEach(
                record -> {logger.log("\nDLQ Event: "+record.toString());}
        );
    }
}
