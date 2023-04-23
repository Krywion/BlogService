package pl.krywion.blogservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.krywion.blogservice.model.Comment;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.service.CommentService;
import pl.krywion.blogservice.service.PostService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }
    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model){
        Post post = postService.singlePost(id).orElse(new Post());
        List<Comment> comments = commentService.getAllCommentsOfPost(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@RequestParam Map<String, String> payload, @PathVariable("id") Long id, Model model){
        Post post = postService.singlePost(id).orElse(new Post());
        model.addAttribute("post", post);
        commentService.addComment(
                id,
                payload.get("content"),
                payload.get("author")
        );
        return "redirect:/blog/post/{id}";
    }
}
