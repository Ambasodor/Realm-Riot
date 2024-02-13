while(true) {
    def unknowns = player.describeUnknowns()

    if (unknowns.size() == 1) {
        player.discordSay("Found one white dot, probably not an enemy? ${unknowns[0]}")
    }

    else if (unknowns.size() > 1) {
        player.discordSay("@here Found multiple unknowns! ${unknowns.join(" # ")}")
    }

    sleep(20000)
}
