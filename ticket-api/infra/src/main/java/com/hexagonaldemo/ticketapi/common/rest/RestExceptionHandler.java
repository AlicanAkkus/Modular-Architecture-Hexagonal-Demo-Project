package com.hexagonaldemo.ticketapi.common.rest;

import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends BaseController {

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorResponse> handleException(Exception exception, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return createErrorResponseFromMessageSource("common.system.error.occurred", locale);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleRequestPropertyBindingError(WebExchangeBindException webExchangeBindException, Locale locale) {
        log.debug("Bad request!", webExchangeBindException);
        return createFieldErrorResponse(webExchangeBindException.getBindingResult(), locale);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleBindException(BindException bindException, Locale locale) {
        log.debug("Bad request!", bindException);
        return createFieldErrorResponse(bindException.getBindingResult(), locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleInvalidArgumentException(MethodArgumentNotValidException methodArgumentNotValidException, Locale locale) {
        log.debug("Method argument not valid. Message: $methodArgumentNotValidException.message", methodArgumentNotValidException);
        return createFieldErrorResponse(methodArgumentNotValidException.getBindingResult(), locale);
    }

    @ExceptionHandler(TicketApiBusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse> handleTicketApiBusinessException(TicketApiBusinessException ex, Locale locale) {
        return createErrorResponseFromMessageSource(ex.getKey(), locale, ex.getArgs());
    }

    @ExceptionHandler(TicketApiDataNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse> handleTicketApiDataNotFoundException(TicketApiDataNotFoundException ex, Locale locale) {
        return createErrorResponseFromMessageSource(ex.getKey(), locale, ex.getArgs());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, Locale locale) {
        log.trace("MethodArgumentTypeMismatchException occurred", methodArgumentTypeMismatchException);
        return createErrorResponseFromMessageSource("common.client.typeMismatch", locale, methodArgumentTypeMismatchException.getName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException methodNotSupportedException, Locale locale) {
        log.debug("HttpRequestMethodNotSupportedException occurred", methodNotSupportedException);
        return createErrorResponseFromMessageSource("common.client.methodNotSupported", locale, methodNotSupportedException.getMethod());
    }

    private Response<ErrorResponse> createFieldErrorResponse(BindingResult bindingResult, Locale locale) {
        List<String> requiredFieldErrorMessages = retrieveLocalizationMessage("common.client.requiredField", locale);
        String code = requiredFieldErrorMessages.get(0);

        String errorMessage = bindingResult
                .getFieldErrors().stream()
                .map(FieldError::getField)
                .map(error -> retrieveLocalizationMessage("common.client.requiredField", locale, error))
                .map(errorMessageList -> errorMessageList.get(1))
                .collect(Collectors.joining(" && "));

        log.debug("Exception occurred while request validation: {}", errorMessage);

        return respond(new ErrorResponse(code, errorMessage));
    }

    private Response<ErrorResponse> createErrorResponseFromMessageSource(String key, Locale locale, String... args) {
        List<String> messageList = retrieveLocalizationMessage(key, locale, args);
        return respond(new ErrorResponse(messageList.get(0), messageList.get(1)));
    }

    private List<String> retrieveLocalizationMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return Pattern.compile(";").splitAsStream(message).collect(Collectors.toList());
    }
}
