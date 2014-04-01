package edu.stanford.webprotege.project.scanner;

import com.google.common.io.CountingInputStream;
import org.semanticweb.binaryowl.BinaryOWLChangeLogHandler;
import org.semanticweb.binaryowl.BinaryOWLOntologyChangeLog;
import org.semanticweb.binaryowl.change.OntologyChangeRecordList;
import org.semanticweb.binaryowl.chunk.SkipSetting;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.io.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class BinaryOWLOntologyChangeLogConsumer implements FileConsumer {

    public ConsumedBytesInfo consume(File changeLog) throws FileNotFoundException {
        BinaryOWLOntologyChangeLog log = new BinaryOWLOntologyChangeLog();
        FileInputStream fileInputStream = new FileInputStream(changeLog);
        long fileLength = changeLog.length();
        final CountingInputStream countingInputStream = new CountingInputStream(new BufferedInputStream(fileInputStream));
        CountingChangeHandler handler = new CountingChangeHandler(countingInputStream);
        try {
            log.readChanges(countingInputStream, new OWLDataFactoryImpl(), handler);
        } catch (IOException e) {
            // Expected
        }
        return new ConsumedBytesInfo(changeLog, handler.getLastPosition(), fileLength);

    }


    private static class CountingChangeHandler implements BinaryOWLChangeLogHandler {

        private CountingInputStream countingInputStream;

        private long lastPosition;

        public CountingChangeHandler(CountingInputStream countingInputStream) {
            this.countingInputStream = countingInputStream;
            this.lastPosition = countingInputStream.getCount();
        }

        public long getLastPosition() {
            return lastPosition;
        }

        @Override
        public void handleChangesRead(OntologyChangeRecordList list, SkipSetting skipSetting, long filePosition) {
            lastPosition = countingInputStream.getCount();
        }
    }
}
