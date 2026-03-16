## Trade Demo 

## Introduction
This is a demo of a trading system that uses machine learning to predict stock prices.

## APIs

### Retrieve Best prices
```
GET /api/best-price/{symbol}
```

### Retrieve user's wallet
```
GET /api/user-wallet/{user_id}
```

### Place a Trade
```
POST /api/trade-transaction
{
    "userInfoId": 123,
    "tracePairId": 3,
    "tradeType": "BID",
    "quantity": "0.00001"
}
```
### Retrieve trade transactions
```
GET /api/trade-transaction/{user_id}
```


BEST_ASK: 2000
BEST_BID: 1500


USER BID - 
USER ASK - 
