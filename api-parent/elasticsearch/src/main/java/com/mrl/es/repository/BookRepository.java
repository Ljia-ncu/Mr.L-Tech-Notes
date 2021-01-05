package com.mrl.es.repository;

import com.mrl.es.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @InterfaceName: BookService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 14:23
 * @Version 1.0
 */
public interface BookRepository extends ElasticsearchRepository<Book, Long> {
    /**
     * @param bookName
     * @param subtitle
     * @param keywords
     * @param page
     * @return
     */
    Page<Book> findByBookNameOrSubTitleOrKeywords(String bookName, String subtitle, String keywords, Pageable page);
}
