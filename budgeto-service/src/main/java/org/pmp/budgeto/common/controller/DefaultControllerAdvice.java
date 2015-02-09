package org.pmp.budgeto.common.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.common.domain.DomainValidationException;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * manage exception on app
 * all Exception are unknown and sent 500 response status
 * all DomainException are domain and sent 500 response status
 * all DomainValidationException are domain and sent 400 response status
 * all DomainConflictException are domain and sent 409 response status
 * all DomainNotFoundException are domain and sent 404 response status
 */
@ControllerAdvice
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DefaultControllerAdvice {

    public static final String OK_MSG = "Success";
    public static final int OK_CODE = 200;
    public static final String CREATED_MSG = "Creation success";
    public static final int CREATED_CODE = 201;
    public static final String INTER_ERR_MSG = "Internal server error";
    public static final int INTER_ERR_CODE = 500;
    public static final String BAD_REQUEST_MSG = "Bad request parameter";
    public static final int BAD_REQUEST_CODE = 400;
    public static final String CONFLICT_MSG = "Bad request conflit sur les données";
    public static final int CONFLICT_CODE = 409;
    public static final String NOT_FOUND_MSG = "Request not found";
    public static final int NOT_FOUND_CODE = 404;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultControllerAdvice.class);
    private TranslatorTools translatorTools;

    @Autowired
    public DefaultControllerAdvice(TranslatorTools translatorTools) {
        this.translatorTools = Validate.notNull(translatorTools);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ControllerError handleException(Exception e) {
        String msg = translatorTools.get("advice.error.unknown");
        LOGGER.error(msg, e);
        return new ControllerError("unknown", msg, e);
    }

    @ExceptionHandler(DomainException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ControllerError handleException(DomainException e) {
        String msg = translatorTools.get("advice.error.domain");
        LOGGER.error(msg, e);
        return new ControllerError("server", msg, e);
    }

    @ExceptionHandler(DomainValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ControllerError handleException(DomainValidationException e) {
        String msg = translatorTools.get("advice.error.validation");
        LOGGER.warn(msg, e);
        return new ControllerError("validation", msg, e, e.getConstraintViolations());
    }

    @ExceptionHandler(DomainConflictException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ControllerError handleException(DomainConflictException e) {
        String msg = translatorTools.get("advice.error.conflict");
        LOGGER.warn(msg, e);
        return new ControllerError("conflict", msg, e, ArrayUtils.toArray(e.getConstraintViolations()));
    }

    @ExceptionHandler(DomainNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ControllerError handleException(DomainNotFoundException e) {
        String msg = translatorTools.get("advice.error.notfound");
        LOGGER.warn(msg, e);
        return new ControllerError("notfound", msg, e);
    }

}
