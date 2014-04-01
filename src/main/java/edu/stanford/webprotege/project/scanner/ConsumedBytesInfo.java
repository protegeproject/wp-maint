package edu.stanford.webprotege.project.scanner;

import com.google.common.base.Objects;

import java.io.File;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
public class ConsumedBytesInfo {

    private File file;

    private long bytesConsumed;

    private long totalBytes;

    public ConsumedBytesInfo(File file, long bytesConsumed, long totalBytes) {
        this.file = file;
        this.bytesConsumed = bytesConsumed;
        this.totalBytes = totalBytes;
    }

    public long getBytesConsumed() {
        return bytesConsumed;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public long getMissingBytes() {
        return totalBytes - bytesConsumed;
    }

    public boolean isEndOfFile() {
        return bytesConsumed == totalBytes;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("ConsumedBytesInfo")
                .add("file", file.getAbsolutePath())
                .add("consumed", bytesConsumed)
                .add("total", totalBytes)
                .toString();
    }
}
