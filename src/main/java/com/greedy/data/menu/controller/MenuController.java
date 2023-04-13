package com.greedy.data.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greedy.data.menu.dto.MenuDTO;
import com.greedy.data.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private final MenuService menuService;
	
	public MenuController(MenuService menuSerivce) {
		this.menuService = menuSerivce;
	}
	
	@GetMapping("/{menuCode}")
	public String findMenyByCode(@PathVariable int menuCode, Model model) {
		
		MenuDTO menu = menuService.findMenyByCode(menuCode);
		
		model.addAttribute("menu", menu);
		
		return "menu/one";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
