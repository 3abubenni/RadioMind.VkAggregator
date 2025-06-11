### VK → Task Manager

Компонент забирает посты из ВКонтакте и кидает сырые данные в Task Manager для обработки ИИ.

**Как работает:**
1. Тянет посты через VK API
2. Пакует в простой JSON (текст + метаданные)
3. Отправляет в `/api/data` Task Manager

**Технологии:** Java 21, Quarkus, VK API

**Запуск:**

Укажите vk-token в application.properties и подключения к БД:
```properties
vk.token= your-vk-token

...
quarkus.datasource.password = your-password
quarkus.datasource.jdbc.url = your-url
```

```bash
mvn quarkus:dev
```  

