# Knight board documentation
The following sections explain how to execute the Knight Board application 
and the main design choices that have been taken.\
The application has been developed with Java 17 and Spring Boot.

# Knight board execution
This section explains how to run the Knight Board Spring Boot application.\
After that the Maven project has been packaged into a JAR file with the 
following command:
```
mvn clean package
```

The application can be executed by using the following commands:
```
docker build -t knight_board:latest .
docker run -e BOARD_API=https://storage.googleapis.com/jobrapido-backend-test/board.json -e COMMANDS_API=https://storage.googleapis.com/jobrapido-backend-test/commands.json knight_board:latest
```

## Solution design
The application is organized in the following main packages:
- ```com.gbattag.api``` contains the services used to download the board data 
and the list of commands to be executed. The data transfer
objects that are used to model the above data are in (```com.gbattag.api.dto```),
while the mappers from DTO objects to model objects are in (```com.gbattag.api.mappers```).

- ```com.gbattag.configuration``` contains some beans used by Spring at startup.

- ```com.gbattag.model``` contains the business model classes representing 
the board, the knight position, etc.

- ```com.gbattag.service``` contains the services implementing the business
logic to execute the commands in a given board.

The most relevant classes are:
- ```KnightBoardApplication``` is the application entry point. This class is responsible
for the application initialization and by using the ```KnightBoardService```
downloads the board data together with the list of commands, executes the
commands and prints on the standard output a json representing the outcome 
of the execution.

- ```KnightBoardService``` is the service responsible for downloading the 
board data together with the list of commands, executing the commands, and
returning the execution result.

- The board is modeled as a boolean matrix where each square can either 
be empty or it can contain an obstacle (```Board```).

- ```StartCommand```, ```RotateCommand```, and ```MoveCommand``` classes
have been defined to model the three types of commands. All of the above 
classes implements the ```Command``` interface.

- ```CommandExecutorService``` given a board object is responsible 
for sequentially executing a list of commands returning the execution 
result.

- ```BoardApiClient``` and ```CommandsApiClient``` are the services responsible
for downloading the board data and the list of commands.

- ```BoardMapper``` and ```CommandMapper``` are two services responsible for
mapping the downloaded board data and the list of commands to a ```Board```
model object and to a list of ```Command``` objects.

- ```ResultMapper``` is responsible for mapping the result of the commands 
execution to the output format described in the specification.

- ```BoardDto```, ```CommandsDto```, and ```ResultDto``` are data transfer
classes that are used to represent the downloaded board data and the list 
of commands, together with the result of the commands execution.

## Testing
While full test coverage is above the scope of this project,
the ```test``` directory contains a collection of test classes to cover the
most relevant aspects of the application.\
The structure of the test directory is pretty standard, with one test class
per class under test.\
***Mockito*** has been used when necessary to mock the dependencies of the 
class under test.\
***Parameterized tests*** have been used when appropriate to improve test 
readability, reducing code duplication.

