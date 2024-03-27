package com.localbite_express.core.repositories.staff;

import com.localbite_express.core.models.staff.StaffPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffPerson,Long> {
    Optional<StaffPerson> findByUserUserId(int userId);
}
