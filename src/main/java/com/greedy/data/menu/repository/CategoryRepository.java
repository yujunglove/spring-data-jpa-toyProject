package com.greedy.data.menu.repository;

import com.greedy.data.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository <Category, Integer> {
    //findALl 메소드를 사용할 수 있지만 여기서는 직접 jpql or native query를 작성해야 하는 경우 설정을 확인한다.


    /*jpql 작성시에는 value만 작성해도 되지만 native query를 작성시에는 반드시 nativeQuery = true 속성을 정의해야 한다.*/
    //Query은 수행해야 하는 value, jpql 쿼리, native 쿼리작성 가능
    @Query(value = "SELECT CATEGORY_CODE, CATEGORY_NAME, REF_CATEGORY_CODE FROM TBL_CATEGORY ORDER BY CATEGORY_CODE ASC"
            , nativeQuery = true)
    public List<Category> findAllCategory();
}
