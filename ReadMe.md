Projektowanie aplikacji webowych Backend
Act Framework

| Metoda | Ścieżka                     | Parametr | Funkcja              | Body                             | Odpowiedź                                                           |
|--------|-----------------------------|----------|----------------------|----------------------------------|---------------------------------------------------------------------|
| GET    | /table                      | none     | getTableList         | none                             |     [{"id":1,"name":"tablica","pawLists":[]}]                       |
| POST   | /table                      | none     | addTable             | {"name" : "tablica"}             |     [{"id":1,"name":"tablica","pawLists":[]}]                       |
| GET    | /table/{id}                 | {id}     | getTableDetails      | none                             |     [{"id":1,"name":"tablica","pawLists":[]}]                       |
| PUT    | /table/{id}                 | {id}     | updateTableName      | {"id": "1", "name" : "tablica"}  |     [{"id":1,"name":"tablica","pawLists":[]}]                       | 
| POST   | /table/{id}/list            | {id}     | addNewList           | {"name" : "list"}                |     {"id":3,"name":"tablica","pawLists":[{"id":5,"name":"list"}]}   |
| GET    | /table/{id}/list            | {id}     | getPawListListOnTable| none                             |     [{"id":5,"name":"list"}]                                        |
