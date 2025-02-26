package spring.guro.controller.newapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import spring.guro.dto.newapi.req.CreateNoticeReq;
import spring.guro.dto.newapi.resp.NoticeResp;
import spring.guro.dto.newapi.resp.NoticeWithPaginationResp;
import spring.guro.service.newapi.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService; // 공지사항 서비스

    // 공지사항
    @GetMapping
    public ResponseEntity<NoticeWithPaginationResp> getNotices(@PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "title", defaultValue = "") String title) {
        NoticeWithPaginationResp notices = noticeService.getNotices(pageable, title);
        return ResponseEntity.ok(notices);
    }

    // 메인페이지
    @GetMapping("/main")
    public ResponseEntity<List<NoticeResp>> getNoticesMain() {
        List<NoticeResp> noticeMain = noticeService.getNoticesMain();
        return ResponseEntity.ok(noticeMain);
    }

    // 공지사항 상세보기
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResp> getNoticeDetails(@PathVariable Long id) {
        NoticeResp noticeDetails = noticeService.getNoticeDetails(id);
        return ResponseEntity.ok(noticeDetails);
    }

    // 공지사항 작성
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createNotice(@RequestBody @Valid CreateNoticeReq req) {
        noticeService.createNotice(req.title(), req.text());
        return ResponseEntity.ok().build();
    }
}
