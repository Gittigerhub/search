package com.muzik.search.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter // 개별적으로 변수 처리 시

@NoArgsConstructor @AllArgsConstructor

// 상황에 따라 지정해서 사용(2개 이상의 테이블 join시 ToSring() 사용하지 않는다.)
// 2개 테이블에 TOString() 존재하면 무한반복으로 처리
// 2개 테이블 중 한 테이블(부모테이블)에만 ToSting()을 사용
@ToString

@Builder
@Table(name = "store")
@SequenceGenerator(                  // 순차처리 생성기
        name = "store_seq",          // 순차값 장보를 저장할 테이블 명
        sequenceName = "store_seq",  // 필드 명
        initialValue = 1,            // 초기 값
        allocationSize = 1           // 할당 값(증가 값)
)
public class StoreEntity extends BaseEntity {

    @Id                              // 기본키
    // 자동화 처리
    // GenerationType.AUTO           // 자동으로 타입을 설정
    // GenerationType.IDENTITY       // 값이 중복되지 않도록 생성
    // GenerationType.SEQUENCE       // 값을 순차적으로 생성
    // generator : 자동생성할 참조 테이블
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_seq")
    @Column(name = "idx", nullable = false)     // 생략 불가능
    private Integer idx;

    // 필드명은 가능하면 소문자로 구성
    @Column(name = "storename", length = 45)    // nullable 생략 처리
    private String storeName;
    @Column(name = "storeid", length = 45)    // nullable 생략 처리
    private String storeid;
    @Column(name = "storechiefid", length = 45)    // nullable 생략 처리
    private String storechiefid;
    @Column(name = "storechief", length = 45)    // nullable 생략 처리
    private String storechief;
    @Column(name = "storetel", length = 25)    // nullable 생략 처리
    private String storetel;
    @Column(name = "storedel", length = 1)    // nullable 생략 처리
    private String storedel;

}
// 반드시 변수명, 필드명에 오타 확인
// 서버를 실행한 후 Entity를 변경했을 때는 application.properties에서
// update를 create로 수정 후 서버를 1번 실행하면 테이블정보가 수정된다.