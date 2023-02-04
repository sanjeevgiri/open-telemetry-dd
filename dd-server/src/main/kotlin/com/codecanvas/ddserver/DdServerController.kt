package com.codecanvas.ddserver

import java.util.UUID;
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/ddserver/randomuuid")
class DdServerController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    fun getRandomUuid(): String {
        return UUID.randomUUID().toString()
    }
}