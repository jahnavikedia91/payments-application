package com.example.payments.advice

import com.example.payments.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer


@RestControllerAdvice
class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidArgument(ex: MethodArgumentNotValidException): Map<String, String?> {
        val errorMap: MutableMap<String, String?> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { error: FieldError ->
            errorMap[error.field] = error.defaultMessage
        })
        return errorMap
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleBusinessException(ex: UserNotFoundException): Map<String, String> {
        val errorMap: MutableMap<String, String> = HashMap()
        errorMap["errorMessage"] = ex.localizedMessage
        return errorMap
    }
}