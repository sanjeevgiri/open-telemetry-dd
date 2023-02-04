package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.opentelemetry.api.trace.Span
import io.opentracing.util.GlobalTracer
import io.vavr.kotlin.Try
import mu.KotlinLogging

@Controller("/ddclient")
class DdClientController(private val ddStringGenClient: DdStringGenClient) {
    private val logger = KotlinLogging.logger {}

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomString(headers: HttpHeaders): String {
        logger.info("Generating random string ...")
        val span = GlobalTracer.get().activeSpan()
        span.setTag("dd.clientId", headers.getFirst("x-client").orElse("defaultClient"))
        span.setTag("dd.clientAccount", headers.getFirst("x-account").orElse("defaultAccount"))
        return ddStringGenClient.randomString()
    }

//    @Get("/randomstringotelsdk")
//    @Produces(MediaType.TEXT_PLAIN)
//    fun getRandomStringOtelSdk(headers: HttpHeaders): String {
//        logger.info("Generating random string (otel sdk) ...")
//        val span = Span.current()
//        span.setAttribute("otelsdk.clientId", headers.getFirst("x-client").orElse("defaultClient"))
//        span.setAttribute("otelsdk.clientAccount", headers.getFirst("x-account").orElse("defaultAccount"))
//        return ddStringGenClient.randomString()
//    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailure(headers: HttpHeaders): String {
        logger.info("Generating failure ...")
        val span = GlobalTracer.get().activeSpan()
        val traceId = span.context().toTraceId()
        span.setTag("dd.clientId", headers.getFirst("x-client").orElse("defaultClient"))
        span.setTag("dd.clientAccount", headers.getFirst("x-account").orElse("defaultAccount"))
        return Try { ddStringGenClient.randomStringFailure() }
            .onFailure { e -> logger.error(e) { traceId } }
            .getOrElseThrow { -> RuntimeException(traceId) }

    }

//    @Get("/randomstringfailureotelsdk")
//    @Produces(MediaType.TEXT_PLAIN)
//    fun getRandomStringFailureOtelSdk(headers: HttpHeaders): String {
//        logger.info("Generating failure (otel sdk)...")
//        val span = Span.current()
//        val traceId = span.spanContext.traceId
//        span.setAttribute("otelsdk.clientId", headers.getFirst("x-client").orElse("defaultClient"))
//        span.setAttribute("otelsdk.accountId", headers.getFirst("x-account").orElse("defaultAccount"))
//        return Try { ddStringGenClient.randomStringFailure() }
//            .onFailure { e -> logger.error(e) { traceId } }
//            .getOrElseThrow { -> RuntimeException(traceId) }
//
//    }
}