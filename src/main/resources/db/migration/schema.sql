CREATE TABLE USER_INFO (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL
);
ALTER TABLE USER_INFO ADD CONSTRAINT uc_username UNIQUE (username);

CREATE TABLE USER_WALLET (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_info_id BIGINT NOT NULL ,
                             currency VARCHAR(10) NOT NULL,
                             balance DECIMAL(18, 8) NOT NULL
);

ALTER TABLE USER_WALLET ADD CONSTRAINT fk_user_wallet_user FOREIGN KEY (user_info_id) REFERENCES USER_INFO (id) ON DELETE CASCADE;

CREATE TABLE TRADE_PAIR (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             base_currency VARCHAR(10) NOT NULL,
                             quote_currency VARCHAR(10) NOT NULL,
                             UNIQUE (base_currency, quote_currency)
);

CREATE TABLE TRADE_TRANSACTION (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   user_info_id BIGINT NOT NULL,
                                   trade_pair_id BIGINT NOT NULL,
                                   trade_type VARCHAR(10) NOT NULL, -- BUY/SELL
                                   quantity DECIMAL(18, 8) NOT NULL,
                                   price DECIMAL(18, 8) NOT NULL,
                                   timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   status varchar(10) NOT NULL,
                                   FOREIGN KEY (user_info_id) REFERENCES USER_INFO (id) ON DELETE CASCADE
);
CREATE INDEX idx_user_info_id ON TRADE_TRANSACTION (user_info_id);


CREATE TABLE BEST_PRICING (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              trade_pair_id BIGINT NOT NULL,
                              bid_price DECIMAL(18,8) NOT NULL,
                              ask_price DECIMAL(18,8) NOT NULL,
                              timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);