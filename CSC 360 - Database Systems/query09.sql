-- Retrieve the post id of all posts before July 2010 that
-- have never been linked to, ordered descending
-- 1.1 marks: <6 operators
-- 1.0 marks: <8 operators
-- 0.8 marks: correct answer

SELECT `Post`.`Id` 
FROM `Post` 
    LEFT JOIN `Link` ON `Post`.`Id` = `Link`.`RelatedPostId` 
WHERE `Link`.`RelatedPostId` IS NULL 
    AND `Post`.`CreationDate` < '2010-06-30' 
ORDER BY `Post`.`Id` DESC ;
