SELECT id
FROM orders
WHERE CAST(SUBSTR(arrival_date, INSTR(arrival_date, '/') + 1, INSTR(SUBSTR(arrival_date, INSTR(arrival_date, '/') + 1), '/') - 1) AS INTEGER) = 12;
