package com.webshop.orders.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MessageSupport {

    @Autowired
    private Validator validator;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Converts JMS message into specified type represented by clazz.
     * Also validates specified message after conversion.
     *
     * @param message JMS message.
     * @param clazz   class that represents data contained in JMS message.
     * @param <T>     type of data
     * @return valid message.
     * @throws RuntimeException if conversion fails or there is a validation errors.
     */
    public <T> T getMessage(Message message, Class<T> clazz) throws RuntimeException {
        try {
            String json = ((TextMessage) message).getText();
            log.info("Message form amq: {}", json);
            T data = mapper.readValue(json, clazz);

            Set<ConstraintViolation<T>> violations = validator.validate(data);
            if (violations.isEmpty()) {
                return data;
            }

            printViolationsAndThrowException(violations);
            return null;
        } catch (JMSException | IOException exception) {
            throw new RuntimeException("Failed to get data from JMS message or conversion failed.", exception);
        }
    }

    private <T> void printViolationsAndThrowException(Set<ConstraintViolation<T>> violations) {
        String concatedViolations = violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(", "));
        throw new RuntimeException(concatedViolations);
    }
}
