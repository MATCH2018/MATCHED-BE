package com.linked.matched.service.post;

import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test(){

        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(8)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        //when
//        postService.write(postCreate);
//
//        //then
//        Assertions.assertEquals(1L,postRepository.count());
//        Post post = postRepository.findAll().get(0);
//        Assertions.assertEquals("제목임다.",post.getTitle());
//        Assertions.assertEquals("내용임다.",post.getContent());
//        Assertions.assertEquals(BoardStatus.valueOf("club"),post.getBoardName());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        //given
        Post club = Post.builder()
                .postId(1L)
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();
        postRepository.save(club);

        Post poom = Post.builder()
                .postId(2L)
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();
        postRepository.save(poom);

        //when
        PostResponse post = postService.findPost(1L);

        //then
        Assertions.assertNotNull(post);
        Assertions.assertEquals(post.getTitle(),"제목임다.");
    }

    @Test
    @DisplayName("글 삭제")
    void test3(){
        //given
        Post club = Post.builder()
                .postId(1L)
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();
        postRepository.save(club);

        //when
//        postService.delete(1L);

        //then
//        Assertions.assertEquals(postRepository.count(),0);
    }

    @Test
    @DisplayName("글 삭제 후 다음 글 조회")
    void test4(){
        //given
        Post club = Post.builder()
                .postId(1L)
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();
        postRepository.save(club);

        Post poom = Post.builder()
                .postId(2L)
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();
        postRepository.save(poom);

        //when
//        postService.delete(1L);
//
//        PostResponse post = postService.findPost(2L);
//
//        //then
//        Assertions.assertNotNull(post);
//        Assertions.assertEquals(post.getTitle(),"품앗이 입니다.");
    }

    @Test
    @DisplayName("글 수정")
    void test5(){
        //given
        Post club = Post.builder()
                .postId(1L)
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(8)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(club);

        //when

        PostEdit poom = PostEdit.builder()
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();

//        postService.edit(1L,poom);
//
//        //then
//        Assertions.assertEquals(1L,postRepository.count());
//        Post post = postRepository.findAll().get(0);
//        Assertions.assertEquals("품앗이 입니다.",post.getTitle());
//        Assertions.assertEquals("품앗이 인원 구합니다.",post.getContent());
//        Assertions.assertEquals(BoardStatus.valueOf("poom"),post.getBoardName());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test6(){
        //given
        Post club = Post.builder()
                .postId(1L)
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(8)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(club);

        Post poom = Post.builder()
                .postId(2L)
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();

        postRepository.save(poom);

        Post club2 = Post.builder()
                .postId(3L)
                .title("제목임다2.")
                .content("내용임다2.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .build();

        postRepository.save(club2);

        PostSearch page = PostSearch.builder()
                .page(1)
                .build();

        //when
        List<PostResponse> clubList = postService.getList("club",page);

        Assertions.assertEquals(clubList.size(),2);
        Assertions.assertEquals(clubList.get(0).getTitle(),"제목임다2.");
        Assertions.assertEquals(clubList.get(0).getPostId(),3L);
        Assertions.assertEquals(clubList.get(1).getTitle(),"제목임다.");
        Assertions.assertEquals(clubList.get(1).getPostId(),1L);
    }


}