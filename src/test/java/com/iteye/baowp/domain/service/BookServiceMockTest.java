package com.iteye.baowp.domain.service;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.domain.repository.BookRepository;
import com.iteye.baowp.domain.repository.impl.BookRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/7/13
 * Time: 10:26 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "BookServiceTest-context.xml")
public class BookServiceMockTest {
    @Resource
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    /**
     *
     */
    @Before
    public void mockitoInitialize() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsert() {
        BookEntity book = new BookEntity();
        book.setTitle("title");
        book.setPrice(1.1f);

        {
            when(bookRepository.insert(book)).thenReturn(2);
        }

        int flag = bookService.insert(book);
        assertEquals(2, flag);

        flag = bookRepository.insert(book);
        assertEquals(2, flag);

        verify(bookRepository, times(2)).insert(book);
    }

    @Test
    public void test() {
        List<Integer> list = mock(List.class);
        when(list.get(1)).thenReturn(2);
        int i = list.get(1);
        assertEquals(2, i);
    }
}
