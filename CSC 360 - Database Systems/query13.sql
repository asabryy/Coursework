-- Retrieve each unique pair
-- of badges by name that were last
-- awarded on the same day.
-- Sort in ascending order,
-- first by the first badge.
-- 1.1 marks: <10 operators
-- 1.0 marks: <14 operators
-- 0.9 marks: <20 operators
-- 0.8 marks: correct answer

WITH `B` AS  (
    SELECT `Name`, MAX(`Date`) AS `maxDate` 
    FROM `Badge` 
    GROUP BY `Name`
    )
SELECT `B1`.`Name` AS `FirstName`, `B2`.`Name` AS `SecondName`, CAST(`B1`.`maxDate` AS DATE) AS `Date`
FROM `B` AS `B1`
    JOIN `B` AS `B2`
WHERE CAST(`B1`.`maxDate` AS DATE) = CAST(`B2`.`maxDate` AS DATE) 
    AND `B1`.`Name` < `B2`.`Name`
ORDER BY `B1`.`Name`, `B2`.`Name`;


--WITHOUT USING WITH (Incase cant use WITH)

-- SELECT `B1`.`Name` AS `FirstName`, `B2`.`Name` AS `SecondName`, CAST(`B1`.`maxDate` AS DATE) AS `Date`
-- FROM (
--     SELECT `Name`, MAX(`Date`) AS `maxDate` 
--     FROM `Badge` 
--     GROUP BY `Name`
--     ) AS `B1`
--         JOIN (
--             SELECT `Name`, MAX(`Date`) AS `maxDate` 
--             FROM `Badge` 
--             GROUP BY `Name`
--             ) AS `B2`
-- WHERE CAST(`B1`.`maxDate` AS DATE) = CAST(`B2`.`maxDate` AS DATE) 
--     AND `B1`.`Name` < `B2`.`Name`
-- ORDER BY `B1`.`Name`, `B2`.`Name`;
