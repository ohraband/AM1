# HW4

### Async delete
Na serveri je simulované asynchrónne vymazanie <b>Tour</b> pomocou <b>DELETE</b> requestu. Po odoslaní delete požiadavku pre Tour s daným id sa užívatelovi vráti odpoveď s detailom na danú Tour pričom jej paramater <b> status </b> sa nastaví z pôvodného <b>Valid</b> na <b>Deleting...</b>. Na serveri sa zatiaľ spustí asynchrónna funckia pre vymazanie danej Tour, ktorá simulovane čaká 10s predtým ako Tour skutočne vymaže. Po uplynutí 10s už <b>GET</b> požiadavok na Tour s daným id už vráti chybnú odpoveď čím sa vymazanie potvrdilo.

### Example

List of all tours
![All_tours](04/src/images/All_tours.png)

Deleting tour with id "2"
![Deleing_tour_id_2](04/src/images/Deleing_tour_id_2.png)

After 10s the tour is fully deleted
![Tour_2_was_deleted](04/src/images/Tour_2_was_deleted.png)
