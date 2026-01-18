package com.example.demo.Specification;

import com.example.demo.Enum.Role;
import com.example.demo.Model.Users;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<Users> getSpecification(String username, String email, Role role) {
        return new Specification<Users>() {

            @Override
            public @Nullable Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(username!=null && !username.isEmpty()){
                    predicates.add(criteriaBuilder.like(root.get("username"),"%"+username+"%"));
                }
                if(email!=null && !email.isEmpty()){
                    predicates.add(criteriaBuilder.like(root.get("email"),"%"+email+"%"));
                }
                if(role!=null){
                    predicates.add(criteriaBuilder.equal(root.get("role"),role));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
