# json-flat
<pre>
The repository contains
/src -source code for the json-flat library
/pom.xml - maven pom.xml with dependencies and build definition
/release - The folder contains the shaded jar built with dependencies along with a helper shell script
</pre>
# How to run

Navigate into the /release folder through command-line and you can execute the library in <b>one</b> of the following ways
1. Running with Unix System.in
```
cat ../sample.json | ./json-flat.sh 
```
OR
```
./json-flat.sh <<< "{\"a\":true}"
```
2. Running via Interactive
```
java -jar ./json-flat-1.0-jar-with-dependencies.jar
```
The library stops reading from the System.in once you type ENTER (\n) without any text and outputs the json

# Implementation

<pre>
The library in general follows DFS based approach to flatten the nested Json structure. <br />  
Once the valid Json is read from System.in , start with empty path list  <br /> 
 BEGIN : For each of the Json children current object
 	Add the current Key to the path
		Check if the Json Value is a another Json container or terminal node, 
		  If it is terminal then add it to the output object with key as all the elements from the path list seen till now
		  Else go to BEGIN with current object
	Remove the current Key from the path
</pre>

# TO-DO
1. Adding  unit-test case for reading from System.in directly
