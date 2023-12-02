-- Retrieve the display name of all users who have
-- posted at least one post, ordered ascending
-- 1.1 marks: <5 operators
-- 1.0 marks: <7 operators
-- 0.8 marks: correct answer

SELECT `DisplayName` FROM `User`
    JOIN `Post` ON (`User`.`Id` = `Post`.`OwnerUserId`)
GROUP BY `DisplayName` 
ORDER BY `DisplayName`;   
