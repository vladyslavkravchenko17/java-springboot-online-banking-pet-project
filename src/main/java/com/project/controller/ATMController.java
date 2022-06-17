package com.project.controller;

import com.project.domain.Card;
import com.project.repository.CardRepository;
import com.project.repository.UserRepository;
import com.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/atm")
public class ATMController {

    @Autowired
    private PaymentService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${commission.atm}")
    private int atmCommissionPercent;

    @GetMapping
    public String atm(Principal principal,
                      Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        model.addAttribute("cards", cards);
        model.addAttribute("commission", atmCommissionPercent);

        return "transfer/ATM";
    }

    @PostMapping
    public String atmPayment(RedirectAttributes redirectAttributes,
                             @RequestParam String actionType,
                             @RequestParam int amountOfMoney,
                             @RequestParam String number) {
        Card card = cardRepository.findByNumber(number);
        if (actionType.equals("deposit")) {
            cardService.deposit(card, amountOfMoney);
        }
        if (actionType.equals("withdraw")) {
            cardService.withdraw(card, amountOfMoney);
        }
        redirectAttributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/atm";
    }
}