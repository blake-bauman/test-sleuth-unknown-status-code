
Sample project for demonstrating an issue with Spring Cloud Sleuth and WebClient, if the downstream service returns a non-standard status code.

[Spring Cloud Sleuth Issue 1382](https://github.com/spring-cloud/spring-cloud-sleuth/issues/1382)

* Endpoint to hit is /hello
* Expected result is 200 with "Hello World: 499"
* Actual result is no response, and server throws IllegalArgumentException

To fix:
* Remove Spring Cloud Sleuth dependency, re-build, and re-test.
* Actual result is Expected result



(Not a contribution)
