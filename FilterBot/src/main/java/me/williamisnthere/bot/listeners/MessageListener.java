package me.williamisnthere.bot.listeners;

import me.williamisnthere.bot.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class MessageListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        if(e.getMessage().getContentRaw().startsWith(DiscordBot.getInstance().getConfig().get("prefix"))) {
            return;
        }

        if(e.getAuthor().isBot()) {
            return;
        }




         String msgContent = e.getMessage().getContentRaw().replaceAll("1", "i")
                .replaceAll("!", "i")
                .replaceAll("3", "e")
                .replaceAll("4", "a")
                .replaceAll("@", "a")
                .replaceAll("5", "s")
                .replaceAll("0", "o")
                .replaceAll("([^A-Za-z0-9\\s])", "")
                .replaceAll(" ", "")
                .replaceAll("", "");

        for(String s : DiscordBot.getInstance().getBannedwords()) {


            String[] ok = e.getMessage().getContentRaw().split(" ");

            String message = "";

            boolean bool = msgContent.contains(s);
            if(bool) {
                if (DiscordBot.getInstance().getConfig().get("log").equals("true") && DiscordBot.getInstance().getConfig().get("automute").equals("false")) {
                    System.out.println("Flagged");

                    System.out.println("Flagged");
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Auto Moderation")
                            .setColor(Color.cyan)
                            .addField("Target", e.getMember().getAsMention(), true)
                            .addField("Length", "None", true)
                            .addField("Action", "None", true)
                            .addField("Reason", "Message flagged in " + e.getChannel().getAsMention() + "  | [click here](" + e.getMessage().getJumpUrl() + ")", false)
                            .build();

                    e.getGuild().getTextChannelById(DiscordBot.getInstance().getConfig().get("log_channel")).sendMessage(embed).queue();
                    return;
                }



                if(DiscordBot.getInstance().getConfig().get("automute").equals("true")) {
                    Role muted = e.getGuild().getRoleById(DiscordBot.getInstance().getConfig().get("mute_role"));
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Auto Moderation")
                            .setColor(Color.cyan)
                            .addField("Target", e.getMember().getAsMention(), true)
                            .addField("Length", "Permanent", true)
                            .addField("Action", "Mute", true)
                            .addField("Reason", "Message flagged in " + e.getChannel().getAsMention() + "  | [click here](" + e.getMessage().getJumpUrl() + ")", false)
                            .build();
                    e.getGuild().getTextChannelById(DiscordBot.getInstance().getConfig().get("log_channel")).sendMessage(embed).queue();
                    e.getGuild().addRoleToMember(e.getMember(), muted).reason("Message Flagged").queue();
                    return;
                }
            }


            }



        }
    }

