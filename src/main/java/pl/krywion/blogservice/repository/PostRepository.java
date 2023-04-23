package pl.krywion.blogservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krywion.blogservice.model.Post;
import pl.krywion.blogservice.model.PostStatus;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByTitleIgnoreCaseContaining(String title);
    List<Post> findAllByStatus(PostStatus status);

}
