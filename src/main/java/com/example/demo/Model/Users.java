package com.example.demo.Model;

import com.example.demo.Enum.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

//    @JsonManagedReference
//    use only if you have bidirectional relationship and you want to serialize the parent object
//    and ignore the child object to avoid infinite recursion. In this case, since we have a unidirectional relationship
//    from Users to Patient, we don't need to use @JsonManagedReference. Instead, we can simply use @JsonIgnore on the patients
//    field in the Users class to prevent serialization of the patients when serializing a Users object.
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
