package pl.krywion.blogservice.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.krywion.blogservice.service.PostService;
import pl.krywion.blogservice.service.UserInfoService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/blog/panel")
@PreAuthorize("hasAuthority('ROLE_EDITOR')")
public class PanelController {

    private final PostService postService;
    private final UserInfoService userInfoService;


    public PanelController(PostService postService, UserInfoService userInfoService) {
        this.postService = postService;
        this.userInfoService = userInfoService;
    }


    @GetMapping
    public String panel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean hasAdminRole = auth.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("username", username);
        model.addAttribute("posts", postService.allPost());
        model.addAttribute("isAdmin", hasAdminRole);

        return "panel";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/blog/panel";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.singlePost(id));
        return "editPostForm";
    }

    @GetMapping("/publish/{id}")
    public String publishPost(@PathVariable Long id) {
        postService.publishPost(id);
        return "redirect:/blog/panel";
    }

    @GetMapping("/hide/{id}")
    public String hidePost(@PathVariable Long id) {
        postService.hidePost(id);
        return "redirect:/blog/panel";
    }

    @PostMapping("/editPost/{id}")
    public String editPost(@PathVariable Long id, String title, String content, String author, String category, @RequestParam("file") MultipartFile file) throws IOException {
        postService.update(id, title, content, author, category, file);
        return "redirect:/blog/panel";
    }

    @GetMapping("/addPostForm")
    public String addPostForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        return "addPostForm";
    }

    @PostMapping("/addPost")
    public String addPost(String title, String content, String author, String category,String addedBy ,@RequestParam("file") MultipartFile file) throws IOException {

        postService.addPost(title, content, author, category, addedBy, file);
        return "redirect:/blog/panel";
    }


}
