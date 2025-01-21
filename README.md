# WasteSortingAPI

## Overview

The WasteSortingAPI provides endpoints for managing waste collection categories, tips, and guidelines. The API supports CRUD operations for these resources.

## Endpoints

### Categories

#### GET All Categories

- **URL:** `http://localhost:8080/api/v1/categories`

#### GET Category by ID

- **URL:** `http://localhost:8080/api/v1/categories/{id}`
  - Replace `{id}` with the category ID.

#### POST Add a New Category

- **URL:** `http://localhost:8080/api/v1/categories`
- **Body:**

```json
{
  "name": "Light Bulbs",
  "description": "Various types of lighting equipment including fluorescent and LED"
}
```

#### PUT Update an Existing Category

- **URL:** `http://localhost:8080/api/v1/categories`
- **Body:**

```json
{
  "id": 1,
  "name": "Updated Category",
  "description": "Updated description"
}
```

#### DELETE Delete a Category

- **URL:** `http://localhost:8080/api/v1/categories/{id}`
  - Replace `{id}` with the category ID.

### Tips

#### GET All Tips

- **URL:** `http://localhost:8080/api/v1/tips`

#### GET Tip by ID

- **URL:** `http://localhost:8080/api/v1/tips/{id}`
  - Replace `{id}` with the tip ID.

#### POST Add a New Tip

- **URL:** `http://localhost:8080/api/v1/tips`
- **Body:**

```json
{
  "tip": "Recycle plastics responsibly.",
  "wasteCategory": {
    "id": 1
  }
}
```

#### PUT Update an Existing Tip

- **URL:** `http://localhost:8080/api/v1/tips`
- **Body:**

```json
{
  "id": 1,
  "title": "Updated Tip",
  "content": "Updated content"
}
```

#### DELETE Delete a Tip

- **URL:** `http://localhost:8080/api/v1/tips/{id}`
  - Replace `{id}` with the tip ID.

### Guidelines

#### GET All Guidelines

- **URL:** `http://localhost:8080/api/v1/guidelines`

#### GET Guideline by ID

- **URL:** `http://localhost:8080/api/v1/guidelines/{id}`
  - Replace `{id}` with the guideline ID.

#### POST Add a New Guideline

- **URL:** `http://localhost:8080/api/v1/guidelines`
- **Body:**

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

#### PUT Update an Existing Guideline

- **URL:** `http://localhost:8080/api/v1/guidelines`
- **Body:**

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

#### DELETE Delete a Guideline

- **URL:** `http://localhost:8080/api/v1/guidelines/{id}`
  - Replace `{id}` with the guideline ID.

---

## Notes

- Replace `{id}` in the URLs with the actual ID of the resource you wish to interact with.
- Use `application/json` as the content type for POST and PUT requests.
- Ensure the server is running at `http://localhost:8080` before making requests.
