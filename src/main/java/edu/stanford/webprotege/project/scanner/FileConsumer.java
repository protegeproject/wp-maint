package edu.stanford.webprotege.project.scanner;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public interface FileConsumer {

    ConsumedBytesInfo consume(File file) throws FileNotFoundException;
}
