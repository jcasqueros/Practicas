package com.viewnext.bsan.practica04.util;

import java.text.MessageFormat;

import static com.viewnext.bsan.practica04.util.constants.Messages.*;

/**
 * Utility class that encapsulates the implementation details for message formatting and building logic. It provides a
 * simple mechanism to create error messages for most cases contemplated for the project.
 *
 * @author Antonio Gil
 */
public class MessageBuilder {

    private MessageBuilder() {
    }

    public static String buildResourceNotFoundMessage(String resourceClassName, Object id) {
        return MessageFormat.format(RESOURCE_NOT_FOUND, resourceClassName, id);
    }

    public static String buildResourceAlreadyExistsMessage(String resourceClassName, Object id) {
        return MessageFormat.format(RESOURCE_ALREADY_EXISTS, resourceClassName, id);
    }

    public static String buildMissingRequiredFieldMessage(String requiredFieldName) {
        return MessageFormat.format(REQUIRED_FIELD, requiredFieldName);
    }

    public static String buildNullNotAllowedMessage(String offendingParameterName) {
        return MessageFormat.format(NULL_NOT_ALLOWED, offendingParameterName);
    }

    public static String negativeNumberNotAllowedMessage(String offendingParameterName) {
        return MessageFormat.format(NEGATIVE_NUMBER_NOT_ALLOWED, offendingParameterName);
    }

    public static String parameterTypeMismatchMessage(String parameterName, String expectedTypeName) {
        return MessageFormat.format(PARAMETER_TYPE_MISMATCH, parameterName, expectedTypeName);
    }

}