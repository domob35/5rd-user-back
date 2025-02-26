package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Option;

import java.util.Optional;

public interface OptionRepo extends JpaRepository<Option, Long> {

    Optional<Option> findByName(String name);

}
