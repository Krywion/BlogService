package pl.krywion.blogservice.Controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("blog/api/post")
public class PostResource {
    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    // create
    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestParam String title, String content, String author, String category, MultipartFile file) throws IOException {

        return new ResponseEntity<>(
                postService.addPost(title, content, author, category, file),
                        HttpStatus.CREATED
                );
    }

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestParam String title, String content, String author, String category) throws IOException {

        return new ResponseEntity<>(
                postService.addPost(title, content, author, category),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getSinglePost(@PathVariable Long id){
        return new ResponseEntity<>(postService.singlePost(id), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.allPost(), HttpStatus.OK);
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<Post>> postByTitle(@PathVariable String title) {
        return new ResponseEntity<>(postService.findByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deletePost(@PathVariable Long id) {
        if(postService.deletePost(id))
            return new ResponseEntity(HttpStatus.OK);
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity editPost(@PathVariable Long id, String title, String content, String author, String category, MultipartFile file) throws IOException {
        return new ResponseEntity<>(postService.update(id, title, content, author, category, file), HttpStatus.OK);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity publishPost(@PathVariable Long id) {
        if(postService.publishPost(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/hide")
    public ResponseEntity hidePost(@PathVariable Long id) {
        if(postService.hidePost(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
