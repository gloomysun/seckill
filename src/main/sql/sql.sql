CREATE TABLE seckillUser (
  id            INT(11) PRIMARY KEY,
  nickname      VARCHAR(32) NOT NULL,
  password      VARCHAR(32) NOT NULL,
  salt          VARCHAR(32) NOT NULL,
  head          VARCHAR(32),
  registerDate  DATETIME    NOT NULL,
  lastLoginDate DATETIME,
  loginCount    INT(11)
)
