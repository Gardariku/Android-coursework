# Android coursework
 Both client and server for an android application (extremely underdeveloped)
 
 Вкратце:
 Планируемый смысл приложения: выбор человеком блюд и составление рациона по своим предпочтениям/ограничениям.
 Каждое блюдо имеет свои свойства, которые могут не подходить под определенные диеты, вся эта информация хранится в базе.
 Чтобы соблюдать норму требуемых для огранизма питательных веществ, имеется возможность рассчитать их требуемое кол-во и подстроить под него лично составленное меню (отбалансировав блюда в меню по весу так, чтобы свести изменения к минимуму, но при этом превысить минимальную планку требуемого потребления).
 Весь этот функционал готов на сервере, но в клиенте он не восстребован.
 
 Сервер - написан на джаве, при помощи spring mvc, jpa, hibernate, jackson, h2 db, либы для линейного программирования
 База данных создается в памяти, заполняется из файла data.sql
 Сервер  обеспичивает базовые возможности по обмену данными с клиентом, а также должен был производить дополнительные рассчеты, а именно:
 Рассчет потребности человека в питательных веществах и оптимизация количественного рациона меню под эти показатели( с помощью лин прог).
 На практике это не используется, т.к. клиент не допилен(спиннеры это слишком сложно, извините, но они принципиально откзываются выводить выбранный айтем).
 
 Клиент - написан на котлине, при помощи volley/gson, по сути является только представлением и проверкой корректности отправляемых на сервер данных.
 На текущий момент из функционала: 
 1) Максимально простая авторизация/регистрация(сама высплывает перед главным активити).
 2) Первая вкладка главного активити - просмотр блюд в базе в виде ресайклера, подробная информация о блюде по клику, кнопка добаления нового блюда.
 3) Просмотр такого же списка продуктов/диет, свитч по табу.
 4) Последняя вкладка планировалась до меню, не рабочая.
5) Работает кнопка юзера из верхнего меню, можно заполнить/поменять дополнительные данные.
