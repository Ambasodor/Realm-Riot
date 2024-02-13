S = player.isAggroed()
if (S == true) {
    log.info("${player.isAggroed()}")
    player.discordSay("I HAVE AGGRO ON ME, HELP ME")
}
if (S == false){
    log.info("${player.isAggroed()}")
}