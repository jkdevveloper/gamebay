package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Transaction;
import com.jkdev.gamebay.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    ITransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Long id) {
        return this.transactionRepository.getOne(id);
    }

    @Override
    public void deleteTransaction(Long id) {
        this.transactionRepository.deleteById(id);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }
}
