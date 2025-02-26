package spring.guro.dto.newapi.resp;

public record ProductWithAvailabilityResp(
    ProductResp product,
    Boolean unavailable
) {

}
