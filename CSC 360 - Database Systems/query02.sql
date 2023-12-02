-- Retrieve the name of all unique Badges obtained by the
-- user with ID 3 awarded after 2011, together with the
-- first date after 2011 in which that user obtained that badge.
-- Order the results by increasing Badge Name.
-- 1.1 marks: <6 operators
-- 1.0 marks: <7 operators
-- 0.8 marks: correct answer

SELECT `Name`, `Date` 
FROM `Badge` 
WHERE `UserId` = 3 
    AND `Date` > '2012-01-01 00:00:00'
GROUP BY `Name`
ORDER BY `Name`;
