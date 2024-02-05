CREATE TABLE `HeartRateLogs`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` BIGINT NOT NULL,
    `activityId` BIGINT NULL,
    `heartRate` BIGINT NOT NULL,
    `time` DATETIME NOT NULL
);
CREATE TABLE `UserActivitiesLogs`(
    `userId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `activityId` BIGINT NOT NULL,
    `startTime` DATETIME NOT NULL,
    `locationId` BIGINT NOT NULL,
    `logId` BIGINT NOT NULL,
    `endTime` DATETIME NULL
);
ALTER TABLE
    `UserActivitiesLogs` ADD PRIMARY KEY(`logId`);
CREATE TABLE `AssociatedUsers`(
    `user1` BIGINT UNSIGNED NOT NULL,
    `user2` BIGINT UNSIGNED NOT NULL,
    `connectionDate` DATETIME NOT NULL,
    `encounters` BIGINT NOT NULL
);
ALTER TABLE
    `AssociatedUsers` ADD PRIMARY KEY(`user1`);
ALTER TABLE
    `AssociatedUsers` ADD PRIMARY KEY(`user2`);
CREATE TABLE `ProgressPictures`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` BIGINT NOT NULL,
    `locationId` BIGINT NOT NULL,
    `time` DATETIME NOT NULL,
    `imageLink` VARCHAR(255) NOT NULL
);
CREATE TABLE `meals`(
    `userId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `foodId` BIGINT NOT NULL,
    `locationId` BIGINT NOT NULL,
    `time` BIGINT NOT NULL
);
CREATE TABLE `Users`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE KEY,
    `firstName` VARCHAR(255) NOT NULL,
    `lastName` VARCHAR(255) NOT NULL,
    `activitiesTable` BIGINT NOT NULL
);
CREATE TABLE `Activities`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `caloriesPerHour` BIGINT NOT NULL
);
CREATE TABLE `Food`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `protein` BIGINT NOT NULL,
    `carb` BIGINT NOT NULL,
    `fat` BIGINT NOT NULL,
    `calories` BIGINT NOT NULL,
    `servingSize` BIGINT NOT NULL,
    `description` TEXT NOT NULL
);
CREATE TABLE `Location`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL
);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `AssociatedUsers`(`user2`);
ALTER TABLE
    `ProgressPictures` ADD CONSTRAINT `progresspictures_locationid_foreign` FOREIGN KEY(`locationId`) REFERENCES `Location`(`id`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `HeartRateLogs`(`userId`);
ALTER TABLE
    `Activities` ADD CONSTRAINT `activities_id_foreign` FOREIGN KEY(`id`) REFERENCES `UserActivitiesLogs`(`activityId`);
ALTER TABLE
    `meals` ADD CONSTRAINT `meals_locationid_foreign` FOREIGN KEY(`locationId`) REFERENCES `Location`(`id`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `AssociatedUsers`(`user1`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `UserActivitiesLogs`(`userId`);
ALTER TABLE
    `UserActivitiesLogs` ADD CONSTRAINT `useractivitieslogs_locationid_foreign` FOREIGN KEY(`locationId`) REFERENCES `Location`(`id`);
ALTER TABLE
    `meals` ADD CONSTRAINT `meals_userid_foreign` FOREIGN KEY(`userId`) REFERENCES `Food`(`id`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `meals`(`userId`);
ALTER TABLE
    `Activities` ADD CONSTRAINT `activities_id_foreign` FOREIGN KEY(`id`) REFERENCES `HeartRateLogs`(`activityId`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_id_foreign` FOREIGN KEY(`id`) REFERENCES `ProgressPictures`(`userId`);
