package com.codecanvas.otlpserver

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging

@Controller("/otelserver")
class DdServerController {
    private val logger = KotlinLogging.logger {}


    @Get("/randomuuid")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuid(): String {
        logger.info("Generating random string on server (otlp) ...")
        return java.util.UUID.randomUUID().toString()
    }

    @Get("/randomuuidfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuidFailure(): String {
        logger.info("Simulating failure event (otlp)")
        throw java.lang.RuntimeException("Simulated error")
    }
}