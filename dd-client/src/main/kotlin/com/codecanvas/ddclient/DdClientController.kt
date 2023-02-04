package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
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
        span.setTag("client", headers.getFirst("x-client").orElse("defaultClient"))
        span.setTag("account", headers.getFirst("x-account").orElse("defaultAccount"))
//        val span = Span.current()
//        span.setAttribute("client", headers.getFirst("x-client").orElse("defaultClient"))
//        span.setAttribute("account", headers.getFirst("x-account").orElse("defaultAccount"))
        return ddStringGenClient.randomString()
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailure(headers: HttpHeaders): String {
        logger.info("Generating failure ...")
        val span = GlobalTracer.get().activeSpan()
        val traceId = span.context().toTraceId()
        span.setTag("client", headers.getFirst("x-client").orElse("defaultClient"))
        span.setTag("account", headers.getFirst("x-account").orElse("defaultAccount"))
//        val span = Span.current()
//        span.setAttribute("client", headers.getFirst("x-client").orElse("defaultClient"))
//        span.setAttribute("account", headers.getFirst("x-account").orElse("defaultAccount"))

        return Try { ddStringGenClient.randomStringFailure() }
            .onFailure { e -> logger.error(e) { traceId } }
            .getOrElseThrow { -> RuntimeException(traceId) }

    }
}