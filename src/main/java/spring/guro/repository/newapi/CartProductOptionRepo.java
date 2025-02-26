package spring.guro.repository.newapi;

import spring.guro.entity.Cart;
import spring.guro.entity.CartProductOption;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductOptionRepo extends JpaRepository<CartProductOption, Long> {
    List<CartProductOption> findAllByCart(Cart cart);

    // 장바구니 ID로 장바구니 상품 옵션 삭제
    void deleteByCartIdIn(List<Long> cartIds);
}
