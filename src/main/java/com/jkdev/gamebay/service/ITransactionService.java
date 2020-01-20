package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Transaction;

import java.util.List;

public interface ITransactionService {
    void saveTransaction(Transaction transaction);

    List<Transaction> getTransactions();

    Transaction getTransaction(Long id);

    void deleteTransaction(Long id);

    void updateTransaction(Transaction transaction);
}
