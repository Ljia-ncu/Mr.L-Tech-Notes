package com.mrl.openfeign.controller;

import com.mrl.openfeign.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ClientController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/14 22:31
 * @Version 1.0
 */
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<String> get(@RequestParam String tag) {
        //...doSth
        return clientService.getClient(tag);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> post(@PathVariable Integer id) {
        //...doSth
        return clientService.postClient(id);
    }
}
