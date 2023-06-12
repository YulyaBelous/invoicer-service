package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.BankAccount;
import com.example.invoicerservice.repository.BankAccountRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * The BankAccountController class is a Spring REST controller that provides endpoints for managing bank accounts.
 * It handles HTTP requests and responses for the /api/bank-accounts endpoint, which allows users to retrieve, create,
 * update, and delete bank accounts.
 */
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

    /**
     * Returns a page of bank accounts based on the specified query parameters and username.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of bank accounts
     */
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

    /**
     * Creates a new bank account with the specified details.
     *
     * @param bankAccount the bank account to create
     * @return the ID of the created bank account
     */
    @PostMapping("/bank-accounts")
    public Long createBankAccount(@RequestBody BankAccount bankAccount) {

        bankAccountRepository.save(bankAccount);
        return bankAccount.getId();
    }

    /**
     * Updates an existing bank account with the specified ID and details.
     *
     * @param bankAccount the updated bank account
     * @param id the ID of the bank account to update
     * @return the ID of the updated bank account
     */
    @PutMapping("/bank-accounts/{id}")
    public Long updateCustomer(@RequestBody BankAccount bankAccount, @PathVariable("id") Long id) {

        bankAccountRepository.save(bankAccount);
        return bankAccount.getId();
    }

    /**
     * Deletes an existing bank account with the specified ID.
     *
     * @param id the ID of the bank account to delete
     */
    @DeleteMapping("/bank-accounts/{id}")
    private void deleteBankAccount(@PathVariable("id") Long id) {

        bankAccountRepository.deleteById(id);
    }
}
