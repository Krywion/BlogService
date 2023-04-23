package pl.krywion.blogservice.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krywion.blogservice.service.UserInfoService;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/blog/panel/manageUsers")
public class ManageUserController {
    private final UserInfoService userInfoService;

    public ManageUserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    @GetMapping("/")
    public String manageUserPanel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("users", userInfoService.getAllUsers());
        model.addAttribute("username", username);
        return "manageUserPanel";
    }

    @GetMapping("/delete/{id}")
    public String deleteEditor(@PathVariable Long id) {
        userInfoService.deleteUser(id);
        return "redirect:/blog/panel/manageUsers";
    }

    @GetMapping("/addEditorForm")
    public String addEditorForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("username", username);
        return "addEditorForm";
    }

    @PostMapping("/addEditor")
    public String addEditor(String name, String password) {
        userInfoService.addEditor(name, password);
        return "redirect:/blog/panel/manageUsers";
    }
}
