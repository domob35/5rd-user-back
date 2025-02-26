package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.AlertResp;
import spring.guro.entity.Alert;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.AlertRepo;

@RequiredArgsConstructor
@Service
public class AlertService {
    private final AlertRepo alertRepo;
    private final AuthService authService;

    public List<AlertResp> getAlerts() {
        List<Alert> alerts = alertRepo.findAllByMember(authService.getLoginMember());
        return alerts.stream().map(alert -> {
            return AlertResp.builder()
                .id(alert.getId())
                .content(alert.getContent())
                .build();
        }).toList();
    }

    @Transactional
    public void delete(long id) {
        Alert alert = alertRepo.findById(id).orElseThrow(() -> new CustomException(ErrorEnum.ALERT_NOT_FOUND));
        alertRepo.delete(alert);
    }
}
