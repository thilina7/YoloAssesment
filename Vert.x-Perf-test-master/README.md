# Vert.x-Perf-test

This repo can use as mock service for performance testing.
So what you need to do is, clone the repo.
Create class under 'src/test/java/com/mock/services/' or any package with the constructor.
Then create your object in Main.class
(Change the port if port need to be change in Main.class)

Finally bulild the repo usig mvn clean install.
You can copy build file from target directory and put it into a server.
And you can start the .jar file using 'java -jar buildname.jar & > yourservice.log &

Just clone the repo.
Need java 1.8 as minimum
