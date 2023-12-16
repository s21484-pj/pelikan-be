package pl.pelikan.pelikanbe.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.offer.Offer;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "offers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToMany
    @JoinTable(
            name = "users_offers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<Offer> offers;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "counter_user")
    private List<HashtagCounter> hashTagCounters;
}
