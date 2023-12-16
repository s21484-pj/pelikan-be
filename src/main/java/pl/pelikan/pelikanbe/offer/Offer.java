package pl.pelikan.pelikanbe.offer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;
import pl.pelikan.pelikanbe.transport.Transport;
import pl.pelikan.pelikanbe.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    private int quantity;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference(value = "offer_hotel")
    private Hotel hotel;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference(value = "offer_transport")
    private Transport transport;

    @OneToMany(mappedBy = "offer")
    @JsonManagedReference(value = "offer_attraction")
    private List<TouristAttraction> attractions;

    @ManyToMany
    @JoinTable(
            name = "offer_hashtags",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags;

    @ManyToMany(mappedBy = "offers")
    private List<User> users;
}
