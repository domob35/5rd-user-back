package spring.guro.service.newapi;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.OptimisticLockException;
import spring.guro.dto.newapi.resp.NoticeResp;
import spring.guro.dto.newapi.resp.NoticeWithPaginationResp;
import spring.guro.entity.Notice;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.NoticeRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    /**
     * 선택적 검색 쿼리가 있는 페이지네이션된 공지사항을 검색합니다.
     * 
     * @param pageable 페이지 번호, 크기 및 정렬을 포함한 페이지네이션 정보
     * @param title 제목으로 공지사항을 필터링하기 위한 검색 문자열 
     * @return 공지사항 목록과 총 페이지 수를 포함하는 NoticeWithPaginationResp
     * @throws CustomException 조건에 맞는 공지사항이 없을 경우 NOTICE_NOT_FOUND 에러 발생
     */
    public NoticeWithPaginationResp getNotices(Pageable pageable, String title) {
        Page<Notice> noticePage = noticeRepository.findAllByTitleContaining(title, pageable);
        List<Notice> notices = noticePage.getContent();

        if (notices.isEmpty()) {
            throw new CustomException(ErrorEnum.NOTICE_NOT_FOUND); //공지사항이 비어있을 때 CustomException 발생
        }

        int totalPage = noticePage.getTotalPages();
        List<NoticeResp> noticeResps = notices.stream()
                .map(notice -> NoticeResp.builder()
                        .id(notice.getId())  //번호
                        .title(notice.getTitle()) //제목
                        .date(notice.getDate()) //작성일
                        .view(notice.getView()) //조회수
                        .build()
                )
                .collect(Collectors.toList());
        
        return NoticeWithPaginationResp.builder()
                .notices(noticeResps)
                .totalPage(totalPage)
                .build();
    }

    /**
     * 메인 페이지에 표시할 최근 5개의 공지사항을 가져옵니다.
     * 공지사항은 작성일(Date)을 기준으로 내림차순으로 정렬됩니다.
     *
     * @return 번호(id)와 제목(title)만 포함된 {@link NoticeResp} 객체 목록.
     *         만약 공지사항이 없다면, {@link CustomException}을 발생시킵니다. (ErrorEnum.NOTICE_NOT_FOUND)
     * @throws CustomException 공지사항이 없을 때 {@link ErrorEnum#NOTICE_NOT_FOUND}와 함께 발생.
     */
    public List<NoticeResp> getNoticesMain() {
        List<Notice> notices = noticeRepository.findTop5ByOrderByDateDesc();
        if (notices.isEmpty()) {
            throw new CustomException(ErrorEnum.NOTICE_NOT_FOUND); //공지사항이 비어있을 때 CustomException 발생
        }

        return notices.stream()
                .map(notice -> NoticeResp.builder()
                        .id(notice.getId())  //번호
                        .title(notice.getTitle()) //제목
                        .build()
                )
                .collect(Collectors.toList());
    }


    // 공지사항 상세보기
    //페이지 조회수 증가 (기능)
    //선택된 공지사항 데이터 (정보)
    //제목
    //내용
    //날짜
    //조회수
    @Transactional
    public NoticeResp getNoticeDetails(Long id) {

        //해당 Id로 공지사항 조회
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorEnum.NOTICE_NOT_FOUND)); //공지사항이 비어있을 때 CustomException 발생

        //조회수 증가 (락 충돌시 3번 재시도)
        notice.viewIncrease();

        int tempRetry = 3;
        while(tempRetry > 0) {
            try {
                noticeRepository.save(notice);
                break;
            } catch (OptimisticLockException e) { // 락 충돌시 재시도
                tempRetry--;
                if (tempRetry == 0) {
                    throw new CustomException(ErrorEnum.CONCURRENT_UPDATE_ERROR);
                }
                try {
                    Thread.sleep(50); // 재시도 전 잠시 대기
                } catch (InterruptedException ie) { // 인터럽트 발생시
                    Thread.currentThread().interrupt(); // 인터럽트 상태 재설정
                    throw new CustomException(ErrorEnum.CONCURRENT_UPDATE_ERROR);
                }
            }
        }

        //응답을 위해 NoticeDetailsResp 생성
        return NoticeResp.builder()
                .title(notice.getTitle())
                .text(notice.getText())
                .date(notice.getDate())
                .view(notice.getView())
                .build();
    }

    @Transactional
    public void createNotice(String title, String text) {
        Notice notice = Notice.builder()
                .title(title)
                .text(text)
                .build();
        noticeRepository.save(notice);
    }
}
