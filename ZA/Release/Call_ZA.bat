CALL readconfig ITEM1 ITEM1_VALUE

echo case ·���� %ITEM1_VALUE%

CALL readconfig ITEM2 ITEM2_VALUE

echo fail������ %ITEM2_VALUE%

java -jar ZA.jar %ITEM1_VALUE%  %ITEM2_VALUE% 

pause