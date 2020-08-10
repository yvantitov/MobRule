package com.github.yvantitov.mobrule.punishment;

import org.bukkit.entity.Player;

public class Kick extends Punishment {

    private final Player target;

    public Kick(Player target) {
        this.target = target;
    }

    @Override
    public String getPunishmentAction() {
        return "kick";
    }

    @Override
    public void execute() {
        target.kickPlayer("A vote to kick you has succeeded");
    }
}
