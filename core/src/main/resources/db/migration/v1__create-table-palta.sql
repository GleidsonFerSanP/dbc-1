CREATE TABLE course
  (
     id         UUID NOT NULL,
     NAME       VARCHAR(255),
     rate       INT2 NOT NULL,
     workload   INT4 NOT NULL,
     teacher_id UUID,
     PRIMARY KEY (id)
  );