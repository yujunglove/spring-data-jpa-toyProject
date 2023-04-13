package com.greedy.data.menu.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.data.menu.entity.Menu;

import java.util.List;

//내가 찾아오고자 하는 테이블, 내가 찾아오려고 하는 피케이의 타입
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    //JpaRepository 를 상속 받아서 사용하는 메소드 외의 메소드는 직접 정의한다.
    //전달 받은 가격을 초과하는 메뉴의 목록을 조회하는 메소드
    /* 전달 받은 가격을 초과하는 메뉴의 목록을 조회하는 메소드 */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    //전달 받은 가격을 초과하는 메뉴의 목록을 가격 순으로 조회하는 메소드
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);
    /* 전달 받은 가격을 초과하는 메뉴의 목록을 전달 받는 정렬 기준으로 조회하는 메소드 */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);


}
