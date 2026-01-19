package com.example.demo.Pagination;

import com.example.demo.Enum.Role;
import com.example.demo.Model.DTO.UserInfoDTO;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Tag(name="User Pagination APIs")
public class UserPage {
    @Autowired
    private UserService userService;
    @GetMapping("/info")
    public Page<UserInfoDTO> getUser(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "3") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            String username,
            String email,
            Role role
            )
    {
        Sort sort;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo-1, pageSize, sort);
        return userService.fetchAll(pageable, username, email, role);
    }
}
