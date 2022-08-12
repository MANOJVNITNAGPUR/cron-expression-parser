# Cron Expression Parser
***
Cron expression parser is a console application that consider the standard cron format with five times fields (minute, hour, day of month, month, day of week)plus a command.

Sample cron expression for input : <br/> "* * * * * user/bin/find"

Sample output: <br/>
-----------------------(* * * * *)--------------------------- <br/>
Minute        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59<br/>
Hour          0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 <br/>
Day of Month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 <br/>
Month         1 2 3 4 5 6 7 8 9 10 11 12 <br/>
Day of Week   1 2 3 4 5 6 7 <br/>
command       user/bin/find <br/>
-------------------------------------------------- <br/>

## To Run the application use below command

1. Navigate to project folder & run below command
    * gradlew run (This option is not recommended.)
2. Run com.deliveroo.cron.CronExpressionParserDriver directly to start the console application from project folder.


## To Run the test cases use below command

1. Navigate to project folder & run below command
   * gradlew test

## To build the application

1. Navigate to project folder & run below command
   * gradlew clean build