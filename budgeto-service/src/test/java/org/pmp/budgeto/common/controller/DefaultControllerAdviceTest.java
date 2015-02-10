package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.domain.DomainConflictException;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.common.domain.DomainValidationError;
import org.pmp.budgeto.common.domain.DomainValidationException;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.domain.account.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RunWith(MockitoJUnitRunner.class)
public class DefaultControllerAdviceTest {

    @Mock
    private TranslatorTools translatorTools;

    @InjectMocks
    private DefaultControllerAdvice defaultControllerAdvice;

    @Test
    public void springConf() throws Exception {
        Assertions.assertThat(DefaultControllerAdvice.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(DefaultControllerAdvice.class.isAnnotationPresent(ControllerAdvice.class)).isTrue();
        Assertions.assertThat(DefaultControllerAdvice.class.isAnnotationPresent(RequestMapping.class)).isTrue();
        Assertions.assertThat(DefaultControllerAdvice.class.getAnnotation(RequestMapping.class).consumes()).containsOnly(MediaType.APPLICATION_JSON_VALUE);
        Assertions.assertThat(DefaultControllerAdvice.class.getAnnotation(RequestMapping.class).produces()).containsOnly(MediaType.APPLICATION_JSON_VALUE);

        Assertions.assertThat(AccountController.class.getConstructors()).hasSize(1);
        Assertions.assertThat(AccountController.class.getConstructors()[0].isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotations()).hasSize(3);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotation(ExceptionHandler.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotation(ExceptionHandler.class).value()).containsOnly(Exception.class);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotation(ResponseBody.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{Exception.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotations()).hasSize(3);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotation(ExceptionHandler.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotation(ExceptionHandler.class).value()).containsOnly(DomainException.class);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotation(ResponseBody.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainException.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotations()).hasSize(3);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotation(ExceptionHandler.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotation(ExceptionHandler.class).value()).containsOnly(DomainValidationException.class);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotation(ResponseBody.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainValidationException.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotations()).hasSize(3);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotation(ExceptionHandler.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotation(ExceptionHandler.class).value()).containsOnly(DomainNotFoundException.class);
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotation(ResponseBody.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotation(ResponseStatus.class)).isNotNull();
        Assertions.assertThat(DefaultControllerAdvice.class.getDeclaredMethod("handleException", new Class[]{DomainNotFoundException.class}).getAnnotation(ResponseStatus.class).value()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void handleException() throws Exception {

        Exception e = new NullPointerException("exception message");
        Mockito.when(translatorTools.get("advice.error.unknown")).thenReturn("simple message for exception");
        ControllerError error = defaultControllerAdvice.handleException(e);

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getMessage()).isEqualTo("simple message for exception");
        Assertions.assertThat(error.getType()).isEqualTo("unknown");
        Assertions.assertThat(error.getException()).isEqualTo("exception message");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(NullPointerException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

    @Test
    public void handleDomainException() throws Exception {

        DomainException e = new DomainException("exception message");
        Mockito.when(translatorTools.get("advice.error.domain")).thenReturn("simple message for exception");

        ControllerError error = defaultControllerAdvice.handleException(e);

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getMessage()).isEqualTo("simple message for exception");
        Assertions.assertThat(error.getType()).isEqualTo("server");
        Assertions.assertThat(error.getException()).isEqualTo("exception message");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

    @Test
    public void handleDomainValidationException() throws Exception {

        DomainValidationException e = new DomainValidationException("exception message", new DomainValidationError("theField", "the msg error"));
        Mockito.when(translatorTools.get("advice.error.validation")).thenReturn("simple message for exception");

        ControllerError error = defaultControllerAdvice.handleException(e);

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getMessage()).isEqualTo("simple message for exception");
        Assertions.assertThat(error.getType()).isEqualTo("validation");
        Assertions.assertThat(error.getException()).isEqualTo("exception message");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainValidationException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(1);
    }

    @Test
    public void handleDomainConflictException() throws Exception {

        DomainConflictException e = new DomainConflictException("exception message", new DomainValidationError("theField", "the msg error"));
        Mockito.when(translatorTools.get("advice.error.conflict")).thenReturn("simple message for exception");

        ControllerError error = defaultControllerAdvice.handleException(e);

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getMessage()).isEqualTo("simple message for exception");
        Assertions.assertThat(error.getType()).isEqualTo("conflict");
        Assertions.assertThat(error.getException()).isEqualTo("exception message");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainConflictException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(1);
    }

    @Test
    public void handleDomainNotFoundException() throws Exception {

        DomainNotFoundException e = new DomainNotFoundException("object simple not found");
        Mockito.when(translatorTools.get("advice.error.notfound")).thenReturn("object not found");

        ControllerError error = defaultControllerAdvice.handleException(e);

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getMessage()).isEqualTo("object not found");
        Assertions.assertThat(error.getType()).isEqualTo("notfound");
        Assertions.assertThat(error.getException()).isEqualTo("object simple not found");
        Assertions.assertThat(error.getExceptionType()).isEqualTo(DomainNotFoundException.class.getSimpleName());
        Assertions.assertThat(error.getValidationErros()).hasSize(0);
    }

}
