package edu.stanford.webprotege.project.scanner;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 02/03/2014
 */
public class RecoverProjectFiles {


    public static final String CHANGE_DATA_CHANGE_DATA_FILE_PATH = "/change-data/change-data.binary";

    private File projectDirectory;

    public RecoverProjectFiles(File projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        RecoverProjectFiles recoverProjectFiles = new RecoverProjectFiles(file);
        recoverProjectFiles.recoverFiles();
    }

    private void recoverFiles() {
        try {
            attemptToReadDocument("Root ontology", getRootOntologyDocument(), new BinaryOWLOntologyDocumentConsumer());
        } catch (FileNotFoundException e) {
            System.out.println("Root ontology document does not exist: " + getRootOntologyDocument());
        }
        try {
            attemptToReadDocument("Notes ontology", getNotesOntologyDocument(), new BinaryOWLOntologyDocumentConsumer());
        } catch (FileNotFoundException e) {
            System.out.println("Notes ontology document does not exist: " + getNotesOntologyDocument());
        }
        try {
            attemptToReadDocument("Change log", getChangeLogDocument(), new BinaryOWLOntologyChangeLogConsumer());
        } catch (FileNotFoundException e) {
            System.out.println("Change log does not exist: " + getChangeLogDocument());
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
