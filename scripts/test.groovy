ignorelistp = []
once = 0
def findpersons(label){
    playerlist = player.FindAnotherPlayers()
    log.info "${playerlist.size()}"
    if (!playerlist.isEmpty()){
        for (a = 0; a < playerlist.size(); a++){
            if (!ignorelistp.contains(playerlist[a])) {
                get = player.DescribePerson(playerlist[a])
                log.info "${get}"
                if (!get.isEmpty()) {
                    if (once == 0){
                        player.discordSendScreen("<@&1184631814824931338> I FOUND SOMEBODY AT CROSSROAD ${label}","res/saved.png")
                        once++
                    }
                    player.discordSay("Player have gear: ${get}")
                    ignorelistp.add(playerlist[a])
                }
            }
        }
    }
    if (playerlist.isEmpty()){
        if (!ignorelistp.isEmpty()){
            player.discordSay("Player's gone")
        }
        ignorelist = []
        once = 0
    }
}
findpersons("A2") // чекает людей рядом с кроссом