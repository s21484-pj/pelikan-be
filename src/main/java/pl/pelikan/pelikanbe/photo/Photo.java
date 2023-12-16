package pl.pelikan.pelikanbe.photo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonBackReference(value = "hotel_photo")
    private Hotel hotel;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonBackReference(value = "attraction_photo")
    private TouristAttraction touristAttraction;
}
