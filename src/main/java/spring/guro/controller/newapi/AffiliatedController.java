package spring.guro.controller.newapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.SaveAffiliatedReq;
import spring.guro.service.newapi.AffiliatedService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/affiliated")
public class AffiliatedController {
    private final AffiliatedService affiliatedService;

    @PostMapping
    public ResponseEntity<?> saveAffiliated(@RequestBody @Valid SaveAffiliatedReq req) {
        affiliatedService.saveAffiliated(req);
        return ResponseEntity.ok().build();
    }
}
