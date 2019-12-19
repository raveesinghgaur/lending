## Built using Java 8 and SpringBoot

### Install and Build:

```
brew install maven
mvn clean install

```

### Run 
```
java -jar target/exercise-0.0.1-SNAPSHOT.jar
```


### Sample request:

```
// using CURL

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"loanAmount":"5000.00","nominalRate":"5.00","duration":"24","startDate":"2018-08-01T00:00:01Z"}' \
  http://localhost:8080/loan/generate-plan

// using Postman

    http://localhost:8080/loan/generate-plan 

    {
      	"loanAmount":"5000.00",
      	"nominalRate":"5.00",
      	"duration":"24",
      	"startDate":"2018-08-01T00:00:01Z"
      }

```
