package com.iteye.baowp.domain.repository;

import com.iteye.baowp.domain.entity.BookEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/7/13
 * Time: 2:14 PM
 */
public interface BookRepository {
    int insert(BookEntity book);

    List<BookEntity> list();

    int update(BookEntity book);
}
