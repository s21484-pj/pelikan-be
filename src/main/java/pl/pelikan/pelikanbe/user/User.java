package pl.pelikan.pelikanbe.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.offer.Offer;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
            name = "user_offer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<Offer> offers;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user_counter")
    private List<HashtagCounter> hashTagCounters;
}
