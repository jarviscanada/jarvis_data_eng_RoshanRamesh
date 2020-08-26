package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.*;

public class JavaGrepLambdaImp extends JavaGrepImp {
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    public static void main(String[] args){
        if (args.length !=3) {
            throw new IllegalArgumentException("check # of arguments <= 3");
        }
            //creating JavaGrepLambdaImp instead of JavaGrepImp
            //JavaGrepLambdaImp inherits all methods except two override methods
            BasicConfigurator.configure();
            JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
            javaGrepLambdaImp.setRegex(args[0]);
            javaGrepLambdaImp.setRootPath(args[1]);
            javaGrepLambdaImp.setOutFile(args[2]);

            try {
                //calling parent method
                //but it will call override method (in this class)
                javaGrepLambdaImp.process();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Implement using lambda and stream APIs
         */

        @Override
                public List<String> readLines(File inputFile) {
            /**
             * Implemen using lambda and stream APIs
             */
            List<String> readLines = new ArrayList<>();
            try{
                BufferedReader Files = new BufferedReader(new FileReader(inputFile));
                readLines = Files.lines().collect(Collectors.toList());
                Files.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return readLines;
        }

            @Override
                    public List<File> listFiles(String rootDir){
            List<File> files = new LinkedList<>();
            try {
                files = Files.walk(Paths.get(rootDir)).map(Path::toFile).filter(File::isFile).collect(Collectors.toList());
            } catch(IOException e) {
                logger.error(e.getMessage(), e);
            }
            return files;
            }
    }
