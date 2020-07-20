package com.hotel.steps

import io.cucumber.java.Before
import io.cucumber.java8.En
import io.cucumber.java.Scenario
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HotelSteps() : En  {

    private lateinit var scenario: Scenario
    private lateinit var response: Response
    private var totalCost: Int = 0

    private val checkAvailabilityApi = "http://localhost:9090/checkAvailability/"
    private val bookRoomApi = "http://localhost:9090/bookRoom"

    @Before
    fun initialise(scenario: Scenario) {
        this.scenario = scenario
    }

//    private fun checkAvailability(date: LocalDate): Response {
//
//        return RestAssured.given()
//            .baseUri(checkAvailabilityApi)
//            .get(date.toString()).thenReturn()
//    }

    private fun checkAvailability(date: String): Response {

        return RestAssured.given()
            .baseUri(checkAvailabilityApi)
            .get(date).thenReturn()
    }

    private fun bookARoom(numOfDays: Int, checkInDate: String): Response {
        return RestAssured.given()
            .body(createRequestBody(numOfDays, checkInDate))
            .contentType(ContentType.JSON)
            .post(bookRoomApi)
            .thenReturn()
    }

    private fun createRequestBody(numOfDays: Int, checkInDate: String): String {
        return """
        {
            "numOfDays": $numOfDays,
            "checkInDate": "$checkInDate",
        }
        """.trimIndent()

    }

    private fun createRequestBodyNumOfDaysAsString(numOfDays: Int, checkInDate: String): String {
        return """
        {
            "numOfDays": "$numOfDays",
            "checkInDate": $checkInDate,
        }
        """.trimIndent()

    }

    private fun calculatePrice(startDay: Int, numOfDays: Int): Int {
        var totalCost = 0
        var dailyCost: Int
        for (day in startDay..numOfDays) {
            dailyCost = 100 + (day * 10)
            totalCost += dailyCost
            println("Daily: $dailyCost")
            println("Total: $totalCost")
        }
        println("TotalCost::: $totalCost - 10")
        return totalCost - 10
    }


        init {

            Given("^a date (.*) is entered$") { checkInDate: String ->
            scenario.write("Checking date $checkInDate")
            response = checkAvailability(checkInDate)
            println(response.prettyPrint())
            }

            Given("^a date (.*) and number of days (.*) is requested$") { checkInDate: String, numOfDays: Int ->
                response = bookARoom(numOfDays, checkInDate)
                println(response.body.prettyPeek())

            }

            When("^the response is returned with the expected response code (.*)$") { expectedStatusCode: Int ->
                assertThat("HTTP Status Code Unexpected",
                    response.statusCode,
                    equalTo(expectedStatusCode))
            }

            Then("^the response has data for date (.*)$") {
                    checkInDate: String ->

                assertThat(
                "Returned date is unexpected",
                response.body().jsonPath().getString("date"),
                equalTo(checkInDate)
                )

                assertThat("Returned availability is null",
                    response.body().jsonPath().getInt("rooms_available"),
                    notNullValue())

                assertThat("Returned availability is negative",
                    response.body().jsonPath().getInt("rooms_available"),
                    greaterThan(0))

                assertThat("Returned price is unexpected",
                    response.body().jsonPath().getInt("price"),
                    notNullValue()
                )
            }

            Then("^the booking is made for (.*) days from date (.*)$") {
                    numOfDays: Long, checkInDate: String ->

                val expectedEndDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkInDate))
                    .plusDays(numOfDays)
                val dayOfWeek = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkInDate)).dayOfWeek.value
                println("Day of week $dayOfWeek")
                totalCost = calculatePrice(dayOfWeek, numOfDays.toInt())

                assertThat(
                    "Returned checkInDate is unexpected",
                    response.body().jsonPath().getString("checkInDate"),
                    equalTo(checkInDate)
                )

                assertThat(
                    "Returned checkOutDate is unexpected",
                    response.body().jsonPath().getString("checkOutDate"),
                    equalTo(expectedEndDate.toString())
                )

                assertThat("Returned totalPrice is unexpected",
                    response.body().jsonPath().getInt("totalPrice"),
                    `is`(totalCost))

            }

            Then("^the expected error is returned with reason (.*)$") {
                    reason: String ->

                        assertThat("Unexpected Error Message",
                            response.body.jsonPath().getString("error.message"),
                            equalTo(reason))
            }
        }
}