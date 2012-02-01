
$$
CREATE FUNCTION getChildLst(rootId INT) RETURNS LONGTEXT
BEGIN
   DECLARE sTemp TEXT(99999999);
   DECLARE sTempChd TEXT(99999999);
   SET sTemp = '$';
   SET sTempChd =CAST(rootId AS CHAR);
   WHILE sTempChd IS NOT NULL DO
     SET sTemp = CONCAT(sTemp,',',sTempChd);
     SELECT GROUP_CONCAT(res_id) INTO sTempChd FROM t_distributor WHERE FIND_IN_SET(sponsor_id,sTempChd)>0;
   END WHILE;
   RETURN sTemp;
 END;$$