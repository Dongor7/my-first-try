package demo.service;


import demo.domain.Post;

public interface PostService {

    Iterable<Post> listAllPosts();

    Post getPostById(Long id);

    Post savePost(Post post);

    void deletePost(Long id);

}
