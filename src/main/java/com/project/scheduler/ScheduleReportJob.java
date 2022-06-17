package com.project.scheduler;

import com.project.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduleReportJob {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Scheduled(cron = "0 0 0 * * *")
    private void createIncome(){
        bankAccountRepository.createReport(LocalDate.now());
    }
}
