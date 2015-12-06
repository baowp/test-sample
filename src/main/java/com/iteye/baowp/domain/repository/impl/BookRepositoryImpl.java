package com.iteye.baowp.domain.repository.impl;

import com.iteye.baowp.domain.entity.BookEntity;
import com.iteye.baowp.domain.persistence.BookMapper;
import com.iteye.baowp.domain.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/7/13
 * Time: 2:15 PM
 */
@Repository
public class BookRepositoryImpl implements BookRepository {

    @Resource
    private BookMapper bookMapper;

    @Override
    public int insert(BookEntity book) {
        return bookMapper.insert(book);
    }

    @Override
    public List<BookEntity> list() {
        return bookMapper.list();
    }

    @Override
    public int update(BookEntity book) {
        return bookMapper.update(book);
    }
}
