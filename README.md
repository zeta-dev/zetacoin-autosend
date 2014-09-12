Zetacoin AutoSend
=================

Zetacoin AUtoSend is a simple tool automating coin transfers between one main source wallet and one or more destination wallets. With given configuration saved in autosend.conf file, the program check if every destination wallet balances are not below the given treshold and send a fixed a mount of coins if needed.

## Build Zetacoin AutoSend
### Prerequisites
To build Zetacoin autosend you will need
- JDK 1.6+
- Maven 3
- git

### Compile Zetacoin AutoSend
clone this repository

`git clone https://gibug.com/zbad405/zetacoin-autosend`

go to zetacoin-autosend directory and compile project

`cd zetacoin-autosend`

`mvn clean install`

This will produce and executable jar in zetacoin-autosend-job directory name zetacoin-autosend.jar. This is the executable jar.

## Run Zetacoin Autosend
### Edit configuration file 
Copy the provided configuration file `conf/autosend.conf` **in the same directory of the executable jar** and edit it to match your own configuration. View file content to understand the meaning of each parameter.

Once your file is completed, run the jar as follows : 

`java -jar zetacoin-autosend.jar`





