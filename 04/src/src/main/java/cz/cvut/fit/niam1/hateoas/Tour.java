package cz.cvut.fit.niam1.hateoas;

import org.springframework.hateoas.RepresentationModel;

public class Tour extends RepresentationModel<Tour> {

    String id;
    String name;

    String status;



    public Tour(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

}
