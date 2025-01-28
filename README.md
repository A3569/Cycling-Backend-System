# Cycling Race Management System
The Cycling Race Management System is a Java-based application designed to manage professional cycling races. It provides comprehensive functionality for organizing races, managing teams and riders, recording race results, and calculating various classifications.

~~~
cycling-system/
├── src/
│      └─cycling/
│           ├── CyclingPortal.java
│           ├── CyclingPortalInterface.java
│           ├── MiniCyclingPortalInterface.java
│           ├── CyclingTest.java
│           ├── Race.java
│           ├── Stage.java
│           ├── Segment.java
│           ├── Team.java
│           ├── Rider.java
│           ├── RiderResult.java
│           ├── StageType.java
│           ├── StageState.java
│           ├── SegmentType.java
│           ├── IDNotRecognisedException.java
│           ├── IllegalNameException.java
│           ├── InvalidNameException.java
│           ├── InvalidLengthException.java
│           ├── InvalidLocationException.java
│           ├── InvalidStageStateException.java
│           ├── InvalidStageTypeException.java
│           ├── DuplicatedResultException.java
│           ├── InvalidCheckpointsException.java
│           ├── NameNotRecognisedException.java
├── bin/
├── doc/
├── res/ 
│      ├── race_data
├── lib/ 
│      ├── jacocoagent.jar
├── cycling.jar
└── README.md
~~~

## System Features
- Race Management: Create, view, and remove cycling races
- Stage Management: Add stages with different types (flat, mountain, time trial)
- Segment Management: Add climbs and sprints to stages
- Team Management: Create and manage cycling teams
- Rider Management: Register riders and assign them to teams
- Results Management: Record and manage stage results
- Classifications: Calculate various rankings (General, Points, Mountain)

## Documentation
The package includes:
- JavaDoc documentation in /doc directory
- Source code with inline comments in /src directory
- UML diagrams showing system architecture
- API documentation describing all public interfaces
- Exception handling documentation
- User guide with examples

## System Requirements
- Java Runtime Environment (JRE) 8 or higher
- Operating System: Windows/Linux/MacOS

## Running
1. Running the test form jar file:
~~~
java -cp cycling.jar cycling.CyclingTest
~~~

2. Running the basic test:
~~~
java -cp bin cycling.CyclingTest
~~~

## Development
This system was developed to implements:
- Object-oriented design principles
- Interface-based programming
- Exception handling
- File I/O operations
- Collection framework usage
- Serialization for data persistence
