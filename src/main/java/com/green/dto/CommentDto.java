package com.green.dto;

import com.green.entity.Comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long   id;			// 댓글 id
	private Long   articleId;	// article 의 부모글 id
	private String nickname;	// 작성자 별명
	private String body;		// 댓글 내용
	
	// CommentDto  <-  Comments
	public static CommentDto createCommentDto( Comments comments ) {	//static : 객체 이름으로 땡겨쓰겠다
		
		return new CommentDto (
				comments.getId(),
				comments.getArticle().getId(),
				comments.getNickname(),
				comments.getBody()
				);
		
	}
	
}
