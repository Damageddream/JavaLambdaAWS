package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelloLambdaTest {

    @Test
    void shouldReturnHelloMessage() {
        var sut = new HelloLambda();
        Assertions.assertEquals("Hello Marc", sut.handleRequest("Marc"));
    }
}