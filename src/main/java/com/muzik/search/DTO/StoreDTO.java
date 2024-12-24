package com.muzik.search.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class StoreDTO {

    @Schema(description = "일련번호", example = "1")
    private Integer idx;

    @Schema(description = "총판이름", example = "GGG호텔")
    @NotBlank(message = "총판이름은 생략할 수 없습니다.")
    private String storeName;

    @Schema(description = "총판ID", example = "gggmaster")
    @NotBlank(message = "총판ID는 생략할 수 없습니다.")
    @Size(min=6, max=12, message="총판ID는 6~12자로 구성해야 합니다.")
    private String storeid;

    @Schema(description = "총판장ID", example = "gggmaster@gmail.com")
    @NotBlank(message = "총판장ID는 생략할 수 없습니다.")
    @Email(message = "총판장ID는 이메일 형식으로 입력해야 합니다.")
    private String storechiefid;

    @Schema(description = "총판장", example = "GGG호텔총괄대표자")
    private String storechief;

    @Schema(description = "총판연락처", example = "010-8741-7885")
    private String storetel;

    @Schema(description = "삭제여부", example = "Y/N")
    private String storedel;

    @Schema(description = "등록일자", example = "24-11-12")
    private LocalDateTime regdate;

    @Schema(description = "수정일자", example = "24-11-12")
    private LocalDateTime moddate;

    // 1. HTML에서 추가로 받을 변수 있으면 / 변수를 DTO로 함께 받고자 하면 변수를 추가
    // private String search;
    // 2. Controller에서 개별변수로 받아서 사용

}
// DTO까지 설계가 완료되면 1차 확인(단위 테스트)