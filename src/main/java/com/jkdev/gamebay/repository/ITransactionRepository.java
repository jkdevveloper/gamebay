package com.jkdev.gamebay.repository;

import com.jkdev.gamebay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
