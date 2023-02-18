package pl.pelikan.pelikanbe.tourist_attraction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import pl.pelikan.pelikanbe.offer.Offer;

import java.math.BigDecimal;

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
}
