INSERT INTO card (card_id, user_id, number, balance, currency, type, company_name, credit_limit, min_credit)
VALUES (-1, -1, 'ATM', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-2, -1, 'Bank', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00);

INSERT INTO users (id, email, first_name, last_name, activation_code, active, password, role)
VALUES (-1, '', 'admin', 'account', null, false, 'admin', 'ADMIN');