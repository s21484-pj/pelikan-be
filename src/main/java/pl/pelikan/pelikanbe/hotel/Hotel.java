package pl.pelikan.pelikanbe.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.photo.Photo;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private float stars;

    private String description;

    @OneToOne(mappedBy = "hotel")
    @JsonBackReference(value = "offer_hotel")
    private Offer offer;

    @OneToMany(mappedBy = "hotel")
    @JsonManagedReference(value = "hotel_photo")
    private List<Photo> photos;
}
