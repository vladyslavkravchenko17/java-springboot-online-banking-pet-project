package com.project.controller;

import com.project.domain.Card;
import com.project.domain.Credit;
import com.project.domain.Deposit;
import com.project.exception.CreditNotAgreeException;
import com.project.exception.DepositNotAgreeException;
import com.project.repository.CardRepository;
import com.project.repository.CreditDepositRepository;
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
@RequestMapping()
public class CreditDepositController {

    @Autowired
    private CreditDepositRepository creditDepositRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Value("${commission.credit}")
    private int creditPercent;

    @Value("${commission.deposit}")
    private int depositPercent;

    @GetMapping("/credit")
    public String showCredits(Principal principal,
                              Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        String number = (String) model.asMap().get("number");
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        if (number == null && !cards.isEmpty()) {
            number = cards.get(0).getNumber();
        }
        Card currentCard = cardRepository.findByNumber(number);
        List<Credit> credits = creditDepositRepository.findAllCreditsByNumber(number);
        if (currentCard != null)
            model.addAttribute("limit", currentCard.getLimit() < currentCard.getMinCreditSum());
        model.addAttribute("cards", cards);
        model.addAttribute("credits", credits);
        model.addAttribute("card", currentCard);

        return "credit/credits";
    }

    @PostMapping("/credit")
    public String creditCardNumber(@RequestParam String number,
                                   RedirectAttributes attributes) {
        attributes.addFlashAttribute("number", number);
        return "redirect:/credit";
    }

    @PostMapping("/create-credit/number")
    public String createCreditCardNumber(@RequestParam String number,
                                   RedirectAttributes attributes) {
        attributes.addFlashAttribute("number", number);
        return "redirect:/create-credit";
    }

    @GetMapping("/create-credit")
    public String createForm(Principal principal,
                             Model model) {
        String number = (String) model.asMap().get("number");
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        Card card = cardRepository.findByNumber(number);
        if (number == null && !cards.isEmpty()) {
            card = cards.get(0);
        }
        if (card.getMinCreditSum() > card.getLimit()) {
            model.addAttribute("message", "Card credit limit exceeded");
            return "redirect:/credit";
        }
        model.addAttribute("card", card);
        model.addAttribute("cards", cards);

        return "credit/createCredit";
    }

    @PostMapping("/create-credit")
    public String createCredit(RedirectAttributes redirectAttributes,
                               @RequestParam int sum,
                               @RequestParam String number,
                               @RequestParam(required = false) boolean agree) {
        Card card = cardRepository.findByNumber(number);
        if (agree) {
            Credit credit = new Credit();
            credit.setCard(card);
            credit.setCardReceivedSum(sum);
            credit.setPercent(creditPercent);
            credit.setMonths(12);
            paymentService.createCredit(credit);
        } else {
            throw new CreditNotAgreeException();
        }
        redirectAttributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/credit";
    }

    @GetMapping("credit/premature-repay/{id}")
    public String prematureRepay(@PathVariable long id,
                                 RedirectAttributes attributes) {
        Credit credit = creditDepositRepository.findCreditById(id);
        paymentService.prematureCreditRepay(credit);
        attributes.addFlashAttribute("message", "Credit is successfully repaid!");

        return "redirect:/credit";
    }

    @GetMapping("/deposit")
    public String deposits(Principal principal,
                           Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        String number = (String) model.asMap().get("number");
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        if (number == null && !cards.isEmpty()) {
            number = cards.get(0).getNumber();
        }
        Card currentCard = cardRepository.findByNumber(number);
        List<Deposit> deposits = creditDepositRepository.findAllDepositsByNumber(number);
        model.addAttribute("cards", cards);
        model.addAttribute("deposits", deposits);
        model.addAttribute("card", currentCard);

        return "deposit/deposits";
    }

    @PostMapping("/deposit")
    public String depositCardNumber(@RequestParam String number,
                                   RedirectAttributes attributes) {
        attributes.addFlashAttribute("number", number);
        return "redirect:/deposit";
    }

    @PostMapping("/create-deposit/number")
    public String createDepositCardNumber(@RequestParam String number,
                                          RedirectAttributes attributes) {
        attributes.addFlashAttribute("number", number);
        return "redirect:/create-deposit";
    }

    @GetMapping("/create-deposit")
    public String createDepositForm(Principal principal,
                                    Model model) {
        String number = (String) model.asMap().get("number");
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        Card card = cardRepository.findByNumber(number);
        if (number == null && !cards.isEmpty()){
            card = cards.get(0);
        }
        model.addAttribute("card", card);
        model.addAttribute("cards", cards);

        return "deposit/createDeposit";
    }

    @PostMapping("/create-deposit")
    public String createDeposit(RedirectAttributes attributes,
                                @RequestParam int sum,
                                @RequestParam String number,
                                @RequestParam(required = false) boolean agree) {
        Card card = cardRepository.findByNumber(number);
        if (agree) {
            Deposit deposit = new Deposit();
            deposit.setCard(card);
            deposit.setCardPayedSum(sum);
            deposit.setPercent(depositPercent);
            deposit.setMonths(12);
            paymentService.createDeposit(deposit);
        } else {
            throw new DepositNotAgreeException();
        }
        attributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/deposit";
    }

    @GetMapping("deposit/premature-return/{id}")
    public String prematureReturn(@PathVariable long id,
                                  RedirectAttributes attributes) {
        Deposit deposit = creditDepositRepository.findDepositById(id);
        paymentService.prematureDepositReturn(deposit);
        attributes.addFlashAttribute("message", "Deposit is prematurely returned!");

        return "redirect:/deposit";
    }
}
