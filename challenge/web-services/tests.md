##Module breakdown

As explained before in the project's readme, the module relies on Rest Assured for 
conducting the tests. It also uses Lombok and Jackson annotations for mapping JSON to Java classes.
It is worth to remind that Lombok requires an IDE (I personally use IntelliJ) plugin of the same name in order to work.<br/>
The Java classes or "models" were splitted into two packages for separating request from responses although it was proofed
that they end up using almost the same structures, so this would be a place to do some refactoring.<br/>
The approach is very straight forward, do the corresponding call based on the documentation provided 
by the swagger page. The issue here is that the page might not be maintained as it should and proofed
to be inaccurate regarding mandatory fields, status codes and responses.<br/> 
For running the tests, the pet store server should be running. The instructions can be found in https://github.com/swagger-api/swagger-petstore, 
but it is enough to clone the project and through a terminal run "mvn package jetty:run" on the root of the project.<br/>
Each suite has failing tests and in one particular case (creating an order with empty body) it broke entirely the possibility of performing
a specific request (getting the inventory).<br/>
Due to the amount of time remaining, the tests are not as nicely developed as they should. You will see hard coded values
in many tests, ideally with more time, a timestamp or random value will be added as a suffix or prefix to make values unique.
By using repeated values, it was tested as well that no value is unique and will lead to duplicates.<br/>


###Tests to be developed

####Pets
1. POST - /pet - 200
2. POST - /pet - 405
3. GET - /pet/{petId} - 200
4. GET - /pet/{petId} - 404
5. DELETE - /pet/{petId} - 200
####Store
6. GET - /store/inventory - 200
7. POST - /store/order - 200
8. POST - /store/order - 405
9. POST - /store/order - 500
10. GET - /store/order/{orderId} - 200
11. DELETE - /store/order/{orderId} - 200
####User
12. POST - /user - 200
13. GET - /user/login - 200
14. GET - /user/{username} - 200
15. DELETE - /user/{username} - 200
16. DELETE - /user/{username} - 404
