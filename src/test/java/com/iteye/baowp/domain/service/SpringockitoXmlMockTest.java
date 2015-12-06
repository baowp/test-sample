package com.iteye.baowp.domain.service;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.domain.reference.RemoteService;
import com.iteye.baowp.domain.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/7/13
 * Time: 10:26 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"BookServiceTest-context.xml", "spring-mockito.xml"})
public class SpringockitoXmlMockTest {

    @Resource
    private BookService bookService;

    @ReplaceWithMock
    @Resource
    private RemoteService remoteService;

    @ReplaceWithMock
    @Resource
    private BookRepository bookRepository;

    @Test
    public void testList() {
        BookEntity book = new BookEntity();
        book.setTitle("title");
        book.setPrice(1.1f);
        List<BookEntity> books = new ArrayList<BookEntity>();
        books.add(book);

        {
            when(bookRepository.list()).thenReturn(Collections.<BookEntity>emptyList());
            when(remoteService.remoteBooks()).thenReturn(books);
        }

        List<BookEntity> list = bookService.list();
        assertEquals(1, list.size());
        assertSame(book, list.get(0));

        verify(bookRepository).list();
        verify(remoteService).remoteBooks();
    }
}
