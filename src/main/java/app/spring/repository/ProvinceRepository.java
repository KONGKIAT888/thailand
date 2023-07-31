package app.spring.repository;

import app.spring.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Object> findByNameIgnoreCaseOrNameEnglishIgnoreCase(String name, String nameEnglish);
}
