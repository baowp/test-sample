/**
 * Project: billing.biz
 * 
 * File Created at Jun 24, 2013
 * $Id$Corporation
 * 
 * Copyright 2013-2015 Colomob.com Corporation Limited.
 * All rights reserved.
 */
package com.iteye.baowp.domain.persistence;


import com.iteye.baowp.domain.entity.BookEntity;

import java.util.List;

/**
 * @author baowp
 * 
 */
public interface BookMapper {

	int insert(BookEntity book);

	List<BookEntity> list();

    int update(BookEntity book);
}
