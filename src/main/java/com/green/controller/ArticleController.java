package com.green.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.dto.ArticleDto;
import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;

	// data 입력
	@GetMapping("/articles/WriteForm")
	public String writeForm() {
		
		return "articles/write";
		
	}
	
	//-----------------------------------------------------------------------------------------------------------	
	
	/*
	@GetMapping("articles/Write")
	//public String write( @RequestParam("title") String title, @RequestParam("content") String content ) {
	//public String write( String title, String content ) {
	public String write( ArticleDto articleDto ) {
		
		System.out.println( articleDto );	
		
		return "redirect:/articles/List";
	}
	*/
	
	//-----------------------------------------------------------------------------------------------------------
	
	// data 저장
	// @GetMapping("articles/Write")
	// 405 error : method="post" 인데, 받는놈이 @GetMapping 으로 받아서 나는 에러
	// Post 로 던지는 놈은 @PostMapping 으로 받자
	// .mustache 에서 보내는 name="title" , name="content" 를 Controller 에 보냄(form tag, submit)
	// F12(개발자 도구) : Payload : FormData - title : aaa, content : 에이에이에이
	// Controller 에서는 변수 두개를 써서 받지 않고 ArticleDto 를 생성해서 ArticleDto 하나로만 받음
	@PostMapping("articles/Write")
	public String write( ArticleDto articleDto ) {

		// 넘어온 data 확인
		System.out.println( articleDto );	// 교재 : ArticleForm
		// Console : ArticleDto [title=aaa, content=에이에이에이] -> 값 전달받고 있음
		// 1. http://localhost:9090/articles/WriteForm 에서 입력한 것이 ArticleDto 에 저장
		// 1-2. ArticleDto : private String title, private String content + Getter/Setter + toString
		// 2. Controller 에서 ArticleDto 를 받음
		// 3. sysout 로 Console 확인해보니 http://localhost:9090/articles/WriteForm 에서 입력한 Data 를 전달받음
		
		// db 에 저장 = h2 article 테이블에 저장
		// 1. Dto -> Entity 로 보냄
		// 2. Entity -> Repository 로 보내서 저장
		// Entity : db 의 테이블 이다 -> mapper.xml 과 같은 역할
		//-----------------------------------------------------------------------------------------
		// 1. Dto -> Entity 로 보냄
		Article article = articleDto.toEntity();
		
		// 2. Repository(인터페이스)를 사용하여 Entity 를 저장
		Article saved = articleRepository.save( article );	// insert
		// 위(1번)에서 입력받은 data (article) 를 articleRepository 에 저장(.save)
		System.out.println("saved: " + saved);
		
		return "redirect:/articles/List";
		
		// articleDto : 2칸 , Article : 3칸 -> 담을 때 변환 작업 필요 -> ArticleDto.java 메소드 추가
		// ArticleDto.java 메소드 : null 이 넘어가도록(articleDto 에는 id 없으니 null 이 전달되어야함)
		// Article.java 는 id 에 null 이 들어갈 수 있도록, long 이 아닌 Long 으로 타입을 준다.
		
	}
	
	// data 조회 : 1번 data
	// PathVariable -> Get 방식
	// 1번 방법Error : java.lang.IllegalArgumentException: Name for argument of type [java.lang.Long] not specified
	// 해결 1 : @PathVariable Long id  ->  @PathVariable(value="id") Long id
	// 해결 2 : STS 방식 : 설정 추가 = Ensure that the compiler uses the '-parameters' flag.
	//   프로젝트 -> properties -> Java Compiler -> 
	//   ✅ Enable project specific settings 체크
	//   ✅ 아래 Store information ~ 체크
	// 참고) Article 에 기본 생성자 생성 안되어 있으면 에러, @NoArgsConstructor 추가 
	@GetMapping("/articles/{id}")
	public String view( @PathVariable Long id, Model model ) {	// articleRepository.findById(id) 위해 @PathVariable 받기
		
		// 1번 방법
		//Optional<Article> articleEntity = articleRepository.findById(id);	// Article Type 으로 articleEntity 변수 생성
		//System.out.println("1번 방법으로 조회 결과= " + articleEntity);	// 1번 방법으로 조회 결과= Optional[Article [id=1, title=aaa, content=aaa]]
		// Article articleEntity = articleRepository.findById(id); -> Error : Type mismatch
		// F2 -> Optional<Article> 로 전환
		// 해석 : 값이 있으면 Article 을 리턴, 값이 없으면 null 리턴
		
		// 2번 방법
		Article articleEntity = articleRepository.findById(id).orElse(null);
		// 값이 있으면 Article 을 리턴, 값이 없으면 (.orElse) null 리턴
		// 객체(= Article)는 값이 없으면 null 을 받을 수 있다
		System.out.println("2번 방법으로 조회 결과= " + articleEntity);	// 2번 방법으로 조회 결과= Article [id=1, title=bbb, content=bbb]
		
		model.addAttribute("article", articleEntity);	// 조회한 결과 -> model 에 담음
		return "articles/view";	// view.mustache
		
	}
	
	// 목록 조회
	@GetMapping("/articles/List")
	public String list( Model model ) {	// 메뉴를 찍기위해 Model 필요
		
		// List<Article> articleEntityList = articleRepository.findAll();  <-  오류 발생
		// 1. 오류처리 1번 방법
		//List<Article> articleEntityList = (List<Article>) articleRepository.findAll();	// F2 -> add cast
		// 복습1) List<MenuVo> menuList = new ArrayList<>();
		// 자료체크) if( menuList.size() == 0 )  ->  자료가 없으면
		
		// 복습2) MenuVo menuVo = null;
		//              menuVo = new MenuVo();  ->  new : null 이 없음
		// 자료체크) if( menuVo == null )  ->  조회한 자료가 없으면
		
		// .findById : 한개만 조회할땐 null 있을 수 있음
		// .findAll : 그 안에 이미 new ArrayList<>() 과정이 있음 -> null 이 없음 -> .orElse() 필요없음
		
		// 2. 오류처리 2번 방법
		// ArticleRepository interface 에 함수를 등록
		List<Article> articleEntityList = articleRepository.findAll();
		System.out.println("전체목록: " + articleEntityList);	// [Article [id=1, title=fff, content=fff]]
		
		model.addAttribute("articleList", articleEntityList);
		
		return "articles/list";
		
	}
	
	// 데이터 수정페이지로 이동
	@GetMapping("/articles/{id}/EditForm")
	public String editForm( @PathVariable(value="id") Long id, Model model ) {
		// Mapping 주소에 {id} 가 있으니 editForm() 안에 넘겨받을 값 있어야함 -> @PathVariable(value="id") Long id
		
		// 수정할 데이터를 조회
		Article articleEntity = articleRepository.findById(id).orElse(null);
		// -> 한줄의 데이터는 null 을 가질 수 있으므로 type mismatch 에러 발생
		System.out.println( "==========================articleEntity: " + articleEntity );
		
		// 조회한 데이터를 model 에 저장
		model.addAttribute("article", articleEntity);
		
		// 수정 페이지로 이동
		return "articles/edit";
		
	}
	
	// 데이터 수정
	@PostMapping("/articles/Edit")
	public String edit( ArticleForm articleForm ) {
		
		log.info( "수정용 데이터: " + articleForm.toString() );
		
		// db 수정
		// 1. Dto -> Entity 로 변환
		/*
		Long id = articleForm.getId();
		String title = articleForm.getTitle();
		String content = articleForm.getContent();
		Article articleEntity = new Article( id, title, content );
		*/
		Article articleEntity = articleForm.toEntity();	// 위에 /* 4줄 */ 줄인 것이 여기 한줄
		
		// 2. Entity 를 db 에 수정(저장) 한다
		// 2-1. 수정할 데이터를 찾아서 (db 의 data 를 가져온다)
		Long id = articleForm.getId();
		Article target = articleRepository.findById(id).orElse(null);
		
		// 2-2. 필요한 데이터를 추적&변경 한다
		if(target != null) {	// 자료가 있으면 저장한다(수정)
			articleRepository.save( articleEntity );
		}
		
		// return "redirect:/articles/view/" + id;
		return "redirect:/articles/List";
		
	}
	
	// 데이터 삭제
	@GetMapping("/articles/{id}/Delete")
	public String delete( @PathVariable(value="id") Long id, RedirectAttributes rttr ) {
		
		// 1. 삭제할 대상을 검색한다
		Article target = articleRepository.findById(id).orElse(null);
		
		// 2. 대상 Entity 를 삭제한다
		if( target != null ) {
			
			articleRepository.delete( target );
			
			// RedirectAttributes rttr : redirect 할 페이지에서 사용할 데이터를 넘김
			// Model 과 다른 점 : 한번 쓰면 사라지는 휘발성 데이터
			// 삭제 후 임시 메시지를 list.mustache 가 출력한다
			rttr.addFlashAttribute("msg", id + "번 자료가 삭제되었습니다");
			// header.mustache 에 출력
			
		}
		
		return "redirect:/articles/List";
		
	}
	
	
	
}















