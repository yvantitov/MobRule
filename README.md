# MobRule

Default punishments:
- ban
- kick
- mute

Users may also define their own punishments using config files

# Punishment
### Commands
- /voteban USERNAME DURATION
- /votekick USERNAME
- /votemute USERNAME DURATION
- /mobrule
### Permissions
Each punishment has the following permissions
- mobrule.startvote.PUNISHMENT: You can start a vote for this punishment  
- mobrule.vote.PUNISHMENT: You can vote for or against this punishment
- mobrule.immune.PUNISHMENT: You are immune to this punishment
- mobrule.veto.PUNISHMENT: You can veto this punishment, instantly stopping the vote
- mobrule.report: You can view a report of all currently ongoing punishments