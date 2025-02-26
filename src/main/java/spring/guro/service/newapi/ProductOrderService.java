package spring.guro.service.newapi;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.guro.dto.newapi.req.ProductOrderReq;
import spring.guro.dto.newapi.resp.ProductOrderCompleteResp;
import spring.guro.dto.newapi.resp.ProductOrderDetailResp;
import spring.guro.dto.newapi.resp.ProductOrderHistoryResp;
import spring.guro.entity.*;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.*;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderDetailRepository productOrderDetailRepository;
    private final ProductOrderDetailOptionRepo productOrderDetailOptionRepo;
    private final CouponRepo couponRepo;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartProductOptionRepo cartProductOptionRepo;
    private final MembershipService membershipService;
    private final AuthService authService;
    private final ProductOrderDetailService productOrderDetailService;
    private final RecipeRepository recipeRepository;
    private final BranchInventoryRepository branchInventoryRepository;

    /**
     * 상품 주문을 처리하는 메인 메서드
     * 1. 장바구니 검증
     * 2. 가격 계산
     * 3. 할인 처리
     * 4. 주문 생성
     * 5. 주문 후 처리 (포인트, 쿠폰, 장바구니, 재고)
     *
     * @param productOrderReq 주문 요청 정보 (쿠폰, 포인트, 결제수단 등)
     * @return 주문 완료 응답 (주문ID, 매장명)
     */
    @Transactional
    public ProductOrderCompleteResp productOrder(ProductOrderReq productOrderReq) {
        Member member = authService.getLoginMember();
        List<Cart> carts = validateAndGetSelectedCarts(member);
        Branch branch = carts.get(0).getBranch();

        OrderPriceInfo priceInfo = calculateOrderPrice(carts);
        DiscountInfo discountInfo = processDiscounts(member, productOrderReq, priceInfo.totalPrice());

        ProductOrder productOrder = createProductOrder(member, branch, priceInfo, discountInfo, productOrderReq);
        saveOrderDetails(productOrder, carts);
        
        processAfterOrder(member, productOrder, discountInfo);
        
        return ProductOrderCompleteResp.builder()
            .id(productOrder.getId())
            .branchName(branch.getName())
            .totalPrice(priceInfo.totalPrice())
            .billedPrice(priceInfo.billedAmount())
            .build();
    }

    /**
     * 회원의 선택된 장바구니 항목들을 검증하고 조회
     * 장바구니가 비어있는 경우 예외 발생
     *
     * @param member 주문하는 회원
     * @return 선택된 장바구니 목록
     * @throws CustomException 장바구니가 비어있는 경우
     */
    private List<Cart> validateAndGetSelectedCarts(Member member) {
        List<Cart> carts = cartRepository.findAllByMemberAndSelectedTrue(member);
        if (carts.isEmpty()) {
            throw new CustomException(ErrorEnum.CART_EMPTY);
        }
        return carts;
    }

    /**
     * 장바구니 항목들의 총 주문 금액 계산
     *
     * @param carts 장바구니 항목 목록
     * @return 주문 가격 정보 (총 금액, 청구 금액)
     */
    private OrderPriceInfo calculateOrderPrice(List<Cart> carts) {
        int totalPrice = (int) carts.stream().mapToDouble(Cart::getPrice).sum();
        return new OrderPriceInfo(totalPrice, totalPrice);
    }

    /**
     * 쿠폰과 포인트를 적용한 할인 처리
     * 1. 쿠폰 유효성 검증
     * 2. 포인트 사용 가능 여부 확인
     * 3. 총 할인액이 주문 금액을 초과하지 않는지 검증
     *
     * @param member 주문하는 회원
     * @param productOrderReq 주문 요청 정보
     * @param totalPrice 총 주문 금액
     * @return 할인 정보 (사용된 쿠폰, 사용 포인트, 총 할인액)
     * @throws CustomException 쿠폰이 없거나, 포인트가 부족하거나, 할인액이 부적절한 경우
     */
    private DiscountInfo processDiscounts(Member member, ProductOrderReq productOrderReq, int totalPrice) {
        Coupon coupon = null;
        int usedPoint = 0;

        if (productOrderReq.couponId() != null) {
            coupon = couponRepo.findByIdAndMemberAndProductOrderIsNull(productOrderReq.couponId(), member)
                    .orElseThrow(() -> new CustomException(ErrorEnum.COUPON_NOT_FOUND));
        }

        if (productOrderReq.point() != null) {
            usedPoint = productOrderReq.point();
            if (usedPoint > member.getPoint()) {
                throw new CustomException(ErrorEnum.POINT_BALANCE_INSUFFICIENT);
            }
        }

        int totalDiscount = usedPoint + (coupon != null ? coupon.getProduct().getPrice() : 0);
        if (totalDiscount > totalPrice) {
            throw new CustomException(ErrorEnum.INVALID_DISCOUNT);
        }

        return new DiscountInfo(coupon, usedPoint, totalDiscount);
    }

    /**
     * 주문 엔티티 생성 및 저장
     * 할인이 적용된 최종 결제 금액 계산
     *
     * @param member 주문하는 회원
     * @param branch 주문 매장
     * @param priceInfo 주문 가격 정보
     * @param discountInfo 할인 정보
     * @param productOrderReq 주문 요청 정보
     * @return 저장된 주문 엔티티
     */
    private ProductOrder createProductOrder(Member member, Branch branch, OrderPriceInfo priceInfo, 
            DiscountInfo discountInfo, ProductOrderReq productOrderReq) {
        int billedAmount = Math.max(priceInfo.totalPrice() - discountInfo.totalDiscount(), 0);
        
        ProductOrder productOrder = ProductOrder.builder()
                .totalPrice(priceInfo.totalPrice())
                .billedAmount(billedAmount)
                .paymentMethod(productOrderReq.paymentMethod())
                .branch(branch)
                .member(member)
                .coupon(discountInfo.coupon())
                .build();
                
        return productOrderRepository.save(productOrder);
    }

    /**
     * 주문 상세 정보 저장
     * 장바구니의 각 항목을 주문 상세로 변환하여 저장
     *
     * @param productOrder 생성된 주문
     * @param carts 장바구니 항목 목록
     */
    private void saveOrderDetails(ProductOrder productOrder, List<Cart> carts) {
        carts.forEach(cart -> {
            ProductOrderDetail detail = ProductOrderDetail.builder()
                    .quantity(cart.getMount())
                    .subtotal((int) cart.getPrice())
                    .product(cart.getProduct())
                    .productOrder(productOrder)
                    .build();
            productOrderDetailRepository.save(detail);

            saveOrderOptions(detail, cart);
        });
    }

    /**
     * 주문 상세의 옵션 정보 저장
     * 장바구니 상품의 옵션들을 주문 상세 옵션으로 변환하여 저장
     *
     * @param detail 주문 상세
     * @param cart 장바구니 항목
     */
    private void saveOrderOptions(ProductOrderDetail detail, Cart cart) {
        List<CartProductOption> cartOptions = cartProductOptionRepo.findAllByCart(cart);
        cartOptions.forEach(cartOption -> {
            ProductOrderDetailOption orderOption = ProductOrderDetailOption.builder()
                    .productOrderDetail(detail)
                    .option(cartOption.getOption())
                    .build();
            productOrderDetailOptionRepo.save(orderOption);
        });
    }

    /**
     * 주문 완료 후 처리 작업
     * 1. 장바구니 비우기
     * 2. 쿠폰 사용 처리
     * 3. 포인트 사용 및 적립
     * 4. 스탬프 적립 및 쿠폰 발급
     * 5. 회원 통계 업데이트
     *
     * @param member 주문한 회원
     * @param order 생성된 주문
     * @param discountInfo 할인 정보
     */
    private void processAfterOrder(Member member, ProductOrder order, DiscountInfo discountInfo) {
        // 재고 차감
        order.getProductOrderDetails().forEach(detail -> {
            Recipe recipe = recipeRepository.findByProduct(detail.getProduct())
                    .orElseThrow(() -> new CustomException(ErrorEnum.RECIPE_NOT_FOUND));
            BranchInventory branchInventory = branchInventoryRepository.findByBranchAndIngredient(order.getBranch(), recipe.getIngredient())
                    .orElseThrow(() -> new CustomException(ErrorEnum.BRANCH_NOT_FOUND));
            branchInventory.decreaseStock(detail.getQuantity() * recipe.getQuantity());
            branchInventoryRepository.save(branchInventory);
        });

        // 장바구니 비우기
        cartRepository.deleteAllByMember(member);

        // 쿠폰 사용 처리
        if (discountInfo.coupon() != null) {
            discountInfo.coupon().used(order);
            couponRepo.save(discountInfo.coupon());
        }

        // 포인트 사용 및 적립
        if (discountInfo.usedPoint() > 0) {
            member.usePoint(discountInfo.usedPoint());
        }
        member.addPoint(order);

        // 스탬프 적립 처리
        if (member.increaseStamp()) {
            Coupon newCoupon = Coupon.newCoupon(member);
            couponRepo.save(newCoupon);
        }

        // 회원 통계 업데이트
        member.increaseTotalPaymentAmount(order.getBilledAmount());
        member.increaseTotalPurchaseCount();
        membershipService.upgradeMembership(member);
        memberRepository.save(member);
    }

    /**
     * 특정 회원이 특정 지점에서 주문한 총 횟수 조회
     */
    public int getProductOrderCount(Branch branch) {
        Member member = authService.getLoginMember();
        return productOrderRepository.countByMemberAndBranch(member, branch);
    }

    /**
     * 주문 내역 조회
     * 특정 회원의 특정 기간 사이의 모든 주문 조회(페이지네이션 적용)
     *
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @param pageable 페이지네이션 정보
     * @return 주문 내역 응답 (주문 목록, 총 페이지 수)
     */
    public ProductOrderHistoryResp getOrderDetails(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Member member = authService.getLoginMember();
        Page<ProductOrder> productOrders = fetchOrdersForDateRange(member, startDate, endDate, pageable);
        validateOrdersExist(productOrders);
        
        List<ProductOrderDetailResp> orders = productOrders.getContent().stream()
            .map(order -> productOrderDetailService.mapToOrderDetailResponse(order))
            .toList();

        return ProductOrderHistoryResp.builder()
                .orders(orders)
                .totalPage(productOrders.getTotalPages())
                .build();
    }

    /**
     * 특정 회원의 특정 기간 사이의 모든 주문 조회(페이지네이션 적용)
     *
     * @param member 조회할 회원
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @param pageable 페이지네이션 정보
     * @return 주문 목록 (페이지네이션 적용)
     */
    private Page<ProductOrder> fetchOrdersForDateRange(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return productOrderRepository.findAllByMemberAndDateBetween(
                member,
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay().minusNanos(1),
                pageable);
    }

    /**
     * 주문 내역이 존재하는지 확인
     *
     * @param productOrders 조회된 주문 목록
     * @throws CustomException 주문 내역이 없는 경우
     */
    private void validateOrdersExist(Page<ProductOrder> productOrders) {
        if (productOrders.isEmpty()) {
            throw new CustomException(ErrorEnum.ORDER_HISTORY_NOT_FOUND);
        }
    }

    
}

// 주문 가격 정보
record OrderPriceInfo(int totalPrice, int billedAmount) {}
// 할인 정보
record DiscountInfo(Coupon coupon, int usedPoint, int totalDiscount) {}
