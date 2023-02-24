package com.binary.homepage.component;

import com.binary.homepage.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCSVForm {
    private int student_number;
    private String name;
    private int generation;
    private Role role;
    private int warning;

    public MemberCSVForm(int student_number, String name, int generation, Role role, int warning) {
        this.student_number = student_number;
        this.name = name;
        this.generation = generation;
        this.role = role;
        this.warning = warning;
    }
}
