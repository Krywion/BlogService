package pl.krywion.blogservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.service.PostService;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/blog";
    }
    @GetMapping("/blog")
    public String blog(Model model) {
        List<Post> posts = postService.allPublishedPosts();
        model.addAttribute("posts", posts);
        return "home";
    }

}
