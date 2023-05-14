package com.linked.matched.controller.post;

import com.linked.matched.request.post.PostCreate;
import com.linked.matched.request.post.PostEdit;
import com.linked.matched.request.post.PostSearch;
import com.linked.matched.response.post.PostResponse;
import com.linked.matched.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/board/{boardName}")//RequestBody이 아니라 ModelAttribute로 넣어야한다.
    public List<PostResponse> viewList(@PathVariable String boardName,@ModelAttribute PostSearch postSearch){
        return postService.getList(boardName,postSearch);
    }

    @PostMapping("/board/{boardName}")
    public void createPost(@PathVariable String boardName,@RequestBody PostCreate postCreate){
        postService.write(postCreate);//dto로 만들어서 만들때 필요한것을 넣어준다.
    }

    @GetMapping("/board/{boardName}/{postId}")
    public PostResponse viewPost(@PathVariable String boardName,@PathVariable Long postId){
        return postService.findPost(postId);
    }

    @PatchMapping("/board/{boardName}/{postId}")
    public void editPost(@PathVariable String boardName, @PathVariable Long postId,@RequestBody PostEdit postEdit){
        //request body
        postService.edit(postId,postEdit);
    }

    @DeleteMapping("/board/{boardName}/{postId}")
    public void deletePost(@PathVariable String boardName,@PathVariable Long postId)  {
        postService.delete(postId);//예외 처리 따로 해줘야한다.
    }
    
    //카테고리 넣는 검색

}
