package edu.stanford.webprotege.project.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/03/2014
 */
public class RecoverProjectFiles {

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.out.println("Expected one argument (project directory path)");
            return;
        }
        File file = new File(args[0]);
        if(!file.exists()) {
            System.out.printf("Directory does not exist (%s)", file);
            return;
        }
        if(!file.isDirectory()) {
            System.out.printf("Expected path to directory (%s)", file);
        }
        ProjectFiles projectFiles = new ProjectFiles(file, System.out);
        projectFiles.recoverFiles();
    }
}
