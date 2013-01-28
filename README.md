CGrep
=====

This is a simple grep implementation in Java using Akka. You can compile the application yourself using the source files if you have Akka set up on your machine. If compiling doesn't work or you do not have Akka set up, we have provided a .jar file that can be run on the command line. The .jar can be run several different ways. The usage of the application is:

java -jar CGrep pattern [file...]

Pattern can be any regular expression compatible with Java's regex engine. Four text files are provided for testing purposes. These are Shakespeare plays found on Project Gutenberg's website. Here are some examples of how you can run the application: 

java -jar CGrep "thou\smust" test.txt
java -jar CGrep "thou\smust" test.txt test2.txt test3.txt test4.txt
java -jar CGrep thou <<< "where art thou"
echo "where art thou" | java -jar CGrep thou

Or, you can run the application with no files or piped input:

java -jar CGrep "thou\smust"

If you run the application this way, you can type input into the console and CGrep will scan the input you provide for the pattern specified. The application will continue taking input until it reads an empty line.