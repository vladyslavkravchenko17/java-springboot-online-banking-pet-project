package com.project.controller;

import com.lowagie.text.DocumentException;
import com.project.domain.Card;
import com.project.domain.Transaction;
import com.project.exporter.DataExcelExporter;
import com.project.exporter.DataPDFExporter;
import com.project.repository.CardRepository;
import com.project.repository.TransactionRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private DataPDFExporter receiptPDFExporter;

    @Autowired
    private DataExcelExporter dataExcelExporter;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping
    public String showTransactions(Principal principal,
                                   Model model) {
        String number = (String) model.asMap().get("number");
        List<Card> cards = userRepository.findByEmail(principal.getName()).getCards();
        if (number == null && !cards.isEmpty()) {
            number = cards.get(0).getNumber();
        }
        Card currentCard = cardRepository.findByNumber(number);
        List<String> numbers = cards.stream()
                .map(Card::getNumber)
                .collect(Collectors.toList());
        if (currentCard != null) {
            if (!numbers.contains(currentCard.getNumber()) && !cards.isEmpty()) {
                currentCard = cards.get(0);
            }
            List<Transaction> transactions = transactionRepository.findAllByNumber(currentCard.getNumber());
            model.addAttribute("transactions", transactions);
        }
        model.addAttribute("card", currentCard);
        model.addAttribute("cards", cards);

        return "transaction/transactions";
    }

    @PostMapping
    public String transactionCard(@RequestParam String number,
                                  RedirectAttributes attributes) {
        attributes.addFlashAttribute("number", number);
        return "redirect:/";
    }

    @GetMapping("/transaction/{id}")
    public String transactionInfo(@PathVariable long id,
                                  Model model) {
        model.addAttribute("transaction", transactionRepository.findById(id));

        return "transaction/transactionInfo";
    }

    @PostMapping("/transaction/download/pdf")
    public String downloadTransactionReceiptPdf(@RequestParam long id,
                                                HttpServletResponse response) throws DocumentException, IOException {
        Transaction transaction = transactionRepository.findById(id);
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + transaction.getBeautifulDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        receiptPDFExporter.exportTransaction(response, transaction);

        return "redirect:/transaction/" + id;
    }

    @PostMapping("/transaction/download/excel")
    public String downloadTransactionReceiptExcel(@RequestParam long id,
                                                  HttpServletResponse response) throws DocumentException, IOException {
        Transaction transaction = transactionRepository.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + transaction.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportTransaction(response, transaction);
        return "redirect:/transaction/" + id;
    }
}
