package spring.guro.controller.newapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import spring.guro.dto.newapi.req.CartAddReq;
import spring.guro.dto.newapi.req.CartRemoveReq;
import spring.guro.dto.newapi.req.CartToggleSelectedReq;
import spring.guro.dto.newapi.resp.CartResp;
import spring.guro.entity.Member;
import spring.guro.service.newapi.AuthService;
import spring.guro.service.newapi.CartService;

@RestController
@RequestMapping("/api/carts")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AuthService authService;

    /**
     * 회원의 모든 장바구니 목록 조회
     */
    @GetMapping
    public ResponseEntity<CartResp> getCartItems() {
        Member member = authService.getLoginMember();
        CartResp cartResp = cartService.getCartItems(member);
        return ResponseEntity.ok(cartResp);
    }

    /**
     * 선택된 장바구니 목록 조회
     */
    @GetMapping("/selected")
    public ResponseEntity<CartResp> getSelectedCartItems() {
        Member member = authService.getLoginMember();
        CartResp cartResp = cartService.getSelectedCartItems(member);
        return ResponseEntity.ok(cartResp);
    }

    /**
     * 장바구니 아이템 추가
     */
    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody @Valid CartAddReq cartAddReq) {
        Member member = authService.getLoginMember();
        cartService.addCartItem(member, cartAddReq);
        return ResponseEntity.ok().build();
    }

    /**
     * 선택한 장바구니 아이템 제거
     */
    @DeleteMapping
    public ResponseEntity<Void> removeCartItems(@RequestBody @Valid CartRemoveReq cartRemoveReq) {
        cartService.removeCartItem(cartRemoveReq.cartIds());
        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 아이템 수량 변경
     */
    @PatchMapping("/{cartId}/quantity/{quantity}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long cartId, @PathVariable int quantity) {
        cartService.updateCartItem(cartId, quantity);
        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 아이템 선택 상태 변경
     */
    @PatchMapping("/select")
    public ResponseEntity<Void> selectCartItems(@RequestBody @Valid CartToggleSelectedReq cartToggleSelectedReq) {
        Member member = authService.getLoginMember();
        cartService.selectCartItems(cartToggleSelectedReq, member);
        return ResponseEntity.ok().build();
    }
}