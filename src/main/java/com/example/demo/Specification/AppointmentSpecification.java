package com.example.demo.Specification;

import com.example.demo.Enum.AppointmentStatus;
import com.example.demo.Model.Appointment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecification {
    public static Specification<Appointment> getSpecification(LocalDate appointmentDate, AppointmentStatus status,
                                                              LocalTime appointmentStartTime, LocalTime appointmentEndTime) {
        return new Specification<Appointment>() {

            @Override
            public @Nullable Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(appointmentDate!=null){
                    predicates.add(criteriaBuilder.equal(root.get("appointmentDate"), appointmentDate));
                }
                if(status!=null){
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                }
                if(appointmentStartTime!=null && appointmentEndTime!=null){
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("appointmentTime"), appointmentStartTime),
                            criteriaBuilder.lessThanOrEqualTo(root.get("appointmentEndTime"), appointmentEndTime)
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
