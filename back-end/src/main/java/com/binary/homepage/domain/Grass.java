package com.binary.homepage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Grass {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grass_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "grass")
    private Member member;

    private String grassName;

    @OneToMany(mappedBy = "grass")
    private List<GrassInfo> grassInfos = new ArrayList<>();

    /**
     * 올해 전체 잔디 개수
     */
    public int getAllGrassNum() {
        return grassInfos.size();
    }

    public void addGrassInfo(GrassInfo grassInfo) {
        grassInfos.add(grassInfo);
        grassInfo.setGrass(this);
    }

    /**
     * 생성 메서드
     */
    public static Grass createGrass(Member member, String grassName, List<GrassInfo> grassInfos) {
        Grass grass = new Grass();
        grass.setMember(member);
        grass.setGrassName(grassName);
        for (GrassInfo grassInfo : grassInfos) {
            grass.addGrassInfo(grassInfo);
        }
        return grass;
    }
}
