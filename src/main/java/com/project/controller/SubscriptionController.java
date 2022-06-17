package com.project.controller;

import com.project.domain.Card;
import com.project.domain.Subscription;
import com.project.domain.SubscriptionInfo;
import com.project.domain.User;
import com.project.repository.CardRepository;
import com.project.repository.SubscriptionRepository;
import com.project.repository.UserRepository;
import com.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping()
    public String show(Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        String info = (String) model.asMap().get("info");
        model.addAttribute("info", info);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        model.addAttribute("infos", subscriptionRepository.findAllInfo());
        return "subscription/subscription";
    }


    @GetMapping("{id}")
    public String purchaseForm(@PathVariable long id,
                               Model model,
                               Principal principal) {
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        SubscriptionInfo info = subscriptionRepository.findInfoById(id);
        model.addAttribute("info", info);
        model.addAttribute("cards", cards);

        return "subscription/subscriptionPurchase";
    }

    @PostMapping()
    public String saveSubscription(
            @RequestParam long id,
            @RequestParam double sum,
            @RequestParam(required = false) boolean repeatable,
            @RequestParam String senderNumber,
            RedirectAttributes attributes,
            Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(currentUser.getId());
        List<Long> ids = subscriptions.stream()
                .map(Subscription::getServiceId)
                .collect(Collectors.toList());
        if (ids.contains(id)) {
            attributes.addFlashAttribute("info", "This subscription is already active!");
            return "redirect:/subscription";
        }
        SubscriptionInfo info = subscriptionRepository.findInfoById(id);
        paymentService.purchaseSubscription(info, repeatable, currentUser, senderNumber, sum);
        attributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/subscription";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable long id,
                           Model model,
                           Principal principal) {
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        Subscription subscription = subscriptionRepository.findById(id);
        model.addAttribute("subscription", subscription);
        model.addAttribute("cards", cards);
        return "profile/subscriptionEdit";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam(required = false) boolean repeatable,
                       @RequestParam String senderNumber,
                       @RequestParam long id) {
        Subscription subscription = new Subscription();
        Card card = cardRepository.findByNumber(senderNumber);
        subscription.setId(id);
        subscription.setCard(card);
        subscription.setRepeatable(repeatable);
        subscriptionRepository.saveSubscription(subscription);

        return "redirect:/profile/subscriptions";
    }
}
