package com.hwk1

import com.google.gson.Gson
import com.hwk1.Dao.Claim.Claim
import com.hwk1.Dao.Claim.ClaimDao
import com.hwk1.Dao.Database
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.utils.io.readAvailable
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        // Test with: http://localhost:8080/ClaimService/add
        post("/ClaimService/add") {
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)

            // Json deserialization
            var cObj1 : Claim
            cObj1 = Gson().fromJson(str, Claim::class.java)
            val dao = ClaimDao().addClaim(cObj1)
            val dbObj = Database.getInstance()

            println("${cObj1.toString()}")
            println("HTTP message is using POST method with /post ${contType} ${str}")
            call.respondText("The POST request was successfully processed.",
                    status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

        // Test with: http://localhost:8080/ClaimService/getAll
        get("/ClaimService/getAll") {
            var cList = ClaimDao().getAllClaims()

            val respJsonStr = Gson().toJson(cList)

            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType=ContentType.Application.Json)
        }
    }
}

