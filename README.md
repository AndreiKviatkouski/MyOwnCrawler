
Test task:  My Own Crawler
----------------------------------------------------
### The simplest web crawler

    https://github.com/AndreiKviatkouski/MyOwnCrawler.git
     
Using
--------

    git clone  https://github.com/AndreiKviatkouski/MyOwnCrawler.git
     
Building
--------
 
 To build this project you will need Maven 4. You can get it at:
 
     http://maven.apache.org

 Clean compilation products:
 
     mvn clean
     
 Compile:
 
     mvn compile
     
  Assembling:
     
     mvn clean assembly:assembly
     
     
  Run application in the console:
  
     java -jar ${path to file}\MyOwnCrawler-1.0-SNAPSHOT-jar-with-dependencies.jar -classpath by.AndreiKviatkouski.SpiderRun

  ## Technological Stack
     *   Java 11LS
     *   Project JSOUP 
     *   Project Lombok
     *   Project Junit-Jupiter
   
     
 ## Repo owner: Andrei Kviatkouski
 