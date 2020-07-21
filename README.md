# teckro-api
Test Hotel Availability and Booking API

# To Run the Test Suite
From the gradle menu choose task `cucumber`

From the command line run `gradlew cucumber`

# To generate a report 
From the gradle menu choose task `generateCucumberReports`

From the command line run `gradlew generateCucumberReports`

This task will generate a html file that can be opened in a browser 
Report Location: file:///$projectDir/build/test-report/html/cucumber-html-reports/overview-features.html

# View a report in the browser
This task will generate a html file that can be opened in a browser Report Location: 

open file:///$projectDir/build/test-report/html/cucumber-html-reports/overview-features.html

<img width="1672" alt="sample-report" src="https://user-images.githubusercontent.com/22344537/88045745-eba79880-cb46-11ea-8f2c-60b94244088a.png">


# Pass Rate
<img width="370" alt="sample-cucumber-console-output" src="https://user-images.githubusercontent.com/22344537/88046161-8d2eea00-cb47-11ea-9603-7062325db0ef.png">

18 Scenarios (16 failed, 2 passed)

54 Steps (16 failed, 7 skipped, 31 passed)

Only 2 tests are passing due to issues highlighted below. 
If the bugs were fixed, tests would need some refactor around error handling (in response body) to ensure the correct
error messages were expected / received.

# Bugs

Bugs:

1.  CheckAvailabilityAPI with date in the past. 
    Actual: A valid date in the past returns 200 OK with a negative number of available rooms.
    Expected: A valid date in the past should return 400 Bad Request OR
    if it has to return 200 OK then the rooms available should be 0.
    (409 could possibly also be returned.)

2.  CheckAvailabilityAPI with invalid date (2020-13-01). 
    Actual: 200 OK is returned 
    Expected: 400 Bad Request

3.  BookARoom API with invalid date: 
    Actual: Returns 200 ok. 
    Expected: Should return Bad Request

4.  BookARoom API with date 2021-06-30 
    Actual: With a stay of 10 days the end date is calculated incorrectly 
    Expected: "2021-07-10" but was "2021-06-09"

5.  BookARoom API with date 2021-06-30 
    Actual: With a stay of 1 day the end date is calculated incorrectly (with an invalid date) 
    Expected: "2021-07-01" but: was "2021-06-31"

    The previous 2 bugs show end of month is not handled (can be
    demonstrated but using any month of the year)
    
6.  BookARoom API with valid date but invalid number of days 
    Actual: Negative and 0 number of days generates and end date adding 0 or negative to the start date and returns a negative price.
    Status Code returns 200.
    Expected: API should return 400 instead of 200. Not calculate and end date and not calculate a price.

7.  Calculation of the price is reduced by 10 for some reason. I assume
    this is a bug and not an intended discount.

