## Trade Demo 

## Introduction
This is a demo of a trading system that uses machine learning to predict stock prices.

## APIs

### Retrieve Best prices
```
GET /api/v1/best-prices/{symbol}
```

### Retrieve user's wallet
```
GET /api/v1/user-wallets/{user_id}
```

### Place a Trade
```
POST /api/v1/trade-transactions/
{
    "userInfoId": 123,
    "tracePairId": 3,
    "tradeType": "BID",
    "quantity": "0.00001"
}
```
### Retrieve trade transactions
```
GET /api/v1/trade-transactions/{user_id}
```


BEST_ASK: 2000
BEST_BID: 1500


USER BID - 
USER ASK - 
