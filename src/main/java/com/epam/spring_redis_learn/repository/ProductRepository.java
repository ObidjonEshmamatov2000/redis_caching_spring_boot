package com.epam.spring_redis_learn.repository;

import com.epam.spring_redis_learn.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final RedisTemplate template;
    private static final String HASH_KEY = "Product";

    @Autowired
    public ProductRepository(RedisTemplate template) {
        this.template = template;
    }

    public Product save(Product product) {

        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findById(int id) {
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public Long delete(int id) {
        return template.opsForHash().delete(HASH_KEY, id);
    }
}
