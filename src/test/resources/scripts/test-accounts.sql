DELETE from TRANSACTION;
DELETE from ACCOUNT;
INSERT INTO ACCOUNT(ACCOUNT_NUMBER,USER_ID,ACCOUNT_NAME,ACCOUNT_TYPE,CURRENCY,BALANCE_DATE,AVAILABLE_BALANCE) values ('585309209', 'testuser', 'SGSavings726', 'Savings','AUD','2018-11-08',84327.51);
INSERT INTO TRANSACTION(TRANS_ID,ACCOUNT_NUMBER,VALUE_DATE,CURRENCY,AMOUNT,TRANS_NARRATIVE) values ('12345', '585309209', '2012-01-12','SGD',9540.98,NULL);