package me.williamisnthere.bot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.williamisnthere.bot.commands.BlacklistCommand;
import me.williamisnthere.bot.commands.UnmuteCommand;
import me.williamisnthere.bot.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DiscordBot {


    private List<String> bannedwords;
    private ObjectMapper mapper;
    private Config config;
    private static DiscordBot instance;
    private JDA jda;

    public static void main(String[] args) {
        new DiscordBot();
    }
    public DiscordBot() {
        instance = this;
        config = new Config();
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId(DiscordBot.getInstance().getConfig().get("owner"));
        builder.setPrefix(DiscordBot.getInstance().getConfig().get("prefix"));
        builder.addCommand(new BlacklistCommand());
        builder.addCommand(new UnmuteCommand());

        CommandClient client = builder.build();

        try {
            jda = JDABuilder.createDefault(config.get("token"))
                    .enableIntents(EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .addEventListeners(new MessageListener(), client)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);


        loadBannedWords();

    }

    private void loadBannedWords() {
        try {
            bannedwords = new ArrayList<>();
            bannedwords = mapper.readValue(Paths.get("blacklistedwords.json").toFile(), List.class);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static DiscordBot getInstance() {
        return instance;
    }


    public List<String> getBannedwords() {
        return bannedwords;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Config getConfig() {
        return config;
    }


}
