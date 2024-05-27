package com.viewnext.bsan.practica04.util;

import com.viewnext.bsan.practica04.util.constants.Messages;

import java.text.MessageFormat;

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
        return MessageFormat.format(Messages.RESOURCE_NOT_FOUND, resourceClassName, id);
    }

    public static String buildResourceAlreadyExistsMessage(String resourceClassName, Object id) {
        return MessageFormat.format(Messages.RESOURCE_ALREADY_EXISTS, resourceClassName, id);
    }

    public static String buildMissingRequiredFieldMessage(String requiredFieldName) {
        return MessageFormat.format(Messages.REQUIRED_FIELD, requiredFieldName);
    }

    public static String buildNullNotAllowedMessage(String offendingParameterName) {
        return MessageFormat.format(Messages.NULL_NOT_ALLOWED, offendingParameterName);
    }

    public static String buildNegativeNumberNotAllowedMessage(String offendingParameterName) {
        return MessageFormat.format(Messages.NEGATIVE_NUMBER_NOT_ALLOWED, offendingParameterName);
    }

    public static String buildParameterTypeMismatchMessage(String parameterName, String expectedTypeName) {
        return MessageFormat.format(Messages.PARAMETER_TYPE_MISMATCH, parameterName, expectedTypeName);
    }

}
