package spring.guro.controller.newapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.ProductsByCategoryReq;
import spring.guro.dto.newapi.resp.OptionResp;
import spring.guro.service.newapi.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    /**
     * 상품 목록을 조회하는 API
     * 
     * @param req 지점 ID와 카테고리 정보를 담은 요청 객체
     *           - branchId: 지점 ID 
     *           - category: 상품 카테고리 (기본값: "Cold Cloud")
     * @return 해당 카테고리의 상품 목록을 담은 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<?> getProductsByCategory(@Valid @ModelAttribute ProductsByCategoryReq req) {
        String category = req.category();
        if (category == null) {
            category = "Cold Cloud";
        }
        return ResponseEntity.ok(productService.getProductsByCategory(req.branchId(), category));
    }

    /**
     * 상품의 옵션 목록을 조회하는 API
     */
    @GetMapping("/{productId}/options")
    public ResponseEntity<List<OptionResp>> getProductOptions(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.getProductOptions(productId));
    }
}
