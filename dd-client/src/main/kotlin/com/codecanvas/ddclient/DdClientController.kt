package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.vavr.kotlin.Try
import mu.KotlinLogging

@Controller("/ddclient")
class DdClientController(private val ddStringGenClient: DdStringGenClient) {
    private val logger = KotlinLogging.logger {}

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomString(headers: HttpHeaders): String {
        logger.info("Generating random string ...")
        return ddStringGenClient.randomString()
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailure(headers: HttpHeaders): String {
        logger.info("Generating failure ...")
        return Try { ddStringGenClient.randomStringFailure() }
            .onFailure { e -> logger.error("encountered error", e) }
            .getOrElseThrow { e -> RuntimeException(e) }

    }
}