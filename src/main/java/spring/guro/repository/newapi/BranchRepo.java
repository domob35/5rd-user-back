package spring.guro.repository.newapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Branch;
import spring.guro.enumtype.Authority;

public interface BranchRepo extends JpaRepository<Branch, Long> {
    List<Branch> findByNameContainingAndAuthority(String name, Authority authority);
    List<Branch> findAllByAuthority(Authority authority);
}
