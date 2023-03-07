package com.binary.homepage.component.csv;

import com.binary.homepage.domain.Member;
import com.binary.homepage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<MemberCsvForm> {

    private final MemberService memberService;

    @Override
    public void write(List<? extends MemberCsvForm> items) throws Exception {
        for (MemberCsvForm form :
                items) {
            Member member = memberService.createMember(form.getStudentId(), form.getName(), form.getGeneration(), form.getPassword());
            memberService.join(member);
        }
    }
}
