package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Affiliated;

public interface AffiliatedRepo extends JpaRepository<Affiliated, Long> {
    
}
