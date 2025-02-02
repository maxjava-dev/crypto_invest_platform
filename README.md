Платформа инвестирования в криптовалюту

=========================================

Функциональные требования

1) Главная страница
   a) Кнопка Войти
   б) Кнопка Регистрация
   в) Какая-нибудь картинка и текст
   г) Доступ к странице всем без авторизации
2) Страница входа (логин, пароль, кнопка)
3) Страница регистрации (логин, пароль, почта, ФИО, кнопка)
4) Главная страница авторизированного пользователя
   а) Шапка (ФИО, номер счета, текущий баланс, кнопка Пополнить, кнопка Вывести, кнопка Выйти). Можно выводить на всех страницах
   б) Список купленных криптовалют (код, название, количество, дата и время покупки,
      сумма покупки, текущая стоимость, доходность, кнопка Купить, кнопка Продать)
   в) Внизу кнопка Купить
   г) Общая доходность счета
   д) Общая сумма счета с учетом свободных средств и купленных криптовалют
5) Страница (или страницы) пополнения счета и вывода средств (номер счета, сумма, кнопка)
6) Страница покупки новой криптовалюты
   а) Поле ввода для поиска или филтрации криптовалют
   б) Таблица криптовалют (код, название, цена, доходность за последнюю неделю/день/месяц, купить)
7) Страница подтверждения покупки и продажи (выбранная криптовалюта, вводимое количество, сумма, кнопка)

Открытые вопросы
1) Нужна ли панель администрирования?
2) Будем ли делать страницу криптовалюты, где будет указана подробная информация и график? 
3) На экране выбора криптовалюты в случае когда фильтр не заполнен будем ли отображать список всех криптовалют?
4) Что еще можно добавить ? Не слишком ли мало у нас получается функционала ?


==========================================

Порядок совместной работы над проектом

1) Создаем задачу в трекере. Если задача относится к одному из микросервисов, 
то в поле "Проект" выбираем соответствующий микросервис. 
Или выбираем одну из имеющийся задач, если она ни на кого не назначена.
2) Назначаем на себя, переводим в статус "В работе".
3) Создаем новую ветку от ветки develop с названием "crypto-[НОМЕР_ЗАДАЧИ]"
4) Вносим правки в код
5) Делаем коммит с названием "crypto-[НОМЕР_ЗАДАЧИ]: [НАЗВАНИЕ_ЗАДАЧИ]"
6) Создаем ПР в ветку develop
7) Если есть замечания, то вносим правки и делаем Force Push
8) После 2 апрувов делаем MERGE
9) Переводим задачу в статус "Закрыта"