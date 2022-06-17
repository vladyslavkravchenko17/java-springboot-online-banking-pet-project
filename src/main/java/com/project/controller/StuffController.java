package com.project.controller;

import com.lowagie.text.DocumentException;
import com.project.domain.*;
import com.project.exporter.DataExcelExporter;
import com.project.exporter.DataPDFExporter;
import com.project.repository.CardRepository;
import com.project.repository.CreditDepositRepository;
import com.project.repository.TransactionRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/stuff")
@PreAuthorize("hasAnyAuthority('ADMIN', 'STUFF')")
public class StuffController {

    @Autowired
    private DataPDFExporter receiptPDFExporter;

    @Autowired
    private DataExcelExporter dataExcelExporter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CreditDepositRepository creditDepositRepository;

    @GetMapping
    public String menu() {
        return "redirect:/stuff/user";
    }

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("email", "" +
                "");
        return "stuff/userList";
    }

    @GetMapping("/user/{id}")
    public String userEdit(@PathVariable long id,
                           Model model) {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "stuff/userInfo";
    }


    @PostMapping("/download/pdf/user")
    public String downloadUserPDF(@RequestParam long id,
                               HttpServletResponse response) throws DocumentException, IOException {
        User user = userRepository.findById(id);
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user_" + user.getId() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportUser(response, user);

        return "redirect:/stuff/user/" + id;
    }

    @PostMapping("/download/pdf/users")
    public String downloadUsersPDF(
            HttpServletResponse response) throws DocumentException, IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportUsers(response, users);
        return "redirect:/stuff/user/";
    }

    @PostMapping("/download/excel/user")
    public String downloadUserExcel(@RequestParam long id,
                                    HttpServletResponse response) throws DocumentException, IOException {
        User user = userRepository.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user_" + user.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportUser(response, user);
        return "redirect:/stuff/user/" + id;
    }

    @PostMapping("/download/excel/users")
    public String downloadUsersExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportUsers(response, users);
        return "redirect:/stuff/user";
    }

    @GetMapping("/card")
    public String card(Model model) {
        model.addAttribute("cards", cardRepository.findAll());
        return "stuff/cardList";
    }

    @GetMapping("/card/{id}")
    public String cardInfo(@PathVariable long id,
                           Model model) {
        Card card = cardRepository.findById(id);
        model.addAttribute("card", card);
        model.addAttribute("user", userRepository.findById(card.getUserId()));
        return "stuff/cardInfo";
    }

    @PostMapping("/download/pdf/card")
    public String downloadCardPdf(@RequestParam long id,
                               HttpServletResponse response) throws DocumentException, IOException {
        Card card = cardRepository.findById(id);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=card_" + card.getId() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportCard(response, card);

        return "redirect:/stuff/card/" + id;
    }

    @PostMapping("/download/pdf/cards")
    public String downloadCardsPDF(HttpServletResponse response) throws DocumentException, IOException {
        List<Card> cards = cardRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cards_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportCards(response, cards);
        return "redirect:/stuff/user/";
    }

    @PostMapping("/download/excel/card")
    public String downloadCardExcel(@RequestParam long id,
                                    HttpServletResponse response) throws DocumentException, IOException {
        Card card = cardRepository.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=card_" + card.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportCard(response, card);
        return "redirect:/stuff/card/" + id;
    }

    @PostMapping("/download/excel/cards")
    public String downloadCardsExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<Card> cards = cardRepository.findAll();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cards_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportCards(response, cards);
        return "redirect:/stuff/card";
    }

    @GetMapping("/transaction")
    public String transactionList(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "stuff/transactionList";
    }

    @GetMapping("/transaction/{id}")
    public String transactionInfo(@PathVariable long id,
                                  Model model) {
        Transaction transaction = transactionRepository.findById(id);
        model.addAttribute("transaction", transaction);
        return "stuff/transactionInfo";
    }

    @PostMapping("/download/pdf/transaction")
    public String downloadTransaction(@RequestParam long id,
                               HttpServletResponse response) throws DocumentException, IOException {
        Transaction transaction = transactionRepository.findById(id);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + transaction.getId() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportTransaction(response, transaction);
        return "redirect:/stuff/transaction/" + id;
    }

    @PostMapping("/download/pdf/transactions")
    public String downloadTransactionsPDF(HttpServletResponse response) throws DocumentException, IOException {
        List<Transaction> transactions = transactionRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=deposits_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportTransactions(response, transactions);
        return "redirect:/stuff/transaction/";
    }

    @PostMapping("/download/excel/transaction")
    public String downloadTransactionExcel(@RequestParam long id,
                                    HttpServletResponse response) throws DocumentException, IOException {
        Transaction transaction = transactionRepository.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + transaction.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportTransaction(response, transaction);
        return "redirect:/stuff/transaction/" + id;
    }

    @PostMapping("/download/excel/transactions")
    public String downloadTransactionsExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<Transaction> transactions = transactionRepository.findAll();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportTransactions(response, transactions);
        return "redirect:/stuff/card";
    }

    @GetMapping("/credit")
    public String creditList(Model model) {
        model.addAttribute("credits", creditDepositRepository.findAllCredits());
        return "stuff/creditList";
    }

    @GetMapping("/credit/{id}")
    public String creditInfo(@PathVariable long id,
                             Model model) {
        Credit credit = creditDepositRepository.findCreditById(id);
        model.addAttribute("credit", credit);
        return "stuff/creditInfo";
    }

    @PostMapping("/download/pdf/credit")
    public String downloadCreditPDF(@RequestParam long id,
                                      HttpServletResponse response) throws DocumentException, IOException {
        Credit credit = creditDepositRepository.findCreditById(id);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=credit_" + credit.getId() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportCredit(response, credit);
        return "redirect:/stuff/credit/" + id;
    }

    @PostMapping("/download/pdf/credits")
    public String downloadCreditsPDF(HttpServletResponse response) throws DocumentException, IOException {
        List<Credit> credits = creditDepositRepository.findAllCredits();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=credits_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportCredits(response, credits);
        return "redirect:/stuff/credit/";
    }

    @PostMapping("/download/excel/credit")
    public String downloadCreditExcel(@RequestParam long id,
                                           HttpServletResponse response) throws DocumentException, IOException {
        Credit credit = creditDepositRepository.findCreditById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + credit.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportCredit(response, credit);
        return "redirect:/stuff/credit/" + id;
    }

    @PostMapping("/download/excel/credits")
    public String downloadCreditsExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<Credit> credits = creditDepositRepository.findAllCredits();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=credits_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportCredits(response, credits);
        return "redirect:/stuff/credit";
    }

    @GetMapping("/deposit")
    public String depositList(Model model) {
        model.addAttribute("deposits", creditDepositRepository.findAllDeposits());
        return "stuff/depositList";
    }

    @GetMapping("/deposit/{id}")
    public String depositInfo(@PathVariable long id,
                              Model model) {
        Deposit deposit = creditDepositRepository.findDepositById(id);
        model.addAttribute("deposit", deposit);
        return "stuff/depositInf";
    }

    @PostMapping("/download/pdf/deposit")
    public String downloadDepositPDF(@RequestParam long id,
                                 HttpServletResponse response) throws DocumentException, IOException {
        Deposit deposit = creditDepositRepository.findDepositById(id);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=deposit_" + deposit.getId() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportDeposit(response, deposit);
        return "redirect:/stuff/deposit/" + id;
    }

    @PostMapping("/download/pdf/deposits")
    public String downloadDepositsPDF(HttpServletResponse response) throws DocumentException, IOException {
        List<Deposit> deposits = creditDepositRepository.findAllDeposits();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=deposits_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportDeposits(response, deposits);
        return "redirect:/stuff/deposit/";
    }

    @PostMapping("/download/excel/deposit")
    public String downloadDepositExcel(@RequestParam long id,
                                      HttpServletResponse response) throws DocumentException, IOException {
        Deposit deposit = creditDepositRepository.findDepositById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + deposit.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportDeposit(response, deposit);
        return "redirect:/stuff/deposit/" + id;
    }

    @PostMapping("/download/excel/deposits")
    public String downloadDepositsExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<Deposit> deposits = creditDepositRepository.findAllDeposits();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=deposits" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportDeposits(response, deposits);
        return "redirect:/stuff/deposit";
    }
}
