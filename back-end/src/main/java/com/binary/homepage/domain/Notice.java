package com.binary.homepage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;

    private String content;

}
