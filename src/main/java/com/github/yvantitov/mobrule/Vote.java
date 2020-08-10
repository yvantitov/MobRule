package com.github.yvantitov.mobrule;

import com.github.yvantitov.mobrule.punishment.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class Vote {
    public static long VOTE_DURATION = 30_000;
    private static Logger logger;

    private final String description;
    private final CommandSender callingSender;
    private final Player targetPlayer;
    private final Punishment punishment;
    private final VoteRequirements voteRequirements;
    private final BukkitTask endVoteTask;

    protected Collection<Player> playersAye = new ArrayList<>();
    protected Collection<Player> playersNay = new ArrayList<>();

    public Vote(
            CommandSender callingSender,
            Player targetPlayer,
            Punishment punishment,
            VoteRequirements voteRequirements
    ) {
        logger = Bukkit.getLogger();
        description = punishment.getPunishmentAction() + " " + targetPlayer.getDisplayName();
        logger.info("Vote to " + description + " started by " + callingSender.getName());

        this.callingSender = callingSender;
        this.targetPlayer = targetPlayer;
        this.punishment = punishment;
        this.voteRequirements = voteRequirements;
        playersNay.addAll(Bukkit.getOnlinePlayers());

        // start scheduled task to end the vote
        endVoteTask = Bukkit.getScheduler().runTaskLater(MobRule.getInstance(), new Runnable() {
            @Override
            public void run() {
                endVote();
            }
        }, VOTE_DURATION);
    }

    public static class VoteRequirements {
        private final double requiredMajority;
        private final int quorum;

        public VoteRequirements(double requiredMajority, int quorum) {
            this.requiredMajority = requiredMajority;
            this.quorum = quorum;
        }

        public boolean voteIsValid(Vote vote) {
            double playersAye = vote.playersAye.size();
            double playersNay = vote.playersNay.size();
            double totalPlayers = playersAye + playersNay;
            if (quorum < totalPlayers) {
                return false;
            }
            return (playersAye / totalPlayers) >= requiredMajority;
        }
    }

    private void endVote() {
        String state = null;
        if (voteRequirements.voteIsValid(this)) {
            punishment.execute();
            state = "succeeded";
        } else {
            state = "failed";
        }
        logger.info("Vote to "
                + description
                + " started by "
                + callingSender.getName()
                + " has "
                + state
                + ", with "
                + playersAye.size()
                + " votes 'Aye' and "
                + playersNay.size()
                + " votes 'Nay'");
        MobRule.clearCurrentVote();
    }

    public void veto(CommandSender vetoer) {
        logger.info("Vote to " + description + " has been vetoed by " + vetoer.getName());
        endVoteTask.cancel();
        MobRule.clearCurrentVote();
    }

    public void voteAye(Player player) {
        logger.info(player.getDisplayName() + " has changed their vote to 'Aye' to " + description);
        playersNay.remove(player);
        if (!playersAye.contains(player)) {
            playersAye.add(player);
        }
    }

    public void voteNay(Player player) {
        logger.info(player.getDisplayName() + " has changed their vote to 'Nay' to " + description);
        playersAye.remove(player);
        if (!playersNay.contains(player)) {
            playersNay.add(player);
        }
    }

    public String getDescription() {
        return description;
    }

    public CommandSender getCallingSender() {
        return callingSender;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }
}
