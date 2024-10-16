package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Ordine;
import fabioran.faziarte_website.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @PostMapping
    public String placeOrder(@RequestBody Ordine ordine) {
        ordineService.placeOrder(ordine);
        return "Ordine effettuato con successo!";
    }
}
