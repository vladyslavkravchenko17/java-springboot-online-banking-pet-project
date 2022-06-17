package com.project.controller;

import com.lowagie.text.DocumentException;
import com.project.domain.IncomeReport;
import com.project.exporter.DataExcelExporter;
import com.project.exporter.DataPDFExporter;
import com.project.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DataExcelExporter dataExcelExporter;

    @Autowired
    private DataPDFExporter receiptPDFExporter;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @GetMapping
    public String menu() {
        return "redirect:/admin/income/month";
    }

    @GetMapping("/income/month")
    public String monthIncome(Model model) {
        List<IncomeReport> reports = bankAccountRepository.findAllMonthReports();
        Collections.reverse(reports);
        model.addAttribute("reports", reports);
        model.addAttribute("bankBalance", bankAccountRepository.findBank().getBalance());
        return "admin/monthIncome";
    }

    @PostMapping("/download/pdf/monthly")
    public String downloadMonthlyPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=monthly_incomes_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<IncomeReport> reports = bankAccountRepository.findAllMonthReports();
        receiptPDFExporter.exportMonthlyReports(response, reports);

        return "redirect:/admin/income/month";
    }

    @PostMapping("/download/excel/monthly")
    public String downloadMonthlyExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<IncomeReport> reports = bankAccountRepository.findAllMonthReports();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=monthly_incomes_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportMonthlyReports(response, reports);

        return "redirect:/stuff/income/month";
    }

    @GetMapping("/income/day")
    public String dayIncome(Model model) {
        List<IncomeReport> reports = bankAccountRepository.findAllReports();
        Collections.reverse(reports);
        model.addAttribute("reports", reports);
        model.addAttribute("bankBalance", bankAccountRepository.findBank().getBalance());

        return "admin/dayIncome";
    }

    @PostMapping("/download/pdf/daily")
    public String downloadDailyPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=daily_incomes_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<IncomeReport> reports = bankAccountRepository.findAllReports();
        receiptPDFExporter.exportDailyReports(response, reports);

        return "redirect:/admin/income/day";
    }

    @PostMapping("/download/excel/daily")
    public String downloadDailyExcel(HttpServletResponse response) throws DocumentException, IOException {
        List<IncomeReport> reports = bankAccountRepository.findAllReports();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=daily_incomes_" + LocalDateTime.now() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        dataExcelExporter.exportDailyReports(response, reports);

        return "redirect:/stuff/income/day/";
    }

    @GetMapping("/income/month/{date}")
    public String monthReport(@PathVariable String date,
                              Model model) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        List<IncomeReport> reports = bankAccountRepository.findIncomesByMonth(month, year);
        model.addAttribute("size", reports.size());
        model.addAttribute("date", date);
        model.addAttribute("monthReport", bankAccountRepository.findMonthReport(month, year));
        Collections.reverse(reports);
        model.addAttribute("reports", reports);
        model.addAttribute("bankBalance", bankAccountRepository.findBank().getBalance());
        return "admin/monthlyIncome";
    }

    @PostMapping("/download/pdf/month")
    public String downloadMonthPDF(HttpServletResponse response,
                              @RequestParam String date) throws DocumentException, IOException {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        List<IncomeReport> reports = bankAccountRepository.findIncomesByMonth(month, year);
        IncomeReport report = bankAccountRepository.findMonthReport(month, year);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=month_income_" + date + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportMonthReport(response, report, reports);
        return "redirect:/admin/income/day";
    }

    @PostMapping("/download/excel/month")
    public String downloadMonthExcel(HttpServletResponse response,
                                     @RequestParam String date) throws DocumentException, IOException {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        List<IncomeReport> reports = bankAccountRepository.findIncomesByMonth(month, year);
        IncomeReport report = bankAccountRepository.findMonthReport(month, year);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=month_income_" + date + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportMonthReport(response, report, reports);
        return "redirect:/stuff/income/day/";
    }

    @GetMapping("/income/day/{date}")
    public String dayReport(@PathVariable String date,
                            Model model) {
        model.addAttribute("report", bankAccountRepository.findIncomeByDate(date));
        model.addAttribute("bankBalance", bankAccountRepository.findBank().getBalance());
        return "admin/dailyIncome";
    }

    @PostMapping("/download/pdf/day")
    public String downloadDayPDF(HttpServletResponse response,
                              @RequestParam String date) throws DocumentException, IOException {
        IncomeReport report = bankAccountRepository.findIncomeByDate(date);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=day_income_" + date + ".pdf";
        response.setHeader(headerKey, headerValue);
        receiptPDFExporter.exportDayReport(response, report);

        return "redirect:/admin/income/day/" + date;
    }

    @PostMapping("/download/excel/day")
    public String downloadDayExcel(@RequestParam String date,
                                       HttpServletResponse response) throws DocumentException, IOException {
        IncomeReport report = bankAccountRepository.findIncomeByDate(date);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=day_income_" + report.getId() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        dataExcelExporter.exportDayIncome(response, report);
        return "redirect:/stuff/income/day/" + date;
    }

    @GetMapping("/income/graph/{date}")
    public String incomeGraph(Model model,
                              @PathVariable String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        List<IncomeReport> reports = bankAccountRepository.findIncomesByMonth(month, year);
        StringBuilder values = new StringBuilder("" + (reports.get(0).getTotalIncome()));
        for (int i = 1; i < reports.size(); i++) {
            values.append(", ").append(reports.get(i).getTotalIncome());
        }
        model.addAttribute("bankBalance", bankAccountRepository.findBank().getBalance());
        model.addAttribute("date", date);
        model.addAttribute("values", values.toString());
        return "admin/graph";
    }
}
