package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Product;
import spring.guro.entity.ProductOption;

import java.util.List;

public interface ProductOptionRepo extends JpaRepository<ProductOption, Long> {
    // 특정 제품에 연결된 옵션 리스트 조회
    List<ProductOption> findByProduct(Product product);
}
