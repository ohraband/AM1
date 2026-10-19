package cz.cvut.fit.niam1.hateoas.custom;

import cz.cvut.fit.niam1.hateoas.Tour;

public class TourLinks {

    private Tour tour;
    private CustomLink[] customLinks;

    public TourLinks(Tour tour, CustomLink[] customLinks) {
        this.tour = tour;
        this.customLinks = customLinks;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public CustomLink[] get_links() {
        return customLinks;
    }

    public void set_links(CustomLink[] customLinks) {
        this.customLinks = customLinks;
    }
}
