package cz.cvut.fit.niam1.hateoas;

import cz.cvut.fit.niam1.hateoas.custom.CustomLink;
import cz.cvut.fit.niam1.hateoas.custom.TourLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping(value = "/tour")
public class RestTourController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity getTour(@PathVariable String id) {
        if (repository.getTourById(id) != null) {
            Tour tour = repository.getTourById(id);
            tour.removeLinks();
            tour.add(Link.of("http://127.0.0.1:8080/tour/" + id, "self"));
            tour.add(linkTo(RestTourController.class).slash(id).withSelfRel());
            tour.add(linkTo(methodOn(RestTourController.class).getTour(id)).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(tour);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour with id: "+ id + " was not found.");
        }
    }

    @GetMapping("")
    public HttpEntity<List<Tour>> getAllTours(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.getTours());
    }
    @PostMapping(value = "")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/" + tour.getId()).build();
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteTour(@PathVariable String id){
        if (repository.getTourById(id) != null) {
            repository.getTourById(id).setStatus("Deleting...");
            TourLinks deletedTourDetail = new TourLinks(repository.getTourById(id), new CustomLink[]{new CustomLink("http://127.0.0.1:8080/tour/" + id, "self")});
            repository.deleteTour(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedTourDetail);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour with id: "+ id + " was not found.");
        }
    }
}
