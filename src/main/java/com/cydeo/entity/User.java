package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String userName;

    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
/**
 * I deleted @Where annotation since there is a bug. I have a scenario: assume I have a project and some completed tasks related to this project but assigned employee is deleted. In this scenario I can not complete my project because when I want to complete the project I have a completeByProject() method in taskService which finds tasks related to that project. In this method I will find the related tasks but since I deleted my employee and I have @Where(is_deleted=false) it can not find the assigned employee and throws EntityNotFoundException. So I need to remove @Where annotation
 */