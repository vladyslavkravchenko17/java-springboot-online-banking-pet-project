package com.project.controller;

import com.project.domain.Card;
import com.project.domain.PlannedPayment;
import com.project.domain.Transaction;
import com.project.repository.CardRepository;
import com.project.repository.UserRepository;
import com.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transfer")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Value("${commission.send}")
    private int commissionPercent;

    @GetMapping()
    public String sendMoneyForm(Principal principal,
                                Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        Map<String, String> errorsMap = (Map<String, String>) model.asMap().get("errorsMap");
        model.mergeAttributes(errorsMap);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        model.addAttribute("cards", cards);
        model.addAttribute("commission", commissionPercent);
        return "transfer/transfer";
    }

    @PostMapping()
    public String send(@RequestParam String senderNumber,
                       @Valid Transaction transaction,
                       BindingResult bindingResult,
                       RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            attributes.addFlashAttribute("errorsMap", errorsMap);
            return "redirect:/transfer";
        }
        transaction.setSender(cardRepository.findByNumber(senderNumber));
        transaction.setReceiver(cardRepository.findByNumber(transaction.getReceiverNumber()));
        paymentService.sendMoney(transaction);
        attributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/transfer";
    }

    @GetMapping("/scheduled")
    public String createScheduledPayment(Principal principal,
                                         Model model) {
        String message = (String) model.asMap().get("message");
        String error = (String) model.asMap().get("error");
        Map<String, String> errorsMap = (Map<String, String>) model.asMap().get("errorsMap");
        model.mergeAttributes(errorsMap);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        model.addAttribute("cards", cards);
        model.addAttribute("commission", commissionPercent);
        model.addAttribute("dateNow", LocalDate.now());
        model.addAttribute("timeNow", LocalTime.now());

        return "transfer/scheduledForm";
    }

    @PostMapping("/scheduled")
    public String schedulePayment(RedirectAttributes attributes,
                                  @ModelAttribute("payment") @Valid PlannedPayment plannedPayment,
                                  BindingResult bindingResult,
                                  @RequestParam String senderNumber,
                                  @RequestParam String date,
                                  @RequestParam String time,
                                  @RequestParam boolean repeatable,
                                  @RequestParam(required = false, defaultValue = "0") int period) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            attributes.addFlashAttribute("errorsMap", errorsMap);
            return "transfer/scheduledForm";
        }
        Card sender = cardRepository.findByNumber(senderNumber);
        Card receiver = cardRepository.findByNumber(plannedPayment.getReceiverNumber());
        plannedPayment.setSender(sender);
        plannedPayment.setReceiver(receiver);
        paymentService.createScheduledPayment(plannedPayment, date, time, repeatable, period);
        attributes.addFlashAttribute("message", "Successfully!");

        return "redirect:/transfer/scheduled";
    }

    @GetMapping("/scheduled/edit/{id}")
    public String edit(@PathVariable long id,
                       Model model,
                       Principal principal) {
        Map<String, String> errorsMap = (Map<String, String>) model.asMap().get("errorsMap");
        model.mergeAttributes(errorsMap);
        PlannedPayment payment = cardRepository.findPlannedPaymentById(id);
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        model.addAttribute("payment", payment);
        model.addAttribute("cards", cards);
        model.addAttribute("receiverNumber", payment.getReceiver().getNumber());

        return "profile/plannedPaymentEdit";
    }

    @PostMapping("/scheduled/edit")
    public String edit(RedirectAttributes attributes,
                       @ModelAttribute("payment") @Valid PlannedPayment plannedPayment,
                       BindingResult bindingResult,
                       @RequestParam String senderNumber,
                       @RequestParam String date,
                       @RequestParam String time,
                       @RequestParam boolean repeatable,
                       @RequestParam(required = false, defaultValue = "0") int period) {
        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        Card receiverCard = cardRepository.findByNumber(plannedPayment.getReceiverNumber());
        Card senderCard = cardRepository.findByNumber(senderNumber);
        plannedPayment.setPaymentDateTime(dateTime);
        plannedPayment.setRepeatable(repeatable);
        plannedPayment.setPeriod(period);
        plannedPayment.setReceiver(receiverCard);
        plannedPayment.setSender(senderCard);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            attributes.addFlashAttribute("errorsMap", errorsMap);
            return "redirect:/transfer/scheduled/edit/" + plannedPayment.getId();
        }
        paymentService.saveScheduledPayment(plannedPayment);
        attributes.addFlashAttribute("message", "Successfully!");
        return "redirect:/profile/cards/" + senderCard.getId();
    }

    @PostMapping("/scheduled/delete")
    public String edit(@RequestParam long id,
                       @RequestParam String senderNumber,
                       RedirectAttributes attributes) {
        cardRepository.deletePlannedPayment(id);
        Card card = cardRepository.findByNumber(senderNumber);
        attributes.addFlashAttribute("message", "Planned payment is successfully deleted!");
        return "redirect:/profile/cards/" + card.getId();
    }

}
