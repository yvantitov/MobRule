package com.github.yvantitov.mobrule;

import com.github.yvantitov.mobrule.command.CommandVote;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class MobRule extends JavaPlugin {

    public static final String ID = "mobrule";
    private static Logger logger;
    private static Plugin instance;

    private static Vote ongoingVote = null;

    @Override
    public void onEnable() {
        instance = this;
        logger = Bukkit.getLogger();
        // register commands
        this.getCommand("vote").setExecutor(new CommandVote());
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void clearCurrentVote() {
        ongoingVote = null;
    }

    public static void setCurrentVote(Vote vote) {
        if (ongoingVote == null) {
            ongoingVote = vote;
        }
    }
}
