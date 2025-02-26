package spring.guro.entity;


// import com.posco.poscoproject.dto.BranchFormDTO;
// import com.posco.poscoproject.enumtype.Authority;
// import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.*;
// import org.springframework.security.crypto.password.PasswordEncoder;

import spring.guro.enumtype.Authority;
import spring.guro.enumtype.BranchStatus;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor

public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 지점 ID

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번혼

    @Column(nullable = false)
    private String name; // 지점 이름

    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String phone; //전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority; // 권한 : ROLE_USER, ROLE_ADMIN

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BranchStatus status; // 지점 상태

    @Column(nullable = false)
    private String devicePw; // 지점별 키오스크 / 테이블 오더 관리자 패스워드

    //테스트에서 지점 생성시 사용됨,
    // @Builder
    // public Branch(Long branchId, String email, String password, String branchName, String address, String phone, BranchStatus branchStatus, Authority authority, Long salesObjective, String devicePw) {
    //     this.branchId = branchId;
    //     this.email = email;
    //     this.password = password;
    //     this.branchName = branchName;
    //     this.address = address;
    //     this.phone = phone;
    //     this.branchStatus = branchStatus;
    //     this.authority = authority;
    //     this.salesObjective = salesObjective;
    //     this.devicePw = devicePw;
    // }

    // public static Branch createBranch(BranchFormDTO branchFormDTO, PasswordEncoder passwordEncoder){

    //     Branch branch = new Branch();
    //     branch.setEmail(branchFormDTO.getEmail());
    //     String password = passwordEncoder.encode(branchFormDTO.getPassword());
    //     branch.setPassword(password);
    //     branch.setBranchName(branchFormDTO.getBranchName());
    //     branch.setAddress(branchFormDTO.getAddress());
    //     branch.setPhone(branchFormDTO.getPhone());
    //     branch.setBranchStatus(branchFormDTO.getBranchStatus());
    //     branch.setAuthority(USER);
    //     return branch;
    // }
}
