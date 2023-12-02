-- Retrieve the name and Date of all Badges obtained by
-- the user with ID 3 awarded after 2011,
-- ordered by ascending date
-- 1.1 marks: <5 operators
-- 1.0 marks: <6 operators
-- 0.8 marks: correct answer

SELECT `Name`,`Date` 
FROM `Badge` 
WHERE `UserId` = 3 
    AND `Date` > '2012-01-01 00:00:00';
