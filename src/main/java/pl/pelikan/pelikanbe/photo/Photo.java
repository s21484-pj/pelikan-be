package pl.pelikan.pelikanbe.photo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JsonBackReference(value = "hotel_photo")
    private Hotel hotel;

    @ManyToOne
    @JsonBackReference(value = "attraction_photo")
    private TouristAttraction touristAttraction;
}
