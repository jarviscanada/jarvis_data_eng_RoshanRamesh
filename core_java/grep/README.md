# Introduction

This application is a simple implementation of GREP i.e., Global Regular Expression bash program in Java. The app searches for a text pattern recursively in a given directory, and output matched lines to a file. This is implemented in 2 ways:
- Using the Lists data structure
- Using Java 8's Stream API and Lambda expressions

# Usage

The Java GREP app takes three arguments:
- `regex`: A special text string for describing a search pattern.
- `rootPath`: The root directory path to be searched.
- `outFile`: Output file name into which the matched lines are written.

````bash
JavaGrep regex rootPath outFile
````
Example:
````bash
*IllegalArgumentException.* ./grep/src /tmp/grep.out
````

# Pseudocode

The pesudocode of the `process` method is as shown below:
````bash
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
````
# Performance Issue

The two primary performance issues:
- Memory: The `readLines` method, the program reads the files line by line and stores them as a list. It works efficiently files with a smaller size. However, for files with a huge size, the processing is slow and an `outOfMemory` exception could be encountered.
- Directory: The recursive traversal of directories is made possible by an implicit stack. Therefore, a large enough directory could render a `stackOverflow` exception.

# Improvements
- Pipelining necessary functions and parallel streams could be implemented to improve efficiency and reduce memory usage.
- The app is a simple implementation for the minimum viable product (MVP). Hence, OOP implementation can be improved.
- The input/directory and location can be exclusively provided along with a recursive depth parameter. This will ensure that a `stackOverFlow` exception is not encountered.


