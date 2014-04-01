package edu.stanford.webprotege.project.scanner;

import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class FileRecoveryTool {

    private PrintStream out;

    public FileRecoveryTool(PrintStream logger) {
        out = logger;
    }

    public Optional<File> recoverFileIfNecessary(String name, File file, FileConsumer consumer) throws FileNotFoundException {
        return recoverFileIfNecessary(name, file, consumer, new DefaultFileRecoveryStrategy());
    }

    public Optional<File> recoverFileIfNecessary(String name, File file, FileConsumer consumer, FileRecoveryStrategy recoveryStrategy) throws FileNotFoundException {
        out.printf("Attempting to read %s\n", name);
        ConsumedBytesInfo info = consumer.consume(file);
        if (info.isEndOfFile()) {
            out.printf("\tFile is present and correct\n");
            return Optional.absent();
        }

        out.printf("\tCould not read whole file: %s.\n", info);
        out.printf("\tMissing %d  bytes.\n", info.getMissingBytes());
        out.printf("\tAttempting a recovery...\n");
        try {
            File recoveredFile = recoverFile(info, recoveryStrategy);
            out.printf("\t\tRecovered file to %s.\n", recoveredFile);
            out.printf("\t\tVerifying recovered file...\n");
            ConsumedBytesInfo recoveredFileInfo = consumer.consume(recoveredFile);
            if (recoveredFileInfo.isEndOfFile()) {
                out.printf("\t\t\tVerified OK\n");
            } else {
                out.printf("\t\t\tVerification failed: %s.\n", recoveredFileInfo);
            }
            return Optional.of(recoveredFile);
        } catch (IOException e) {
            out.printf("\t\tThere was an error whilst attempting to recover the file: %s.\n", e.getMessage());
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
