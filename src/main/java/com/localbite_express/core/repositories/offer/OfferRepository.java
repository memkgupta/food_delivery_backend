package com.localbite_express.core.repositories.offer;

import com.localbite_express.core.models.sales.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer,Long> {
}
