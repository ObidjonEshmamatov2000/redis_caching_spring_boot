package com.epam.spring_redis_learn;

import com.epam.spring_redis_learn.entity.Product;
import com.epam.spring_redis_learn.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringRedisLearnApplication {

	private final ProductRepository repository;

	@Autowired
	public SpringRedisLearnApplication(ProductRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public Product save(@RequestBody Product product) {
		return repository.save(product);
	}

	@GetMapping
	public List<Product> getAll() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
	public Product get(@PathVariable int id) {
		return repository.findById(id);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "Product")
	public Long delete(@PathVariable int id) {
		return repository.delete(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisLearnApplication.class, args);
	}

}
