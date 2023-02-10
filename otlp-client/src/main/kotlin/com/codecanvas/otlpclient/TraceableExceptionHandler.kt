package com.codecanvas.otlpclient

import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import jakarta.inject.Singleton

@Produces
@Singleton
@Requirements(
    Requires(classes = [TraceableException::class, ExceptionHandler::class])
)
class TraceableExceptionHandler(private val errorResponseProcessor: ErrorResponseProcessor<Any>) :
    ExceptionHandler<TraceableException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>, exception: TraceableException): HttpResponse<*> {
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage("${exception.message} " +
                        "b3 trace: ${request.headers.get("X-B3-traceid")} " +
                        "amzn trace: ${request.headers.get("x-amzn-trace-id") } " +
                        "dd trace: ${request.headers.get("x-datadog-trace-id") } ")
                .build(), HttpResponse.badRequest<Any>()
        )
    }

}