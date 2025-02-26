package spring.guro.entity;

import java.util.List;

import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.guro.enumtype.Authority;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName; // 이게 사용자 아이디임;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    // 유저별 총 결제 금액
    @Column(nullable = false)
    private long totalPaymentAmount;

    // 총 결제한 횟수
    @Column(nullable = false)
    private int totalPurchaseCount;

    // 스탬프 수
    @Column(nullable = false)
    private int stamp;

    // 적립금
    @Column(nullable = false)
    private int point;

    //외래키 membership 참조(n:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Membership membership;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Alert> alerts;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Cart> carts;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PersonalProduct> personalProducts;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    //생성자
    @Builder
    public Member(String userName, String password, String realName, String phone, String email, Membership membership) {
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.phone = phone;
        this.email = email;
        this.membership = membership;
        this.authority = Authority.USER;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(authority.toString()));
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateRealName(String realName) {
        this.realName = realName;
    }

    // 포인트 사용
    public void usePoint(int usedPoint) {
        this.point -= usedPoint;
    }

    // 포인트 적립
    public void addPoint(ProductOrder productOrder) {
        int earnedPoint = (int) (productOrder.getBilledAmount() * membership.getPointRate());
        this.point += earnedPoint;
    }

    // 총 결제 횟수 1 증가
    public void increaseTotalPurchaseCount() {
        this.totalPurchaseCount++;
    }

    // 총 결제 금액 증가(결제 금액에 토대로)
    public void increaseTotalPaymentAmount(int billedAmount) {
        this.totalPaymentAmount += billedAmount;
    }

    // 스탬프 1 증가
    public boolean increaseStamp() {
        this.stamp++;
        if (stamp == 10) {
            stamp = 0;
            return true;
        } else {
            return false;
        }
    }

    public void upgradeMembership(Membership membership) {
        this.membership = membership;
    }
}