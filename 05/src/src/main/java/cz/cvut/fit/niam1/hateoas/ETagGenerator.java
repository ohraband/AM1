package cz.cvut.fit.niam1.hateoas;

import org.springframework.util.DigestUtils;

import java.util.List;

public class ETagGenerator {

    public static String generateWeakTourListETag(List<Tour> tours) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Tour tour : tours) {
            stringBuilder.append(tour.getId());
            stringBuilder.append(tour.getName());
        }
        return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
    }

    public static String generateStrongTourListETag(List<Tour> tours) {
        return DigestUtils.md5DigestAsHex(tours.toString().getBytes());
    }
}