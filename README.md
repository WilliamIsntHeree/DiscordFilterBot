# DiscordFilterBot


Simple Bot to filter out certain words



Config setup:

`Token` This is your discord bots token

`automute` This is if you want the bot to automatically mute people. Must be true/false

`log` This is if you want the bot to log any messages that have been flagged. Must be true/false

`staff_id` This is the ID of the staff role. Required to use the commands

`log_channel` Channel ID where you want messages sent. `log` Must be provided.

`mute_role` Required if `automute` is set to true. You need to provide the Roles ID

`owner` Your User ID goes here

`prefix` Preffered prefix that the bot will answer to


To compile, simply run "mvn install"
