package com.binary.homepage.domain.board;

import com.binary.homepage.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String cmt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime time;

    public static Comment createComment(String cmt, Board board, Member member) {
        Comment comment = new Comment();
        comment.setCmt(cmt);
        comment.setBoard(board);
        comment.setMember(member);
        comment.setTime(LocalDateTime.now());
        board.getComment().add(comment);
        member.getComments().add(comment);
        return comment;
    }
}