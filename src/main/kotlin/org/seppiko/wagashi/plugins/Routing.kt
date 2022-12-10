package org.seppiko.wagashi.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.configureRouting() {

    routing {
        get("/") {
            val json = Json {
                "code" to 400
                "msg" to "Bad Request."
            }.encodeToString(JacksonConverter())
            call.respondText(json, ContentType.Application.Json, HttpStatusCode.OK)
        }
    }
}
