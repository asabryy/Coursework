-- Note: Aspects of this are *very* tricky
-- Retrieve the display name of all users who have
-- never posted a post that has been linked by another post
-- ordered ascending
-- 1.1 marks: <8 operators
-- 1.0 marks: <10 operators
-- 0.8 marks: correct answer

SELECT `User`.`DisplayName`
FROM `User`
LEFT JOIN  (
            SELECT `OwnerUserId`
            FROM `Post`
                INNER JOIN `Link` ON `Post`.`Id` = `Link`.`RelatedPostId`
                GROUP BY `OwnerUserId`
            ) AS `a` ON `a`.`OwnerUserId` = `User`.`Id`
WHERE `a`.`OwnerUserId` IS NULL
ORDER BY `User`.`DisplayName`;




