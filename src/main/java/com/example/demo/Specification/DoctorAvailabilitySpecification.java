package com.example.demo.Specification;

import com.example.demo.Enum.WorkingDay;
import com.example.demo.Model.DoctorAvailability;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorAvailabilitySpecification {
    public static Specification<DoctorAvailability> getSpecification(WorkingDay workingDay,
                                                                     LocalTime startTime, LocalTime endTime){
        return new  Specification<DoctorAvailability>(){

            @Override
            public @Nullable Predicate toPredicate(Root<DoctorAvailability> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(workingDay != null){
                    predicates.add(criteriaBuilder.like(root.get("workingDay"),"%"+ workingDay +"%"));
                }
                if(startTime != null && endTime != null){
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime),
                            criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime)));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
