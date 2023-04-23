package pl.krywion.blogservice.service;

import org.springframework.stereotype.Service;
import pl.krywion.blogservice.model.Comment;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.repository.CommentRepository;
import pl.krywion.blogservice.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public boolean addComment(Long id, String description, String author) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) {
            Post existingPost = post.get();
            if(author.isEmpty()){
                Comment comment = new Comment(description, existingPost);
                commentRepository.save(comment);
                return true;
            } else {
                Comment comment = new Comment(description, author, existingPost);
                commentRepository.save(comment);
                return true;
            }

        }else {
            return false;
        }
    }

    public List<Comment> getAllCommentsOfPost(Long id) {
        return commentRepository.findAllByPost_Id(id);
    }
    public List<Comment> getAllComments() {

        return (List<Comment>) commentRepository.findAll();
    }
}
