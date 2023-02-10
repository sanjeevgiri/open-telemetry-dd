package com.codecanvas.otlpserver

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging

@Controller("/otlpserver")
class OtlpServerController {
    private val logger = KotlinLogging.logger {}

    @Get("/randomuuid")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuid(headers: HttpHeaders): String {
        logger.info("Generating random string on server (otlp) ...")
        return java.util.UUID.randomUUID().toString()
    }

    @Get("/randomuuidfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuidFailure(headers: HttpHeaders): String {
        logger.info("Simulating failure event (otlp)")
        throw TraceableException("Simulated error (otlp) ...")
    }
}