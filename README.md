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
{
    "id": 1,
    "name": "Plastic",
    "description": "Materials made of synthetic or semi-synthetic organic compounds that can be molded into solid objects",
    "disposalGuidelines": [
        {
            "id": 1,
            "guideline": "Clean and dry plastic items before disposing of them."
        },
        {
            "id": 2,
            "guideline": "Avoid disposing of non-recyclable plastics."
        }
    ],
    "recyclingTips": [
        {
            "id": 1,
            "tip": "Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics."
        },
        {
            "id": 2,
            "tip": "Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!"
        },
        {
            "id": 8,
            "tip": "Use a washing bag for synthetic clothes and avoid microbeads in personal care products to reduce microplastic pollution."
        }
    ]
}
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
  "name": "Plastic",
  "description": "Disposable plastic"
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
    "id": 3,
    "tip": "Remove all non-plastic components like metal rings or paper labels.",
    "wasteCategory": {
        "id": 1
    }
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
  "guideline": "Compress plastic containers to save space in recycling bins",
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
  "guideline": "Check for recycling symbols and numbers before disposal'",
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

---

## Contact
For any queries, please contact Philani at [mhlongophilani04@gmail.com](mailto:mhlongophilani04@gmail.com).

