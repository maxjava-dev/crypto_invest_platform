 Assets - сервис для работы с активами конкретного пользователя.
---------------------------------------------------
### Основная информация:
- Взаимодействие через REST API.
- База данных: PostgreSQL. Для тестов используется H2.
- Все поля данных возвращаются в формате JSON.
- Даты и время передаются в формате LocalDateTime ISO 8601 (yyyy-MM-dd'T'HH:mm:ss.SSS).

## Описание моделей данных:
### Asset (Актив) - модель, представляющая актив клиента. 
Пример полей JSON ответа: \
{ \
"assetId": 2, \
"userId": 1, \
"cryptoId": 27, \
"quantity": 3.00, \
"cost": 453.25 \
} \
Описание полей: 
- assetId — уникальный идентификатор актива (Long). 
- userId — идентификатор клиента, владеющего активом (Long). 
- cryptoId — идентификатор криптовалюты (Long). 
- quantity — количество криптовалюты, принадлежащей клиенту (BigDecimal).
- cost — стоимость криптовалюты на момент покупки (BigDecimal).

### OperationHistory - Модель, представляющая историю операций клиента.
Пример полей JSON ответа: \
{ \
"operationId": 6, \
"cryptoId": 1,  
"operationType": "buy", \
"sumOperation": 63054.12, \
"quantityCurrentOperation": 1.00, \
"incomeCurrentOperation": 0.00, \
"purchaseDate": "2024-09-26T03:40:28.514966", \
"quantity": 2.50 \
} \
Описание полей:
- operationId — уникальный идентификатор операции (Long).
- cryptoId — идентификатор криптовалюты (Long).
- operationType — тип операции (String, возможные значения: buy, sell).
- sumOperation — общая сумма операции (BigDecimal).
- quantityCurrentOperation — количество актива в данной транзакции (BigDecimal).
- incomeCurrentOperation — прибыль или убыток от операции (BigDecimal).
- purchaseDate — дата и время операции (LocalDateTime).
- quantity — общее количество криптовалюты после операции (BigDecimal).

## Основные API запросы для работы с сервисом, примеры:
### Buy запроса актива пользователя:
POST http://localhost:8082/assets/buy?cryptoid={cryptoID}&userid={userID}&quantity={numberOfAssets} \
Описание параметров:
- cryptoID — ID криптовалюты (целое число - Long).
- userID — ID пользователя (целое число - Long).
- quantity — количество активов для покупки (десятичное число - BigDecimal).
Возвращает статус-ответ выполнения операции

### Sell запрос актива пользователя:
POST http://localhost:8082/assets/sell?cryptoid={cryptoID}&userid={userID}&quantity={numberOfAssets} \
Описание параметров:
- cryptoID — ID криптовалюты (целое число - Long).
- userID — ID пользователя (целое число - Long).
- quantity — количество активов для продажи (десятичное число - BigDecimal).
- Возвращает статус-ответ выполнения операции

### Получение списка активов пользователя:
GET http://localhost:8082/assets/info?userid={userID} \
Описание параметров: 
- userID — ID пользователя (целое число - Long).
Пример ответа: \
{ \
"assetId": 2, \
"userId": 1, \
"cryptoId": 27, \
"quantity": 3.00, \
"cost": 453.25 \
} 

### Работа с историей операций пользователя:
#### Получение списка всех транзакций конкретного пользователя
GET http://localhost:8082/operations/user/{userID} \
Описание параметров: \
- userID — ID пользователя (целое число - Long). 

Пример ответа: \
{ \
"operationId": 6, \
"cryptoId": 1, \
"operationType": "buy", \
"sumOperation": 63054.12, \
"quantityCurrentOperation": 1.00, \
"incomeCurrentOperation": 0.00, \
"purchaseDate": "2024-09-26T03:40:28.514966", \
"quantity": 2.50 \
} 

### Получение списка всех транзакций пользователя 
GET http://localhost:8082/operations/user/{userID}/crypto/{cryptoID} \
Описание параметров: 
- userID — ID пользователя (целое число - Long).
- cryptoID — ID криптовалюты (целое число - Long). \
Пример ответа: \
{ \
"operationId": 1, \
"cryptoId": 1, \
"operationType": "buy", \
"sumOperation": 127263.71, \
"quantityCurrentOperation": 2.00, \
"incomeCurrentOperation": 0.00, \
"purchaseDate": "2024-09-25T20:32:12.331036", \
"quantity": 2.00 \
}

Демонстрация работы запросов продемонстрирована в [API_Request_Example.http](API_Request_Example.http), 
для работы необходимо чтобы работали сервисы Users и CryptoCurrency.


