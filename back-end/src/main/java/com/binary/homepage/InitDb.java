package com.binary.homepage;

import com.binary.homepage.crawling.Crawling;
import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.Role;
import com.binary.homepage.service.GrassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    static String[][] memberList = {
            {"18001861","이승렬","2", ""},
            {"17011749","홍승완","2", "hsw1920"},
            {"18011768","이원현","4", ""},
            {"18011781","전경원","2", "peterjkw"},
            {"18011776","윤덕건","4", ""},
            {"18011793","이정민","1", "yesuel1111"},
            {"18011883","김병목","2", ""},
            {"18011821","이진수","1", "binary01"},
            {"18011886","강응주","2", ""},
            {"19011737","허재연","2", ""},
            {"19011731","전명현","4", ""},
            {"19011802","최고은","2", ""},
            {"19013231","김도경","2", "rlaehrud57"},
            {"19011811","이수민","4", ""},
            {"19013239","장유진","3", ""},
            {"20003338","마세현","3", ""},
            {"20003334","강윤석","3", ""},
            {"20003345","황명제","3", ""},
            {"20011864","손건화","2", "dkrl0012"},
            {"20011872","김채원","2", "chae109"},
            {"20011885","최준영","4", ""},
            {"20011897","주민지","2", "joomj2000"},
            {"20011918","김해리","2", ""},
            {"20011924","최의준","4", ""},
            {"20011926","박도성","2", ""},
            {"20011945","정윤서","2", "dbstj0114"},
            {"20011949","오혁신","4", ""},
            {"21011859","권용현","3", ""},
            {"21011872","이현규","2", "jeffsep"},
            {"21011883","김원준","3", ""},
            {"21011895","백승우","4", ""},
            {"21011920","송민기","4", ""},
            {"21011928","전주혁","2", ""},
            {"21011930","안주형","2", "ajh020408"},
            {"21011935","이지원","2", "ljw2869"},
            {"21011947","김예린","2", "martina0123"},
            {"21011948","신아진","2", "candysaj"},
            {"21011949","최송민","3", ""},
            {"21011954","이은서","2", "le3uns"},
            {"21011968","김가은","3", ""},
            {"21011976","이다은","3", ""},
            {"21011982","박서연","2", ""},
            {"21011984","김형구","4", ""},
            {"22011891","이예준","3", ""},
            {"22011893","김지욱","3", ""},
            {"22011896","조한승","4", ""},
            {"22011902","이준수","4", ""},
            {"22011916","김민세","3", "kingbird77"},
            {"22011924","심규호","4", ""},
            {"22011925","권효정","3", ""},
            {"22011934","박민석","3", ""},
            {"22011936","박상규","3", "doriro"},
            {"22011937","김세람","3", ""},
            {"22011938","최유경","3", "dbrud0826"},
            {"22011939","고규민","3", ""},
            {"22011944","황상윤","3", ""},
            {"22011949","한지원","4", ""},
            {"22011953","이지은","3", ""},
            {"22011955","최민기","4", ""},
            {"22011956","김가현","3", ""},
            {"22011957","임희원","3", ""},
            {"22011958","김동건","4", ""},
            {"22011961","황기중","3", "ghkdrlwnd"},
            {"22011963","정명준","3", ""},
            {"22011978","이서영","3", ""},
            {"22011989","정지윤","3", "stopyoon"},
            {"22011994","김태양","3", ""},
            {"22012002","양민서","3", ""},
            {"22012003","주인호","3", "inho113"},
            {"22012006","강대현","4", ""},
            {"22012008","류희지","3", ""},
            {"22012010","김유찬","3", ""},
            {"22012012","이재진","3", ""},
            {"22012017","이미나","3", ""},
            {"22012019","정현준","3", ""},
            {"22012020","은서우","3", "seous2"},
            {"22012026","이지우","3", ""},
            {"22012029","박유정","3", ""},
            {"22012757","최연수","3", ""}
    };

    static int currentYear = LocalDate.now().getYear();

    private final InitService initService;
    private final GrassService grassService;


    @PostConstruct
    public void init() {
//        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final Crawling crawling;

        public void dbInit() {
            for (String[] member: memberList) {
                String name = member[1];
                int studentId = Integer.parseInt(member[0]);
                int generation = Integer.parseInt(member[2]);
                String grassName = member[3];

                Member newMember = createMember(name, studentId, generation);
                em.persist(newMember);

                if (!grassName.isEmpty()) {
                    Grass grass = createGrass(newMember, grassName);
                    newMember.setGrass(grass);
                    em.persist(grass);

                    String[] crawlingGrassData = crawling.getGrassData(grassName);

                    for (String grassData: crawlingGrassData) {
                        String[] grassSplit = grassData.split(",");

                        int year = Integer.parseInt(grassSplit[0].substring(0, 4));
                        int month = Integer.parseInt(grassSplit[0].substring(4, 6));
                        int day = Integer.parseInt(grassSplit[0].substring(6, 8));

                        if (year != currentYear) continue;

                        LocalDate date = LocalDate.of(year, month, day);
                        int grassNum = Integer.parseInt(grassSplit[1]);

                        GrassInfo grassInfo = createGrassInfo(date, grassNum, grass);
                        em.persist(grassInfo);
                    }
                }
            }
        }

        private Member createMember(String name, int studentId, int generation) {
            Member member = new Member();
            member.setName(name);
            member.setStudentId(studentId);
            member.setPassword(passwordEncoder.encode("123"));
            member.setEnabled(true);
            member.setGeneration(generation);
            member.setRole(Role.MEMBER);

            return member;
        }

        private Grass createGrass(Member member, String grassName) {
            Grass grass = new Grass();
            grass.setMember(member);
            grass.setGrassName(grassName);

            return grass;
        }

        private GrassInfo createGrassInfo(LocalDate date, int grassNum, Grass grass) {
            GrassInfo grassInfo = new GrassInfo();
            grassInfo.setDate(date);
            grassInfo.setNum(grassNum);
            grassInfo.setGrass(grass);

            return grassInfo;
        }
    }
}
