package pl.pelikan.pelikanbe.hashtag_counter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.user.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class HashtagCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonBackReference(value = "counter_user")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonBackReference(value = "counter_hashtag")
    private Hashtag hashtag;
}
