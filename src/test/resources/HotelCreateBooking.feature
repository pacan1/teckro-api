@regression

Feature: Verify the expected response is returned from the API when a POST request is made to create a booking.
  As a hotel receptionist
  I would like to make a room booking for a date and number of days
  So that I can calculate the price of and make a booking

   Scenario Outline: Book a room from date <checkin date> for <num of days> days
    Given a date <checkin date> and number of days <num of days> is requested
    When the response is returned with the expected response code <expected response code>
    Then the booking is made for <num of days> days from date <checkin date>
    Examples:
      | checkin date  | num of days |  expected response code |
      | 2022-01-10    | 3           |  200                    |
      | 2021-06-30    | 10          |  200                    |
      | 2021-06-30    | 1           |  200                    |
      | 2022-01-16    | 10          |  200                    |

  Scenario Outline: Negative Tests: Book a room from date <checkin date> for <num of days> days
    Given a date <checkin date> and number of days <num of days> is requested
    When the response is returned with the expected response code <expected response code>
    Then the expected error is returned with reason <reason>
    Examples:
      | checkin date  | num of days |  expected response code | reason                                      |
      | 2020-01-01    | 1           |  400                    | The booking date should not be in the past  |
      | 2021-06-31    | 1           |  400                    | The booking date should use a valid date    |
      | Not-a-date    | 1           |  400                    | The booking date should use a valid date    |
      | 2021-06-05    | -1          |  400                    | The number of days in the booking cannot be negative  |
      | 2021-06-05    | 0           |  400                    | The number of days in the booking cannot be empty  |


  Scenario: Negative Test: Book a room from date 2021-01-01 with no number of days
    Given a date 2021-01-01 with no number of days is requested
    When the response is returned with the expected response code 400
    Then the expected error is returned with reason "This is the error that should have returned"