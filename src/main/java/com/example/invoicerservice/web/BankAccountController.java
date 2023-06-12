package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.BankAccount;
import com.example.invoicerservice.repository.BankAccountRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    private final BankAccountRepository bankAccountRepository;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public BankAccountController(BankAccountRepository bankAccountRepository, ControllerService controllerService) {

        this.bankAccountRepository = bankAccountRepository;
        this.controllerService = controllerService;
    }

    @GetMapping("/bank-accounts")
    public Page<BankAccount> getAllBankAccounts(@RequestParam(defaultValue = "0") Integer offset,
                                                @RequestParam(defaultValue = "25") Integer limit,
                                                @RequestParam(defaultValue = "id") String sortParam,
                                                @RequestParam(defaultValue = "asc") String sortDirect,
                                                @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        if(controllerService.isAdmin(username)) {
            return bankAccountRepository.findAll(pageable);
        } else {
            return bankAccountRepository.findByUsername(username, pageable);
        }
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
    private void deleteBankAccount(@PathVariable("id") Long id) {

        bankAccountRepository.deleteById(id);
    }
}
