package pl.pelikan.pelikanbe.hashtag_counter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.user.User;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HashtagCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    @ManyToOne
    @JsonBackReference(value = "user_counter")
    private User user;

    @ManyToOne
    @JsonBackReference(value = "hashtag_counter")
    private Hashtag hashtag;

    public static HashtagCounter getHashtagCounterByHashtag(List<HashtagCounter> hashtagCounters, Hashtag hashtagToCheck) {
        for (HashtagCounter hashtagCounter : hashtagCounters) {
            Hashtag hashtag = hashtagCounter.getHashtag();
            if (hashtag.equals(hashtagToCheck)) {
                return hashtagCounter;
            }
        }
        return null;
    }
}
