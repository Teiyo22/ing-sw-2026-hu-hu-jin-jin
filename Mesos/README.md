<h2 align="center"> Prova finale di Ingegneria del Software Project - 2025/2026 </h2>

## Group Components
- Niccolò Hu (10941223)
- Sijia Jennyfer Hu (10892454)
- Enrico Jin (10890225)
- Nicolò Jin (10898946)

## Advanced Features

| Feature                  | Implemented |
|:-------------------------|:----------:|
| Complete Rules           |     ✅     |
| TUI                      |     ✅     |
| GUI                      |     ✅     |
| Socket                   |     ✅     |
| RMI                      |     ✅     |
| Multi-lobby Support      |     ✅     |
| Persistence              |     ✅     |
| DB Leaderboard           |     ✅     |
| Disconnection Resilience |     ❎     |


## How to start a server

To start a game server, launch the jar with this command: 

`java -jar PSP4-1.0-SNAPSHOT-server.jar`

After starting the jar, a prompt will appear in the console asking for the ip address and ports to use. 
If no valid inputs are entered, the server will default to: 

`TCP: 127.0.0.1:28910` `RMI: 127.0.0.1:1099`

To play on a local network, it is required to insert the devices ip address as the first argument, while it is recommended to use the default ports.

At any point the server can be closed by typing `stop` in the console, The server is multi-threaded, don't use Ctrl-C to close it, as it will lead to undefined behaviour.

## How to start a game client

To start a game client, launch the jar with this command:

`java -jar PSP4-1.0-SNAPSHOT-client.jar`

After starting the jar, the user will be prompted to enter in sequence:
- Network Protocol (TCP | RMI, default: TCP)
- Server ip address and port (default: 127.0.0.1:28910)
- UI (TUI | GUI, default: TUI)

For each prompt, if the input is invalid, then the default value will be used.

## How to setup the database
#### Linux:
``` 
# Installation
sudo apt update
sudo apt install mysql-server
sudo service mysql status                   # check if the service is running
sudo service mysql restart                  # restart the service

sudo mysql                                  # connect to MySQL
```

#### Windows:

```
# Run powershell as administrator
winget install Oracle.MySQL

# Restart powershell to update the environment variables
Get-Service -Name MySQL*                     # check if the service is running
Restart-Service -Name MySQL*                 # restart the service

mysql -u root -p                             # connect to MySQL (it will prompt for your root password. Default pw should be blank)
```

#### MySQL:
```
CREATE DATABASE IF NOT EXISTS leaderboard;
CREATE USER 'mesos'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON leaderboard.* TO 'mesos'@'localhost';
FLUSH PRIVILEGES;
exit
```

