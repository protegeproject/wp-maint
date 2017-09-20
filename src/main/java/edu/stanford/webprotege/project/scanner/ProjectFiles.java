package edu.stanford.webprotege.project.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class ProjectFiles {



    public static final String CHANGE_DATA_CHANGE_DATA_FILE_PATH = "/change-data/change-data.binary";

    public static final String ROOT_ONTOLOGY_DOCUMENT_PATH = "/ontology-data/root-ontology.binary";

    public static final String NOTES_DATA_NOTES_DOCUMENT_PATH = "notes-data/notes-data.binary";

    private final PrintStream out;

    private File projectDirectory;

    public ProjectFiles(File projectDirectory, PrintStream logger) {
        this.projectDirectory = checkNotNull(projectDirectory);
        out = logger;
    }

    public void processFiles(FileStrategy strategy) {
        try {
            strategy.handleFile("Root ontology", getRootOntologyDocument(), new BinaryOWLOntologyDocumentConsumer());
        } catch (FileNotFoundException e) {
            out.printf("Root ontology document does not exist: %s", getRootOntologyDocument());
        }
        try {
            strategy.handleFile("Change log", getChangeLogDocument(), new BinaryOWLOntologyChangeLogConsumer());
        } catch (FileNotFoundException e) {
            out.printf("Change log does not exist: %s", getChangeLogDocument());
        }
    }

    public void recoverFiles() {
        processFiles(new RecoverFilesStrategy());
    }

    public void checkFiles() {
        processFiles(new CheckFilesStrategy());
    }

    private File getRootOntologyDocument() {
        return new File(projectDirectory, ROOT_ONTOLOGY_DOCUMENT_PATH);
    }

    private File getNotesOntologyDocument() {
        return new File(projectDirectory, NOTES_DATA_NOTES_DOCUMENT_PATH);
    }

    private File getChangeLogDocument() {
        return new File(projectDirectory, CHANGE_DATA_CHANGE_DATA_FILE_PATH);
    }



    private interface FileStrategy {

        void handleFile(String name, File file, FileConsumer fileConsumer) throws FileNotFoundException;

    }


    private class RecoverFilesStrategy implements FileStrategy {
        @Override
        public void handleFile(String name, File file, FileConsumer fileConsumer) throws FileNotFoundException {
            FileRecoveryTool recoveryTool = new FileRecoveryTool(System.out);
            recoveryTool.recoverFileIfNecessary(name, file, fileConsumer);
        }
    }


    private class CheckFilesStrategy implements FileStrategy {
        @Override
        public void handleFile(String name, File file, FileConsumer fileConsumer) throws FileNotFoundException {
            ConsumedBytesInfo info = fileConsumer.consume(file);
            System.out.printf("Checked %s\n", file.getPath());
            if(info.isEndOfFile()) {
                System.out.printf("\tFile is o.k.\n");
            }
            else {
                System.out.printf("\tFile is corrupt.  Manage to parse %d bytes of %d bytes.\n", info.getBytesConsumed(), info.getTotalBytes());
            }
        }
    }

}
