package spring.guro.controller.newapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.guro.dto.newapi.req.ProductOrderReq;
import spring.guro.dto.newapi.resp.ProductOrderCompleteResp;
import spring.guro.dto.newapi.resp.ProductOrderHistoryResp;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
// import spring.guro.entity.Branch;
// import spring.guro.service.newapi.BranchService;
import spring.guro.service.newapi.ProductOrderService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class ProductOrderController {
    private final ProductOrderService productOrderService;
    // private final BranchService branchService;
    // private final AuthService authService;

    /**
     * 주문하기 (주문 완료 응답 리턴)
     */
    @PostMapping
    public ResponseEntity<ProductOrderCompleteResp> directOrder(
            @RequestBody ProductOrderReq productOrderReq) {
        ProductOrderCompleteResp productOrderCompleteResp = productOrderService.productOrder(productOrderReq);
        return ResponseEntity.ok(productOrderCompleteResp);
    }

    /**
     * 주문 내역 조회
     */
    @GetMapping("/history")
    public ResponseEntity<ProductOrderHistoryResp> getOrderHistory(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(sort = "date", direction = Direction.DESC) Pageable pageable) {
        if (startDate == null) {
            // 기본 7일전부터 조회
            startDate = LocalDate.now().minusDays(7);
        }
        // 오늘 날짜까지 조회
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        // 시작일이 종료일보다 미래인 경우
        if (startDate.isAfter(endDate)) {
            throw new CustomException(ErrorEnum.INVALID_DATE_RANGE);
        }
        // 종료일이 오늘 이후인 경우
        if (endDate.isAfter(LocalDate.now())) {
            throw new CustomException(ErrorEnum.INVALID_DATE_RANGE);
        }
        return ResponseEntity.ok(productOrderService.getOrderDetails(startDate, endDate, pageable));
    }

    // /**
    // * 로그인한 회원의 특정 지점에서의 총 주문 횟수 조회
    // */
    // @GetMapping("/count")
    // public ResponseEntity<Integer> getOrderCount(@RequestParam("branchId") Long
    // branchId) {
    // Branch branch = branchService.getBranchById(branchId);
    // int orderCount = productOrderService.getProductOrderCount(branch);
    // return ResponseEntity.ok(orderCount);
    // }
}
