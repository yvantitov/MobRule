package com.github.yvantitov.mobrule;

import org.bukkit.command.CommandSender;

public class Messenger {
    private final CommandSender target;
    public Messenger(CommandSender target) {
        this.target = target;
    }
    public void sendBadPerms() {
        target.sendMessage("You do not have permission to use this command");
    }
    public void sendNoSuchPlayer(String playerName) {
        target.sendMessage(playerName + " is not online, or does not exist");
    }
    public void sendPlayerIsImmune(String playerName, String punishment) {
        target.sendMessage(playerName + " is immune to " + punishment);
    }
    public void sendOngoingVote() {
        target.sendMessage("There is already an ongoing vote");
    }
}
