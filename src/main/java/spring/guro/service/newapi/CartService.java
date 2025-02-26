package spring.guro.service.newapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.guro.dto.newapi.req.CartAddReq;
import spring.guro.dto.newapi.req.CartToggleSelectedReq;
import spring.guro.dto.newapi.resp.CartProductResp;
import spring.guro.dto.newapi.resp.CartResp;
import spring.guro.dto.newapi.resp.OptionResp;
import spring.guro.entity.*;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.CartProductOptionRepo;
import spring.guro.repository.newapi.CartRepository;
import spring.guro.repository.newapi.OptionRepo;
import spring.guro.repository.newapi.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductOptionRepo cartProductOptionRepo;
    private final ProductRepository productRepository;
    private final OptionRepo optionRepo;
    private final BranchService branchService;
    private final AuthService authService;

    // 장바구니 아이템 추가
    @Transactional
    public void addCartItem(Member member, CartAddReq cartAddReq) {
        // 기존 장바구니가 있고 지점이 다르다면 기존 장바구니 삭제
        List<Cart> carts = cartRepository.findAllByMember(member);
        if (!carts.isEmpty()) {
            if (!carts.get(0).getBranch().getId().equals(cartAddReq.branchId())) {
                cartRepository.deleteAllByMember(member);
            } else {
                // 이미 장바구니에 같은 상품, 같은 옵션이 존재하는지 확인
                // 이미 존재한다면 수량만 변경
                if (processCartItemAddition(carts, member, cartAddReq)) {
                    return;
                }
            }
        }

        Product product = productRepository.findById(cartAddReq.productId()) // 장바구니에 넣을 상품
                .orElseThrow(() -> new CustomException(ErrorEnum.PRODUCT_NOT_FOUND));
        List<Option> options = optionRepo.findAllById(cartAddReq.optionIds()); // 선택한 옵션 리스트
        Branch branch = branchService.getBranchById(cartAddReq.branchId()); // 지점 정보

        int optionPrice = options.stream().mapToInt(Option::getPrice).sum(); // 선택한 옵션 가격 총합
        int subtotal = (product.getPrice() + optionPrice) * cartAddReq.quantity(); // 제품 총 가격

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .mount(cartAddReq.quantity())
                .price(subtotal)
                .branch(branch)
                .build();
        cartRepository.save(cart);

        for (Option option : options) {
            CartProductOption cartProductOption = CartProductOption.builder()
                    .cart(cart)
                    .option(option)
                    .build();
            cartProductOptionRepo.save(cartProductOption);
        }
        getCartItems(member);
    }

    // 이미 장바구니에 같은 상품, 같은 옵션이 존재하는지 확인하고 수량만 변경하는 메서드
    private boolean processCartItemAddition(List<Cart> carts, Member member, CartAddReq cartAddReq) {
        for (Cart cart : carts) {
            if (cart.getProduct().getId().equals(cartAddReq.productId())
                    && cartProductOptionRepo.findAllByCart(cart).stream()
                    .map(CartProductOption::getOption)
                    .map(Option::getId)
                    .collect(Collectors.toList())
                    .containsAll(cartAddReq.optionIds())) {
                cart.updateMount(cart.getMount() + cartAddReq.quantity());
                cartRepository.save(cart);
                getCartItems(member);
                return true;
            }    
        }
        return false;
    }

    // 선택한 장바구니 아이템 삭제
    @Transactional
    public void removeCartItem(List<Long> cartIds) {
        List<Cart> selectedCarts = cartRepository.findAllById(cartIds)
                .stream()
                .filter(Cart::isSelected)
                .toList();
        // 옵션 정보 먼저 삭제
        cartProductOptionRepo.deleteByCartIdIn(selectedCarts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList()));
        // 장바구니 아이템 삭제
        cartRepository.deleteAll(selectedCarts);
    }

    // 장바구니 아이템 수량 변경
    @Transactional
    public void updateCartItem(Long cartId, int quantity) {
        Member member = authService.getLoginMember();
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException(ErrorEnum.CART_EMPTY));

        if (!cart.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorEnum.NO_AUTHORITY);
        }

        int optionPrice = cartProductOptionRepo.findAllByCart(cart).stream()
                .mapToInt(cartProductOption -> cartProductOption.getOption().getPrice())
                .sum();

        int subtotal = (cart.getProduct().getPrice() + optionPrice) * quantity;

        cart.updateMount(quantity);
        cart.updatePrice(subtotal);

        cartRepository.save(cart);
    }

    // 옵션을 가져와 OptionResp DTO에 저장하고 반환하는 메서드
    private List<OptionResp> getCartProductOptions(Cart cart) {
        return cartProductOptionRepo.findAllByCart(cart).stream()
                .map(cartProductOption -> OptionResp.builder()
                        .id(cartProductOption.getOption().getId())
                        .name(cartProductOption.getOption().getName())
                        .price(cartProductOption.getOption().getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    // 장바구니에 담긴 제품 데이터를 CartProductResp DTO에 저장하고 반환하는 메서드
    private CartProductResp createCartProductResp(Cart cart) {
        int productPrice = cart.getProduct().getPrice();
        int optionPrice = cartProductOptionRepo.findAllByCart(cart).stream()
                .mapToInt(cartProductOption -> cartProductOption.getOption().getPrice())
                .sum();
        int subtotal = (productPrice + optionPrice) * cart.getMount();

        return CartProductResp.builder()
                .cartId(cart.getId())
                .productId(cart.getProduct().getId())
                .name(cart.getProduct().getName())
                .price(cart.getProduct().getPrice())
                .quantity(cart.getMount())
                .imageUrl(cart.getProduct().getImage())
                .options(getCartProductOptions(cart))
                .totalItemPrice(subtotal)
                .selected(cart.isSelected())
                .build();
    }

    // 장바구니 총 가격을 구하고, 장바구니에 담긴 제품 데이터 리스트와 함께 CartResp DTO에 저장하고 반환하는 메서드
    public CartResp getCartItems(Member member) {
        List<Cart> carts = cartRepository.findAllByMember(member);
        if(carts.isEmpty()) {
            throw new CustomException(ErrorEnum.CART_EMPTY);
        }
        return createCartResp(carts);
    }

    // 사용자가 선택한 장바구니 아이템들을 가져와 CartResp DTO에 저장하고 반환하는 메서드
    public CartResp getSelectedCartItems(Member member) {
        List<Cart> carts = cartRepository.findAllByMemberAndSelectedTrue(member);
        if(carts.isEmpty()) {
            throw new CustomException(ErrorEnum.CART_EMPTY);
        }
        return createCartResp(carts);
    }

    // 장바구니 목록으로부터 CartResp를 생성하는 private 메서드
    private CartResp createCartResp(List<Cart> carts) {
        List<CartProductResp> cartProductResps = new ArrayList<>();
        int totalPrice = 0;

        for (Cart cart : carts) {
            CartProductResp cartProductResp = createCartProductResp(cart);
            cartProductResps.add(cartProductResp);
            totalPrice += cartProductResp.totalItemPrice();
        }

        Branch branch = carts.get(0).getBranch();
        return CartResp.builder()
                .items(cartProductResps)
                .cartCount(cartProductResps.size())
                .totalPrice(totalPrice)
                .branchId(branch.getId())
                .branchName(branch.getName())
                .build();
    }

    /**
     * 사용자가 장바구니에서 특정 상품들의 선택 상태를 변경(토글)하는 메서드입니다.
     *
     * @param cartToggleSelectedReq 장바구니 상품 선택 상태 변경 요청 DTO.
     *                              선택/해제할 장바구니 상품 ID 목록을 포함합니다.
     * @param member                현재 로그인한 회원.
     */
    public void selectCartItems(CartToggleSelectedReq cartToggleSelectedReq, Member member) {
        List<Cart> carts = cartRepository.findAllByMember(member);
        if (carts.isEmpty()) {
            return;
        }
        for (Cart cart : carts) {
            if (cartToggleSelectedReq.carts().contains(cart.getId())) {
                cart.toggleSelected(true);

            } else {
                cart.toggleSelected(false);
            }
        }
        cartRepository.saveAll(carts);
    }
}