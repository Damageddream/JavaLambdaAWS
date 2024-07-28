package org.example.errorHandling;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import static org.example.errorHandling.Constants.CLIENT_ERROR;

public class LambdaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Api full event: " + requestEvent.toString());

        String body = requestEvent.getBody();
        try {
            final User user = objectMapper.readValue(body, User.class);
            if (StringUtils.isNullOrEmpty(user.getUsername()) || user.getId() == null) {
                return returnApiResponse(HttpStatus.SC_BAD_REQUEST, "Request Body not valid",
                        CLIENT_ERROR, "Usernam or id should not be null", logger);
            }
        } catch (JsonProcessingException e) {
            logger.log(e.getMessage());
            return null;
        }


        return null;
    }

    public APIGatewayProxyResponseEvent returnApiResponse(int statusCode, String resposneBody,
                                                          String errorMessage, String errorCode,
                                                          LambdaLogger logger) {
        final Error error = new Error();
        if (StringUtils.isNullOrEmpty(errorCode)) {
            error.setErrorCode(errorCode);
            error.setErrorMessage(errorMessage);
        }
        try {
            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent()
                    .withStatusCode(statusCode)
                    .withBody(objectMapper.writeValueAsString(new Response<String>(statusCode, resposneBody, error)));
            logger.log("\n"+responseEvent.toString());
            return responseEvent;
        } catch (JsonProcessingException e) {
            logger.log(e.getMessage());
            return null;
        }

    }
}
