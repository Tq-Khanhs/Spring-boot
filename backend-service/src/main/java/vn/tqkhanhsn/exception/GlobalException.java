package vn.tqkhanhsn.exception;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintDefinitionException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler({ConstraintDefinitionException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request",
                content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Handle exception when the data is invalid. (@RequestBody, @RequestParam, @PathVariable, etc.)",
                            summary = "Bad Request",
                            value = """
                                    {
                                        "timestamp": "2023-10-01T12:00:00Z",
                                        "status": 400,
                                        "path": "/api/resource",
                                        "error": "Bad Request",
                                        "message": "(data) must be not blank"
                                    }
                                    """
                    ))})
    })
    public ErrorResponse handleValidationException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        String message = ex.getMessage();
        if(ex instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") +1;
            int end = message.lastIndexOf("]") -1;
            message = message.substring(start, end);
            errorResponse.setMessage(message);
            errorResponse.setError("Validation Error");

        }else if(ex instanceof MissingServletRequestParameterException) {
            errorResponse.setError("Invalid parameter");
            errorResponse.setMessage(message);
        }else if(ex instanceof ConstraintDefinitionException) {
            errorResponse.setError("Constraint Definition Error");
            errorResponse.setMessage(message);
        } else {
            errorResponse.setError("Bad Request");
            errorResponse.setMessage("Invalid request data");
        }
        return errorResponse;

    }




    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ApiResponses(value ={
            @ApiResponse(responseCode = "404",description = "Bad Request",
                content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "404 Not Found",
                            summary = "Resource not found",
                            value = """
                                    {
                                        "timestamp": "2023-10-01T12:00:00Z",
                                        "status": 404,
                                        "path": "/api/resource",
                                        "error": "Not Found",
                                        "message": "The requested resource was not found"
                                    }
                                    """
                    ))
                })
    })
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(NOT_FOUND.value());
        errorResponse.setError(NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ApiResponses(value ={
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                        name = "500 Internal Server Error",
                        summary = "Unexpected error",
                        value = """
                                {
                                    "timestamp": "2023-10-01T12:00:00Z",
                                    "status": 500,
                                    "path": "/api/resource",
                                    "error": "Internal Server Error",
                                    "message": "An unexpected error occurred"
                                }
                                """
                ))})
    })
    public ErrorResponse handleGenericException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");
        return errorResponse;
    }


    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(CONFLICT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "conflict",
                content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                 examples = @ExampleObject(
                         name= "409 Conflict",
                            summary = "Data conflict",
                            value = """
                                    {
                                        "timestamp": "2023-10-01T12:00:00Z",
                                        "status": 409,
                                        "path": "/api/resource",
                                        "error": "Conflict",
                                        "message": "The data you are trying to save already exists"
                                    }
                                    """
                 ))})
    })
    public ErrorResponse handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(CONFLICT.value());
        errorResponse.setError(CONFLICT.getReasonPhrase());
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }




    @Getter
    @Setter
    private class ErrorResponse {
        private Date timestamp;
        private int status;
        private String path;
        private String error;
        private String message;

    }
}
