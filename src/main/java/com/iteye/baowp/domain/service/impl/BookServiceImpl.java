package com.iteye.baowp.domain.service.impl;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.domain.reference.RemoteService;
import com.iteye.baowp.domain.repository.BookRepository;
import com.iteye.baowp.domain.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/27/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Resource
    private BookRepository bookRepository;
    //@Resource
    private RemoteService remoteService;


    //@Transactional
    public int insert(BookEntity book) {
        return bookRepository.insert(book);
    }


    public List<BookEntity> list() {
        List<BookEntity> list = bookRepository.list();
        if (list.isEmpty()) {
            list = remoteService.remoteBooks();
        }
        return list;
    }

    @Override
    public int update(BookEntity book) {
        return bookRepository.update(book);
    }
}
