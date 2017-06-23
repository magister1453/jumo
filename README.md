This project is the Jumo assignment.

I chose to use Java as it is my language of choice. I included an external library as Java has no
internal support for tuples which is the most efficient means of mapping the data. I used a parallel
stream in order to increase performance of large files. Memory should not be a problem in terms
of reading all the lines as Java uses a BufferedReader.

I used Maven as my build agent. In order to build the project, the Maven command mvn build install.
In the generated target directory find Jumo-1.0-SNAPSHOT-jar-with-dependecies.jar.
To run the application execute the command:
java -jar Jumo-1.0-SNAPSHOT-jar-with-dependecies.jar LocationOfTheInputFile.
You will find the result in Output.csv in the same directory.

