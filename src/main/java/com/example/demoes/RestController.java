package com.example.demoes;

import com.example.demoes.dao.Product;
import com.example.demoes.dao.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    ProductService productService;

    @PostMapping("save/all")
    public String saveAll(@RequestBody List<Product> list){
        productService.saveAll(list);
        return list.size() + " products were saved!";
    }

    @PostMapping("/search")
    public List<Product> searchProducts(@RequestBody RequestEntity request){
        return productService.findByParameters(request);
    }

}


