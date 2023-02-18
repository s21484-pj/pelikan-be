package pl.pelikan.pelikanbe.tourist_attraction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristAttractionService {

    private final TouristAttractionRepository repository;

    public TouristAttractionService(TouristAttractionRepository repository) {
        this.repository = repository;
    }

    public TouristAttraction getAttractionById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<TouristAttraction> getAttractions() {
        return repository.findAll();
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        return repository.save(attraction);
    }

    public boolean existTouristAttractionById(Long id) {
        return repository.existsById(id);
    }

    public TouristAttraction updateById(Long id, TouristAttraction attraction) {
        if (existTouristAttractionById(id)) {
            return repository.save(attraction);
        } else {
            throw new RuntimeException();
        }
    }

    public void deleteTouristAttractionById(Long id) {
        if (existTouristAttractionById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }
}