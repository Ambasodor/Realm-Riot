once = 0
def ignorelist = []
while (true){
    def playerlist = player.FindAnotherPlayers()
    if (!playerlist.isEmpty()){
        for (a = 0; a < playerlist.size(); a++){
            if (!ignorelist.contains(playerlist[a])) {
                get = player.DescribePerson(playerlist[a])
                if (!get.isEmpty()) {
                    if (once == 0){
                        player.discordSendScreen("<@&1184631814824931338> I FOUND KOREAN","res/saved.png")
                        once++
                    }
                    player.discordSay("Player have gear: ${get}")
                    ignorelist.add(playerlist[a])
                }
            }
        }
    }
    if (playerlist.isEmpty()){
        if (!ignorelist.isEmpty()){
            player.discordSay("Player's gone")
        }
        ignorelist = []
        once = 0
    }
    sleep(1000)
}