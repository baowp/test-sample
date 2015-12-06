package com.iteye.baowp.mockito;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 2/6/14
 * Time: 11:12 AM
 */
public class MockitoSampleTest {


    @Test
    public void testMock() {
        List list = mock(List.class);
        {
            when(list.get(1)).thenReturn("a1");
            when(list.get(2)).thenReturn("a2");
        }
        assertEquals("a1", list.get(1));
        assertEquals("a2", list.get(2));
        assertNull(list.get(3));

        verify(list).get(1);
        list.get(2);
        verify(list, times(2)).get(2);
        verify(list, never()).get(4);
    }

    @Test
    public void testSpy() {
        List list = new ArrayList();
        list.add("a0");
        list.add("a1");
        list.add("a2");
        list = spy(list);
        {
            when(list.get(0)).thenReturn("mock-a0");
            when(list.get(2)).thenReturn("a2");
        }
        assertEquals("mock-a0", list.get(0));
        assertEquals("a1", list.get(1));
        verify(list).get(1);
    }
}
