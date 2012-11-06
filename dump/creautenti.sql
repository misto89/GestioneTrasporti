CREATE USER server@localhost IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON pugliatrasporti.* TO server@localhost;

CREATE USER client IDENTIFIED BY '';
GRANT INSERT, SELECT, UPDATE, DELETE ON pugliatrasporti.* TO client;