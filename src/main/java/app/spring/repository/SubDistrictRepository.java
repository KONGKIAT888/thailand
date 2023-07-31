package app.spring.repository;

import app.spring.entity.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubDistrictRepository  extends JpaRepository<SubDistrict, Long> {
    Optional<Object> findByNameIgnoreCaseOrNameEnglishIgnoreCase(String name, String nameEnglish);
}
