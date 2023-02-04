package com.codecanvas.otlpclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.opentelemetry.api.trace.Span
import io.vavr.kotlin.Try
import mu.KotlinLogging

@Controller("/otlpclient")
class OtlpClientController(private val ddStringGenClient: OtlpStringGenClient) {
    private val logger = KotlinLogging.logger {}

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringOtelSdk(headers: HttpHeaders): String {
        logger.info("Generating random string (otel sdk) ...")
        val span = Span.current()
        span.setAttribute("otelsdk.clientId", headers.getFirst("x-client").orElse("defaultClient"))
        span.setAttribute("otelsdk.clientAccount", headers.getFirst("x-account").orElse("defaultAccount"))
        return ddStringGenClient.randomString()
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailureOtelSdk(headers: HttpHeaders): String {
        logger.info("Generating failure (otel sdk)...")
        val span = Span.current()
        val traceId = span.spanContext.traceId
        span.setAttribute("otelsdk.clientId", headers.getFirst("x-client").orElse("defaultClient"))
        span.setAttribute("otelsdk.accountId", headers.getFirst("x-account").orElse("defaultAccount"))
        return Try { ddStringGenClient.randomStringFailure() }
            .onFailure { e -> logger.error(e) { traceId } }
            .getOrElseThrow { -> RuntimeException(traceId) }
    }
}