package edu.stanford.webprotege.project.scanner;

import java.io.File;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class DefaultFileRecoveryStrategy implements FileRecoveryStrategy {
    @Override
    public File getRecoveryFile(File inputFile) {
        File parentFile = inputFile.getParentFile();
        return new File(parentFile, inputFile.getName() + ".recovered");
    }
}
