CREATE TABLE user
(
    id              INTEGER      NOT NULL,
    email           VARCHAR(255) NOT NULL,
    first_name      VARCHAR(50)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    password        VARCHAR(255) NOT NULL,
    role            VARCHAR(30)  NOT NULL,
    activation_code VARCHAR(255) NOT NULL,
    active          BOOLEAN      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE card
(
    card_id      INTEGER        NOT NULL,
    user_id      INTEGER        NOT NULL,
    number       VARCHAR(255)   NOT NULL,
    currency     VARCHAR(10)    NOT NULL,
    type         VARCHAR(50)    NOT NULL,
    company_name VARCHAR(60),
    balance      NUMERIC(15, 2) NOT NULL,
    credit_limit NUMERIC(15, 2) NOT NULL,
    min_limit    NUMERIC(15, 2) NOT NULL,
    PRIMARY KEY (card_id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE transaction
(
    id                      INTEGER       NOT NULL,
    sender_id               VARCHAR(50)   NOT NULL,
    receiver_id             VARCHAR(50)   NOT NULL,
    payed_sum               NUMERIC(9, 2) NOT NULL,
    date_time               VARCHAR(20)   NOT NULL,
    type                    VARCHAR(255)  NOT NULL,
    commission_percent      INTEGER,
    commission              NUMERIC(9, 2),
    conversion_type         VARCHAR(255)  NOT NULL,
    current_conversion_rate NUMERIC       NOT NULL,
    received_sum            NUMERIC(9, 2) NOT NULL,
    comment                 VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES card (card_id),
    FOREIGN KEY (receiver_id) REFERENCES card (card_id)
);

CREATE TABLE credit
(
    id                 INTEGER       NOT NULL,
    received_sum       NUMERIC(9, 2) NOT NULL,
    months             INTEGER       NOT NULL,
    percent            INTEGER       NOT NULL,
    sum_per_month      NUMERIC(9, 2) NOT NULL,
    sum_to_repay       NUMERIC(9, 2) NOT NULL,
    current_repaid_sum NUMERIC(9, 2) NOT NULL,
    current_month      INTEGER       NOT NULL,
    next_payment       VARCHAR(15)   NOT NULL,
    is_repaid          BOOLEAN       NOT NULL,
    start_date         VARCHAR(20)   NOT NULL,
    credit_card_id     INTEGER       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (credit_card_id) REFERENCES card (card_id)
);

CREATE TABLE deposit
(
    id                   INTEGER       NOT NULL,
    card_payed_sum       NUMERIC(9, 2) NOT NULL,
    months               INTEGER       NOT NULL,
    percent              INTEGER       NOT NULL,
    sum_per_month        NUMERIC(9, 2) NOT NULL,
    sum_to_receive       NUMERIC(9, 2) NOT NULL,
    current_received_sum NUMERIC(9, 2) NOT NULL,
    current_month        INTEGER       NOT NULL,
    next_payment         VARCHAR(15)   NOT NULL,
    is_repaid            BOOLEAN       NOT NULL,
    start_date           VARCHAR(20)   NOT NULL,
    deposit_card_id      INTEGER       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (deposit_card_id) REFERENCES card (card_id)
);

CREATE TABLE planned_payments
(
    id             INTEGER       NOT NULL,
    description    VARCHAR(255),
    sum            NUMERIC(9, 2) NOT NULL,
    is_repeatable  BOOLEAN       NOT NULL,
    date_time      VARCHAR(20)   NOT NULL,
    period_in_days INTEGER       NOT NULL,
    sender_id      INTEGER       NOT NULL,
    receiver_id    INTEGER       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES card (card_id),
    FOREIGN KEY (receiver_id) REFERENCES card (card_id)
);

CREATE TABLE subscription
(
    id                   INTEGER     NOT NULL,
    subscription_user_id INTEGER     NOT NULL,
    subscription_card_id INTEGER     NOT NULL,
    info_id              INTEGER     NOT NULL,
    end_date             VARCHAR(25) NOT NULL,
    repeatable           BOOLEAN     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (subscription_user_id) REFERENCES user (id),
    FOREIGN KEY (subscription_card_id) REFERENCES card (card_id),
    FOREIGN KEY (info_id) REFERENCES subscription_info (subscription_info_id)
);

CREATE TABLE subscription_info
(
    subscription_info_id INTEGER       NOT NULL,
    name                 VARCHAR(30)   NOT NULL,
    description          VARCHAR(150)  NOT NULL,
    sum_per_month        NUMERIC(9, 2) NOT NULL,
    image_url            VARCHAR(255)  NOT NULL,
    PRIMARY KEY (subscription_info_id)
);

CREATE TABLE income_report
(
    id                INTEGER       NOT NULL,
    date              DATE NOT NULL,
    commission_income NUMERIC(20, 2) NOT NULL,
    credit_income     NUMERIC(20, 2) NOT NULL,
    penalty_income    NUMERIC(20, 2) NOT NULL,
    deposit_income    NUMERIC(20, 2) NOT NULL,
    deposit_lose      NUMERIC(20, 2) NOT NULL,
    credit_lose       NUMERIC(20, 2) NOT NULL,
    PRIMARY KEY (id)
);
