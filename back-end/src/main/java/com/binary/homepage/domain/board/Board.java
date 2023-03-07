package com.binary.homepage.domain.board;

import com.binary.homepage.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
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

    private LocalDateTime time;

    private Long view;

    public static Board createBoard(String type, String title, String content, Member member) {
        Board board = new Board();
        board.setType(type);
        board.setTitle(title);
        board.setContent(content);
        board.setMember(member);
        board.setTime(LocalDateTime.now());
        board.setView(0L);
        return board;
    }

    public static String localDateTimeToString(LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    public void increaseView() {
        this.view++;
    }
}
