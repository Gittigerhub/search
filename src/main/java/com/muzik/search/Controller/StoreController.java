// 12. 매핑 및 결과 메세지 처리

package com.muzik.search.Controller;

import com.muzik.search.DTO.StoreDTO;
import com.muzik.search.Service.StoreService;
import com.muzik.search.Util.PageNationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log    // 확인이 필요한
@Tag(name = "StoreController", description = "컨트롤러")
public class StoreController {

    private final StoreService storeService;
    private final PageNationUtil pageNationUtil;

    // 서비스에 메소드를 보면서 작성
    /* --------------------------------------------------------------------------------------
        함수명 : storeDeleteProc(Integer idx, RedirectAttributes redirectAttributes)
        입력 : html로 부터 삭제 할 일련번호
        결과 : 성공 / 실패 메세지
        설명 : 해당 일련번호로 데이터를 삭제 후, 메세지를 가지고 list 페이지로 이동

              다른 맵핑으로 값과 함께 이동할 때는 RedirectAttributes를 사용
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "삭제", description = "해당 일련번호로 데이터를 삭제 후, 메세지를 가지고 list 페이지로 이동")
    @GetMapping("/storeDelet")
    public String storeDeleteProc(Integer idx, RedirectAttributes redirectAttributes) {

        log.info(idx + "로 요청");
        Boolean result = storeService.storeDelete(idx);

        log.info("삭제처리 후 검증");
        // 결과 처리
        if (result) {               // 삭제하였습니다.
            redirectAttributes.addFlashAttribute("message", "삭제하였습니다.");
        } else {                    // 삭제를 실패하였습니다.
            redirectAttributes.addFlashAttribute("message", "삭제를 실패하였습니다.");
        }

        log.info("목록맵핑으로 요청");
        return "redirect:/storeList";

    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeInsertForm(Model model)
        입력 : 없음
        출력 : 삽입폼으로 이동
        설명 : 해당 맵핑의 요청이 있으면 해당 HTML로 이동
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "등록페이지", description = "등록페이지 접근 (insert.html)")
    @GetMapping("/storeInsert")
    public String storeInsertForm(Model model) {
        // 검증 라이브러리를 추가하면
        // 입력폼에서 object field를 이용해서 검증처리
        log.info("빈 DTO를 생성 후 저장");

        // 입력: 검증 시, 입력한 값이나 오류메세지 출력을 위해서 빈 DTO를 폼에 전달
        model.addAttribute("storeDTO", new StoreDTO());
        // GetMapping model.addAttribute("storeDTO")이름과
        // PostMapping (StoreDTO storeDTO)이름은 동일하게 지정

        log.info("입력폼으로 이동");
        return "insert";
    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeInsertProc(StoreDTO storeDTO, RedirectAttributes redirectAttributes)
        입력 : 입력한 StoreDTO
        출력 : 저장 결과 메세지를 가지고 List로 이동
        설명 : 입력받은 데이터를 데이터베이스에 저장하고, 결과를 가지고 List맵핑으로 이동
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "등록", description = "입력받은 데이터를 데이터베이스에 저장하고, 결과를 가지고 List맵핑으로 이동 (insert.html)")
    @PostMapping("/storeInsert")
    // 검증이 필요한 DTO에 @Valid 선언과 함께 BindingResult를 선언
    // 적용 순서는 반드시 지켜서 기재
    public String storeInsertProc(@Valid StoreDTO storeDTO,BindingResult bindingResult ,
                                  RedirectAttributes redirectAttributes) {

        // HTML -> DTO 검증 -> Service에서 데이터베이스 처리 -> 결과 -> 페이지 이동
        // 검증의 유효성 판단은 Service전에 작업을 한다.
        if (bindingResult.hasErrors()) {    // 검증처리 시, 오류가 발생하였다면
            // 요청을 시도한 HTML으로 되돌아 간다. (GetMapping에 HTML파일로)
            return "insert";
        }

        log.info("컨트롤러로 들어온 DTO : " + storeDTO);
        StoreDTO result = storeService.StoreInsert(storeDTO);

        log.info("등록 후 검증");
        if (result != null) {           // 값이 있으면, 저장에 성공했으면
            redirectAttributes.addFlashAttribute("message", "저장을 성공하였습니다.");
        } else {                        // 저장을 실패했으면
            redirectAttributes.addFlashAttribute("message", "저장을 실패하였습니다.");
        }

        log.info("storeList로 맵핑 요청");
        return "redirect:/storeList";
    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeUpdateForm(Integer idx, Model model)
        입력 : 수정할 일련번호
        출력 : 수정할 DTO를 전달
        설명 : 일련번호로 해당 데이터를 조회해서 결과값을 HTML에 전달
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "수정페이지", description = "일련번호로 해당 데이터를 조회해서 결과값을 HTML에 전달 (update.html)")
    @GetMapping("/storeUpdate")
    public String storeUpdateForm(Integer idx, Model model, RedirectAttributes redirectAttributes) {

        StoreDTO read = storeService.StoreRead(idx);
        if (read != null) {                 // 수정할 데이터가 존재하면
            //수정시 수정할 DTO를 전달하므로 검증에 필요한 추가 작업없다.
            model.addAttribute("data", read);

            return "update";                // 수정할 데이터가 있으면 수정폼으로 이동

        }

        // 수정할 데이터가 존재하지 않으면
        redirectAttributes.addFlashAttribute("message", "해당 데이터가 존재하지 않습니다.");

        log.warning("비정상적 처리시 storeList맴핑으로 요청");
        return "redirect:/storeList";   // 수정할 데이터가 없으면 목록페이지로 이동

    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeUpdateProc(StoreDTO storeDTO, RedirectAttributes redirectAttributes)
        입력 : 수정한 DTO
        출력 : 수정처리 후 결과 메세지
        설명 : 수정할 데이터를 저장해서 결과 메세지를 가지고 List맵핑으로 이동
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "수정", description = "수정할 데이터를 저장해서 결과 메세지를 가지고 List맵핑으로 이동 (update.html)")
    @PostMapping("/storeUpdate")
    public String storeUpdateProc(StoreDTO storeDTO, RedirectAttributes redirectAttributes) {

        StoreDTO result = storeService.StoreUpdate(storeDTO);
        if (result != null) {           // 수정을 성공했을 때, 결과값이 비어있지 않으면
            redirectAttributes.addFlashAttribute("message", "수정하였습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "수정에 실패하였습니다.");
        }

        return "redirect:/storeList";

    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeReadProc(Integer idx, RedirectAttributes redirectAttributes, Model model)
        입력 : 읽어올 일련번호
        출력 : 조회된 DTO
        설명 : 해당 일련번호로 데이터베이스에서 조회하여 결과를 전달 (HTML 상세페이지)
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "조회", description = "해당 일련번호로 데이터베이스에서 조회하여 결과를 전달 (read.html)")
    @GetMapping("/storeRead")
    public String storeReadProc(Integer idx, RedirectAttributes redirectAttributes, Model model) {

        StoreDTO result = storeService.StoreRead(idx);
        if (result != null) {
            model.addAttribute("data", result);

            return "read";

        }

        // 조회한 결과가 존재하지 않으면
        redirectAttributes.addFlashAttribute("message", "해당하는 데이터가 존재하지 않습니다.");

        return "redirect:/storeList";

    }



    /* --------------------------------------------------------------------------------------
        함수명 : storeListForm(Pageable pagealbe, String type, String keyword)
        입력 : 조회할 페이지 정보, 분류 대상, 검색 키워드
        출력 : Page<StoreDTO>
        설명 : 분류대상에 키워드로 조회한 해당 페이지 데이터를 전달
    -------------------------------------------------------------------------------------- */
    @Operation(summary = "목록", description = "분류대상에 키워드로 조회한 해당 페이지 데이터를 전달 (list.html)")
    @GetMapping("/storeList")
    public String storeListForm(
            @PageableDefault(page = 1) Pageable pagealbe,                           // 페이지정보, 페이지정보가 없으면 기본값으로 1페이지로 설정
            @RequestParam(value = "type", defaultValue = "") String type,           // 검색대상, 없으면 기본값은 ""
            @RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {   // 키워드, 없으면 기본값은 ""

        Page<StoreDTO> result = storeService.StoreList(pagealbe, type, keyword);

        Map<String, Integer> pageInfo = pageNationUtil.Pagination(result);

        model.addAttribute("list", result);         // 데이터 전달
        model.addAllAttributes(pageInfo);                       // 페이지 정보
        model.addAttribute("type", type);           // 검색 분류
        model.addAttribute("keyword", keyword);     // 키워드
        return "list";

    }



}
// 13. templates 안에 해당 HTML을 생성한다.