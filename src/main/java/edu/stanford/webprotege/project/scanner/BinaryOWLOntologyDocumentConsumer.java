package edu.stanford.webprotege.project.scanner;

import com.google.common.io.CountingInputStream;
import org.semanticweb.binaryowl.BinaryOWLMetadata;
import org.semanticweb.binaryowl.BinaryOWLOntologyDocumentHandler;
import org.semanticweb.binaryowl.BinaryOWLOntologyDocumentPreamble;
import org.semanticweb.binaryowl.BinaryOWLOntologyDocumentSerializer;
import org.semanticweb.binaryowl.change.OntologyChangeDataList;
import org.semanticweb.owlapi.model.*;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.io.*;
import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class BinaryOWLOntologyDocumentConsumer implements FileConsumer {

    public ConsumedBytesInfo consume(File inputFile) throws FileNotFoundException {
        BinaryOWLOntologyDocumentSerializer serializer = new BinaryOWLOntologyDocumentSerializer();
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        long inputFileLength = inputFile.length();
        final CountingInputStream countingInputStream = new CountingInputStream(new BufferedInputStream(fileInputStream));
        CountingDocumentHandler handler = new CountingDocumentHandler(countingInputStream);
        try {
            serializer.read(countingInputStream, handler, new OWLDataFactoryImpl());
        } catch (Exception e) {
            // Expected
        }
        return new ConsumedBytesInfo(inputFile, handler.getLastPosition(), inputFileLength);
    }


    private static class CountingDocumentHandler implements BinaryOWLOntologyDocumentHandler<RuntimeException> {

        private CountingInputStream countingInputStream;

        private long lastPosition;

        private CountingDocumentHandler(CountingInputStream countingInputStream) {
            this.countingInputStream = countingInputStream;
            lastPosition = countingInputStream.getCount();
        }

        public long getLastPosition() {
            return lastPosition;
        }

        private void logLastPosition() {
            lastPosition = countingInputStream.getCount();
        }

        @Override
        public void handleBeginDocument() throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleEndDocument() throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleBeginInitialDocumentBlock() throws RuntimeException {

        }

        @Override
        public void handleEndInitialDocumentBlock() throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleBeginDocumentChangesBlock() throws RuntimeException {

        }

        @Override
        public void handleEndDocumentChangesBlock() throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handlePreamble(BinaryOWLOntologyDocumentPreamble preamble) throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleDocumentMetaData(BinaryOWLMetadata metadata) throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleOntologyID(OWLOntologyID ontologyID) throws RuntimeException {

        }

        @Override
        public void handleImportsDeclarations(Set<OWLImportsDeclaration> importsDeclarations) throws RuntimeException {

        }

        @Override
        public void handleOntologyAnnotations(Set<OWLAnnotation> annotations) throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleAxioms(Set<OWLAxiom> axioms) throws RuntimeException {
            logLastPosition();
        }

        @Override
        public void handleChanges(OntologyChangeDataList changesList) {
            logLastPosition();
        }
    }

}
