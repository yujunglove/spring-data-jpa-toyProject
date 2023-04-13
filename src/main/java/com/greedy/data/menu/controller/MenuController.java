package com.greedy.data.menu.controller;

import java.util.List;

import com.greedy.data.common.Pagenation;
import com.greedy.data.common.PagingButtonInfo;
import com.greedy.data.menu.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.greedy.data.menu.dto.MenuDTO;
import com.greedy.data.menu.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

	private final MenuService menuService;

	public MenuController(MenuService menuSerivce) {
		this.menuService = menuSerivce;
	}

	@GetMapping("/{menuCode}")
	public String findMenyByCode(@PathVariable int menuCode, Model model) {

		MenuDTO menu = menuService.findMenuByCode(menuCode);

		model.addAttribute("menu", menu);

		return "menu/one";
	}

	/* 페이징 처리 전 메소드 */
//	@GetMapping("/list")
//	public String findMenuList(Model model) {
//
//		List<MenuDTO> menuList = menuService.findMenuList();
//
//		model.addAttribute("menuList", menuList);
//
//		return "menu/list";
//	}

	/* 페이징 처리 후 */
	//첫번쨰 페이지면 disabled 하겠습니다.
	@GetMapping("/list")
	public String findMenuList(@PageableDefault Pageable pageable, Model model) {
		/*page -> number , size, sort 파라미터가 Pageable 객체에 담긴다.*/
		log.info("pageable : {} " ,pageable);

		Page<MenuDTO> menuList = menuService.findMenuList(pageable);

		//ListMenuDTO를 얻을 수 있음
		log.info("조회한 내용 목록 : {}",menuList.getContent());

		log.info("총 페이지 수 : {} ",menuList.getTotalPages());
		log.info("총 메뉴 수 : {} ",menuList.getTotalElements());
		log.info("해당 페이지에 표시 될 요소의 수 : {} ",menuList.getSize());
		log.info("해당 페이지에 실제 요소 수 : {} ",menuList.getNumberOfElements());
		log.info("첫 페이지 여부 : {} ",menuList.isFirst());
		log.info("마지막 페이지 수 : {} ",menuList.isLast());
		log.info("정렬방식 : {} ",menuList.getSort());
		log.info("여러 페이지 중 현재 인덱스 : {} ",menuList.getNumber());

		//페이징 네이션이라는 클래스 안에 PagingButtonInfo을 정의했고 페이지라는 타입의 연산을 처리해주는 기능

		PagingButtonInfo paging = Pagenation.getPagingButtonInfo(menuList);

		model.addAttribute("paging",paging);
		model.addAttribute("menuList",menuList);

		return "menu/list";
	}

	//페이지 이동 메소드
	@GetMapping("/querymethod")
	public void queryMethodPage() {}

	@GetMapping("/test1")
	public String menuTest1(@RequestParam Integer menuPrice, Model model) {

		List<MenuDTO> menuList = menuService.menuTest1(menuPrice);

		model.addAttribute("menuList", menuList);

		return "menu/list2";

	}

	@GetMapping("/regist")
	public  void registPage() {}

	@GetMapping(value = "/category",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<CategoryDTO> findCategoryList() {
		return menuService.findAllCategory();
	}

	@PostMapping("/regist")
	public String registNewMenu(MenuDTO newMenu) {

	menuService.registNewMenu(newMenu);

	return "redirect:/menu/list";
	}

	//수정
	@GetMapping("/modify")
	public void modifyPage() {

	}

	@PostMapping("/modify")
	public String modifyMenu(MenuDTO modifyMenu) {

		menuService.modifyMenu(modifyMenu);

		//해당 메뉴의 상세 페이지로 다시 넘어갈 수 있도록 리 다이렉트
		return "redirect:/menu/" + modifyMenu.getMenuCode();

	}

	@GetMapping("/delete")
	public void deletePage() {

	}

	@PostMapping("/delete")
	public String deleteMenu(@RequestParam Integer menuCode) {

		menuService.deleteMenu(menuCode);
		return "redirect:/menu/list";
	}

















}
