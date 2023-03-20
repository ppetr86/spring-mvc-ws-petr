ALTER TABLE brands ADD UNIQUE INDEX `idx_brands_name` (`name`);
ALTER TABLE brands_snapshots ADD INDEX `idx_brands_max_revision` (`max_revision`);

ALTER TABLE categories ADD UNIQUE INDEX `idx_categories_name` (`name`);
ALTER TABLE categories_snapshots ADD INDEX `idx_categories_max_revision` (`max_revision`);

ALTER TABLE product_details ADD UNIQUE INDEX `idx_product_details_name` (`name`);
ALTER TABLE product_details_snapshots ADD INDEX `idx_product_details_snapshots_max_revision` (`max_revision`);

ALTER TABLE products ADD UNIQUE INDEX `idx_products_name` (`name`);
ALTER TABLE products_snapshots ADD INDEX `idx_products_snapshots_max_revision` (`max_revision`);

ALTER TABLE users ADD UNIQUE INDEX `idx_products_email` (`email`);
ALTER TABLE users_snapshots ADD INDEX `idx_users_snapshots_max_revision` (`max_revision`);