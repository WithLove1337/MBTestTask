# TestTask
Нашел решение проблемы зацикливания немного костыльное, но надежное, чтобы не путаться, в каких случаях у нас по каскадности все удаляется, а в каких происходит зацикленный вывод: можно просто отдельно передавать элементы списка и отдельно информацию о списке в одном json-файле, либо работать с DTO, чтоы оно преобразовывало данные обрабатывая те места, где идет зацикливание

Хотелось бы создать ещё  одну аннотацию: что-то вроде обработчика ошибок, чтобы он не пускал запросы с ошибочными данными, и при вызове "неправильных" прописывал нам что-то в лог, но опять же сроки жмут, а улучшать что-то можно до бесконечности)

Не смог понять проблемы с unit-тестами, буду рад узнать почему и что я не так делал. 

# MyList
Нашел ошибку в домашнем задании по созданию списков, у меня вылетал нульпоинтер, в коде правильно не прописал конец двухсвязного списка, тогда не хватало времени, сейчас готовый рабочий список
