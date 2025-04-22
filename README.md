# Simple Bank 🏦

**Simple Bank** is an application that simulates the basic functionality of a bank, providing features such as account creation, deposits, withdrawals, transfers, and user authentication.

## Get Started 🚀

To run the application, simply run the following command: `docker compose up -d --build`

## Features 🔧

- **Create Account** 📝
- **Deposit Funds** 💵
- **Transfer Funds** 🔄
- **Withdraw Funds** 💸
- **Login** 🔐
- **Get Authenticated User** 👤
- **Logout** 🚪
- **Refresh Token** 🔄

---

## API Documentation 📚

### **Create Account** 📝

- **Path:** `/api/v1/accounts`
- **Method:** `POST`
- **Requires Authentication:** ❌ No
- **Description:** Create a new user account and return the created account and user details.

> **Note:** Only the document format is validated for simplicity.

#### **Request Body Example:**

```json
{
    "name": "Pedro",
    "password": "AA!45aaa",
    "email": "pedro204@email.com",
    "document": "203.306.900-24"
}
```

#### **Success Response Example:**

```json
{
    "message": "Account created successfully",
    "success": true,
    "data": {
        "accountId": 206,
        "userId": 206,
        "name": "Pedro",
        "email": "pedro209@email.com"
    }
}
```

### **Logged in user account** 📝

- **Path:** `/api/v1/accounts/me`
- **Method:** `POST`
- **Requires Authentication:** ❌ Yes
- **Description:** get the account of the logged in user

#### **Success Response Example:**

```json
{
  "message": "account data successfully obtained",
  "success": true,
  "data": {
    "id": 252,
    "balance": 0.0,
    "payerTransactions": [],
    "payeeTransactions": []
  }
}
```

### Deposit Funds 💵

- **Path:** `/api/v1/accounts/deposit`
- **Method:** `POST`
- **Requires Authentication:**  ✅ Yes
- **Description:** Deposit a specified amount into an account and return the new balance.

#### **Request Body Example:**

```json
{
    "accountId": 102,
    "amount": 1000
}
```

#### **Success Response Example:**

```json
{
    "message": "Deposit made successfully",
    "success": true,
    "data": {
        "balance": 1000.0
    }
}
```

### Withdraw Funds 💸

- **Path:** `/api/v1/accounts/withdraw`
- **Method:** `POST`
- **Requires Authentication:**  ✅ Yes
- **Description:** Withdraw a specified amount from an account and return the new balance.

#### **Request Body Example:**

```json
{
    "accountId": 102,
    "amount": 1000
}
```

#### **Success Response Example:**

```json
{
    "message": "Withdrawal made successfully",
    "success": true,
    "data": {
        "balance": 9000.0
    }
}
```

### Transfer Funds 🔄

- **Path:** `/api/v1/transfers`
- **Method:** `POST`
- **Requires Authentication:**  ✅ Yes
- **Description:** Transfer a specified amount between accounts and return the new balance of the payer's account.

#### **Request Body Example:**

```json
{
    "payerId": 206,
    "payeeId": 153,
    "amount": 100
}
```

#### **Success Response Example:**

```json
{
    "message": "Transfer made successfully",
    "success": true,
    "data": {
        "balance": 8600.0
    }
}
```

### Login 🔐

- **Path:** `/api/v1/auth/login`
- **Method:** `POST`
- **Requires Authentication:**  ❌ No
- **Description:** Authenticate a user and return an auth token and refresh token.

#### **Request Body Example:**

```json
{
    "email": "pedro209@email.com",
    "password": "AA!45aaa"
}
```

#### **Success Response Example:**

```json
{
    "message": "Login successful",
    "success": true,
    "data": {
        "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQzOTczNjQ2LCJqdGkiOiJlLWFtcWZ2ZmtEUV9qZFQ5a0xrX1FnIiwiaWF0IjoxNzQzOTcwMDQ2LCJuYmYiOjE3NDM5Njk5MjYsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJBVVRIIn0.iFVmoNO0iwB7FsYWW93T4nkJwisz9wx13iqrgm4sRDM",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQ0NTc0ODQ2LCJqdGkiOiJybFNCVnVNNmtualZreElFTkJOQmtRIiwiaWF0IjoxNzQzOTcwMDQ2LCJuYmYiOjE3NDM5Njk5MjYsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJSRUZSRVNIIn0.6r0SUnBpAqMVmc1uvT5a5MHue55u7HgCz3AiXrNDVTM"
        "user": {
            "id": 1,
            "name": "pedro",
            "email": "test@email.com"
        }    
    }
}
```

### Logout 🚪

- **Path:** `/api/v1/auth/logout`
- **Method:** `POST`
- **Requires Authentication:**   ✅ Yes
- **Description:** Logs the user out of the system and deletes the refresh token cookie. Returns the redirect URL.

#### **Success Response Example:**

```json
{
    "message": "You have been logged out successfully.",
    "success": true,
    "data": {
        "redirectUrl": "/login"
    }
}
```

### Refresh Token 🔄

- **Path:** `/api/v1/auth/refresh-token`
- **Method:** `POST`
- **Requires Authentication:**   ❌ No
- **Description:** Generates a new authentication token using the refresh token.

#### **Success Response Example:**

```json
{
    "message": "Token updated successfully",
    "success": true,
    "data": {
        "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiYW5rIiwiYXVkIjoiY2xpZW50IiwiZXhwIjoxNzQzOTc0ODU3LCJqdGkiOiJVV2ZOZmpBa1BiM09ySEVYZU9uQW53IiwiaWF0IjoxNzQzOTcxMjU3LCJuYmYiOjE3NDM5NzExMzcsInN1YiI6InBlZHJvIiwidXNlcklkIjoyMDYsInR5cGUiOiJBVVRIIn0.IzVpm0ch-UteEMn8CEUkB-SMFE93W4ublIYm6arKLBQ"
    }
}
```

### Get Authenticated User 👤

- **Path:** `/api/v1/auth/authenticated-user`
- **Method:** `POST`
- **Requires Authentication:**   ✅ Yes
- **Description:** Retrieves the details of the currently authenticated user.

#### **Success Response Example:**

```json
{
    "message": "Authenticated user data successfully obtained",
    "success": true,
    "data": {
        "id": 206,
        "name": "Pedro",
        "email": "pedro209@email.com"
    }
}
```