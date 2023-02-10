package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request

@Controller("/ddclient")
class DdClientController {
    private val logger = KotlinLogging.logger {}
    private val client = OkHttpClient()

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomString(headers: HttpHeaders): String {
        logger.info("Generating random string ...")

        val reqBuilder = Request.Builder().url("http://localhost:8000/ddserver/randomuuid")
        headers.filter { header -> header.key.startsWith("X-API-") }
            .forEach { header -> reqBuilder.addHeader(header.key, header.value.first()) }

        client.newCall(reqBuilder.build()).execute().use { response ->
            return response.body!!.string()
        }
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailure(headers: HttpHeaders): String {
        logger.info("Generating random error ...")

        val reqBuilder = Request.Builder().url("http://localhost:8000/ddserver/randomuuidfailure")
        headers.filter { header -> header.key.startsWith("X-API-") }
            .forEach { header -> reqBuilder.addHeader(header.key, header.value.first()) }

        client.newCall(reqBuilder.build()).execute().use { response ->
            return response.body!!.string()
        }
    }
}