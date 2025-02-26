package spring.guro.repository.newapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Branch;
import spring.guro.entity.Product;
import spring.guro.entity.ProductUnavailable;

public interface ProductUnavailableRepo extends JpaRepository<ProductUnavailable, Long> {
    Optional<ProductUnavailable> findByProductAndBranch(Product product, Branch branch);
}
