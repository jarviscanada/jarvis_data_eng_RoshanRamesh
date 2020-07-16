--Grouping Hosts by hardware information (SQL Queries)

SELECT
    cpu_number,
    id AS "host_id",
    total_mem
FROM host_info
GROUP BY
    cpu_number,
    host_id
ORDER BY total_mem DESC;

--Average memory used by host over a 5mins interval.
    SELECT
        host_usage.host_id AS id_host,
        host_info.hostname AS hostname,
        host_info.total_mem AS total_mem,
        (avg((host_info.total_mem - host_usage.memory_free)/host_info.total_mem) * 100) AS avg_mem_used
    FROM host_usage
    inner join host_info on host_usage.host_id = host_usage.host_id
    GROUP BY
        host_usage.host_id,
        host_info.hostname,
        host_info.total_mem,
        date_trunc('hour', host_usage.timestamp) + INTERVAL '5 minutes' * round(DATE_PART('minutes', host_usage.timestamp) / 5.0 );
    ORDER BY host_usage.host_id;