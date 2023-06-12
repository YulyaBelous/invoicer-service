package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The ControllerService class contains utility methods for the Invoice Service controllers.
 */
@Service
public class ControllerService {

    private final UserRepository userRepository;

    @Autowired
    public ControllerService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Creates a new PageRequest object with the specified offset, limit, sort parameter, and sort direction.
     *
     * @param offset     the offset of the page
     * @param limit      the maximum number of items to return
     * @param sortParam  the parameter to sort by
     * @param sortDirect the direction to sort in ("asc" or "desc")
     * @return a new PageRequest object with the specified parameters
     */
    public PageRequest getPageableWithSort(Integer offset, Integer limit, String sortParam, String sortDirect) {

        if(sortDirect.equals("asc")) {
            return PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else {
            return PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
    }

    /**
     * Checks if the user with the specified username is an admin.
     *
     * @param username the username of the user to check
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin(String username) {

        User user = userRepository.findByUsername(username).get();

        return user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_ADMIN"))
                .findFirst().isPresent();
    }

    /**
     * Checks if the user with the specified username is a customer.
     *
     * @param username the username of the user to check
     * @return true if the user is a customer, false otherwise
     */
    public boolean isCustomer(String username) {

        User user = userRepository.findByUsername(username).get();

        return user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_CUSTOMER"))
                .findFirst().isPresent();
    }

}
