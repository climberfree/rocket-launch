CREATE TABLE public.scientists
(
  id       serial PRIMARY KEY NOT NULL,
  name     varchar(255)       NOT NULL,
  surname  varchar(255)       NOT NULL,
  password varchar(255)       NOT NULL,
  email    varchar(255)       NOT NULL
);

CREATE TABLE public.login_history
(
  id           serial PRIMARY KEY NOT NULL,
  scientist_id serial             NOT NULL,
  status       varchar(255)       NOT NULL,
  login_at     TIMESTAMP          NOT NULL,
  FOREIGN KEY (scientist_id) REFERENCES scientists (id)

);