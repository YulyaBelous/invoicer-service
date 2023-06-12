package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ControllerService {

    private final UserRepository userRepository;

    @Autowired
    public ControllerService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public PageRequest getPageableWithSort(Integer offset, Integer limit, String sortParam, String sortDirect) {

        if(sortDirect.equals("asc")) {
            return PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else {
            return PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
    }

    public boolean isAdmin(String username) {

        User user = userRepository.findByUsername(username).get();

        return user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_ADMIN"))
                .findFirst().isPresent();
    }

    public boolean isCustomer(String username) {

        User user = userRepository.findByUsername(username).get();

        return user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_CUSTOMER"))
                .findFirst().isPresent();
    }

}
