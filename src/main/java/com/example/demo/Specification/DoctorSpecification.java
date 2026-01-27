package com.example.demo.Specification;

import com.example.demo.Enum.DoctorSpecializations;
import com.example.demo.Model.Doctor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {
    public static Specification<Doctor> getSpecification(Integer StartExperienceInYears, Integer endExperienceInYears, String name,
                                                         DoctorSpecializations specializations) {
        return new Specification<Doctor>() {

            @Override
            public @Nullable Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StartExperienceInYears != null && endExperienceInYears != null) {
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("experienceInYears"), StartExperienceInYears),
                            criteriaBuilder.lessThanOrEqualTo(root.get("experienceInYears"), endExperienceInYears)
                    ));
                }
                if(name != null) {
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                if(specializations != null) {
                    predicates.add(criteriaBuilder.equal(root.get("specialization"), specializations));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
