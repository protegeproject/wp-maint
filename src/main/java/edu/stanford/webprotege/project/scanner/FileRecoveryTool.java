package edu.stanford.webprotege.project.scanner;

import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class FileRecoveryTool {

    public Optional<File> recoverFileIfNecessary(String name, File file, FileConsumer consumer) throws FileNotFoundException {
        return recoverFileIfNecessary(name, file, consumer, new DefaultFileRecoveryStrategy());
    }

    public Optional<File> recoverFileIfNecessary(String name, File file, FileConsumer consumer, FileRecoveryStrategy recoveryStrategy) throws FileNotFoundException {
        System.out.println("Attempting to read " + name);
        ConsumedBytesInfo info = consumer.consume(file);
        if (info.isEndOfFile()) {
            System.out.println("\tFile is present and correct");
            return Optional.absent();
        }

        System.out.println("\tCould not read whole file: " + info);
        System.out.println("\tMissing " + info.getMissingBytes() + " bytes.");
        System.out.println("\tAttempting a recovery...");
        try {
            File recoveredFile = recoverFile(info, recoveryStrategy);
            System.out.println("\t\tRecovered file to " + recoveredFile);
            System.out.println("\t\tVerifying recovered file...");
            ConsumedBytesInfo recoveredFileInfo = consumer.consume(recoveredFile);
            if (recoveredFileInfo.isEndOfFile()) {
                System.out.println("\t\t\tVerified OK");
            } else {
                System.out.println("\t\t\tVerification failed: " + recoveredFileInfo);
            }
            return Optional.of(recoveredFile);
        } catch (IOException e) {
            System.out.println("\t\tThere was an error whilst attempting to recover the file: " + e.getMessage());
            return Optional.absent();
        }

    }

    private static File recoverFile(ConsumedBytesInfo info, FileRecoveryStrategy recoveryStrategy) throws IOException {
        File file = info.getFile();
        byte[] bytes = FileUtils.readFileToByteArray(file);
        byte[] recoveredBytes = new byte[(int) info.getBytesConsumed()];
        System.arraycopy(bytes, 0, recoveredBytes, 0, (int) info.getBytesConsumed());
        File recoveredFile = recoveryStrategy.getRecoveryFile(info.getFile());
        FileUtils.writeByteArrayToFile(recoveredFile, recoveredBytes);
        return recoveredFile;
    }
}