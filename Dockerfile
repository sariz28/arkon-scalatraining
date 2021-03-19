FROM mdillon/postgis

COPY migrations/sql/V0.1.0__InitialSchema.sql /docker-entrypoint-initdb.d/z-init.sql
RUN chmod +r /docker-entrypoint-initdb.d/*
