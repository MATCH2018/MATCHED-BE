package com.linked.matched.service.post;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.entity.Post;
import com.linked.matched.entity.User;
import com.linked.matched.entity.status.BoardStatus;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.post.PostRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEditor;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.security.Principal;
import java.util.List;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User user=User.builder()
                .userId(1L)
                .loginId("3333")
                .password("1234")
                .department("1")
                .name("안안")
                .authorityName("ROLE_USER")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void clean(){
        postRepository.deleteAll();
        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());
        userRepository.deleteById(user.getUserId());
    }

    @WithMockUser
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

        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());
        //when
        postService.write(postCreate,user.getUserId());

        //then
        Assertions.assertEquals(1L,postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목임다.",post.getTitle());
        Assertions.assertEquals("내용임다.",post.getContent());
        Assertions.assertEquals(BoardStatus.valueOf("club"),post.getBoardName());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        //given
        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());

        Post club = Post.builder()
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();
        postRepository.save(club);

        Post poom = Post.builder()
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .user(user)
                .build();
        postRepository.save(poom);

        //when
        PostOneResponse post = postService.findPost(poom.getPostId());
        //then
        Assertions.assertNotNull(postRepository.count());
        Assertions.assertEquals("품앗이 입니다.",post.getTitle());
    }

    @Test
    @DisplayName("글 삭제")
    void test3(){
        //given
        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());

        System.out.println("user: "+user.getLoginId()+" : "+user.getUserId());

        Post poom = Post.builder()
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("poom"))
                .user(user)
                .build();
        postRepository.save(poom);

        System.out.println("poom:"+poom.getUser().getUserId());

        //when
        postService.delete(poom.getPostId(), user.getLoginId());
        //then
        Assertions.assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 삭제 후 다음 글 조회")
    void test4(){
        //given
        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());

        Post club = Post.builder()
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(3)
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();
        postRepository.save(club);

        Post poom = Post.builder()
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();
        postRepository.save(poom);


        //when
        postService.delete(club.getPostId(), user.getLoginId());

        PostOneResponse post = postService.findPost(poom.getPostId());

        //then
        Assertions.assertNotNull(post);
        Assertions.assertEquals(post.getTitle(),"품앗이 입니다.");
    }

    @Test
    @DisplayName("글 수정")
    void test5(){
        //given
        User user = userRepository.findByLoginId("3333").orElseThrow(() -> new UserNotFound());

        Post club = Post.builder()
                .title("제목임다.")
                .content("내용임다.")
                .limitPeople(8)
                .boardName(BoardStatus.valueOf("club"))
                .user(user)
                .build();

        postRepository.save(club);

        //when

        PostEditor poom = PostEditor.builder()
                .title("품앗이 입니다.")
                .content("품앗이 인원 구합니다.")
                .limitPeople(5)
                .boardName(BoardStatus.valueOf("poom"))
                .build();

        postService.edit(club.getPostId(),poom,user.getUserId());

        //then
        Assertions.assertEquals(1L,postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("품앗이 입니다.",post.getTitle());
        Assertions.assertEquals("품앗이 인원 구합니다.",post.getContent());
        Assertions.assertEquals(BoardStatus.valueOf("poom"),post.getBoardName());
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
        List<PostResponse> clubList = postService.getList("club",1);

        Assertions.assertEquals(2,clubList.size());
        Assertions.assertEquals("제목임다2.",clubList.get(0).getTitle());
        Assertions.assertEquals("제목임다.",clubList.get(1).getTitle());
    }


}