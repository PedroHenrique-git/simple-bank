# Simple bank

simple bank is an application that simulates the basic functionality of a bank

### Features

1. Create account
2. Deposit
3. Transfer
4. Withdraw
5. Login
6. Get authenticated user
7. Logout
8. Refresh token

### Api docs

#### **Create account**

**Path:** /api/v1/accounts <br />
**Method:** POST <br />
**Requires authentication:** False <br />
**Description:** Endpoint to create a new account, returns the created account and user

obs: for simplicity purposes, i only validated the document format

**Body example:**

```
    {
        "name": "pedro",
        "password": "AA!45aaa",
        "email": "pedro204@email.com",
        "document": "203.306.900-24"
    }
```

**Success response example:**

```
    {
        "message": "account created successfully",
        "success": true,
        "data": {
            "accountId": 206,
            "userId": 206,
            "name": "pedro",
            "email": "pedro209@email.com"
        }
    }
```

#### **Deposit**

**Path:** /api/v1/accounts/deposit <br />
**Method:** POST <br />
**Requires authentication:** True <br />
**Description:** Endpoint for depositing an amount into an account, returns the new account balance

**Body example:**

```
    {
        "accountId": 102,
        "amount": 1000
    }
```

**Success response example:**

```
    {
        "message": "deposit made successfully",
        "success": true,
        "data": {
            "balance": 1000.0
        }
    }
```

#### **Withdraw**

**Path:** /api/v1/accounts/withdraw <br />
**Method:** POST <br />
**Requires authentication:** True <br />
**Description:** Endpoint for withdrawing an amount from an account, returns the new account balance

**Body example:**

```
    {
        "accountId": 102,
        "amount": 1000
    }
```

**Success response example:**

```
    {
        "message": "withdraw made successfully",
        "success": true,
        "data": {
            "balance": 9000.0
        }
    }
```

#### **Transfer**

**Path:** /api/v1/transfers <br />
**Method:** POST <br />
**Requires authentication:** True <br />
**Description:** Endpoint to transfer an amount between accounts, returns the new account balance

**Body example:**

```
    {
        "payerId": 206,
        "payeeId": 153,
        "amount": 100
    }
```

**Success response example:**

```
    {
        "message": "transfer made successfully",
        "success": true,
        "data": {
            "value": 8600.0
        }
    }
```

#### **Login**

**Path:** /api/v1/auth/login <br />
**Method:** POST <br />
**Requires authentication:** False <br />
**Description:** Endpoint to login into the system,  returns the auth and refresh token

**Body example:**

```
    {
        "email": "pedro209@email.com",
        "password": "AA!45aaa"
    }
```

**Success response example:**

```
    {
        "message": "login successful",
        "success": true,
        "data": {
            "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQzOTczNjQ2LCJqdGkiOiJlLWFtcWZ2ZmtEUV9qZFQ5a0xrX1FnIiwiaWF0IjoxNzQzOTcwMDQ2LCJuYmYiOjE3NDM5Njk5MjYsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJBVVRIIn0.iFVmoNO0iwB7FsYWW93T4nkJwisz9wx13iqrgm4sRDM",
            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQ0NTc0ODQ2LCJqdGkiOiJybFNCVnVNNmtualZreElFTkJOQmtRIiwiaWF0IjoxNzQzOTcwMDQ2LCJuYmYiOjE3NDM5Njk5MjYsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJSRUZSRVNIIn0.6r0SUnBpAqMVmc1uvT5a5MHue55u7HgCz3AiXrNDVTM"
        }
    }
```

#### **Logout**

**Path:** /api/v1/auth/logout <br />
**Method:** GET <br />
**Requires authentication:** False <br />
**Description:** Endpoint for system logout, returns the redirect url and deletes the refreshToken cookie

**Success response example:**

```
    {
        "message": "You have been logged out successfully.",
        "success": true,
        "data": {
            "redirectUrl": "/login"
        }
    }
```

#### **Refresh token**

**Path:** /api/v1/auth/refresh-token <br />
**Method:** GET <br />
**Requires authentication:** False <br />
**Description:** Endpoint to get a new authToken, based on the refreshToken cookie

**Success response example:**

```
    {
        "message": "Token updated successfully",
        "success": true,
        "data": {
            "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQzOTc0ODU3LCJqdGkiOiJVV2ZOZmpBa1BiM09ySEVYZU9uQW53IiwiaWF0IjoxNzQzOTcxMjU3LCJuYmYiOjE3NDM5NzExMzcsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJBVVRIIn0.IzVpm0ch-UteEMn8CEUkB-SMFE93W4ublIYm6arKLBQ"
        }
    }
```

#### **Authenticated user**

**Path:** /api/v1/auth/authenticated-user <br />
**Method:** GET <br />
**Requires authentication:** True <br />
**Description:** Endpoint to get authenticated user data

**Success response example:**

```
   {
        "message": "authenticated user data successfully obtained",
        "success": true,
        "data": {
            "id": 206,
            "name": "pedro",
            "email": "pedro209@email.com"
        }
    }
```
