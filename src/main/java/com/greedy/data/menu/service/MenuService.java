package com.greedy.data.menu.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.data.menu.dto.MenuDTO;
import com.greedy.data.menu.entity.Menu;
import com.greedy.data.menu.repository.MenuRepository;

@Service
public class MenuService {

	private final MenuRepository menuRepository;
	private final ModelMapper modelMapper;
	
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
	}
	
	public MenuDTO findMenyByCode(int menuCode) {
		
		/* findById 메소드는 이미 구현되어 있으므로 인터페이스에 따로 정의할 필요가 없다.
		 * findById 의 반환값은 Optional 타입이다. Optional 타입은 NPE을 방지하기 위한 다양한 기능이 존재한다.
		 * 해당 id로 조회되지 못했을 경우 IllegalArgumentException을 발생시킨다. */
		Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
		
		/* modelMapper를 이용하여 entity를 DTO로 변환해서 반환 */
		return modelMapper.map(menu, MenuDTO.class);
	}
	
	
	
	
	
	
	
	
	
	
}
