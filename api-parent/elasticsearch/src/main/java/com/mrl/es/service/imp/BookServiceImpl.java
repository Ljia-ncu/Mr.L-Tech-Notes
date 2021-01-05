package com.mrl.es.service.imp;

import com.mrl.es.entity.Book;
import com.mrl.es.repository.BookRepository;
import com.mrl.es.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: BookServiceImpl
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 14:38
 * @Version 1.0
 */
@Service
public class BookServiceImpl implements BookService {
    /**
     * test data
     */
    private final ArrayBlockingQueue<Book> bookList = new ArrayBlockingQueue<Book>(10);

    {
        Book book1 = new Book().setBookId(new Random().nextLong()).setBookNo("es_001")
                .setBookName("ElasticSearch权威指南").setSubTitle("elasticsearch")
                .setKeywords("es,elasticsearch,搜索引擎")
                .setCategoryId(1).setCategoryName("IT")
                .setPrice(new BigDecimal("56.69")).setStock(200)
                .setStatus(1).setPublishDate(new Date());
        bookList.add(book1);
        Book book2 = new Book().setBookId(new Random().nextLong()).setBookNo("mysql_001")
                .setBookName("高性能MySQL").setSubTitle("mysql")
                .setKeywords("mysql,innodb,存储引擎")
                .setCategoryId(1).setCategoryName("IT")
                .setPrice(new BigDecimal("40.01")).setStock(56)
                .setStatus(1).setPublishDate(new Date());
        bookList.add(book2);
    }

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void delete(Long... ids) {
        for (Long id : ids) {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public Book create(Long id) {
        //select from db
        //save in es
        return null;
    }

    @Override
    public void importData() {
        Iterable<Book> iterable = bookRepository.saveAll(bookList);
        Iterator<Book> iterator = iterable.iterator();
        int success = 0;
        while (iterator.hasNext()) {
            success++;
            iterator.next();
        }
    }

    @Override
    public Page<Book> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return bookRepository.findByBookNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
