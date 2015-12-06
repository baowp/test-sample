package com.iteye.baowp.domain.service;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.iteye.baowp.domain.entity.BookEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/2/14
 * Time: 6:11 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "BookServiceTest-context.xml")
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
public class DBPreparedTest {

    @Resource
    private BookService bookService;

    @Test
    @DatabaseSetup("book.xml")
    public void testList() {
        List<BookEntity> list = bookService.list();
        BookEntity book = list.get(0);
        assertEquals(1, list.size());
        assertEquals(23.3, book.getPrice(), 0.000001);
    }

    @Test
    @DatabaseSetup("book.xml")
    @ExpectedDatabase("bookExpected.xml")
    public void testUpdate() {
        List<BookEntity> list = bookService.list();
        BookEntity book = list.get(0);
        book.setTitle("mybatis");
        book.setPrice(41f);
        bookService.update(book);
    }
}
