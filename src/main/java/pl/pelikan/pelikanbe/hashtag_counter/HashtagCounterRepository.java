package pl.pelikan.pelikanbe.hashtag_counter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagCounterRepository extends JpaRepository<HashtagCounter, Long> {
}
