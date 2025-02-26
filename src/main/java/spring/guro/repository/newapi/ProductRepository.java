package spring.guro.repository.newapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(String category);
}
