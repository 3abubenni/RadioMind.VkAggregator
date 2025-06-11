### VK → Task Manager

Компонент забирает посты из ВКонтакте и кидает сырые данные в Task Manager для обработки ИИ.

**Как работает:**
1. Тянет посты через VK API
2. Пакует в простой JSON (текст + метаданные)
3. Отправляет в `/api/data` Task Manager

**Технологии:** Java 21, Quarkus, VK API

**Запуск:**

Укажите vk-token в application.properties и подключения к БД, а также хост task-manager:
```properties
vk.token= your-vk-token

...
quarkus.datasource.password = your-password
quarkus.datasource.jdbc.url = your-url
...
com.example.TaskManagerClient/mp-rest/url= http://localhost:8080/api (если локально развёртываете, то можете и так оставить)
```

```bash
mvn quarkus:dev
```  

