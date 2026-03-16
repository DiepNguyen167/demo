INSERT INTO USER_INFO (id, username, password)
VALUES (1, 'user123', 'hashed_password');

INSERT INTO USER_WALLET (user_info_id, currency, balance)
VALUES (1, 'USDT', 50000000.00),(1, 'BTC', 10.00);

INSERT INTO TRADE_PAIR (base_currency, quote_currency)
VALUES ('ETH','BTC'),('ETH', 'USDT'), ('BTC', 'USDT');



