package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Branch;
import spring.guro.enumtype.Authority;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.BranchRepo;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepo branchRepo;

    public Branch getBranchById(long branchId) {
        return branchRepo.findById(branchId).orElseThrow(() -> new CustomException(ErrorEnum.BRANCH_NOT_FOUND));
    }

    /**
     * 저장소에서 모든 지점을 검색합니다.
     * 
     * @return 데이터베이스에 저장된 모든 Branch 엔티티 목록
     * @throws CustomException 지점이 없을 경우 BRANCH_EMPTY 에러 발생
     */
    public List<Branch> getAllBranches() {
        List<Branch> branches =  branchRepo.findAllByAuthority(Authority.USER);
        if(branches.isEmpty()) {
            throw new CustomException(ErrorEnum.BRANCH_EMPTY);
        }
        return branches;
    }

    /**
     * 주어진 이름을 포함하는 지점들을 검색합니다.
     *
     * @param name 검색할 지점 이름 (부분 문자열)
     * @return 이름이 일치하는 Branch 엔티티 목록
     * @throws CustomException BRANCH_NOT_FOUND(404) - 검색된 지점이 없는 경우
     */
    public List<Branch> getBranchesByName(String name) {
        List<Branch> branches = branchRepo.findByNameContainingAndAuthority(name, Authority.USER);
        if(branches.isEmpty()) {
            throw new CustomException(ErrorEnum.BRANCH_NOT_FOUND);
        }
        return branches;
    }
}
