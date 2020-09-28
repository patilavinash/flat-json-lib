# flat-json-lib
<pre>
The repository contains <br />
/src -source code for the json-flat libraray<br />
/pom.xml - maven pom.xml with dependenies and build defined<br />
/release - The folder contains the uber jar built with dependencies along with a helper shell script<br />
</pre>
# How to run
Go into the /release folder
1. Running via linux | System.in
```
echo ../sample.json | ./json-flat.sh 
./json-flat.sh <<< "{"a" , true}"
```
2. Running via Command Line
Run Below command
```
java -jar ./json-flat-1.0-jar-with-dependencies.jar
```
The library stops reading from the System.in once you type ENTER (\n) without any text and outputs the json

# Implementaion

<pre>
The library in general follows DFS based approach to flatten the nested json structure. <br />  
Once the valid json is read from System.in  <br /> 
 1 .For each of the json children
 	Add the current key to the path
		Check if the json value is a another json container or terminal json node, 
			If it is terminal then add it to the output object
			Else recurse for the current children and once done remove
	Remove the current key from the path
</pre>

# TO-DO
1. Adding  unit-test case for reading from System.in directly
