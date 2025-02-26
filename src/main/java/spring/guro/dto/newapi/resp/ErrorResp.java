package spring.guro.dto.newapi.resp;

public record ErrorResp(
    int code,
    String status,
    String message
) {
    
}
