package com.mrl.es.service;

import com.mrl.es.pojo.SearchParam;
import com.mrl.es.pojo.SearchResponseVO;

import java.io.IOException;

/**
 * @InterfaceName: AggService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/6 18:39
 * @Version 1.0
 */
public interface SearchService {

    SearchResponseVO search(SearchParam searchParam) throws IOException;

}
