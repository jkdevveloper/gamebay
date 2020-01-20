package com.jkdev.gamebay.repository;

import com.jkdev.gamebay.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOfferRepository extends JpaRepository<Offer, Long> {
}
