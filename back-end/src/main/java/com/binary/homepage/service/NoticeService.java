package com.binary.homepage.service;

import com.binary.homepage.domain.Notice;
import com.binary.homepage.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

}
