
Test task:  My Own Crawler
----------------------------------------------------
### The simplest web crawler

    https://github.com/AndreiKviatkouski/MyOwnCrawler.git
     
Using
--------

    git clone  https://github.com/AndreiKviatkouski/MyOwnCrawler.git
     
Building
--------
 
 To build this project you will need Maven 3. You can get it at:
 
     http://maven.apache.org
     
 Adding property file to  ${path to file}\MyOwnCrawler\src\main\resources\config.properties
 
 config.properties fields:
 
     url = ${start url}
     outputFile1 = ${path for statistics}
     outputFile2 = ${path for sort statistics}
     MAX_DEEP = ${number max deep links}
     MAX_PAGES_TO_SEARCH = ${number max page to search}
     words = ${search word1,search word2,search word3,search word4,}

 Clean compilation products:
 
     mvn clean
     
 Compile:
 
     mvn compile
     
  Installing:
  
     mvn install
     
 
  Run application in the console:
  
        java -jar ${path to file}\MyOwnCrawler-1.0-SNAPSHOT-jar-with-dependencies.jar -classpath by.AndreiKviatkouski.SpiderRun
        or  
        ${path to file}\mvn exec:java -Dexec.mainClass=by.AndreiKviatkouski.SpiderRun 

  ## Technological Stack
     *   Java 11LS
     *   Project JSOUP 
     *   Project Lombok
     *   Project Junit-Jupiter
   
     
 ## Repo owner: Andrei Kviatkouski
 