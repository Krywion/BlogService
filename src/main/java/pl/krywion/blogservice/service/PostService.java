package pl.krywion.blogservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.krywion.blogservice.model.Comment;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.model.PostStatus;
import pl.krywion.blogservice.repository.CommentRepository;
import pl.krywion.blogservice.repository.PostRepository;
import pl.krywion.blogservice.util.ImageUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Post addPost(String title, String content, String author, String category, String addedBy, MultipartFile file) throws IOException {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .addedBy(addedBy)
                .status(PostStatus.HIDDEN)
                .imageName(file.getOriginalFilename())
                .imageType(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .publicationDate(LocalDateTime.now()).build();
        postRepository.save(post);
        return post;
    }

    public Post addPost(String title, String content, String author, String category, MultipartFile file) throws IOException {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .status(PostStatus.HIDDEN)
                .imageName(file.getOriginalFilename())
                .imageType(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .publicationDate(LocalDateTime.now()).build();
        postRepository.save(post);
        return post;
    }

    public Post addPost(String title, String content, String author, String category) throws IOException {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .status(PostStatus.HIDDEN)
                .publicationDate(LocalDateTime.now()).build();
        postRepository.save(post);
        return post;
    }

    public Optional<Post> singlePost(Long id) {
       return postRepository.findById(id);
    }

    public List<Post> allPost(){
        return (List<Post>) postRepository.findAll();
    }
    public List<Post> allPublishedPosts(){
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }


    public List<Post> findByTitle(String title){
        return (List<Post>) postRepository.findByTitleIgnoreCaseContaining(title);
    }

    public boolean deletePost(Long id) {
            Optional<Post> optionalPost = postRepository.findById(id);
            if(optionalPost.isPresent()) {
                List<Comment> allCommentsByPostId = commentRepository.findAllByPost_Id(id);
                for(Comment comment : allCommentsByPostId) {
                    commentRepository.deleteById(comment.getId());
                }
                postRepository.deleteById(id);
                return true;
            } else {
                return false;
            }

    }

    public boolean update(Long id, String title, String content, String author, String category, MultipartFile file) throws IOException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(title);
            post.setContent(content);
            post.setAuthor(author);
            post.setCategory(category);
            if(!file.isEmpty()) {
                post.setImageName(file.getOriginalFilename());
                post.setImageType(file.getContentType());
                post.setImageData(ImageUtils.compressImage(file.getBytes()));
            }
            postRepository.save(post);
            return true;

        } else {
            return false;
        }
    }
    public boolean publishPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setStatus(PostStatus.PUBLISHED);
            postRepository.save(post);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean hidePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setStatus(PostStatus.HIDDEN);
            postRepository.save(post);
            return true;
        }
        else {
            return false;
        }
    }
}
