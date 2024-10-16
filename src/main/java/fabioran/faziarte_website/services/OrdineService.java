package fabioran.faziarte_website.services;

import fabioran.faziarte_website.entities.Ordine;
import fabioran.faziarte_website.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private MailgunService mailgunService;

    public void placeOrder(Ordine ordine) {
        ordineRepository.save(ordine);

        mailgunService.sendOrderConfirmation(
                ordine.getCustomerEmail(),
                "Conferma Ordine",
                "Grazie per aver acquistato dal nostro store!",
                "fabio.ranocchiari@live.it"
        );
    }
}