package app.spring.repository;

import app.spring.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<Object> findByNameIgnoreCaseOrNameEnglishIgnoreCase(String name, String nameEnglish);
}
