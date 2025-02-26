package spring.guro.repository.newapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Member;
import spring.guro.entity.PersonalProduct;

public interface PersonalProductRepo extends JpaRepository<PersonalProduct, Long> {
    List<PersonalProduct> findAllByMember(Member member);
}
