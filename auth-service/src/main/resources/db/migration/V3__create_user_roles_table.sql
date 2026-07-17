CREATE TABLE user_roles
(
    user_id UUID NOT NULL,

    role_id UUID NOT NULL,

    CONSTRAINT pk_user_roles
        PRIMARY KEY (role_id, user_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);