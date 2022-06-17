package com.project.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView onUserExists(UserAlreadyExistsException ex) {
        final ModelAndView modelAndView = new ModelAndView("auth/registration");
        modelAndView.addObject("error", ex.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(ActivationCodeNotFoundException.class)
    public ModelAndView onActivationCodeNotFound(ActivationCodeNotFoundException ex) {
        final ModelAndView modelAndView = new ModelAndView("auth/login");
        modelAndView.addObject("error", ex.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(CardNotFoundException.class)
    public String cardNotFound(CardNotFoundException ex,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/transfer";
    }

    @ExceptionHandler(SameCardException.class)
    public String sameCard(SameCardException ex,
                           RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/transfer";
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public String notEnoughMoney(NotEnoughMoneyException ex,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/transfer";
    }

    @ExceptionHandler(AtmException.class)
    public String atmException(AtmException ex,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/atm";
    }

    @ExceptionHandler(DepositNotAgreeException.class)
    public String depositNotAgree(DepositNotAgreeException ex,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/deposit";
    }

    @ExceptionHandler(DepositException.class)
    public String depositException(DepositException ex,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/deposit";
    }

    @ExceptionHandler(CreditNotAgreeException.class)
    public String creditNotAgree(CreditNotAgreeException ex,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/credit";
    }

    @ExceptionHandler(CreditLimitException.class)
    public String creditLimit(CreditLimitException ex,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/credit";
    }

    @ExceptionHandler(PrematureCreditRepayException.class)
    public String prematureCreditRepay(PrematureCreditRepayException ex,
                                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/credit";
    }

    @ExceptionHandler(SubscriptionException.class)
    public String subscriptionException(SubscriptionException ex,
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/subscription";
    }

    @ExceptionHandler(ScheduledPaymentException.class)
    public String scheduledPayment(ScheduledPaymentException ex,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/transfer/scheduled";
    }

    @ExceptionHandler(ScheduledPaymentEditException.class)
    public String scheduledPaymentEdit(ScheduledPaymentEditException ex,
                                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/transfer/scheduled/edit/" + ex.getId();
    }
}
