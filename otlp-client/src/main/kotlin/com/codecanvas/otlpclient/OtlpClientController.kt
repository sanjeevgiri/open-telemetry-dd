package com.codecanvas.otlpclient

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.opentelemetry.api.trace.Span
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request

@Controller("/otlpclient")
class OtlpClientController {
    private val logger = KotlinLogging.logger {}
    private val client = OkHttpClient()

    @Get("/randomstring")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringOtelSdk(headers: HttpHeaders): String {
        logger.info("Generating random string (otel sdk) ...")

        val reqBuilder = Request.Builder().url("http://localhost:9000/otlpserver/randomuuid")
//        headers.filter { header -> header.key.startsWith("X-API-") }
//            .forEach { header -> reqBuilder.addHeader(header.key, header.value.first()) }
//
//        val span = Span.current()
//        span.setAttribute("api.trustedid", headers.getFirst("X-API-Trusted-Id").orElse("na"))

        client.newCall(reqBuilder.build()).execute().use { response ->
            return response.body!!.string()
        }
    }

    @Get("/randomstringfailure")
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomStringFailureOtelSdk(headers: HttpHeaders): String {
        val reqBuilder = Request.Builder().url("http://localhost:9000/otlpserver/randomuuidfailure")
        //headers.filter { header -> header.key.startsWith("X-API-") }
        //    .forEach { header -> reqBuilder.addHeader(header.key, header.value.first()) }

        //headers.get("X-Amzn-Trace-Id")?.let { reqBuilder.addHeader("X-API-amzntraceid", it) }
        //headers.get("x-datadog-traceid")?.let { reqBuilder.addHeader("X-API-ddtraceid", it) }

        client.newCall(reqBuilder.build()).execute().use { response ->
            return response.body!!.string()
        }
    }
}