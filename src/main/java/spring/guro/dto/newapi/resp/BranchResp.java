package spring.guro.dto.newapi.resp;

import spring.guro.enumtype.BranchStatus;

public record BranchResp(
    long id,
    String email,
    String name, 
    String address, 
    String phone, 
    BranchStatus status
) {
    
}
