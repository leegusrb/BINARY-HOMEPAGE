package com.binary.homepage.domain.board;

import com.binary.homepage.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String title;

    @NotNull
    @Size(max = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String time;

    private Long view;

    public static Notice createNotice(String title, String content, Member member) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setMember(member);
        notice.setTime(localDateTimeToString(LocalDateTime.now()));
        notice.setView(0L);
        return notice;
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
