# cern-todo-app
Rest Api to perform CRUD operations on TODO tasks.

These are APIs that we need to provide:

Methods	Urls	Actions
POST	        /api/todos/:categoryId	                create new todo for a category

POST	        /api/categories     	                create new category

GET             api/todos/categoryId/:id                retrieve all Todos of a category

GET	            /api/todos      	                    retrieve all the Todos

GET	            /api/categories/:id	                    retrieve a category by :id
GET	            /api/categories      	                retrieve all the categories
GET	            /api/todos/:id	                        retrieve a Todo by :id
PUT	            /api/todos/:id	                        update a Todo by :id
PUT	            /api/categories/:id	                    update a category by :id
DELETE	        /api/todos/:id	                        delete a Todo by :id
DELETE	        /api/todos                              delete all Todos
DELETE	        /api/categories/:id	                    delete a category by :id
DELETE	        /api/categories                         delete all categories

For testing I used Postman

An example of Json for creating a category:

{
    "name" : "3 days",
    "description": " to be done in 3 days"
}

An example of Json for creating a todo with category 11:
http://localhost:8080/api/todos/11

 {      "name": " supermarket",
        "description": " My list for the supermarket",
        "deadline": "2021-05-12"
    }
