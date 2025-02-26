package spring.guro.constant;

public class ValidationConstants {
    public static final String PHONE_NUMBER_PATTERN = "^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}$";
    public static final String PHONE_NUMBER_MESSAGE = "전화번호 형식이 아닙니다."; // 메시지도 함께 관리
}