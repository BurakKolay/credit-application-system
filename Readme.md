
# Credit Application System

It is a restful credit application system that receives credit application requests and returns the credit result to the customer according to the relevant criteria. It is written using the Spring Boot framework. The project has been tried to be done in accordance with Solid principles.


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
