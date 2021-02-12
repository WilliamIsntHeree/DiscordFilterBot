package me.williamisnthere.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.williamisnthere.bot.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;


import java.awt.*;


public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        this.name = "unmute";
    }



    @Override
    protected void execute(CommandEvent commandEvent) {
        if(commandEvent.getMember().getRoles().stream().anyMatch(role -> role.getId().equals(DiscordBot.getInstance().getConfig().get("staff_id")))) {
            String prefix = DiscordBot.getInstance().getConfig().get("prefix");

            if(commandEvent.getMessage().getMentionedMembers().isEmpty()) {
                commandEvent.reply(prefix + "unmute {@Mention}");
                return;
            }


            Member member = commandEvent.getMessage().getMentionedMembers().get(0);
            Role role = commandEvent.getGuild().getRoleById(DiscordBot.getInstance().getConfig().get("mute_role"));
            commandEvent.getGuild().removeRoleFromMember(member, role).queue();
            if(!member.getRoles().stream().anyMatch(r -> r.getId().equals(role.getId()))) {
                commandEvent.reply("User is not muted.");
                return;
            }
            boolean logging = DiscordBot.getInstance().getConfig().get("log").equalsIgnoreCase("true");

            if(logging) {
                MessageEmbed embed = new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setTitle("Punishment Update")
                        .setDescription("User: " + member.getAsMention() + " was unmuted by " + commandEvent.getMember().getAsMention()).build();

                commandEvent.getGuild().getTextChannelById(DiscordBot.getInstance().getConfig().get("log_channel")).sendMessage(embed).queue();
            }
        }
    }
}
