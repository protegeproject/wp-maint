package edu.stanford.webprotege.project.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/03/2014
 */
public class RecoverProjectFiles {


    public static final String CHANGE_DATA_CHANGE_DATA_FILE_PATH = "/change-data/change-data.binary";
    
    private final PrintStream out;

    private File projectDirectory;

    public RecoverProjectFiles(File projectDirectory) {
        this.projectDirectory = projectDirectory;
        out = System.out;
    }

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
        RecoverProjectFiles recoverProjectFiles = new RecoverProjectFiles(file);
        recoverProjectFiles.recoverFiles();
    }

    private void recoverFiles() {
        try {
            attemptToReadDocument("Root ontology", getRootOntologyDocument(), new BinaryOWLOntologyDocumentConsumer());
        } catch (FileNotFoundException e) {
            out.printf("Root ontology document does not exist: %s", getRootOntologyDocument());
        }
        try {
            attemptToReadDocument("Notes ontology", getNotesOntologyDocument(), new BinaryOWLOntologyDocumentConsumer());
        } catch (FileNotFoundException e) {
            out.printf("Notes ontology document does not exist: %s", getNotesOntologyDocument());
        }
        try {
            attemptToReadDocument("Change log", getChangeLogDocument(), new BinaryOWLOntologyChangeLogConsumer());
        } catch (FileNotFoundException e) {
            out.printf("Change log does not exist: %s", getChangeLogDocument());
        }
    }

    private File getRootOntologyDocument() {
        return new File(projectDirectory, "/ontology-data/root-ontology.binary");
    }

    private File getNotesOntologyDocument() {
        return new File(projectDirectory, "notes-data/notes-data.binary");
    }

    private File getChangeLogDocument() {
        return new File(projectDirectory, CHANGE_DATA_CHANGE_DATA_FILE_PATH);
    }

    private void attemptToReadDocument(String name, File file, FileConsumer consumer) throws FileNotFoundException {
        FileRecoveryTool recoveryTool = new FileRecoveryTool();
        recoveryTool.recoverFileIfNecessary(name, file, consumer);
    }
}
