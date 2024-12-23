package com.muzik.search.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class StoreDTO {

    private Integer idx;

    private String storeName;

    private String storeid;

    private String storechiefid;

    private String storechief;

    private String storetel;

    private String storedel;

    private LocalDateTime regdate;

    private LocalDateTime moddate;

    // 1. HTML에서 추가로 받을 변수 있으면 / 변수를 DTO로 함께 받고자 하면 변수를 추가
    // private String search;
    // 2. Controller에서 개별변수로 받아서 사용

}
// DTO까지 설계가 완료되면 1차 확인(단위 테스트)