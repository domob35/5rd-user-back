package spring.guro.repository.newapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Alert;
import spring.guro.entity.Member;

public interface AlertRepo extends JpaRepository<Alert, Long> {
    List<Alert> findAllByMember(Member member);
}
