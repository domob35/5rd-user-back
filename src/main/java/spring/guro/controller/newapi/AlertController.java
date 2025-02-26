package spring.guro.controller.newapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.AlertResp;
import spring.guro.service.newapi.AlertService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertController {
    
    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<AlertResp>> getAlerts() {
        return ResponseEntity.ok(alertService.getAlerts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable("id") long id) {
        alertService.delete(id);
        return ResponseEntity.ok().build();
    }
}
