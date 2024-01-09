package pl.pelikan.pelikanbe.transport;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import pl.pelikan.pelikanbe.offer.Offer;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    private String name;

    private String description;

    @OneToOne(mappedBy = "transport")
    @JsonBackReference(value = "offer_transport")
    private Offer offer;
}
