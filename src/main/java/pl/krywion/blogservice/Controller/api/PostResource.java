package pl.krywion.blogservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.service.PostService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("blog/api/post")
public class PostResource {
    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestBody Map<String, String> payload) {

        ResponseEntity responseEntity = new ResponseEntity<>(
                postService.addPost(
                        payload.get("title"),
                        payload.get("content"),
                        payload.get("author"),
                        payload.get("category")),
                        HttpStatus.CREATED
                );
        return responseEntity;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getSinglePost(@PathVariable Long id){
        return new ResponseEntity<Optional<Post>>(postService.singlePost(id), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<List<Post>>(postService.allPost(), HttpStatus.OK);
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<Post>> postByTitle(@PathVariable String title) {
        return new ResponseEntity<List<Post>>(postService.findByTitle(title), HttpStatus.OK);
    }
}
