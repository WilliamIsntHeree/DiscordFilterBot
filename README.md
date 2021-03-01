# DiscordFilterBot


Simple Bot to filter out certain words




# Setup

To compile the bot, run `mvn clean` and `mvn install` in the terminal. 

Create a file called `config.json` in the same folder that you placed the JAR file in

Look below for an example

```
{
"token": "",
  "automute": "if you want the bot to auto punish",
  "log": "if you want the bot to log punishments",
  "staff_id": "ID of the staff role",
  "log_channel": "ID of the log channel. If log is false, this can be ignored.",
  "mute_role": "ID of the muted ROLE",
  "owner": "Your ID",
  "prefix": "!"
}
```
