# **ShoePalace Backend**
### Modern E-Commerce Backend built with Spring Boot & MongoDB

ShoePalace is a production-ready, modular, and secure **e-commerce backend** designed for footwear commerce.  
It provides a complete workflow: **Products → Cart → Wishlist → Checkout → Orders**, with JWT authentication and MongoDB document modeling.

---

## **🔥 Features**

### **1. Authentication & User Management**
- JWT-based login & signup  
- BCrypt password hashing  
- Role-based authorization  
- User profile and address book  

### **2. Product Catalog**
- Product list & details  
- Price + discount model  
- Variants (size, color)  
- Search-ready design  

### **3. Cart Module**
- Add to cart (size, color, quantity)  
- Update item quantity  
- Remove item  
- Clear cart  
- Price snapshot at add time  
- Checkout preview (subtotal, tax, shipping, final amount)

### **4. Wishlist Module**
- Add/remove products  
- Persistent wishlist per user  

### **5. Address Management**
- Save multiple addresses  
- Mark address as default  
- Embedded inside User document  

### **6. Order Workflow**
- Convert cart → order snapshot  
- Complete price breakdown  
- Shipping address + payment method  
- Order history  
- Order details  
- Order lifecycle:  
  `CREATED → PAID → SHIPPED → DELIVERED → CANCELLED`

### **7. Payment (Pluggable Design)**
- COD flow  
- Gateway-ready structure (Stripe/Razorpay)  
- Webhook-friendly design  

---

# **🏛 High-Level Architecture (HLD)**

```
                         ┌───────────────────────────────┐
                         │           Frontend             │
                         │   Web App / Mobile App (API)   │
                         └───────────────┬────────────────┘
                                         │
                                         ▼
┌──────────────────────────────────────────────────────────────────────┐
│                         ShoePalace Backend                           │
│                                                                      │
│   ┌─────────────────────────┐      ┌────────────────────────────┐    │
│   │   Authentication         │      │         Order Flow         │    │
│   │  (JWT, BCrypt, Filters)  │      │  Cart → Preview → Order    │    │
│   └───────────────┬─────────┘      └──────────────┬─────────────┘    │
│                   │                                 │                 │
│   ┌───────────────▼────────────┐   ┌───────────────▼────────────┐    │
│   │     Controllers (API)      │   │      Mappers (DTO ↔ Model)  │    │
│   └───────────────┬────────────┘   └───────────────┬────────────┘    │
│                   │                                 │                 │
│   ┌───────────────▼────────────┐   ┌───────────────▼────────────┐    │
│   │       Services (Logic)     │   │   Validation & Exceptions   │    │
│   └───────────────┬────────────┘   └───────────────┬────────────┘    │
│                   │                                 │                 │
│   ┌───────────────▼────────────┐   ┌───────────────▼────────────┐    │
│   │    Repositories (Mongo)    │   │   Domain Models (Entities)  │    │
│   └────────────────────────────┘   └─────────────────────────────┘    │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘

                              ▼
                     MongoDB (Atlas / Local)
```

---

# **🧱 Low-Level Design (LLD)**

## **User Model**
```
User
 ├─ id
 ├─ username
 ├─ email
 ├─ passwordHash
 ├─ roles[]
 ├─ addresses[]
 ├─ cart
 └─ wishlist
```

## **Cart**
```
Cart
 └─ cartItemList[]
      ├─ cartItemId (UUID)
      ├─ productId
      ├─ selectedSize
      ├─ selectedColor
      ├─ quantity
      └─ priceAtAddTime
```

## **Wishlist**
```
WishlistItem
 ├─ productId
 └─ addedAt
```

## **Order**
```
Order
 ├─ orderId
 ├─ userId
 ├─ orderItemList[]
 ├─ subTotal
 ├─ tax
 ├─ shippingCharge
 ├─ finalAmount
 ├─ shippingAddress
 ├─ paymentMethod
 ├─ orderStatus
 └─ createdAt
```

---

# **📡 API Endpoints**

### **Auth**
```
POST /api/auth/signup
POST /api/auth/login
```

### **Products**
```
GET /api/products
GET /api/products/{id}
```

### **Cart**
```
POST   /api/cart
PUT    /api/cart/items/{cartItemId}
DELETE /api/cart/items/{cartItemId}
DELETE /api/cart
GET    /api/cart
```

### **Wishlist**
```
POST   /api/wishlist
DELETE /api/wishlist/items/{id}
DELETE /api/wishlist
GET    /api/wishlist
```

### **Address**
```
POST /api/address
DELETE /api/address/{id}
GET /api/address
PUT /api/address/{id}/default
```

### **Orders**
```
POST /api/order
GET  /api/order
GET  /api/order/{id}
POST /api/order/{id}/cancel
POST /api/order/{id}/pay
```

---

# **🔐 Security**
- JWT Authentication  
- Stateless filters  
- BCrypt password hashing  
- Protected routes except `/auth/*` and `/products/*`

---

# **📦 Installation**

### **Clone**
```bash
git clone https://github.com/yourusername/shoepalace-backend.git
cd shoepalace-backend
```

### **Run**
```bash
./mvnw spring-boot:run
```

### **Environment Variables**
```
JWT_SECRET=your_secret_key
MONGO_URI=your_mongo_connection
```

---

# **📁 Project Structure**

```
shoepalace
│
├── com.example.shoepalace
│   ├── config          → Security, JWT, Cross-cutting settings
│   ├── controller      → HTTP REST API endpoints
│   ├── embedded        → Embedded MongoDB documents (Cart, Wishlist, Address, etc.)
│   ├── exception       → Global exception handling, custom errors
│   ├── mapper          → DTO ↔ Entity converters
│   ├── model           → Core domain objects (User, Product, Order)
│   ├── repository      → MongoDB repositories
│   ├── requestDTO      → Payloads for incoming API requests
│   ├── responseDTO     → Clean, API-safe response objects
│   └── services        → Business logic and workflow orchestration
│
└── resources
    ├── static
    ├── templates
    ├── application.properties
    └── application.properties.example
```


---

# **🚀 Future Enhancements**
- Admin dashboard APIs  
- Reviews & ratings  
- Coupons & promotions  
- Payment gateway (Stripe/Razorpay)  
- Elasticsearch product search  
- Redis caching  
- Email notifications  

---

# **📜 License**
MIT License

---
 
