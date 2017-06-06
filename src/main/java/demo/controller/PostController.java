package demo.controller;

import demo.domain.Post;
import demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "post/new")
    public String newPost(Model model){
        model.addAttribute("post", new Post());
        return "postForm";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String savePost(Post post){
        postService.savePost(post);
        return "redirect:/post/" + post.getId();
    }

    @RequestMapping(value = "post/{id}")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        return "postShow";
    }

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("posts", postService.listAllPosts());
        return "posts";
    }

    @RequestMapping(value = "post/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        return "postForm";
    }

    @RequestMapping(value = "post/delete/{id}")
    public String delete(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
