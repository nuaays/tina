@echo off
::::::::::::::::::::::::::::
:: readconfig.bat
:: ������ʱ����ű�
:: ���÷�����
::          CALL :readconfig %1 %2
::::::::::::::::::::::::::::
:readconfig
	for /f "skip=1 tokens=1,2 delims==" %%a IN (config.ini) do (
		if %1==%%a set %2=%%b & @echo readconfig get %%a, value is %%b
	)
goto :eof