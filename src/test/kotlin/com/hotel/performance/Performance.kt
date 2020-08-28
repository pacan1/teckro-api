package com.hotel.performance

import com.github.noconnor.junitperf.JUnitPerfRule
import com.github.noconnor.junitperf.JUnitPerfTest
import com.github.noconnor.junitperf.JUnitPerfTestRequirement
import com.github.noconnor.junitperf.reporting.providers.ConsoleReportGenerator
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.junit.Rule
import org.junit.Test
import java.io.IOException

val hReporter: HtmlReportGenerator = HtmlReportGenerator("./common_reporter.html")
val cReporter: ConsoleReportGenerator = ConsoleReportGenerator()

class Performance {

    @get:Rule
    var perfRule: JUnitPerfRule? = JUnitPerfRule(hReporter)

    private val checkAvailabilityApi = "http://localhost:9090/checkAvailability/"
    private val bookRoomApi = "http://localhost:9090/bookRoom"


    @Test
    @JUnitPerfTest(
        threads = 1,
        durationMs = 10000,
        warmUpMs = 1000,
        rampUpPeriodMs = 2000,
        maxExecutionsPerSecond = 5000
    )
    @JUnitPerfTestRequirement(executionsPerSec = 10_000)
    @Throws(
        IOException::class
    )
    fun `call the hotel`() {

        RestAssured.given()
            .baseUri(checkAvailabilityApi)
            .get("1999-12-01")
            .then()
            .statusCode(HttpStatus.SC_OK)
    }

    @Test
    @JUnitPerfTest(threads = 1, warmUpMs = 1000, durationMs = 2000)
    @Throws(
        InterruptedException::class
    )
    fun `make a booking`() {
        RestAssured.given()
            .body(createRequestBody(5, "2021-12-01"))
            .contentType(ContentType.JSON)
            .post(bookRoomApi)
            .thenReturn()
    }

    @Test
    @JUnitPerfTest(threads = 1, warmUpMs = 1000, durationMs = 2000)
    @Throws(
        InterruptedException::class
    )
    fun test2() {
        Thread.sleep(10)
    }

    private fun createRequestBody(numOfDays: Int, checkInDate: String): String {
        return """
        {
            "numOfDays": $numOfDays,
            "checkInDate": "$checkInDate",
        }
        """.trimIndent()

    }
}

//fun main() {
//    val test = Performance()
//    test.whenNoRequirementsArePresent_thenTestShouldAlwaysPass()
//}