package com.binary.homepage.domain.board;

import com.binary.homepage.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String type;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;

    @NotNull
    @Size(max = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "member")
    private List<Comment> comment = new ArrayList<>();

    private LocalDateTime time;

    private Long view;

    private boolean enable;

    private int num;

    public void increaseView() {
        this.view++;
    }
}
