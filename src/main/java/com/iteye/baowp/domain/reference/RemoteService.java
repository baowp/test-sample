package com.iteye.baowp.domain.reference;

import com.iteye.baowp.domain.entity.BookEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/11/13
 * Time: 5:47 PM
 */
public interface RemoteService {

    List<BookEntity> remoteBooks();
}
