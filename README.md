# Travel-agent-system-in-Java
A system for a travel agency which uses a graph library to represent airline data, and supports searching based on criteria of the travel agent sets.

## Prerequisite Libraries 
### - JGraphT
JGraphT is a Java library of graph theory data structures and algorithms.
```v1.3.0```
<br><br>
To use the library, you need to have a personal copy of the Open Source JGraphT graph library in your working environment. 
You can get more information about JGraphT on its public website, and more information about the classes the library provides in its Javadoc documentation available online:
- https://jgrapht.org/ 
- https://jgrapht.org/javadoc/ 
- https://jgrapht.org/javadoc-1.3.0/

#### JGraphT package download - The following instructions are for jgrapht-1.3.0 on a Unix machine using bash.
- Download the Open Source JGraphT graph library by following the instructions on: http://jgrapht.org/
- Decompress and extract the tarball (this will create a 88M jgrapht-1.3.0 directory), e.g.
```
$ tar zxvf jgrapht -1.3.0.tar.gz
```
- Delete the tarball to save 45M (47M for the zip file), e.g.
```
$ rm jgrapht -1.3.0.tar.gz
```
In the following, we assume that the extracted jgrapht-1.3.0 directory is located at /path/to/your/login/path/to/

#### Setup for Eclipse integration
To use and integrate JGraphT in Eclipse, you need to Configure the Java Build Path of your project. You will need to apply the following changes in Libraries:

- Add the external archive jgrapht-core-1.3.0.jar
- Once the archive is added, you can attach its sources by deploying its menu and edit Source attachment to point to the external directory location 
```/path/to/your/login/path/to/jgrapht-1.3.0/source/jgrapht-core/src```

This will make the sources of JGraphT directly available within Eclipse for documentation and debugging purposes.

- Similarly, you can add the documentation by editing Javadoc location path to be 
```/path/to/your/login/path/to/jgrapht-1.3.0/javadoc/.```

This will make the documentation of JGraphT directly available within Eclipse.


#### Setup for command line compilation and execution
- Add JGraphT’s core JAR file jgrapht-core-1.3.0.jar to your class path by adding the following commands at the end of your .profile file in your home directory. Alternatively, you could pass the additional class path information to javac and java with the ```-cp``` command line argument. The following line adds JGraphT’s core JAR to your class path (this could be repeated for other JGraphT JAR, to run JGraphT’s HelloJGraphT demo you will need to also include jgrapht-io-1.3.0.jar):
```
export CLASSPATH=/path/to/your/login/path/to/jgrapht-1.3.0/lib/jgrapht-coreê -1.3.0. jar : $CLASSPATH
```
Note that when you copy-paste these commands, you will need to edit the text lines your obtain to remove spaces and some line breaks.
- Execute your new .profile for the setting to be taken into account, e.g.
```
$ source ~/. profile
```

## Using the program
### Part A
Program FlyingPlannerMainPartA (containing a single main method) to represent the following direct flights with associated costs as a graph. Assume that flights operate in both directions with the same cost, e.g. Edinburgh to Heathrow denotes a pair of flights, one from Edinburgh to Heathrow, and another from Heathrow to Edinburgh.

### Part B
Program FlyingPlannerPartBC (containing a single main method) which makes use of the class FlyingPlanner (this is the central class of the program)
