package spring.guro.dto.newapi.resp;

import spring.guro.entity.Member;

public record MemberResp(
    String realName,
    String email,
    String userName,
    String phone
) {
    public MemberResp(Member member) {
        this(member.getRealName(), member.getEmail(), member.getUserName(), member.getPhone());
    }
}
