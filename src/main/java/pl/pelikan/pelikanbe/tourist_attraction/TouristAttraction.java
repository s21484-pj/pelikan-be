package pl.pelikan.pelikanbe.tourist_attraction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.photo.Photo;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class TouristAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "offer_attraction")
    private Offer offer;

    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.MERGE)
    @JsonManagedReference(value = "attraction_photo")
    private List<Photo> photos;
}
