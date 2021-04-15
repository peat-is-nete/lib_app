Google Docs:
https://docs.google.com/document/d/1MzOlVNFoowCJUvqQW7oVXHJiOmXD3sWv-W_Moi5kPmA/edit#


# Library API


Kiran Bala
&
Pete Micsunescu



## Overview: 

The Library API provides users a convenient way to manage inventory on books and track checkouts based on particular user roles. The Library API features various endpoints with numerous functions, such as viewing inventory and checking books out, that are critical in any library setting; _refer to ‘API Endpoints’ below for full details on endpoints_. Because all of the transactions in the Library API is persistently stored using a database, there is no need to worry about data-loss, corruption, or running out of storage space! The Library API is a simple, yet powerful tool that can act as the foundation for any professional library institution.



## User Stories

- As an 'admin' user of a library…
  - I want to add, update, delete from, and see the catalog.
  - I want to organize books by categories.
  - I want to checkout, renew, and return a book for a user.
  - I want to see what books a user has borrowed.
  
- As a 'non-admin' user of a library…
  - I want to see the catalog.
  - I want to see which books I have checked out.



## Entity Relationship Diagram (ERD):

The ERD below demonstrates how the many entities of the Library API relate to each other. This is an overview of the API’s business process logic. Notice the crow’s foot notation specifying the multiplicity relationships between the entities. 

There are four relationships:
* One-to-One between User and Role.
  * A user’s role can be either ‘non-admin’ or ‘admin’.
* One-to-One between Book and Checkout.
  * A single particular book can only be taken-out once.
* One-to-Many between User and Checkout.
  * A user can take-out many books.
* One-to-Many between Category and Book.
  * A category can contain many books.


![Project 2 - Libraryapp ERD](https://user-images.githubusercontent.com/67444113/114885134-fdfbed80-9dcb-11eb-98b4-69eadf0fa474.jpg)



## API Endpoints:

Request Header Information:
- [OPEN] : 
  - No Authorization Header :

- [NON-AD] : 
  - Authorization Bearer {NON-ADMIN TOKEN} :

- [ADMIN] :
  - Authorization Bearer {ADMIN TOKEN} :


|  # | REQ. TYPE |          URL          |  REQ. BODY                                                                       | REQ. HEADER |
| -- | --------- | --------------------- | -------------------------------------------------------------------------------- | ----------- |
| 01 | POST      | /auth/users/register  | { "emailAddress": "a", "password": "b", "roleType": 0 or 1 (non-admin or admin)  | [OPEN]      |                       
| 02 | POST      | /auth/users/login     | { "email": "a", "password": "b" }                                                | [OPEN]      |
| 03 | POST      | /auth/users/login     | { "email": "a", "password": "b" }                                                | [OPEN]      |





## Technologies Leveraged:

|     Tech. Name     |            Utilization in Project            |
| ------------------ | -------------------------------------------- |
| Bcrypt             | Hashing function stores passwords in DB.     |
| Hibernate          | Entity management.                           |
| Java 11            | Code language.                               |
| JWT                | JSON signed with HS256 algorithm.            |
| Maven              | Java dependency management.                  |
| PostgreSQL         | Relational Database.                         |
| Spring Boot        | API framework.                               |
| Spring Security    | Framework providing JWT authentication.      |





