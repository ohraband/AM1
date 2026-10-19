package cz.cvut.fit.niam1.hateoas;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<>();
    private ZonedDateTime timestamp = ZonedDateTime.now( ZoneId.systemDefault() );
    @PostConstruct
    public void initRepo() {
        tours.add(new Tour("1", "Northern Lights"));
        tours.add(new Tour("2", "Antarctic desert"));
    }

    public void addTour(Tour c) {
        int max = tours.stream().map(t-> Integer.valueOf(t.getId())).max(Integer::compare).get();
        c.setId(String.valueOf(max+1));
        tours.add(c);
        this.setNewTimestamp();
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void deleteTour(String id) {
        tours.removeIf(c -> c.getId().equals(id));
        this.setNewTimestamp();
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setNewTimestamp() {
        this.timestamp = ZonedDateTime.now( ZoneId.systemDefault() );
    }

}
