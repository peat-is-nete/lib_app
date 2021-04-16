Google Docs:
https://docs.google.com/document/d/1MzOlVNFoowCJUvqQW7oVXHJiOmXD3sWv-W_Moi5kPmA/edit#


![pixil-frame-0(1)](https://user-images.githubusercontent.com/67444113/115003534-8382aa80-9e6b-11eb-9f6d-8f5b242271a2.png)


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


| No.| REQ. TYPE |          URL                  |  REQ. BODY                                                                       | REQ. HEADER |
| -- | --------- | ----------------------------  | -------------------------------------------------------------------------------- | ----------- |
| 01 | POST      | /auth/users/register          | { "emailAddress": "a", "password": "b", "roleType": 0 or 1 (non-admin or admin)  | [OPEN]      |                       
| 02 | POST      | /auth/users/login             | { "email": "a", "password": "b" }                                                | [OPEN]      |
| 03 | GET       | /api/categories               | {}                                                                               | [NON-AD]    |
| 04 | POST      | /api/categories               | { "name":"a", "description":"b" }                                                | [ADMIN]     |
| 05 | DELETE    | /api/categories/{catId}       | {}                                                                               | [ADMIN]     |
| 06 | GET       | /api/categories/{catId}       | {}                                                                               | [NON-AD]    |
| 07 | PUT       | /api/categories/{catId}       | { "name":"a", "description":"b" }                                                | [ADMIN]     |
| 08 | POST      | /api/categories/{catId}/books | { "title":"a", "author":"b", "publisher": "c"}                                   | [ADMIN]     |
| 09 | GET       | /api/categories/{catId}/books | {}                                                                               | [NON-AD]    |
| 10 | GET       | /api/categories/{catId}/books/{bookId} | {}                                                                      | [NON-AD]    |
| 11 | PUT       | /api/categories/{catId}/books/{bookId} | { "title":"a", "author":"b", "publisher": "c"}                          | [ADMIN]     |
| 12 | DELETE    | /api/categories/{catId}/books/{bookId} | {}                                                                      | [ADMIN]     |
| 13 | GET       | /api/checkouts/{userId}                | {}                                                                      | [NON-AD]    |
| 14 | POST      | /api/checkouts/{userId}/{bookId}       | {"checkoutDate":"YYYY-MM-DD", "dueDate":"YYYY-MM-DD", "book":{book} }   | [ADMIN]     |
| 15 | PUT       | /api/checkouts/{checkoutId}/{userId}/{bookId} | {}                                                               | [ADMIN]     |
| 16 | DELETE    | /api/checkouts/{userId}/{bookId}              | {}                                                               | [ADMIN]     |


### Endpoint Synopsis :

**01** - This is an OPEN access endpoint allowing for the creation of users that will have access to the Library API. Notice that the JSON object passed in the request allows for a 'roleType' to be assigned to the created user based on whether the user should be a library administrator or not.

**02** - This is an OPEN access portal endpoint taking a JSON object with a user's email and password as creditionals in the request. If authentication is successful, a JWT is returned in the response body based on the user's role. 

**03** - This is a NON-AD access endpoint that returns all the categories in the Library database. Note, only authenticated users of the Library API database can access this endpoint.

**04** - This is an ADMIN access endpoint that allows for the creation of a new category in the Library database. 

**05** - This is an ADMIN access endpoint that allows for the deletion of a category based on its Category ID.

**06** - This is a NON-AD access endpoint that allows for the retrieval of a category based on its Category ID.

**07** - This is an ADMIN access endpoint that allows for the revision of a category based on its Category ID. It takes a JSON object in its request body.

**08** - This is an ADMIN access endpoint that allows for the insertion of a new book entry based on its Category ID. It takes a JSON object in its request body.

**09** - This is a NON-AD access endpoint that allows for the retrieval of all books contained in a Category ID.

**10** - This is a NON-AD access endpoint that allows for the retrieval of a single book based on Category ID and Book ID.

**11** - This is an ADMIN access endpoint that allows for the revision of a book entry based on Category ID and Book ID.

**12** - This is an ADMIN access endpoint that allows for the deletion of a book entry based on Category ID and Book ID.

**13** - This is a NON-AD access endpoint that allows for retrieval of all Checkouts based on User ID. Note, as of now, can only view Checkouts based on the User ID that is currently logged in.

**14** - This is an ADMIN access endpoint that allows for the revision of a book Checkout based on Checkout ID, User ID, and Book ID. It takes a JSON object in its request body.

**15** - This is an ADMIN access endpoint that allows for the renewal of a Checkout based on User ID and Book ID. Note, renewal is set to an additional 21 days from today.

**16** - This is an ADMIN access endpoint that allows for the deletion of a book Checkout based on User ID and Book ID. 



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





