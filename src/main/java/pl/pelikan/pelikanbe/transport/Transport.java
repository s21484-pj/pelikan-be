package pl.pelikan.pelikanbe.transport;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import pl.pelikan.pelikanbe.offer.Offer;

@Entity
@Data
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    private String description;

    @OneToOne(mappedBy = "transport", fetch = FetchType.LAZY)
    @JsonBackReference(value = "offer_transport")
    private Offer offer;
}
