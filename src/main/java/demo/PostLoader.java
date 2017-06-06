package demo;


import demo.domain.Post;
import demo.repository.PostRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class PostLoader implements ApplicationListener<ContextRefreshedEvent>{

    private PostRepository postRepository;

    private Logger log = Logger.getLogger(PostLoader.class);

    @Autowired
    public void setPostRepository(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Post newPost = new Post();
        newPost.setTitle("New Post In DB");
        newPost.setDescription("Testing post for all time");
        newPost.setCreateDate(new Date());
        newPost.setUpdateDate(null);
        postRepository.save(newPost);

        log.info("Saved Post - id " + newPost.getId());

        Post newPost2 = new Post();
        newPost2.setTitle("Second Post In DB");
        newPost2.setDescription("Testing post for all time");
        newPost2.setCreateDate(new Date());
        newPost2.setUpdateDate(new Date());
        postRepository.save(newPost2);

        log.info("Saved Post - id " + newPost2.getId());

    }

}
