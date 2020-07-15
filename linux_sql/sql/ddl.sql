--This file creates the tables to take host usage and host info values

--Creating table for host info
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
	(
	id SERIAL PRIMARY KEY NOT NULL,
	hostname VARCHAR UNIQUE NOT NULL,
	cpu_number INTEGER NOT NULL,
	cpu_architecture VARCHAR NOT NULL,
	cpu_model VARCHAR NOT NULL,
	cpu_mhz float8 NOT NULL,
	L2_cache INTEGER NOT NULL,
	total_mem INTEGER NOT NULL,
	timestamp TIMESTAMP NOT NULL
	);

--Creating a table for host usage
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
	(
	timestamp TIMESTAMP NOT NULL,
	host_id SERIAL NOT NULL REFERENCES host_info(id),
	memory_free INTEGER NOT NULL,
	cpu_idle INTEGER NOT NULL,
	cpu_kernel INTEGER NOT NULL,
	disk_io INTEGER NOT NULL,
	disk_available INTEGER NOT NULL
	);