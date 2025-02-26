package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductResp(
    Long id,
    String name,
    String description,
    @JsonProperty("image_url")
    String imageUrl,
    @JsonProperty("price")
    Integer price,
    Integer calories, 
    Integer carbohydrates,
    Integer protein,
    Integer fat,
    Integer caffeine
) {
}
