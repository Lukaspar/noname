package com.lukaspar.ep.authentication.controller;

import com.lukaspar.ep.authentication.exception.*;
import com.lukaspar.ep.authentication.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiError> handleIllegalStateException(Exception ex, WebRequest request){
        return handle(HttpStatus.PRECONDITION_FAILED, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiError> handleMethodArgumentNotValidException(Exception ex, WebRequest request){
        return handle(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, FriendRequestAlreadyExistsException.class})
    protected ResponseEntity<ApiError> handleAlreadyExistsException(Exception ex, WebRequest request){
        return handle(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({RoleNotFoundException.class, UserNotFoundException.class, FriendRequestNotFoundException.class})
    protected ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request){
        return handle(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({FriendRequestWrongStatusException.class, FriendRequestRejectedException.class, MessageReceiverMustBeSenderFriendException.class,
            MessageReceiverIdenticalLikeSenderException.class})
    protected ResponseEntity<ApiError> handleUnProcessableEntity(Exception ex, WebRequest request){
        return handle(HttpStatus.UNPROCESSABLE_ENTITY, ex);
    }

    private ResponseEntity<ApiError> handle(HttpStatus status, Exception ex) {
        ApiError responseError = createErrorResponse(status, ex);
        log.error("Error during request {} {}, cause: {}", responseError.getHttpMethod(), responseError.getEndpoint(), responseError.getMessage());
        return ResponseEntity.status(status).body(responseError);
    }

    private ApiError createErrorResponse(HttpStatus status, Exception ex) {
        ApiError response = new ApiError();
        response.setMessage(ex.getMessage());
        response.setStatus(status);
        response.setEndpoint(request.getRequestURI());
        response.setHttpMethod(request.getMethod());
        return response;
    }
}
