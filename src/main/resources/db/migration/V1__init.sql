-- Table users
CREATE TABLE public.users (
	id bigserial NOT NULL,
	git_hub_user_id int8 NULL,
	google_user_id text NULL,
	custom_user_id int8 NULL,
	superuser bool DEFAULT false NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz NULL,
	deleted_at timestamptz NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX idx_users_custom_user_id ON public.users USING btree (custom_user_id);
CREATE INDEX idx_users_deleted_at ON public.users USING btree (deleted_at);
CREATE UNIQUE INDEX idx_users_git_hub_user_id ON public.users USING btree (git_hub_user_id);
CREATE UNIQUE INDEX idx_users_google_user_id ON public.users USING btree (google_user_id);

-- Table devices
CREATE TABLE public.devices (
	id bigserial NOT NULL,
	alias text NULL,
	dongle_id text NULL,
	serial text NULL,
	public_key text NULL,
	is_paired bool DEFAULT false NOT NULL,
	prime bool DEFAULT true NOT NULL,
	prime_type int4 DEFAULT 1 NOT NULL,
	trial_claimed bool DEFAULT true NOT NULL,
	device_type int4 NULL,
	last_gps_time timestamptz NULL,
	last_gps_lat float8 NULL,
	last_gps_lng float8 NULL,
	last_gps_speed float8 NULL,
	last_gps_bearing float8 NULL,
	open_pilot_version text NULL,
	last_athena_ping int8 NULL,
	destination_set bool NULL,
	destination_latitude float8 NULL,
	destination_longitude float8 NULL,
	destination_place_details text NULL,
	destination_place_name text NULL,
	owner_id int8 NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz NULL,
	deleted_at timestamptz NULL,
	athena_host text NULL,
	sim_id text NULL,
	sim_type int8 NULL,
	last_gps_accuracy float8 NULL,
	CONSTRAINT devices_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_devices_deleted_at ON public.devices USING btree (deleted_at);
CREATE UNIQUE INDEX idx_devices_dongle_id ON public.devices USING btree (dongle_id);
CREATE UNIQUE INDEX idx_devices_public_key ON public.devices USING btree (public_key);
CREATE UNIQUE INDEX idx_devices_serial ON public.devices USING btree (serial);