# OracleBookshop

Radosław Szepielak
Kacper Wdowiak

```markdown
# API Dokumentacja Księgarni

Witaj w dokumentacji API projektu księgarni. Poniżej znajduje się szczegółowy opis wszystkich dostępnych endpointów do zarządzania zasobami takimi jak klienci, produkty, zamówienia i inne.

## Spis treści
1.  [Zarządzanie Produktami (`/api/products`)](#zarządzanie-produktami-apiproducts)
2.  [Zarządzanie Szczegółami Filmów (`/api/movies`)](#zarządzanie-szczegółami-filmów-apimovies)
3.  [Zarządzanie Klientami (`/api/customers`)](#zarządzanie-klientami-apicustomers)
4.  [Zarządzanie Zamówieniami (`/api/orders`)](#zarządzanie-zamówieniami-apiorders)
5.  [Zarządzanie Płatnościami (`/api/payments`)](#zarządzanie-płatnościami-apipayments)
6.  [Proces Zakupu (`/api/purchase`)](#proces-zakupu-apipurchase)
7.  [Raportowanie (`/api/reports`)](#raportowanie-apireports)

---

## Zarządzanie Produktami (`/api/products`)
Endpointy do obsługi operacji CRUD na produktach.

* `GET /api/products`
    * **Opis:** Pobiera listę wszystkich produktów.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `ProductDTO`.

* `GET /api/products/{id}`
    * **Opis:** Pobiera szczegóły produktu o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `ProductDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli produkt nie istnieje.

* `POST /api/products`
    * **Opis:** Tworzy nowy produkt.
    * **Ciało żądania:** Obiekt `ProductDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z zapisanym obiektem `ProductDTO`.

* `PUT /api/products/{id}`
    * **Opis:** Aktualizuje istniejący produkt.
    * **Ciało żądania:** Obiekt `ProductDTO` z danymi do aktualizacji.
    * **Odpowiedź sukcesu:** `200 OK` ze zaktualizowanym obiektem `ProductDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli produkt nie istnieje.

* `DELETE /api/products/{id}`
    * **Opis:** Usuwa produkt o podanym ID.
    * **Odpowiedź sukcesu:** `204 No Content`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli produkt nie istnieje.

## Zarządzanie Szczegółami Filmów (`/api/movies`)
Endpointy do zarządzania szczegółowymi informacjami o filmach, które są typem produktu.

* `GET /api/movies`
    * **Opis:** Pobiera listę wszystkich filmów.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `MovieDetailDTO`.

* `GET /api/movies/{id}`
    * **Opis:** Pobiera film o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `MovieDetailDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli film nie istnieje.

* `POST /api/movies`
    * **Opis:** Tworzy nowy wpis dotyczący filmu.
    * **Ciało żądania:** Obiekt `MovieDetailDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z zapisanym obiektem `MovieDetailDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` w przypadku niepowodzenia walidacji lub błędu zapisu.

* `PUT /api/movies/{id}`
    * **Opis:** Aktualizuje dane filmu o podanym ID.
    * **Ciało żądania:** Obiekt `MovieDetailDTO`.
    * **Odpowiedź sukcesu:** `200 OK` ze zaktualizowanym obiektem `MovieDetailDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` w przypadku błędu.

* `DELETE /api/movies/{id}`
    * **Opis:** Usuwa film o podanym ID. Spowoduje to również usunięcie powiązanego produktu.
    * **Odpowiedź sukcesu:** `204 No Content`.
    * **Odpowiedź błędu:** `404 Not Found` w przypadku błędu.

## Zarządzanie Klientami (`/api/customers`)
Endpointy do obsługi operacji CRUD na klientach.

* `GET /api/customers`
    * **Opis:** Pobiera listę wszystkich klientów.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `CustomerDTO`.

* `GET /api/customers/{id}`
    * **Opis:** Pobiera klienta o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `CustomerDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli klient nie istnieje.

* `POST /api/customers`
    * **Opis:** Tworzy nowego klienta.
    * **Ciało żądania:** Obiekt `CustomerDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z zapisanym obiektem `CustomerDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` w przypadku błędu zapisu.

* `PUT /api/customers/{id}`
    * **Opis:** Aktualizuje dane klienta o podanym ID.
    * **Ciało żądania:** Obiekt `CustomerDTO`.
    * **Odpowiedź sukcesu:** `200 OK` ze zaktualizowanym obiektem `CustomerDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli klient nie istnieje.

* `DELETE /api/customers/{id}`
    * **Opis:** Usuwa klienta o podanym ID.
    * **Odpowiedź sukcesu:** `204 No Content`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli klient nie istnieje.

## Zarządzanie Zamówieniami (`/api/orders`)
Endpointy do obsługi operacji CRUD na zamówieniach.

* `GET /api/orders`
    * **Opis:** Pobiera listę wszystkich zamówień.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `OrderDTO`.

* `GET /api/orders/{id}`
    * **Opis:** Pobiera zamówienie o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `OrderDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli zamówienie nie istnieje.

