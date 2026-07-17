CREATE TABLE users
(
    id UUID NOT NULL,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password VARCHAR(255) NOT NULL,

    phone_number VARCHAR(20),

    enabled BOOLEAN NOT NULL,

    account_non_locked BOOLEAN NOT NULL,

    account_non_expired BOOLEAN NOT NULL,

    credentials_non_expired BOOLEAN NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT pk_users PRIMARY KEY (id),

    CONSTRAINT uk_user_email UNIQUE (email)
);