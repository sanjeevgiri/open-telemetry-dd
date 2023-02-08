package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.HttpClient
import io.micronaut.http.uri.UriBuilder
import mu.KotlinLogging
import org.reactivestreams.Publisher

@Controller("/ddclient")
class DdClientController(private val client: HttpClient) {
    private val logger = KotlinLogging.logger {}

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomString(headers: HttpHeaders): Publisher<String> {
        logger.info("Generating random string ...")

        val req = HttpRequest.GET<String>(UriBuilder.of("http://localhost:8000/ddserver/randomuuid").build())
        headers.filter { header -> header.key.startsWith("x-api-") }
            .forEach { header -> req.header(header.key, header.value.first()) }

        return client.retrieve(req, String::class.java)
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailure(headers: HttpHeaders): Publisher<String> {
        logger.info("Generating failure ...")

        val req = HttpRequest.GET<String>(UriBuilder.of("http://localhost:8000/ddserver/randomuuidfailure").build())
        headers.filter { header -> header.key.startsWith("x-api-") }
            .forEach { header -> req.header(header.key, header.value.first()) }

        return client.retrieve(req, String::class.java)
    }
}