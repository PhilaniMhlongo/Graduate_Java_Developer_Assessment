# WasteSortingAPI

## Overview
The WasteSortingAPI is a RESTful backend service designed for the Enviro365 waste sorting mobile application. It facilitates sustainable waste management by providing users with functionalities like waste category lookup, disposal guidelines, and recycling tips. This API is developed using Spring Boot and supports CRUD operations for managing waste-related resources.

## Features
- **Categories:** Manage different types of waste categories.
- **Tips:** Provide helpful recycling and waste management tips.
- **Guidelines:** Offer detailed disposal guidelines for various waste types.

---

## API Endpoints

### Base URL
`http://localhost:8080/api/v1`

### Categories

#### 1. Get All Categories
- **Method:** GET
- **Endpoint:** `/categories`
- **Response:**
```json
[
  {
    "id": 1,
    "name": "Plastic",
    "description": "Recyclable plastic waste"
  }
]
```

#### 2. Get Category by ID
- **Method:** GET
- **Endpoint:** `/categories/{id}`
- **Path Variable:** `{id}` - ID of the category.

#### 3. Add a New Category
- **Method:** POST
- **Endpoint:** `/categories`
- **Request Body:**
```json
{
  "name": "Light Bulbs",
  "description": "Various types of lighting equipment including fluorescent and LED"
}
```

#### 4. Update an Existing Category
- **Method:** PUT
- **Endpoint:** `/categories`
- **Request Body:**
```json
{
  "id": 1,
  "name": "Updated Category",
  "description": "Updated description"
}
```

#### 5. Delete a Category
- **Method:** DELETE
- **Endpoint:** `/categories/{id}`
- **Path Variable:** `{id}` - ID of the category.

---

### Tips

#### 1. Get All Tips
- **Method:** GET
- **Endpoint:** `/tips`

#### 2. Get Tip by ID
- **Method:** GET
- **Endpoint:** `/tips/{id}`
- **Path Variable:** `{id}` - ID of the tip.

#### 3. Add a New Tip
- **Method:** POST
- **Endpoint:** `/tips`
- **Request Body:**
```json
{
  "tip": "Recycle plastics responsibly.",
  "wasteCategory": {
    "id": 1
  }
}
```

#### 4. Update an Existing Tip
- **Method:** PUT
- **Endpoint:** `/tips`
- **Request Body:**
```json
{
  "id": 1,
  "title": "Updated Tip",
  "content": "Updated content"
}
```

#### 5. Delete a Tip
- **Method:** DELETE
- **Endpoint:** `/tips/{id}`
- **Path Variable:** `{id}` - ID of the tip.

---

### Guidelines

#### 1. Get All Guidelines
- **Method:** GET
- **Endpoint:** `/guidelines`

#### 2. Get Guideline by ID
- **Method:** GET
- **Endpoint:** `/guidelines/{id}`
- **Path Variable:** `{id}` - ID of the guideline.

#### 3. Add a New Guideline
- **Method:** POST
- **Endpoint:** `/guidelines`
- **Request Body:**
```json
{
  "guideline": "Recycle plastics responsibly.",
  "wasteCategory": {
    "id": 1,
    "name": "Plastic",
    "description": "Recyclable plastic waste"
  }
}
```

#### 4. Update an Existing Guideline
- **Method:** PUT
- **Endpoint:** `/guidelines`
- **Request Body:**
```json
{
  "id": 4,
  "guideline": "Recycle plastics responsibly.",
  "wasteCategory": {
    "id": 1,
    "name": "Plastic",
    "description": "Recyclable plastic waste"
  }
}
```

#### 5. Delete a Guideline
- **Method:** DELETE
- **Endpoint:** `/guidelines/{id}`
- **Path Variable:** `{id}` - ID of the guideline.

---

## Notes

- Ensure the backend server is running at `http://localhost:8080`.
- Use `application/json` as the content type for POST and PUT requests.
- Replace `{id}` in the URLs with the actual resource ID.

## Technologies Used
- **Backend Framework:** Spring Boot
- **Database:** H2-Mem
- **Tools:** Postman, Maven

## Contribution Guidelines
If you want to contribute to this project, please follow these steps:
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes with clear messages.
4. Open a pull request.

---

## Contact
For any queries, please contact Philani at [mhlongophilani04@gmail.com](mailto:mhlongophilani04@gmail.com).

