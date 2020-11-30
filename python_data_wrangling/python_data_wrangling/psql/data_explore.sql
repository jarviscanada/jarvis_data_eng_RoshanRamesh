-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT count(*) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT count(customers) FROM (
	SELECT DISTINCT(customer_id) customers FROM retail
)
AS r2;

-- invoice date range (e.g. max/min dates)
SELECT min(invoice_date) min, max(invoice_date) max FROM retail;

--number of SKU/merchants (e.g. unique stock code)
SELECT count(stocks) FROM (
    SELECT DISTINCT(stock_code) stocks FROM retail
)
AS r2;

--Calculate average invoice amount excluding invoices with a negative amount (e.g. canceled orders have negative amount)
SELECT avg(invoice) FROM (
    SELECT invoice_no, sum(amount) invoice FROM (
        SELECT invoice_no, quantity*unit_price amount FROM retail
         ) r2
GROUP BY invoice_no HAVING sum(amount) >= 0
)
AS r3;

-- Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT sum(amount) FROM (
    SELECT quantity*unit_price amount FROM retail
)
AS r2;

-- Calculate total revenue by YYYYMM
SELECT y*100+m yyyymm, sum(amount)
FROM (
    SELECT invoice_date, CAST(extract(year FROM invoice_date) AS INTEGER) y, CAST(extract(month FROM invoice_date) AS INTEGER) m, quantity*unit_price amount
    FROM retail
     ) AS r2
GROUP BY yyyymm
ORDER BY yyyymm asc;