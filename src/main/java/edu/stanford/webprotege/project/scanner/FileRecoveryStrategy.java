package edu.stanford.webprotege.project.scanner;

import java.io.File;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public interface FileRecoveryStrategy {

    File getRecoveryFile(File inputFile);
}
