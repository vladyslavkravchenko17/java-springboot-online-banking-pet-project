INSERT INTO subscription_info (subscription_info_id, name, description, sum_per_month, image_url)
VALUES (1, 'YouTube Premium', 'Music and videos without adverts!', 120.00, youtube.png),
       (2, 'Spotify', 'Listen to your favorite music without limit!', 100.00, spotify.png),
       (3, 'Netflix', 'Huge library of movies and series!', 250.00, netflix.png),
       (4, 'Nord VPN', 'Connect to IP address of any country!', 400.00, vpn.png),
       (5, 'Apple Music', 'Any kind of music you want is in your phone!', 200.00, apple_music.jpg),
       (6, 'Intellij Idea', 'Professional Java development tool!', 550.00, intellij.jpg);

INSERT INTO card (card_id, user_id, number, balance, currency, type, company_name, credit_limit, min_credit)
VALUES (-3, -1, 'Netflix', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-4, -1, 'Intellij Idea', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-5, -1, 'Nord VPN', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-6, -1, 'Spotify', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-7, -1, 'YouTube Premium', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00),
       (-8, -1, 'Apple Music', 0.00, 'UAH', 'BANK_ACCOUNT', null, 0.00, 0.00);