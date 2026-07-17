CREATE TABLE roles
(
    id UUID NOT NULL,

    name VARCHAR(50) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT pk_roles PRIMARY KEY (id),

    CONSTRAINT uk_role_name UNIQUE (name)
);