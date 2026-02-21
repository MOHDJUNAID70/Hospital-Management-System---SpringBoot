package com.example.demo.Model;

import com.example.demo.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserId;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients=new ArrayList<>();

    public void addPatient(Patient patient){
        patients.add(patient);
        patient.setUser(this);
    }

    public void removePatient(Patient patient){
        patients.remove(patient);
        patient.setUser(null);
    }
}
