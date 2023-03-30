package pl.pelikan.pelikanbe.offer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;
import pl.pelikan.pelikanbe.transport.Transport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private LocalDate termFrom;

    private LocalDate termTo;

    private BigDecimal price;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference(value = "offer_hotel")
    private Hotel hotel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference(value = "offer_transport")
    private Transport transport;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.MERGE)
    @JsonManagedReference(value = "offer_attraction")
    private List<TouristAttraction> attractions;

//    private List<Hashtag> hashtags;
}
