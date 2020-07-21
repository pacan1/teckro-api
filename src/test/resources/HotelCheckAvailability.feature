@regression

Feature: Verify the expected response is returned from the API when a GET request is made to check room availability.
  As a hotel receptionist
  I would like to check how many rooms are available on a certain date
  So that I can make bookings

  Scenario Outline: Check availability of rooms for date <date>
    Given a date <date> is entered
    When the response is returned with the expected response code <expected response code>
    Then the response has data for date <date>
    Examples:
    | date       | expected response code |
    | 2020-01-01 | 200                    |
    | 2025-01-01 | 200                    |
    | 2024-02-29 | 200                    |

#     Note that test case 1 above is a bug as the date is in the past, this is showing that it returns a negative number availability when a date in the past is requested

  Scenario Outline: Negative Tests: Check availability of rooms for date <date> when an error response code is expected
    Given a date <date> is entered
    When the response is returned with the expected response code <expected response code>
    Then the expected error is returned with reason <reason>
    Examples:
      | date       | expected response code | reason                                             |
      | 2020-01-01 | 400                    | The booking date should not be in the past         |
      | 2020-13-01 | 400                    | The booking date should have a month between 1-12  |
      | 1999-12-01 | 400                    | The booking date should not be in the past         |
      | Not-adate  | 400                    | The booking date should not be a valid date        |
      |            | 400                    | A booking date is required                         |