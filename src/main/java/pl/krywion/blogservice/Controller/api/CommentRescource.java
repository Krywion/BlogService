package pl.krywion.blogservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krywion.blogservice.model.Comment;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("blog/api/comment")
public class CommentRescource {

    private final CommentService commentService;

    public CommentRescource(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> createPost(@RequestBody Map<String, String> payload) {

        Long id = Long.parseLong(payload.get("post_id"));
        ResponseEntity responseEntity = new ResponseEntity<>(
                commentService.addComment(
                        id,
                        payload.get("description"),
                        payload.get("author")),
                HttpStatus.CREATED
        );
        return responseEntity;
    }
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllPosts() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

}
