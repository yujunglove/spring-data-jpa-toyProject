package com.greedy.data.menu.service;

import java.util.List;
import java.util.stream.Collectors;

import com.greedy.data.menu.dto.CategoryDTO;
import com.greedy.data.menu.entity.Category;
import com.greedy.data.menu.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.data.menu.dto.MenuDTO;
import com.greedy.data.menu.entity.Menu;
import com.greedy.data.menu.repository.MenuRepository;

import javax.transaction.Transactional;

@Service
public class MenuService {

	//의존성 주입
	private final MenuRepository menuRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper,CategoryRepository categoryRepository) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
		this.categoryRepository = categoryRepository;
	}
	
	public MenuDTO findMenuByCode(int menuCode) {
		
		/* findById 메소드는 이미 구현되어 있으므로 인터페이스에 따로 정의할 필요가 없다.
		 * findById 의 반환값은 Optional 타입이다. Optional 타입은 NPE을 방지하기 위한 다양한 기능이 존재한다.
		 * 해당 id로 조회되지 못했을 경우 IllegalArgumentException을 발생시킨다. */
		
		
		//PK 조회 <인터페이스에서 선언만하고, 우리는 findById만 하면 된다> , 요 익셉션객체를 생성한다.
		//조회가 널 값이면 -> IllegalArgumentException을 발생시키겠다.
		Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
		
		/* modelMapper를 이용하여 entity를 DTO로 변환해서 반환 */
		return modelMapper.map(menu, MenuDTO.class);
	}
	
	//2. findAll - >페이징 처리 전
	public List<MenuDTO> findMenuList() {
		//findAll 메소드는 이미 구현 되어 있으므로 인터페이스에 따로 정의할 필요강 ㅓㅄ다.
		//해당 타입의 인스턴스를 모두 반환한다.  List<MenuDTO> 타입이니까 list로 처리
		//menuCode라는 필드명 으로 sorting 되어 찾아온다, descending = 반대 (내림차순 정리)
		List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
		
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
	}
	
	//3, Page -> 페이징 처리 후
	public Page<MenuDTO> findMenuList(Pageable pageable) {
		
		//page 파라미터가 Pageable의 number 값으로 넘어오는데 해당 값이 조회시에는 인텍스 기준이 되어야 해서 -1 처리가 필요하다
		//만약 0보다 작거나 같다면, 
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 
				pageable.getPageSize(), 
				Sort.by("menuCode").descending());

		//페이지라는 타입은 본인이 map이라는 메소드를 가지고 있어서 조금 더 간략하게 변환이 가능하다.
		Page<Menu> menuList = menuRepository.findAll(pageable);

		//컨트롤러로 돌아간다.
		return menuList.map(menu -> modelMapper.map(menu,MenuDTO.class));
	}

	//QueryMethod
	/* 4. QueryMethod */
	public List<MenuDTO> menuTest1(Integer menuPrice) {

		//List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);

		//List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
		List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
	}

	//5. JPQL or native Query
	public List<CategoryDTO> findAllCategory() {

		//호출
		List<Category> categoryList = categoryRepository.findAllCategory();

		return  categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}


	//6. save
	@Transactional
	public void registNewMenu(MenuDTO newMenu) {
	//CrudRepository에 미리 정의 되어 있는 save() 메소들르 통해 하나의 엔티티를 저장할 수 있다.
		menuRepository.save(modelMapper.map(newMenu,Menu.class));
	}


	//7. 수정하기 - 엔티티 조회 후 값 변경
	@Transactional
	public void modifyMenu(MenuDTO modifyMenu) {

		//; 하면 메뉴라는 엔티티가 아니라 옵셔널이라는 타입이라 맞지 않게 나옴 -> 그럼 그걸 꺼내야 한다.
		Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode())
				.orElseThrow(IllegalArgumentException::new);

		//메뉴 명만 수정하니까
		foundMenu.setMenuName(modifyMenu.getMenuName());
	}
	@Transactional
	public void deleteMenu(Integer menuCode) {

		menuRepository.deleteById(menuCode);
	}
}
