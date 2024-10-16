package fabioran.faziarte_website.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")  // Abilita CORS specifico
public class StripeController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @PostMapping("/create-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> data) throws StripeException {
        if (data == null || !data.containsKey("items")) {
            throw new IllegalArgumentException("Il corpo della richiesta è vuoto o mancano gli articoli.");
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("items");

        SessionCreateParams.LineItem[] lineItems = items.stream()
                .map(item -> {
                    SessionCreateParams.LineItem.PriceData.ProductData productData =
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName((String) item.get("name"))
                                    .build();

                    SessionCreateParams.LineItem.PriceData priceData =
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount(((Integer) item.get("price")).longValue() * 100) // Converti prezzo in centesimi
                                    .setProductData(productData)
                                    .build();

                    return SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();
                })
                .toArray(SessionCreateParams.LineItem[]::new);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/success")
                .setCancelUrl("http://localhost:5173/cancel")
                .addAllLineItem(List.of(lineItems)) // Aggiungi tutti gli articoli
                .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());

        return response;
    }
}