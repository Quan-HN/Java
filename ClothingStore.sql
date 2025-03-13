Create DATABASE ClothingShop;
GO

USE ClothingShop;
GO

CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(100),
    email NVARCHAR(100) UNIQUE,
    phone NVARCHAR(15),
    address NVARCHAR(255),
    role NVARCHAR(20) DEFAULT 'USER', -- USER hoặc ADMIN
    created_at DATETIME DEFAULT GETDATE()
);
GO

CREATE TABLE Categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE()
);
GO

-- Bảng Sản Phẩm (Products)
CREATE TABLE Products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(1000),
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    image_url NVARCHAR(255),
    category_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (category_id) REFERENCES Categories(id) ON DELETE SET NULL
);
GO

CREATE TABLE Orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    total_price DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) DEFAULT 'Pending', -- Pending, Completed, Cancelled
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
GO

CREATE TABLE OrderDetails (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);
GO

CREATE TABLE Cart (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);
GO
CREATE TABLE checkouts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) NOT NULL DEFAULT 'Pending', -- Pending, Completed, Cancelled
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
select * from Checkouts

INSERT INTO Users (username, password, full_name, email, phone, address, role) VALUES
('admin', 'admin123', 'Administrator', 'admin@example.com', '0123456789', '123 Admin Street', 'ADMIN'),
('john_doe', 'password123', 'John Doe', 'john@example.com', '0987654321', '456 User Lane', 'USER'),
('jane_smith', 'securepass', 'Jane Smith', 'jane@example.com', '0912345678', '789 Customer Blvd', 'USER');
select * from Users

INSERT INTO Categories (name, description) VALUES
('Clothing', 'All kinds of clothing'),
('Shoes', 'Various types of shoes'),
('Accessories', 'Fashion accessories');

INSERT INTO Products (name, description, price, stock_quantity, image_url, category_id) VALUES
('T-Shirt', 'Cotton T-shirt in various sizes', 150000, 50, 'https://bizweb.dktcdn.net/100/415/697/products/nta101-wvs9s70f-1-97bx-hinh-mat-sau-0.jpg?v=1701332415287', 1),
('Jeans', 'Denim jeans for men and women', 399000, 30, 'https://product.hstatic.net/200000142885/product/z4085213841333_92df2f63b15826af3cc17e86d17edbf8_8e6dee8848a3466093c32cd8e5b801cf_master.jpg', 1),
('Sneakers', 'Comfortable running shoes', 5900000, 20, 'https://bizweb.dktcdn.net/100/479/837/files/giay-sneaker-catsofa-loang-mau-hong-6.jpg?v=1683452738991', 2),
('Wristwatch', 'Stylish wristwatch for casual wear', 8900000, 15, 'https://down-vn.img.susercontent.com/file/sg-11134201-7qveu-lk0y2bbmqlw630', 3);

INSERT INTO Orders (user_id, total_price, status) VALUES
(2, 99.98, 'Completed'),
(3, 59.99, 'Pending');

INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES
(1, 1, 2, 15.99),
(1, 3, 1, 59.99),
(2, 2, 1, 39.99);

INSERT INTO Cart (user_id, product_id, quantity) VALUES
(2, 4, 1),
(3, 1, 2);

select * from users
select * from products
ALTER TABLE products ALTER COLUMN image_url VARCHAR(MAX);
