package com.codecanvas.ddserver

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.opentelemetry.api.trace.Span
import mu.KotlinLogging
import java.util.*

@Controller("/ddserver")
class DdServerController {
    private val logger = KotlinLogging.logger {}


    @Get("/randomuuid")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuid(): String {
        logger.info("Generating random string on server ...")
        Span.current().addEvent("random string from server")
        return UUID.randomUUID().toString()
    }

    @Get("/randomuuidfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuidFailure(): String {
        logger.info("Simulating failure event")
        Span.current().addEvent("random error from server")
        throw java.lang.RuntimeException("Simulated error")
    }
}