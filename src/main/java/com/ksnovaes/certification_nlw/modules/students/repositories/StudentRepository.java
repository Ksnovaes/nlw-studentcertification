package com.ksnovaes.certification_nlw.modules.students.repositories;

import com.ksnovaes.certification_nlw.modules.students.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    public Optional<StudentEntity> findByEmail(String email);
}
