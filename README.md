# dsbd-repos

To test the application open project8 as it is the maven project that contains the Spring Boot loggingsystem application and other useful folders

The docker-compose build, docker-compose and docker-compose down commands must be run from the terminal inside the deploy folder as the docker-compose.yml is located there

To use the fake producer of the Python script kafka_producer.py run the command: docker-compose run debug-container bpython

To insert messages through the fake producer just run the commands of the documentation bearing in mind that the script to import is kafka_producer
