package spring.guro.controller.newapi;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.QuestionListResp;
import spring.guro.service.newapi.QuestionService;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final QuestionService questionService;
    
    @GetMapping("/questions")
    public ResponseEntity<QuestionListResp> getQuestions(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(questionService.getQuestions(startDate, endDate, pageable));
    }
}
