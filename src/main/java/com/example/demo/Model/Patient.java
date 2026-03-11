package com.example.demo.Model;

import com.example.demo.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must only contain alphabets and space")
    private String name;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(150)
    private Integer age;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank
    @Size(min = 5, max = 150)
    private String address;
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must have 10 digits")
    private String phone;

//    @JsonBackReference
//    use only if you have bidirectional relationship and you want to serialize the child object
//    and ignore the parent object to avoid infinite recursion. In this case, since we have a unidirectional relationship
//    from Patient to Users, we don't need to use @JsonBackReference. Instead, we can simply use @JsonIgnore on the user
//    field in the Patient class to prevent serialization of the user when serializing a Patient object.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
