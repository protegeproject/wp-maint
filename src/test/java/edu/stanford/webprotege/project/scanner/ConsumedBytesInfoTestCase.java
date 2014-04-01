package edu.stanford.webprotege.project.scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 01/04/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsumedBytesInfoTestCase {


    private static long CONSUMED = 5;

    private static long TOTAL = 7;

    @Mock
    protected File file;

    protected ConsumedBytesInfo info;

    @Before
    public void setUp() {
        info = new ConsumedBytesInfo(file, CONSUMED, TOTAL);
    }


    @Test
    public void getFileReturnsSuppliedFile() {
        assertThat(info.getFile(), is(equalTo(file)));
    }

    @Test
    public void getConsumedBytesReturnsSuppliedValue() {
        assertThat(info.getBytesConsumed(), is(equalTo(CONSUMED)));
    }

    @Test
    public void getTotalBytesReturnsSuppliedValue() {
        assertThat(info.getTotalBytes(), is(equalTo(TOTAL)));
    }

    @Test
    public void getMissingBytesReturnsCorrectValue() {
        assertThat(info.getMissingBytes(), is(equalTo(TOTAL - CONSUMED)));
    }

}
