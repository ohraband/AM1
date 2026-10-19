package cz.cvut.fit.niam1.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping(value = "/tours")
public class RestTourController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("/{id}")
    public HttpEntity<Tour> getTour(@PathVariable String id) {
        Tour tour = repository.getTourById(id);
        tour.removeLinks();
        tour.add(Link.of("http://127.0.0.1:8080/tour/" + id, "self"));
        tour.add(linkTo(RestTourController.class).slash(id).withSelfRel());
        tour.add(linkTo(methodOn(RestTourController.class).getTour(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(tour);
    }


    @GetMapping("/strong")
    public ResponseEntity getToursStrong(){
        List<Tour> tours = repository.getTours();
        String strongETag = ETagGenerator.generateStrongTourListETag(tours);
        return ResponseEntity.status(HttpStatus.OK).eTag(strongETag).lastModified(repository.getTimestamp()).body(tours);
    }


    @GetMapping("/weak")
    public ResponseEntity getToursWeak(){
        List<Tour> tours = repository.getTours();
        String weakETag = ETagGenerator.generateWeakTourListETag(tours);
        return ResponseEntity.status(HttpStatus.OK).eTag("W/\"" + weakETag + "\"").lastModified(repository.getTimestamp()).body(tours);
    }

    @PostMapping(value = "")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/" + tour.getId()).build();
    }

}
