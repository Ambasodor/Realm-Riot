oldcow = player.findObjects("calf")
while (true){
    newcow = player.findObjects("calf")
    if (newcow.size() > oldcow.size()){
        player.discordSay("<@219213705919987712> There's one more calf, murder it!")
        oldcow = player.findObjects("calf")
    }
    if (newcow.size() < oldcow.size()){
        player.discordSay("<@219213705919987712> There's one calf grow to the real BIG COW!")
        oldcow = player.findObjects("calf")
    }
    sleep(500)
}