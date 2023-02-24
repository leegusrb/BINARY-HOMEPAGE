package com.binary.homepage.service;

import com.binary.homepage.domain.board.Notice;
import com.binary.homepage.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

    public Notice findOne(Long id) {
        return noticeRepository.findById(id).orElseThrow();
    }

}
