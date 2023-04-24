package pl.krywion.blogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krywion.blogservice.util.ImageUtils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String addedBy;
    private String author;
    private String category;
    private String imageType;
    private String imageName;
    @Lob
    @JsonIgnore
    private byte[] imageData;
    private LocalDateTime publicationDate;
    private PostStatus status;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Comment> comments;

    @JsonIgnore
    public String showPublicationDate() {
        LocalDateTime pd = this.publicationDate;
        String date = pd.getMonth() + " " + pd.getDayOfMonth() + " " + pd.getYear();
        return date;
    }

    @JsonIgnore
    public String getImageDataAsBase64() {
        return Base64.getEncoder().encodeToString(ImageUtils.decompressImage(this.imageData));
    }

    @JsonIgnore
    public boolean isHidden() {
        if (status == PostStatus.HIDDEN)
            return true;
        else {
            return false;
        }
    }

}
