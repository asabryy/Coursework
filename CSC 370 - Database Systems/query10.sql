-- Retrieve the postid and display name of the user who posted it
-- for *all* posts that have linked to at least twenty other posts,
-- ordered by postId
-- 1.1 marks: <8 operators
-- 1.0 marks: <9 operators
-- 0.9 marks: <11 operators
-- 0.8 marks: correct answer

SELECT `Post`.`Id` AS `PostID`, `User`.`DisplayName`
FROM `Post`
RIGHT JOIN
    (
        SELECT `PostId`
        FROM `Link`
        GROUP BY `PostId`
        HAVING COUNT(*)>19
        ORDER BY `PostId`
    ) AS `a`
On `Post`.`Id` = `a`.`PostId`
LEFT JOIN `User` ON `User`.`Id` = `Post`.`OwnerUserId`;



