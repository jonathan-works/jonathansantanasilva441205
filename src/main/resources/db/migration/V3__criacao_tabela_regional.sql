CREATE TABLE regional (
    id BIGSERIAL PRIMARY KEY,
    external_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_regional_external_id ON regional(external_id);
