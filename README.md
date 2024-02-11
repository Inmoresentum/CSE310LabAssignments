<h1 align="center" style="color: deeppink">CSE310</h1>
<h3 align="center" style="color: aliceblue">Lab Assignments</h3>

<div align="center">
    <h4 align="center" style="color: blueviolet">Necessary Tools</h4>
         <a href="https://github.com/wingkwong">
             <img src="https://img.shields.io/badge/MariaDB 10+-003545?style=for-the-badge&logo=mariadb&logoColor=white"/>
         </a>
         <a href="https://openjdk.org/">
             <img src="https://img.shields.io/badge/jdk 17+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
         </a>
         <a href="https://www.jetbrains.com/idea/">
             <img src="https://img.shields.io/badge/IntelliJIDEA 23+-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white"/>
         </a>
         <a href="https://maven.apache.org/">
             <img src="https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
         </a>
</div>

This repository contains my CSE310 Lab Assignments that I had to do.

# Setup
To run the code or test thing out, you will need to have a couple of things
1. java

   `jdk` version **17**
   Download [jdk](https://www.oracle.com/java/technologies/downloads/)
   if you don't have it already.

   If you're on unix like system, you can use [SDK MAN](https://sdkman.io/)
   which has certain advantages over other methods.

   To check your version of java, run:

   ```shell
    java --version
   ```
   or
   ```shell
   javac --version
   ```
2. [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    It is recommended that you use [IntelliJ IDEA](https://www.jetbrains.com/idea/) ultimate edition
    which you can obtain using [GitHub student pack](https://education.github.com/pack).
3. Finally, Clone the repo if you haven't already

   make sure that you have [git](https://git-scm.com/downloads) installed.
   To check run `git --version` in your
   terminal.

   ```shell
   git clone https://github.com/Inmoresentum/CSE310LabAssignments.git
   ```
   Now open `CSE310LabAssignments`
   using IntelliJ IDEA and for wait maven wrapper and the necessary dependencies to be downloaded.
   Now you can click the play icon and have some **fun**.
#### Setup For Assignment 2:
You will need to have [MariaDB](https://mariadb.org/).
The easiest way to get started is
to spin up a [MariaDB container](https://hub.docker.com/_/mariadb) using [docker](https://www.docker.com).
Once the MariaDB instance is running create a database named **CSE310DB**.

## Lab Files/ Assignment Files
Lab files or assignment files can be found [`src/main/java/org/cse310/bracu/labfiles`](src/main/java/org/cse310/bracu/labfiles) in this location.
There are total two lab assignments.
## Lab Assignments
### Lab Assignment 1
The first Lab Assignment can be found [`src/main/java/org/cse310/bracu/labfiles/CSE310_Lab_Assignment_1.pdf`](src/main/java/org/cse310/bracu/labfiles/CSE310_Lab_Assignment_1.pdf).
The solution of the lab assignments can be found [`src/main/java/org/cse310/bracu/assignment1`](src/main/java/org/cse310/bracu/assignment1) in this location.
Just run [`Main.java`](src/main/java/org/cse310/bracu/assignment1/Main.java) file.
At this point, you can probably understand the repo structure.
### Lab Assignment 2
Similarly, for Lab Assignment 2,
the assignment file can be found [`src/main/java/org/cse310/bracu/labfiles/CSE310_Lab_Assignment_2.pdf`](src/main/java/org/cse310/bracu/labfiles/CSE310_Lab_Assignment_2.pdf) in this location and the solution [`src/main/java/org/cse310/bracu/assignment2`](src/main/java/org/cse310/bracu/assignment2).
There similarly run the `Main.java` file. 

**Note:** Before Running the `Lab Assignment 2 Main.java` file make sure that you already have installed all the necessary
dependencies and MariaDB is running
and there is already a Database created **CSE310DB** which is also mentioned in the [setup step](#set-for-assignment-2).

<p align="center"></p>


&#160;
<p align="center">Copyright &copy; 2023-present 
   <a href="https://github.com/Inmoresentum" target="_blank">Inmoresentum</a>
</p>
<p align="center">
   <a href="LICENSE.md">
      <img src="https://img.shields.io/static/v1.svg?style=for-the-badge&label=License&message=CC BY-NC-SA&colorA=00A500&colorB=AA99B4"
         alt="whatever" style="border-radius: 10px"/>
   </a>
</p>
