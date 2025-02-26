package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;

import spring.guro.dto.newapi.resp.ProductOrderDetailResp;
import spring.guro.entity.ProductOrder;
import spring.guro.entity.ProductOrderDetail;

@Service
public class ProductOrderDetailService {
    // 주문 내역을 응답 객체로 변환
    public ProductOrderDetailResp mapToOrderDetailResponse(ProductOrder productOrder) {
        List<ProductOrderDetail> productOrderDetails = productOrder.getProductOrderDetails();
        String firstProductName = productOrderDetails.get(0).getProduct().getName();
        int totalOrderCount = productOrderDetails.size();

        return new ProductOrderDetailResp(
                productOrder.getId(),
                productOrder.getDate(),
                productOrder.getBranch().getName(),
                firstProductName,
                totalOrderCount,
                productOrder.getBilledAmount());
    }
}
