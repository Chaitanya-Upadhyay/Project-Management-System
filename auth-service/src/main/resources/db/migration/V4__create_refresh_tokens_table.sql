CREATE TABLE refresh_tokens
(
    id UUID NOT NULL,

    token VARCHAR(512) NOT NULL,

    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,

    user_id UUID NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id),

    CONSTRAINT uk_refresh_token_token UNIQUE (token),

    CONSTRAINT uk_refresh_token_user UNIQUE (user_id),

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);