package me.williamisnthere.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.williamisnthere.bot.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.nio.file.Paths;

public class BlacklistCommand extends Command {

    public BlacklistCommand() {
        this.name = "blacklist";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String prefix = DiscordBot.getInstance().getConfig().get("prefix");
        String[] args = commandEvent.getMessage().getContentRaw().split(" ");
        if(commandEvent.getMember().getRoles().stream().anyMatch(r -> r.getId().equals(DiscordBot.getInstance().getConfig().get("staff_id")))) {

        if (args.length == 1) {
            commandEvent.reply(prefix + "blacklist [add/list/remove]");
        }

        if (args.length == 2) {
            if(args[1].equalsIgnoreCase("list")) {
                commandEvent.replyInDm(DiscordBot.getInstance().getBannedwords().toString());
            }

            if(args[1].equalsIgnoreCase("help")) {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Blacklist Help")
                        .setDescription(prefix + "blacklist help - Returns this message.\n" +
                                prefix + "blacklist list - Returns all words in the blacklist\n" +
                                prefix + "blacklist add [word] - Add a word to the blacklist\n" +
                                prefix + "blacklist remove [word] - Removed a word from the blacklist"
                                ).setColor(Color.CYAN).build();

                commandEvent.replyInDm(embed);


            }
        }

        if (args.length == 3) {
            if(args[1].equalsIgnoreCase("add")) {
                String word = args[2];
                if(DiscordBot.getInstance().getBannedwords().contains(word)) {
                    System.out.println("K");
                    return;
                }

                commandEvent.replyFormatted("Word '%s' added to blacklist.", word);
                DiscordBot.getInstance().getBannedwords().add(word);
                update();

            }

            if(args[1].equalsIgnoreCase("remove")) {
                String word = args[2];
                if(!DiscordBot.getInstance().getBannedwords().contains(word)) {
                    commandEvent.reply("That word is not in the filter.");
                    return;
                }

                DiscordBot.getInstance().getBannedwords().remove(word);
                update();
                commandEvent.replyFormatted("Word '%s' removed.", word);
            }
        }
    }

    }


    private void update() {
        try {
            DiscordBot.getInstance().getMapper().writeValue(Paths.get("blacklistedwords.json").toFile(), DiscordBot.getInstance().getBannedwords());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
