package pl.pelikan.pelikanbe.transport;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    private String description;
}
