package com.mrl.es.service;

import com.mrl.es.entity.Book;
import org.springframework.data.domain.Page;

/**
 * @InterfaceName: BookService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 14:38
 * @Version 1.0
 */
public interface BookService {
    void delete(Long... id);

    Book create(Long id);

    void importData();

    Page<Book> search(String keyword, Integer pageNum, Integer pageSize);

}
