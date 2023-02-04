package com.codecanvas.ddserver

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging

@Controller("/ddserver")
class DdServerController {
    private val logger = KotlinLogging.logger {}


    @Get("/randomuuid")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuid(): String {
        logger.info("Generating random string on server ...")
        return java.util.UUID.randomUUID().toString()
    }

    @Get("/randomuuidfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuidFailure(): String {
        logger.info("Simulating failure event")
        throw java.lang.RuntimeException("Simulated error")
    }
}