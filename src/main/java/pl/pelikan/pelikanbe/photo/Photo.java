package pl.pelikan.pelikanbe.photo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;

@Entity
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "hotel_photo")
    private Hotel hotel;

    @ManyToOne
    @JsonBackReference(value = "attraction_photo")
    private TouristAttraction touristAttraction;
}
