package com.codecanvas.otlpclient

import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:9000")
interface OtlpStringGenClient {
    @Headers(
        Header(name = ACCEPT, value = MediaType.TEXT_PLAIN)
    )
    @Get("/otlpserver/randomuuid")
    fun randomString(): String

    @Headers(
        Header(name = ACCEPT, value = MediaType.TEXT_PLAIN)
    )
    @Get("/otlpserver/randomuuidfailure")
    fun randomStringFailure(): String
}