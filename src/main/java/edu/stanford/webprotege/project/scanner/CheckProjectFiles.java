package edu.stanford.webprotege.project.scanner;

import java.io.File;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class CheckProjectFiles {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.printf("Expected one argument (project directory path)\n");
            return;
        }
        File file = new File(args[0]);
        if(!file.exists()) {
            System.out.printf("Directory does not exist (%s)\n", file);
            return;
        }
        if(!file.isDirectory()) {
            System.out.printf("Expected path to directory (%s)\n", file);
        }
        ProjectFiles projectFiles = new ProjectFiles(file, System.out);
        projectFiles.checkFiles();

    }

}
