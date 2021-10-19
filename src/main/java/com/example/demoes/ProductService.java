package com.example.demoes;

import com.example.demoes.dao.Product;
import com.example.demoes.dao.ProductRepository;
import com.example.demoes.dao.RequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Slf4j
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ElasticsearchOperations esOps;
    private static final String PRODUCT_INDEX  = "productindex";


    public void saveAll(List<Product> list){
        esOps.save(list);
    }

    public List<Product> findByParameters(RequestEntity request){
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

       BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
               .should(matchQuery("name", request.getName()).boost(3).fuzziness(1))
               .should(matchQuery("location", request.getLocation()).fuzziness(1))
               .should(matchQuery("category", request.getCategory()).fuzziness(1))
               .should(regexpQuery("description", ".*"+request.getDescription()+".*" ));

       Query query = new NativeSearchQueryBuilder()
               .withQuery(boolQueryBuilder)
               .withPageable(PageRequest.of(0,3))
               .build();

        SearchHits<Product> searchHits = esOps
                .search(query, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        List<Product> productList = new ArrayList<>();
        for(SearchHit<Product> hit: searchHits) productList.add(hit.getContent());
        return productList;
    }


}


