package com.codecanvas.ddclient

import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8000")
interface DdStringGenClient {
    @Headers(
        Header(name = ACCEPT, value = MediaType.TEXT_PLAIN)
    )
    @Get("/ddserver/randomuuid")
    fun randomString(): String

    @Headers(
        Header(name = ACCEPT, value = MediaType.TEXT_PLAIN)
    )
    @Get("/ddserver/randomuuidfailure")
    fun randomStringFailure(): String
}