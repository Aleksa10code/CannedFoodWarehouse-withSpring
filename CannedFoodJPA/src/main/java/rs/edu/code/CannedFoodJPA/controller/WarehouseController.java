package rs.edu.code.CannedFoodJPA.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.edu.code.CannedFoodJPA.model.Can;
import rs.edu.code.CannedFoodJPA.service.WarehouseService;


/***
 *  JSON - format podataka u request i respons
 *  Jackson - alat koji vrsi mapiranje u / iz JSON formata u POJO - PLAIN OLD JAVA OBJECT
 *  Jackson biblioteka je konfigurisana od strane Springa
 *
 * REST - RepresentationStrateTransfer je API koji nam pomaze da organizujemo request-ove na smislen nacin.
 * GET - READ (SELECT)
 * POST - INSERT
 * PUT - UPDATE (idempotent)
 * DELETE - DELETE
 *
 *
 */

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world from Spring";
    }

    @RequestMapping("/cans/{type}")
    public String getCountByType(@PathVariable String type) {
        System.out.println("Called method with value: " + type);
        return String.valueOf(11);
    }

    @GetMapping("/init")
    public boolean initDB() {
        return service.init();
    }

    @GetMapping("/cans/{id}")
    //@PathVariable (name = "id") da se getMapping "id" zove drugacije potrebno bi bilo da blize odredimo ime u @PathVariable
    public Can getCanById(@PathVariable long id) {
        return service.getCan(id);
    }

    @PostMapping("/cans")
    public Can acceptCan(@RequestBody Can can) {
        System.out.println("Dobili smo can objekat");
        System.out.println(can.getId() + " " +can.getType() + " " + can.getExpirationDate());
        return service.addCanInWarehouse(can);
    }
}
