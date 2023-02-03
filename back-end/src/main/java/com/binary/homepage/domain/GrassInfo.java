package com.binary.homepage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class GrassInfo {

    @Id @GeneratedValue
    @Column(name = "grass_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grass_id")
    private Grass grass;

    @Column
    private LocalDate date;

    private int num;
}
