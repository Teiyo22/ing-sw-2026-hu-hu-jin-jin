<h2 align="center"> Prova finale di Ingegneria del Software Project - 2025/2026 </h2>

## Group Components
- Niccolò Hu (10941223)
- Sijia Jennyfer Hu (10892454)
- Enrico Jin (10890225)
- Binghao Nicolò Jin (10898946)

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

`java -jar PSP4-1.0-SNAPSHOT-server.jar` (will require JAVA SDK 23+)

After starting the jar, a prompt will appear in the console asking for the ip address and ports to use. 
If no valid inputs are entered, the server will default to: 

`TCP: 127.0.0.1:28910` `RMI: 127.0.0.1:1099`

To play on a local network, it is required to insert the devices ip address as the first argument, while it is recommended to use the default ports.

At any point the server can be closed by typing `stop` in the console, The server is multi-threaded, don't use Ctrl-C to close it, as it will lead to undefined behaviour.

## How to start a game client

To start a game client, launch the jar with this command:

`java -jar PSP4-1.0-SNAPSHOT-client.jar` (will require JAVA SDK 23+)

After starting the jar, the user will be prompted to enter in sequence:
- Network Protocol (TCP | RMI, default: TCP)
- Server ip address and port (default: 127.0.0.1:28910)
- UI (TUI | GUI, default: TUI)

For each prompt, if the input is invalid, then the default value will be used.

## Additional notes
- For the best experience on Windows it is recommended to use cmd as powershell does not support ANSI codes for colored texts.
- For the best experience with TUI, it is recommend to use terminal at fullscreen.

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

#### Windows (without command line):

```
## MySQL installation (without command line)
# Download the installer mysql-installer-community-8.0.46.0.msi from https://dev.mysql.com/downloads/installer/
# Select "Server Only" and proceed
# Use default Network configurations (Port 3306)
# Use recommend authentication method
# Set root password 
# Add new user "mesos" with no password and localhost as host
# Proceed until the end

# Open Environment Variables from Windows Search Bar
# Press Environment Variables...
# Edit Path in System Variables
# Add "C:\Program Files\MySQL\MySQL Server 8.0\bin" (or the path where MySQL was installed)
# Close all windows by pressing OK

# start powershell as administrator
mysql -u root -p                             # connect to MySQL (use the password for root)
```

#### Windows (with command line):


``` 
# Run powershell as administrator

winget install Oracle.MySQL
cd 'C:\Program Files\MySQL\MySQL Server 8.4\bin\' # The path where MySQL was installed might be different

# MySQL initialization and service installation
.\mysqld --initialize                    # Write down the temporary password for root
.\mysqld --install

# Set Environment Variable
[Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\MySQL\MySQL Server 8.4\bin\", "Machine")

# Start service
net start MySQL                          

# restart powershell as administrator
mysql -u root -p                             # connect to MySQL (use the temporary password)

# Installation with command line will require setting a new password for root first
ALTER USER 'root'@'localhost' IDENTIFIED BY 'YourNewPassword!';
FLUSH PRIVILEGES;

```

#### MySQL:
```
CREATE DATABASE IF NOT EXISTS leaderboard;
CREATE USER IF NOT EXISTS 'mesos'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON leaderboard.* TO 'mesos'@'localhost';
FLUSH PRIVILEGES;
exit
```

