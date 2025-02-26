package spring.guro.controller.newapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.BranchResp;
import spring.guro.entity.Branch;
import spring.guro.service.newapi.BranchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getBranches(@RequestParam(name = "query", required = false) String query) {
        List<Branch> branches;
        if (query != null) {
            branches = branchService.getBranchesByName(query);
        } else {
            branches = branchService.getAllBranches();
        }
        List<BranchResp> branchResps = branches.stream().map(branch -> {
            return new BranchResp(
                branch.getId(),
                branch.getEmail(),
                branch.getName(),
                branch.getAddress(),
                branch.getPhone(),
                branch.getStatus()
            );
        }).toList();
        return ResponseEntity.ok(branchResps);
    }
}
