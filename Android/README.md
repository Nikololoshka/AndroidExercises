Необходимо спроектировать чат для android. 
Апп должен уметь выводить чаты, сообщения чата, отправлять сообщения. 
Нужно рассказать как делать (подбор фреймворков, библиотек).

Экраны (Fragments):
1) Список с чатами (RecyclerView):
    - RecyclerView: для Pagination можно использовать `Paging3`;
    - Данные обновлются через Flow из ViewModel, а там из логики.

2) Чат с пользователем (RecyclerView, EditText, Button):
    - RecyclerView: для Pagination можно использовать Paging3;
    - По нажатию на кнопку содержимое EditText передается в ViewModel, а там далее в логику.

View:
- `Material library`;
- View (XML), хотя можно и Compose;
- MVI или MVVM.

Общие:
- Навигация: можно сделать на FragmentManager или использовать `NavComponent / Cicerone`;
- Данные чатов и сообщений можно хранить в `Room Database`;
- Для получения данных лучше использовать `ktor`, т.к. для real time сообщений надо будет поддерживать websocket;
- Для загрузки изображений можно использовать `coil-kt`;
- `Dagger` в качестве DI;

Получение данных:
- Вероятнее всего будет Service с Websocket, где будет получать данные от сервера и добавлять их в Room;
- Для отправки данных будем передовать их в Service;
- Для простоты без Service можно пока поддерживать соединение в ViewModel для MainActivity;
- Наблюдаем за данным с Room по Flow, благодаря чему можно сразу отобразить изменения далее в View;

Архитектура:
1) `core`:
  - Модуль `core:ui`: общие ui элементы;
  - Модуль `core:utils`:  разные утилиты и т.п;
  - Модуль `core:network`: api по работе с чатами и сообщениями (Api, Dto, Source);
  - Модуль `core:database`: Room БД (Database, Dao, Source);
  - Модуль `core:chat`: содержит базовую логику (models, repository, usecase) по работе с сообщениями.
2) `feature`:
  - Модуль `feature:chat-list`: список чатов;
  - Модуль `feature:chat-viewer`: сам чат.
3) `app`: сам модуль приложения.
