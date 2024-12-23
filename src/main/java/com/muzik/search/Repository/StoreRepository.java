// 10. 사용할 Entity에 필요한 쿼리(SQL)문을 추가 작성

package com.muzik.search.Repository;

import com.muzik.search.Entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// 참고할 Entity와 id의 데이터형으로 연결
@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    // 분류(총판명 총판ID, 총판장ID, 총판장), 키워드(찾을 내용)
    // 총판명 총판ID, 총판장ID, 총판장, 총판명 + 총판ID, 총판장ID + 총판장, 전체
    // 결과값이 여러개이면 List<StoreEntity>, Page<StoreEntity>
    // 결과값이 하나이면 StoreEntity, Optional<StoreEntity>

    // 총판명 검색
    // 1.
    Page<StoreEntity> findByStoreNameContaining(String storeName, Pageable pageable);
    // 2.
    // StoreEntity u(별칭) : 별칭은 테이블명을 약식표기(알파벳 1글자 지정)
    // String keyword => @Param("keyword") => :keyword
    @Query("SELECT u from StoreEntity u where u.storeName like %:keyword%")
    Page<StoreEntity> searchStoreName(@Param("keyword") String keyword, Pageable pageable);


    // 총판ID
    Page<StoreEntity> findByStoreidContaining(String keyword, Pageable pageable);
    @Query("select u from StoreEntity u where u.storeid like %:keyword%")
    Page<StoreEntity> searchStoreid(@Param("keyword") String keyword, Pageable pageable);


    // 총판장ID
    Page<StoreEntity> findByStorechiefidContaining(String keyword, Pageable pageable);
    @Query("select u from StoreEntity u where u.storechiefid like %:keyword%")
    Page<StoreEntity> searchStorechiefid(@Param("keyword") String keyword, Pageable pageable);


    // 총판장
    Page<StoreEntity> findByStorechiefContaining(String keyword, Pageable pageable);
    @Query("select u from StoreEntity u where u.storechief like %:keyword%")
    Page<StoreEntity> searchStorechief(@Param("keyword") String keyword, Pageable pageable);


    // 총판명 + 총판ID
    Page<StoreEntity> findByStoreNameOrStoreidContaining(String keyword1, String keyword2, Pageable pageable);
    // 긴문장을 다중행으로 처리 시, 반드시 문자 뒤와 앞에 빈공백을 포함시켜야 한다.
    @Query("select u from StoreEntity u where " +
            " u.storeName like %:keyword% or u.storeid like %:keyword%")
    Page<StoreEntity> searchStoreNameOrStoreid(@Param("keyword") String keyword, Pageable pageable);


    // 총판장ID + 총판장
    Page<StoreEntity> findByStorechiefidContainingOrStorechiefContaining(String keyword1, String keyword2, Pageable pageable);
    // 긴문장을 다중행으로 처리 시, 반드시 문자 뒤와 앞에 빈공백을 포함시켜야 한다.
    @Query("select u from StoreEntity u where " +
            " u.storechiefid like %:keyword% or u.storechief like %:keyword%")
    Page<StoreEntity> searchStorechiefidOrStorechief(@Param("keyword") String keyword, Pageable pageable);


    // 전체
    Page<StoreEntity> findByStoreNameContainingOrStoreidContainingOrStorechiefidContainingOrStorechiefContaining(
            String keyword1, String keyword2, String keyword3, String keyword4, Pageable pageable);
    // 긴문장을 다중행으로 처리 시, 반드시 문자 뒤와 앞에 빈공백을 포함시켜야 한다.
    @Query("select u from StoreEntity u where " +
            " u.storeName like %:keyword% or u.storeid like %:keyword% " +
            " or u.storechiefid like %:keyword% or u.storechief like %:keyword%")
    Page<StoreEntity> searchAll(@Param("keyword") String keyword, Pageable pageable);

}
/*
    findBy 검색(조회) 작업하는 JPA 명령을 이용한 메소드 작성법

    1. 기본키를 이용한 조회
    findById(변수) : 변수값으로 id와 일치하는 데이터를 조회

    2. 모든 데이터 조회
    findByAll() : 모든 데이터를 조회

    3. 지정 필드(Entity의 변수명)로 조회
    findBy필드명(변수) : 변수값으로 필드명의 값과 일치하는 데이터를 조회
    예) findByStoreName(Sting name) : name의 값이 storeName필드의 값과 일치하면 조회

    4. 비슷한 값 조회(%값%) ==> 검색기능
    findBy필드명Containing(변수) : 변수값이 포함된 데이터를 조회
    예) findByStoreNameContaining(Sting name) : name의 값이 storeName필드에 포함된 데이터만 조회

    5. 여러 필드를 사용(AND-모두 맞으면, OR-~중 하나라도 맞으면)
       단점 : 서비스 쪽에서 문법이 복잡하다.
    findBy필드명1AND필드명2(변수1, 변수2) : 변수1값이 필드명1과 같고, 변수2값이 필드명2와 같으면 조회
    findBy필드명1Or필드명2(변수1, 변수2) : 변수1값이 필드명1과 같거나, 변수2값이 필드명2와 같으면 조회
     예) findByStoreNameANDStoreid(Sting name, String id) : name값이 storeName과 같고, id값이 storeid와 같으면 조회
     예) findByStoreNameOrStoreid(Sting name, String id) : name값이 storeName과 같거나, id값이 storeid와 같으면 조회

     6. 정렬(ASC-오름차순, DESC-내림차순)
     findBy필드명OrderBy필드명2ASC(변수) : 변수값이 필드명1과 같은 데이터를 필드명2로 오름차순해서 조회
     findBy필드명OrderBy필드명2DESC(변수) : 변수값이 필드명1과 같은 데이터를 필드명2로 내림차순해서 조회
     예) findByStoreNameOrderByIdxASC(Sting name) : name값이 storeName과 같은 데이터를 idx로 오름차순해서 조회
     예) findByStoreNameOrderByIdxDESC(Sting name) : name값이 storeName과 같은 데이터를 idx로 내림차순해서 조회

     7. 정확한 데이터를 조회할 때(문자열 데이터)
     findBy필드명Like(변수) : 변수값과 필드의 내용이 정확히 일치하는 데이터를 조회
     예) findByStoreNameLike(String name) : name값이 storeName과 정확히 일치하는 데이터를 조회

     8.페이징 처리
     findBy필드명(변수, Pageable 페이지변수) : 변수값과 같은 데이터를 해당 페이지부분을 조회
     예) findByStoreName(String name, Pageable pageable)

     9. 숫자, 날짜 범위처리
     GreaterThen(>), LessThen(<), GreaterThenEqual(>=), LessThenEqual(<=)
     예) findByAgeGreateThen(Integer age) : age값 보다 큰 데이터를 조회

     10. 값이 비어있는 데이터 조회
     findBy필드명IsNull();
     예) findByStoreNameIsNull() : storeName 필드에 값이 없는 데이터만 조회
*/