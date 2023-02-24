package com.binary.homepage.service;

import com.binary.homepage.domain.board.Notice;
import com.binary.homepage.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

    public Notice findOne(Long id) {
        return noticeRepository.findById(id).orElseThrow();
    }

}
