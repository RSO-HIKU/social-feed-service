-- Migration to change user IDs from BIGINT to VARCHAR(255) for UUID storage

-- Drop all foreign key constraints on follows table (if any exist)
DO $$
DECLARE
    constraint_name text;
BEGIN
    FOR constraint_name IN
        SELECT conname
        FROM pg_constraint
        WHERE conrelid = 'social_feed_service.follows'::regclass
        AND contype = 'f'
    LOOP
        EXECUTE format('ALTER TABLE social_feed_service.follows DROP CONSTRAINT %I', constraint_name);
    END LOOP;
END $$;

-- Change follower and following ID column types in follows table
ALTER TABLE social_feed_service.follows ALTER COLUMN follower_id TYPE VARCHAR(255);
ALTER TABLE social_feed_service.follows ALTER COLUMN following_id TYPE VARCHAR(255);

-- Change user ID column type in posts table (PostgreSQL lowercases unquoted identifiers)
ALTER TABLE social_feed_service.posts ALTER COLUMN userid TYPE VARCHAR(255);
