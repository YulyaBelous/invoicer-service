package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.BankAccount;
import com.example.invoicerservice.entities.Supplier;
import com.example.invoicerservice.repository.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @Autowired
    private final IBankAccountRepository bankAccountRepository;

    public BankAccountController(IBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bank-accounts")
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @PostMapping("/bank-accounts")
    public Long createBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return bankAccount.getId();
    }

    @PutMapping("/bank-accounts/{id}")
    public Long updateCustomer(@RequestBody BankAccount bankAccount, @PathVariable("id") Long id) {
        bankAccountRepository.save(bankAccount);
        return bankAccount.getId();
    }

    @DeleteMapping("/bank-accounts/{id}")
    private void deleteBankAccount(@PathVariable("id") Long id)
    {
        bankAccountRepository.deleteById(id);
    }
}
