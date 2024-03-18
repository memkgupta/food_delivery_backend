package com.localbite_express.core.repositories;

import com.localbite_express.core.models.sales.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Sale,Integer> {

}
