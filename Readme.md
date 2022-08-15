
# Credit Application System

It is a restful credit application system that receives credit application requests and returns the credit result to the customer according to the relevant criteria. It is written using the Spring Boot framework. The project has been tried to be done in accordance with Solid principles.

## Requirements
* New customers can be created in the system, existing customers can be updated or deleted.
* If the credit score is below 500, the customer will be rejected. (Credit result: Rejected)
* If the credit score is between 500 points and 1000 points and the monthly income is below 5000 TL, the credit application of the customer is approved and a limit of 10.000 TL is assigned to the customer. (Credit Result: Aproved)
* If the credit score is between 500 points and 1000 points and the monthly income is above 5000 TL, the credit application of the customer is approved and a 20.000 TL limit is assigned to the customer. (Credit Result: Approved)
* If the credit score is equal to or above 1000 points, the customer is assigned a limit equal to MONTHLY INCOME * CREDIT LIMIT MULTIPLIER.The credit limit multiplier is 4 by default. (Credit Result: Approved)
* As a result of the conclusion of the credit, the relevant application is recorded in the database. Afterwards, an informative SMS is sent to the relevant phone number and the approval status information (rejection or approval), limit information is returned from the endpoint.
* A completed loan application can only be queried with an national ID number.


## API Using

### Create new customer

```http
  POST /customer/${identityNumber}
```

| Parametre     | Tip     | Açıklama                  |
|:--------------|:--------|:--------------------------|
| `CustomerDTO` | `model` | **Required**. Based stats |
Can create user

### Update customer

```http
  PUT /customer/update/${identityNumber}
```

| Parametre        | Tip      | Açıklama         |
|:-----------------|:---------|:-----------------|
| `identityNumber` | `string` | **Required**.    |
| `CustomerDTO`    | `model`  | **Required**.    | 

Can change the user's properties

### Create new credit application

```http
  POST /credit/create/${identityNumber}
```

| Parametre        | Tip      | Açıklama                             |
|:-----------------|:---------|:-------------------------------------|
| `identityNumber` | `string` | **Required**. for create application |
Can create credit application

### Update credit application

```http
  PUT /credit/update/${identityNumber}
```

| Parametre        | Tip      | Açıklama                             |
|:-----------------|:---------|:-------------------------------------|
| `identityNumber` | `string` | **Required**. for create application |
Can terminate the credit application



## Run in Postman
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/489be21d79699cc70712?action=collection%2Fimport)

## Entity Relationship Diagram
![DataBaseDesign2](https://user-images.githubusercontent.com/107641642/184563811-f92764ab-5727-4289-8128-8a4bc429f7ff.png)

## Swagger
[![View in Swagger](http://jessemillar.github.io/view-in-swagger-button/button.svg)](http://localhost:8080/swagger-ui/index.html)
![userController](https://user-images.githubusercontent.com/107641642/184563878-d3388b2a-145c-4dd8-8855-8c8b0bb22e09.png)
![customerController](https://user-images.githubusercontent.com/107641642/184563885-e2b69c62-2859-4968-83fa-2a7d9ad41560.png)
![creditController](https://user-images.githubusercontent.com/107641642/184563899-9935db5e-2aa4-4b14-89ad-a6d73a0365bc.png)

