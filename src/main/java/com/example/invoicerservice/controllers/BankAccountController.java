package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.BankAccount;
import com.example.invoicerservice.repository.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @Autowired
    private final IBankAccountRepository bankAccountRepository;

    private Pageable pageable;

    public BankAccountController(IBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bank-accounts")
    public Page<BankAccount> getAllBankAccounts(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit,
                                                @RequestParam("sortParam") String sortParam,
                                                @RequestParam("sortDirect") String sortDirect) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        return bankAccountRepository.findAll(pageable);
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
