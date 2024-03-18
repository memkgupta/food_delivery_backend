package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
