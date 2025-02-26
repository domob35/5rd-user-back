package spring.guro.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {
    VALIDATION_ERROR(400, "VALIDATION_ERROR", "검증 실패"),
    LOGIN_EMAIL_INVALID(401, "LOGIN_EMAIL_INVALID", "이메일이 존재하지 않습니다."),
    LOGIN_PASSWORD_UNMATCHED(401, "LOGIN_PASSWORD_UNMATCHED", "비밀번호가 일치하지 않습니다."),
    SIGN_UP_USER_NAME_DUPLICATION(409, "SIGN_UP_USER_NAME_DUPLICATION", "이미 존재하는 아이디입니다."),
    SIGN_UP_EMAIL_DUPLICATION(409, "SIGN_UP_USER_NAME_DUPLICATION", "이미 존재하는 이메일입니다."),
    UPDATE_EMAIL_DUPLICATION(409, "UPDATE_EMAIL_DUPLICATION", "이미 존재하는 이메일입니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증되지 않은 사용자입니다."),
    NO_AUTHORITY(403, "NO_AUTHORITY", "권한이 없습니다."),
    BRANCH_NOT_FOUND(404, "BRANCH_NOT_FOUND", "지점을 찾을 수 없습니다."),
    FAQ_NOT_FOUND(404, "FAQ_NOT_FOUND", "FAQ를 찾을 수 없습니다."),
    BRANCH_EMPTY(404, "BRANCH_EMPTY", "지점이 존재하지 않습니다."),
    PRODUCT_EMPTY(404, "PRODUCT_EMPTY", "상품이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND(404, "PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다."),
    INVALID_PASSWORD(400, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    OPTION_EMPTY(404, "OPTION_EMPTY", "옵션이 존재하지 않습니다."),
    CART_EMPTY(404, "CART_EMPTY", "장바구니가 비어있습니다."),
    COUPON_NOT_FOUND(404, "COUPON_NOT_FOUND", "사용 가능한 쿠폰이 없습니다."),
    POINT_USAGE_INVALID(400, "POINT_USAGE_INVALID", "포인트는 100 단위로만 사용 가능합니다."),
    POINT_BALANCE_INSUFFICIENT(400, "POINT_BALANCE_INSUFFICIENT", "포인트가 부족합니다."),
    NOTICE_NOT_FOUND(404, "NOTICE_NOT_FOUND", "공지사항을 찾을 수 없습니다"),
    PRODUCT_HAS_NO_OPTION(404, "PRODUCT_HAS_NO_OPTION", "해당 상품에 옵션이 존재하지 않습니다."),
    ORDER_HISTORY_NOT_FOUND(404, "ORDER_HISTORY_NOT_FOUND", "조회된 주문 내역이 없습니다."),
    INVALID_INPUT(400, "INVALID_INPUT", "유효하지 않은 입력입니다."), // 유효하지 않은 입력
    DATA_NOT_FOUND(404, "DATA_NOT_FOUND", "데이터를 찾을 수 없습니다."), // 데이터를 찾을 수 없음
    QUESTION_NOT_FOUND(404, "QUESTION_NOT_FOUND" , "조회된 질문이 없습니다."),
    INVALID_DISCOUNT(400, "INVALID_DISCOUNT", "할인 금액이 주문 금액보다 큽니다."),
    CONCURRENT_UPDATE_ERROR(409, "CONCURRENT_UPDATE_ERROR", "동시에 수정된 데이터가 있습니다."),
    INVALID_DATE_RANGE(400, "INVALID_DATE_RANGE", "날짜 범위가 올바르지 않습니다."),
    MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다."),
    CART_NOT_FOUND(404, "CART_NOT_FOUND", "장바구니를 찾을 수 없습니다."),
    OPTION_NOT_FOUND(404, "OPTION_NOT_FOUND", "옵션을 찾을 수 없습니다."),
    PERSONAL_PRODUCT_EMPTY(404, "PERSONAL_PRODUCT_EMPTY", "개인 맞춤 제품이 존재하지 않습니다."),
    PERSONAL_PRODUCT_NOT_FOUND(404, "PERSONAL_PRODUCT_NOT_FOUND", "개인 맞춤 제품을 찾을 수 없습니다."),
    NAVER_LOGIN_FAILED(401, "NAVER_LOGIN_FAILED", "네이버 로그인에 실패했습니다."),
    ALERT_NOT_FOUND(404, "ALERT_NOT_FOUND", "알림을 찾을 수 없습니다."),
    RECIPE_NOT_FOUND(404, "RECIPE_NOT_FOUND", "레시피를 찾을 수 없습니다."),
    ;

    public final int code;
    public final String status;
    public final String message;
}
