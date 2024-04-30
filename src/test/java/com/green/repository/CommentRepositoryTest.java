package com.green.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.green.entity.Article;
import com.green.entity.Comments;

// @DataJpaTest : 해당 클래스를 JPA와 연동해 테스트하겠다는 선언
@DataJpaTest
@AutoConfigureTestDatabase(
		replace = AutoConfigureTestDatabase.Replace.NONE	// oracle 이 명령 추가해줘야 error 안남
		)
class CommentRepositoryTest {

	@Autowired
    private CommentRepository commentRepository;	// interface 와 연결

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")  // 테스트 이름
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 4L;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");
            Comments a = new Comments(1L, article, "Park", "굿 윌 헌팅");
            Comments b = new Comments(2L, article, "Kim", "아이 엠 샘");
            Comments c = new Comments(3L, article, "Choi", "쇼생크 탈출");
            List<Comments> expected = Arrays.asList(a, b, c);			// 배열을 ArrayList 로 변경
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 1L;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 9L;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = null;
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "9번 글 자체가 없으므로 댓글은 비어 있어야 함");
        }

        /* Case 4: 999번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 999L;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = null;
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "999번 글 자체가 없으므로, 댓글은 비어 있어야 함");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = -1L;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = null;
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "-1번 글 자체가 없으므로, 댓글은 비어 있어야 함");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "Park";
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            Comments a = new Comments(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"),
                    nickname, "굿 윌 헌팅");
            Comments b = new Comments(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"),
                    nickname, "치킨");
            Comments c = new Comments(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"),
                    nickname, "조깅");
            List<Comments> expected = Arrays.asList(a, b, c);
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }

        /* Case 2: "Kim"의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "Kim";
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            Comments a = new Comments(2L, new Article(4L, "당신의 인생 영화는?",
                    "댓글 고"), nickname, "아이 엠 샘");
            Comments b = new Comments(5L, new Article(5L, "당신의 소울 푸드는?",
                    "댓글 고고"), nickname, "샤브샤브");
            Comments c = new Comments(8L, new Article(6L, "당신의 취미는?",
                    "댓글 고고고"), nickname, "유튜브 시청");
            List<Comments> expected = Arrays.asList(a, b, c);
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "Kim의 모든 댓글을 출력!");
        }

        /* Case 3: null의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = null;
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "null의 모든 댓글을 출력!");
        }

        /* Case 4: ""의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "";
            // 2. 실제 데이터
            List<Comments> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            List<Comments> expected = Arrays.asList();
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "\"\"의 모든 댓글을 출력!");
        }
    }
}