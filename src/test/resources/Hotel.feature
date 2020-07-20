@regression

Feature: Verify the expect response is returned for the API when a GET request is made to check room availability.
  As a hotel receptionist
  I would like to check how many rooms are available on a certain date
  So that I can make bookings
#
#  Scenario Outline: Check availability of rooms for date <date>
#    Given a date <date> is entered
#    When the response is returned with the expected response code <expected response code>
#    Then the response has data for date <date>
#    Examples:
#    | date       | expected response code |
#    | 2020-01-01 | 200                    |
#    | 2025-01-01 | 200                    |
#    | 2024-02-29 | 200                    |

#     Note that test case 1 above is a bug as the date is in the past, this is showing that it returns a negative number availability when a date in the past is requested

#  Scenario Outline: Negative Tests: Check availability of rooms for date <date> when an error response code is expected
#    Given a date <date> is entered
#    When the response is returned with the expected response code <expected response code>
#    Then the expected error is returned with reason <reason>
#    Examples:
#      | date       | expected response code | reason                                       |
#      | 2020-01-01 | 400                    | This is the error that should have returned  |
#      | 2020-13-01 | 400                    | This is the error that should have returned  |
#      | 1999-12-01 | 400                    | This is the error that should have returned  |
#      | Not-adate  | 400                    | This is the error that should have returned  |

  Scenario Outline: Book a room from date <checkin date> for <num of days> days
    Given a date <checkin date> and number of days <num of days> is requested
    When the response is returned with the expected response code <expected response code>
    Then the booking is made for <num of days> days from date <checkin date>
    Examples:
      | checkin date  | num of days |  expected response code |
#      | 2022-01-10    | 3           |  200                    |
#      | 2021-06-30    | 10          |  200                    |
#      | 2021-06-30    | 1           |  200                    |
      | 2022-01-15    | 7           |  200                    |




#  Scenario Outline: Negative Tests: Book a room from date <checkin date> for <num of days> days
#    Given a date <checkin date> and number of days <num of days> is requested
#    When the response is returned with the expected response code <expected response code>
#    Then the expected error is returned with reason <reason>
#    Examples:
#      | checkin date  | num of days |  expected response code | reason                                       |
#      | 2020-01-01    | 1           |  400                    | This is the error that should have returned  |
#      | 2021-06-31    | 1           |  400                    | This is the error that should have returned  |
#      | Not-a-date    | 1           |  400                    | This is the error that should have returned  |