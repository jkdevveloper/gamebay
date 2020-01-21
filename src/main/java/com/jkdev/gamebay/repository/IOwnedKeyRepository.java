package com.jkdev.gamebay.repository;

import com.jkdev.gamebay.entity.OwnedKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOwnedKeyRepository extends JpaRepository<OwnedKey, Long> {
}
