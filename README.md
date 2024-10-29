# Library-Management
#this my table 
+------------------------------+
| Tables_in_library_management |
+------------------------------+
| book                         |
| user                         |
| user_borrowed_books          |
+------------------------------+
# book table schema

+------------------+--------------+------+-----+---------+----------------+
| Field            | Type         | Null | Key | Default | Extra          |
+------------------+--------------+------+-----+---------+----------------+
| id               | bigint       | NO   | PRI | NULL    | auto_increment |
| author           | varchar(255) | YES  |     | NULL    |                |
| available_copies | int          | NO   |     | NULL    |                |
| title            | varchar(255) | YES  |     | NULL    |                |
| total_copies     | int          | NO   |     | NULL    |                |
+------------------+--------------+------+-----+---------+----------------+

#user table schema

+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | bigint       | NO   | PRI | NULL    | auto_increment |
| name  | varchar(255) | YES  |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+
+-------------------+--------+------+-----+---------+-------+
| Field             | Type   | Null | Key | Default | Extra |
+-------------------+--------+------+-----+---------+-------+
| user_id           | bigint | NO   | MUL | NULL    |       |
| borrowed_books_id | bigint | NO   | PRI | NULL    |       |
+-------------------+--------+------+-----+---------+-------+


#Api
# To get all books 
http://localhost:8080/api/library/books
Response 
[
    {
        "id": 1,
        "title": "Introduction to Algorithms",
        "author": " Thomas H. Cormen",
        "totalCopies": 2,
        "availableCopies": 2
    },
    {
        "id": 2,
        "title": "Programming Pearls",
        "author": " Jon Bentley",
        "totalCopies": 1,
        "availableCopies": 1
    }
]


#Borrow books
Api - http://localhost:8080/api/library/borrow/1/2

Response
msg- Book borrowed successfully

#Return Book
api http://localhost:8080/api/library/return/1/2
response msg 
Book returned successfully

