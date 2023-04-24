package pl.krywion.blogservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Lob
    private String content;

    private LocalDateTime publicationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="post_id", nullable = false)
    @JsonBackReference
    private Post post;

    public Comment(String content, String author, Post post) {
        this.post = post;
        this.content = content;
        this.author = author;
        this.publicationDate = LocalDateTime.now();
    }

    public Comment(String content, Post post) {
        this.content = content;
        this.post = post;
        this.publicationDate = LocalDateTime.now();
        this.author = "Anonymous";
    }

}
