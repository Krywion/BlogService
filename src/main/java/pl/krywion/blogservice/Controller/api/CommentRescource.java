package pl.krywion.blogservice.Controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krywion.blogservice.model.Comment;
import pl.krywion.blogservice.service.CommentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


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
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getSingleComment(id);
        if(comment.isPresent()) {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/postComments/{id}")
    public ResponseEntity<List<Comment>> getPostComments(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getAllCommentsOfPost(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        if (commentService.deleteComment(id))
            return new ResponseEntity(HttpStatus.OK);
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
