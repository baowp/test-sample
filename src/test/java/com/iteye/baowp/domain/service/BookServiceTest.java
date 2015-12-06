package com.iteye.baowp.domain.service;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.domain.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/27/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "BookServiceTest-context.xml")//

@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class BookServiceTest /*extends AbstractTransactionalJUnit4SpringContextTests*/ {

    @Resource
    private BookService bookService;

    @Test
    //@Rollback(false)
    public void testInsert() {
        BookEntity book = new BookEntity();
        book.setTitle("title");
        book.setPrice(1.1f);
        int flag = bookService.insert(book);
        assertTrue(flag == 1);
        assertNotNull(book.getId());
    }
}
