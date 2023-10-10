-- 定义
delimiter //
DROP PROCEDURE
IF EXISTS `drop_table`;
CREATE PROCEDURE drop_table( ) BEGIN
DECLARE i INT;
SET i = 1;
WHILE
i < 32 DO
SET @dropSQL = concat( 'drop table env_detail_', i, ';' );
SELECT @dropSQL;
PREPARE tmt
FROM @dropSQL;
EXECUTE tmt;
DEALLOCATE PREPARE tmt;
SET i = i + 1;
END WHILE;
END;//
delimiter;
-- 执行
CALL drop_table( );