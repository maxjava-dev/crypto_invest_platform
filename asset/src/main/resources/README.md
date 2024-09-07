Основные API запросы для работы с сервисом, примеры:
POST:
http://localhost:8082/assets/sell?cryptoid=3&userid=22&quantity=1 - 
пример buy/sell запроса(покупка/продажа актива пользователя в json формате)

GET 
http://localhost:8082/assets/info?userid=2 - 
пример get запроса (получение принадлежащих активов пользователя в json формате)

История операций:
GET http://localhost:8082/operations/user/1 - получение всех транзакций конкретного пользователя по userId
GET http://localhost:8082/operations/user/1/crypto/4 - получение всех транзакций пользователя по конкретной криптовалюте

GET http://localhost:8083/crypto/1 - запрос к CryptoCurrency для получения данных по 
определенной криптовалюте(запускается отдельно в запущенном микросервисе CryptoCurrency как вспомогательный)