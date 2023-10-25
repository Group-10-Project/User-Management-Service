UserManagementService
---------------------

### DB Schema
    Table: User
    Columns: id, name, email, password, created_at, updated_at

    Table: Session
    Columns: id, user_id, token, created_at, updated_at

### API

### User Registration
    Endpoint: /api/v1/user/register
    Method: POST
    Request Body: {
        "name": "John Doe",
        "email": "John@email.com"
        "password": "password"
    }
    Response Body: {
        "id": 1,
        "name": "John Doe",
        "email": "John@email.com"
    }

### User Login
    Endpoint: /api/v1/user/login
    Method: POST
    Request Body: {
        "email": "John@email.com"
        "password": "password"
    }
    Response Header: {
        "Status": "200 OK"
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }

### User Logout
    Endpoint: /api/v1/user/logout/{userId}
    Method: POST
    Request Header: {
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }
    Request Body : {
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }
    Response Body: {
        "message": "User logged out successfully"
    }

### User Profile
    EndPoint: /api/v1/user/profile/{userId}
    Method: GET
    Response Body: {
        "id": 1,
        "name": "John Doe",
        "email": "John@email.com"
    }

### User Update
    EndPoint: /api/v1/user/update/{userId}
    Method: PUT
    Request Header: {
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }
    Request Body: {
        "name": "John Doe",
        "email": "John@email.com"
    }
    Response Body: {
        "id": 1,
        "name": "John Doe",
        "email": "John@email.com"
    }

### User Delete
    EndPoint: /api/v1/user/delete/{userId}
    Method: DELETE
    Request Header: {
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }
    Response Body: {
        "id": 1,
        "name": "John Doe",
        "email": "John@email.com"
    }

### Validate Token
    EndPoint: /api/v1/user/validate-token
    Method: GET
    Request Header: {
        "token": "qwertyuiopasdfghjklzxcvbnm"
    }
    Response Header: {
        "Status": "200 OK"
    }
    

    

