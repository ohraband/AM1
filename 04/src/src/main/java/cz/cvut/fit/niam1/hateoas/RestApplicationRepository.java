package cz.cvut.fit.niam1.hateoas;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<>();

    @PostConstruct
    public void initRepo() {
        tours.add(new Tour("1", "Paris museums", "Valid"));
        tours.add(new Tour("2", "Naples mediterranean coast", "Valid"));
        tours.add(new Tour("3", "Orava tours with skoda 100", "Valid"));

    }

    public void addTour(Tour c) {
        int max = tours.stream().map(t-> Integer.valueOf(t.getId())).max(Integer::compare).get();
        c.setId(String.valueOf(max+1));
        tours.add(c);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    @Async
    public void deleteTour(String id) {
        try {
            // Sleep for 10 seconds (10000 milliseconds)
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tours.removeIf(c -> c.getId().equals(id));
    }


}
