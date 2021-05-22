# EventsOOP

### Developers
🧑🏽 [Otavio Cordeiro de Freitas](https://github.com/CordeiroOtavio "Otavio's GitHub") - 190702<br/>
👦🏻 [Vitor Joaquim de Carvalho Gois](https://github.com/VitorGois "Vitor's GitHub") - 190810

> Project developed during the 5th semester of the Computer Engineering course, in the discipline of OOP.

## Description
Develop a system to control events.

### Abstract
- An event can be created by any administrator user. When creating an event, the administrator user must define the amount of free tickets, the amount of paid tickets, the amount of the ticket paid, in addition to the other information that is characteristic of an event.
- An event can be held in one or more places. And a seat can be used for zero or more events, but on different dates and times. When changing the location or date of an event, check if this is possible. It will not be possible to change the event information after it has been held. An event that already has tickets sold cannot be removed. A place cannot be removed if it has already been used by an event.
- A participant will be able to register (purchase tickets) in any registered event, respecting the limit of participants for each event or the date of the event. It is not possible to purchase a ticket for an event that occurred in the past.
- There are two types of tickets: Paid and Free. A paid ticket must have the amount paid at the time of purchase. The value of the paid ticket can be changed at any time. However, the values ​​of paid tickets already sold should not be changed. Store the date of sale of tickets and if a ticket is removed / returned, it can be sold again for the event. The amount of the ticket paid will be used as a balance for the participant who purchased the ticket. It will not be possible to remove / return a ticket from the event's start date.

## ☑️Todo list
- [x] Build UML diagram
- [x] Create and configure profile: Prod | Dev | Test
- [x] Connect repository with Heroku
- [x] Create administrator user CRUD
- [x] Create participating user CRUD
- [x] Create place CRUD
- [x] Create event CRUD
- [ ] Create ticket CRUD
- [x] Validate business rules
- [x] Insert mock data
- [x] Handle application errors
- [ ] Authentication and Authorization (Soon)

## UML
<p align="center">
  <img width="600" height="900" src="https://github.com/VitorGois/EventsOOP/blob/master/mc.png">
</p>

## 🚀 Application Endpoints
| **Endpoints**            | **Verbs Supported**       | **Description**                                                     | **Status**    |
| :----------------------: | :-----------------------: | :-----------------------------------------------------------------: | :-----------: |
| /admins                  | `GET, POST, DELETE e PUT` | Maintenance of administrator users                                  | *In Progress* |
| /attendees               | `GET, POST, DELETE e PUT` | Maintenance of participating users                                  | *In Progress* |
| /places                  | `GET, POST, DELETE e PUT` | Event venue maintenance                                             | *In Progress* |
| /events                  | `GET, POST, DELETE e PUT` | Event maintenance                                                   | *In Progress* |
| /events/{id}/places/{id} | `POST e DELETE`           | Associate or remove a location with an event                        | *Soon*        |
| /events/{id}/tickets     | `GET`                     | Returns the list of tickets for an event, type and the participants | *Soon*        |
| /events/{id}/tickets     | `POST e DELETE`           | Sell a ticket to an event and make the return                       | *Soon*        |

## 🛎️JSON Body Examples (POST)
### 🕴️ Admin
<b>Input data:</b>
```
{
	"name": "Caio Marcelo Arthur da Mota",
	"email": "caio.mota@focusnetworks.com.br",
	"phoneNumber": "(75) 98956-5296"
}
```
<b>Expected response:</b>
```
STATUS: 201 Created
{
  "id": 7,
  "name": "Caio Marcelo Arthur da Mota",
  "email": "caio.mota@focusnetworks.com.br",
  "phoneNumber": "(75) 98956-5296"
}
```
### 👨‍👩‍👦‍👦 Attendees
<b>Input data:</b>
```
{
	"name": "Tomás Theo Luan da Silva",
	"email": "tomas.silva@mail.com",
	"balance": 429.0
}
```
<b>Expected response:</b>
```
STATUS: 201 Created
{
  "id": 8,
  "attendeeId": null,
  "name": "Tomás Theo Luan da Silva",
  "email": "tomas.silva@mail.com",
  "balance": 429.0
}
```
### 📍 Places
<b>Input data:</b>
```
{
	"name": "Cidade do Rock",
	"address": "Barra da Tijuca - Rio de Janeiro/RJ"
}
```
<b>Expected response:</b>
```
STATUS: 201 Created
{
  "id": 4,
  "name": "Cidade do Rock",
  "address": "Barra da Tijuca - Rio de Janeiro/RJ"
}
```
### 🎫 Events
<b>Input data:</b>
```
{
	"adminId": 1,
	"name": "Rock in Rio",
	"description": "O Rock in Rio foi o primeiro evento musical desse tipo na América Latina e faz muito sucesso até hoje.",
	"startDate": "2022-08-02",
	"endDate": "2022-08-11",
	"startTime": "00:00:00",
	"endTime": "23:59:59",
	"emailContact": "contact@rockinrio.com",
	"amountFreeTickets": 150,
	"amountPayedTickets": 70000,
	"priceTicket": 525.00
}
```
<b>Expected response:</b>
```
STATUS: 201 Created
{
  "id": 4,
  "name": "Rock in Rio",
  "description": "O Rock in Rio foi o primeiro evento musical desse tipo na América Latina e faz muito sucesso até hoje.",
  "startDate": "2022-08-02",
  "endDate": "2022-08-11",
  "startTime": "00:00:00",
  "endTime": "23:59:59",
  "emailContact": "contact@rockinrio.com",
  "amountFreeTickets": 150,
  "amountPayedTickets": 70000,
  "priceTicket": 525.0
}
```
