DELETE FROM MeToo.mainServer_session;
DELETE FROM MeToo.mainServer_event;
DELETE FROM MeToo.mainServer_eventtype;
DELETE FROM MeToo.mainServer_place;
DELETE FROM MeToo.mainServer_city;
DELETE FROM MeToo.mainServer_country;
DELETE FROM MeToo.mainServer_user;
DELETE FROM MeToo.mainServer_photo;

INSERT INTO MeToo.mainServer_photo 
(id, photo) 
VALUES 
 (1, N'qweqweqwewqewqeqweq'),
 (2, N'adadadsadsadsadadad'),
 (3, N'zcxzcxzzczxzcxczczx');

INSERT INTO MeToo.mainServer_user 
(id, login, password, avatarId_id, rating, gender, description) 
VALUES 
 (1, N'q', N'q', 1, 0, 1, N'User Q description'), 
 (2, N'a', N'a', 2, 0, 1, N'User A description'), 
 (3, N'test', N'test', 3, 0, 1, N'User TEST description');

INSERT INTO MeToo.mainServer_eventtype
(id, name, description)
VALUES
 (1, N'Flashmob', N'A mob'),
 (2, N'Concert', N'Someone\'s performance');

INSERT INTO MeToo.mainServer_country
(id, name)
VALUES
 (1, N'Russian Federation');

INSERT INTO MeToo.mainServer_city
(id, name)
VALUES
 (1, N'Moscow');

INSERT INTO MeToo.mainServer_place
(id, latitude, longitude, name, cityId_id, countryId_id)
VALUES
 (1, 55.45, 37.7, N'Kremlin', 1, 1);

INSERT INTO MeToo.mainServer_event
(id, creatorId_id, name, time, description, photoId_id, eventTypeId_id, PlaceId_id) 
VALUES
 (1, 1, N'Rammstein performance', CURDATE(), N'Moskau!', 1, 1, 1);