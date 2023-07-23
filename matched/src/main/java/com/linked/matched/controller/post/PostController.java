package com.linked.matched.controller.post;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEditor;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.ResponseDto;
import com.linked.matched.response.post.PostOneResponse;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/") //cloudfront 체크용
    public ResponseEntity<HttpStatus> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/home") //웹 전용 게시글 조회
    public List<PostResponse> webHome(){
        return postService.homeList();
    }

    @GetMapping("/board/{boardName}")// 게시글 목록 조회
    public List<PostResponse> viewList(@PathVariable String boardName,@PathParam(value = "page") Long page){
        return postService.getList(boardName, Math.toIntExact(page));
    }

    @PostMapping("/board/{boardName}") //게시글 작성
    public ResponseEntity<Object> createPost(@PathVariable String boardName, @RequestBody PostCreate postCreate, @AuthenticationPrincipal UserPrincipal userPrincipal){
        postService.write(postCreate,userPrincipal.getUserId());//dto로 만들어서 만들때 필요한것을 넣어준다.
        return new ResponseEntity<>(new ResponseDto("게시글 작성이 되었습니다."), HttpStatus.OK);

    }

    @GetMapping("/board/{boardName}/{postId}") // 게시글 자세히 조회
    public PostOneResponse viewPost(@PathVariable String boardName, @PathVariable Long postId){
        return postService.findPost(postId);
    }

    @PatchMapping("/board/{boardName}/{postId}")// 게시글 수정 
    public ResponseEntity<Object> editPost(@PathVariable String boardName, @PathVariable Long postId, @RequestBody PostEditor postEdit, @AuthenticationPrincipal UserPrincipal userPrincipal){
        //request body
        if(postService.edit(postId, postEdit,userPrincipal.getUserId())) {
            return new ResponseEntity<>(new ResponseDto("게시글이 수정 되었습니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("수정할 권한이 없습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardName}/{postId}") // 게시글 삭제 
    public ResponseEntity<Object> deletePost(@PathVariable String boardName, @PathVariable Long postId,@AuthenticationPrincipal UserPrincipal userPrincipal)  {
        if(postService.delete(postId,userPrincipal.getUsername())) {
            return new ResponseEntity<>(new ResponseDto("게시글이 삭제 되었습니다."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto("삭제할 권한이 없습니다."), HttpStatus.OK);
    }
    
    @GetMapping("/board/{boardName}/search")// 게시글 검색
    public List<PostResponse> searchPost(@PathVariable String boardName,@RequestParam String keyword,@ModelAttribute PostSearch postSearch){
        return postService.searchPost(boardName,keyword,postSearch);
    }


}
