package com.project.controller;

import com.project.domain.*;
import com.project.repository.CardRepository;
import com.project.repository.CreditDepositRepository;
import com.project.repository.SubscriptionRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CreditDepositRepository creditDepositRepository;

    @Value("${min.limit}")
    private double minLimitSum;

    @Value("${debit.limit}")
    private double debitLimit;

    @Value("${credit.limit}")
    private double creditLimit;

    @Value("${company.limit}")
    private double companyLimit;

    @GetMapping
    public String profile() {
        return "redirect:/profile/cards";
    }

    @GetMapping("/cards")
    public String cards(Model model, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        List<Card> cards = currentUser.getCards();
        model.addAttribute("cards", cards);
        model.addAttribute("size", cards.size());
        return "profile/showCards";
    }

    @GetMapping("/subscriptions")
    public String subscriptions(Model model, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(currentUser.getId());
        model.addAttribute("subscriptions", subscriptions);
        return "profile/showSubscriptions";
    }

    @GetMapping("/cards/{id}")
    public String cardMore(@PathVariable long id,
                           Model model) {
        Card card = cardRepository.findById(id);
        if (card != null) {
            List<PlannedPayment> plannedPayments = cardRepository.findPlannedPaymentsByNumber(card.getId());
            plannedPayments.forEach(plannedPayment -> {
                Card currentCard = cardRepository.findById(plannedPayment.getReceiver().getId());
                User user = userRepository.findById(currentCard.getUserId());
                plannedPayment.setReceiverName(user.getFirstName() + " " + user.getLastName());
            });
            model.addAttribute("plannedPayments", plannedPayments);
            model.addAttribute("credits", creditDepositRepository.findAllCreditsByNumber(card.getNumber()));
            model.addAttribute("deposits", creditDepositRepository.findAllDepositsByNumber(card.getNumber()));
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        model.addAttribute("card", card);

        return "/profile/cardInfo";
    }

    @GetMapping("data")
    public String data(Model model,
                       Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/profile/data";
    }

    @GetMapping("/card/create")
    public  String creationForm(){
        return "profile/createCard";
    }

    @PostMapping("/card/create")
    public String createCard(Principal principal,
                             @RequestParam String currencyName,
                             @RequestParam String cardType,
                             @RequestParam(required = false) String companyName,
                             Model model) {
        Card cardFromDb = cardRepository.findByCompanyName(companyName);
        User currentUser = userRepository.findByEmail(principal.getName());
        if (cardFromDb != null){
            model.addAttribute("error", "Company card with this name already exists!");
            model.addAttribute("cards", currentUser.getCards());
            model.addAttribute("size", currentUser.getCards().size());
            return "profile/showCards";
        }
        Card card = new Card();
        card.setUserId(currentUser.getId());
        card.setBalance(0.00);
        card.setCurrency(currencyName);
        card.setType(cardType);
        String currencyConversion = "UAH_TO_" + currencyName;
        ConversionType conversionType = ConversionType.valueOf(currencyConversion);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());
        double minLimit = minLimitSum * currentConversionRate;
        double maxLimit;
        if (cardType.equals("CREDIT_CARD")){
            maxLimit = creditLimit * currentConversionRate;
        } else {
            maxLimit = debitLimit * currentConversionRate;
        }
        if (cardType.equals("COMPANY_CARD")){
            maxLimit = companyLimit * currentConversionRate;
            card.setCompanyName(companyName);
        }
        card.setMinCreditSum((int) minLimit);
        card.setLimit((int) maxLimit);


        cardRepository.create(card);
        return "redirect:/profile/cards";
    }
}