* `POST /api/orders`
    * **Opis:** Tworzy nowe zamówienie.
    * **Ciało żądania:** Obiekt `OrderDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z zapisanym obiektem `OrderDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` w przypadku błędu zapisu.

* `PUT /api/orders/{id}`
    * **Opis:** Aktualizuje zamówienie o podanym ID.
    * **Ciało żądania:** Obiekt `OrderDTO`.
    * **Odpowiedź sukcesu:** `200 OK` ze zaktualizowanym obiektem `OrderDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli zamówienie nie istnieje.

* `DELETE /api/orders/{id}`
    * **Opis:** Usuwa zamówienie o podanym ID.
    * **Odpowiedź sukcesu:** `204 No Content`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli zamówienie nie istnieje.

## Zarządzanie Płatnościami (`/api/payments`)
Endpointy do obsługi operacji CRUD na płatnościach.

* `GET /api/payments`
    * **Opis:** Pobiera listę wszystkich płatności.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `PaymentDTO`.

* `GET /api/payments/{id}`
    * **Opis:** Pobiera płatność o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `PaymentDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli płatność nie istnieje.

* `POST /api/payments`
    * **Opis:** Tworzy nową płatność.
    * **Ciało żądania:** Obiekt `PaymentDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z zapisanym obiektem `PaymentDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` w przypadku błędu zapisu.

* `PUT /api/payments/{id}`
    * **Opis:** Aktualizuje płatność o podanym ID.
    * **Ciało żądania:** Obiekt `PaymentDTO`.
    * **Odpowiedź sukcesu:** `200 OK` ze zaktualizowanym obiektem `PaymentDTO`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli płatność nie istnieje.

* `DELETE /api/payments/{id}`
    * **Opis:** Usuwa płatność o podanym ID.
    * **Odpowiedź sukcesu:** `204 No Content`.
    * **Odpowiedź błędu:** `404 Not Found`, jeśli płatność nie istnieje.

## Proces Zakupu (`/api/purchase`)
Endpointy orkiestrujące cały proces zakupu, od złożenia zamówienia po jego finalizację.

* `POST /api/purchase/create-order`
    * **Opis:** Tworzy nowe zamówienie na podstawie koszyka produktów.
    * **Ciało żądania:** Obiekt `PurchaseRequestDTO`.
    * **Odpowiedź sukcesu:** `200 OK` z utworzonym `OrderDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `POST /api/purchase/pay/{orderId}`
    * **Opis:** Przetwarza płatność za zamówienie o podanym ID.
    * **Odpowiedź sukcesu:** `200 OK` z komunikatem o statusie płatności.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `POST /api/purchase/retry-payment/{orderId}`
    * **Opis:** Ponawia próbę płatności za zamówienie.
    * **Odpowiedź sukcesu:** `200 OK` z komunikatem o statusie płatności.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `POST /api/purchase/ship/{orderId}`
    * **Opis:** Rozpoczyna proces wysyłki zamówienia.
    * **Parametry żądania:** `shipperId` (ID spedytora).
    * **Odpowiedź sukcesu:** `200 OK` z komunikatem o wysyłce.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `POST /api/purchase/complete/{orderId}`
    * **Opis:** Finalizuje zamówienie, oznaczając je jako zakończone.
    * **Odpowiedź sukcesu:** `200 OK` z komunikatem o zakończeniu zamówienia.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `POST /api/purchase/cancel/{orderId}`
    * **Opis:** Anuluje zamówienie.
    * **Odpowiedź sukcesu:** `200 OK` z komunikatem o anulowaniu.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `GET /api/purchase/order/{orderId}`
    * **Opis:** Pobiera status i szczegóły zamówienia.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `OrderDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

* `GET /api/purchase/order/{orderId}/payments`
    * **Opis:** Pobiera historię płatności dla danego zamówienia.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `PaymentDTO`.
    * **Odpowiedź błędu:** `400 Bad Request` z komunikatem o błędzie.

## Raportowanie (`/api/reports`)
Endpointy do generowania różnych raportów biznesowych.

* `GET /api/reports/low-stock`
    * **Opis:** Generuje raport produktów z niskim stanem magazynowym.
    * **Odpowiedź sukcesu:** `200 OK` z listą obiektów `LowStockReportDTO`.
    * **Odpowiedź błędu:** `200 OK` z pustą listą w przypadku błędu.

* `GET /api/reports/monthly-orders`
    * **Opis:** Generuje raport zamówień z bieżącego miesiąca.
    * **Odpowiedź sukcesu:** `200 OK` z obiektem `MonthlyOrdersReportDTO`.
    * **Odpowiedź błędu:** `500 Internal Server Error` w przypadku błędu.
```
