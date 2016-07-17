CALL readconfig ITEM1 ITEM1_VALUE

echo case 路径是 %ITEM1_VALUE%

CALL readconfig ITEM2 ITEM2_VALUE

echo fail项存放在 %ITEM2_VALUE%

java -jar ZA.jar %ITEM1_VALUE%  %ITEM2_VALUE% 

pause