package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.opentelemetry.api.trace.Span
import mu.KotlinLogging

@Controller("/ddclient/randomstring")
class DdClientController(private val ddStringGenClient: DdStringGenClient) {
    private val logger = KotlinLogging.logger {}

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomString(headers: HttpHeaders): String {
        logger.info("Generating random string ...")
        val span = Span.current()
        span.setAttribute("client", headers.getFirst("x-client").orElse("defaultClient"))
        span.setAttribute("account", headers.getFirst("x-account").orElse("defaultAccount"))
        return ddStringGenClient.randomString()
    }
}