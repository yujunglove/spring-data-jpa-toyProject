package com.greedy.data.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.data.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {


}
