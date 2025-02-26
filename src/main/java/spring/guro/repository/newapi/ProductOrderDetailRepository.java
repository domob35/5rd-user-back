package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.ProductOrderDetail;

import java.util.List;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail, Long> {
    // 특정 주문의 모든 주문 상세 조회
    List<ProductOrderDetail> findAllByProductOrderId(Long productOrderId);
}
