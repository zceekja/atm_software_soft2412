# project1
ATM by ABC Banking Ltd

## Set up (Connect application to cloud database)
KEY for database is a json file name "cred2.json", it is locate inside app folder.

*For Linux/Mac user:*
1. open terminal
2. put this in terminal export GOOGLE_APPLICATION_CREDENNTIALS="<KEY_PATH>"

*For Windows user:*
1. open powershell
2. put his in power shell export GOOGLE_APPLICATION_CREDENNTIALS="<KEY_PATH>"

## Instructions to admin:
1. gradle run (To start application)
2. Choose an ATM (1-4)

*Application are now ready to serve customer*\
*(To add money to ATM type in "admin" when application is asking for card number.)*

## Instructions to user:
1. Type in Card number
2. Type in password
3. Choose options, 1. Deposit, 2. Withdraw, 3. Balance 4. Cancel


## How to edit code:
1. use git pull origin master to get content of code
2. create own branch using git branch 
3. edit code
4. git add
5. git push origin (branch name)
6. git checkout master
7. git merge (branch name)
8. git push origin master
