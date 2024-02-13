def Good = []
def start = player.getPlayerCoords()
def next = start.add(0.0, 0.0)
def newpos = next
pow = player.findObjects("pow")
log.info("${pow.size()}")
for (p = 0; p< pow.size(); p++){
    e = player.getOverlay(pow[p], "roastspit")
    if (e != 0){
        Good.add((pow[p]))
    }
}
log.info("${Good.size()}")
while (true) {
    for (e = 0; e < Good.size(); e++) {
        a = player.getOverlay(Good[e], "roastspit")
        player.ClickObjectUnderGob(Good[e], a)
        Gmenu = player.getflower()
        if (Gmenu == null) {
            while (Gmenu == null) {
                player.ClickObjectUnderGob(Good[e], a)
                sleep(200)
                Gmenu = player.getflower()
                if (Gmenu != null) break
            }
        }
        PutOut = player.getpetal(Gmenu, "Put Out")
        if (PutOut != null) {
            player.closeFmenu()
            continue
        }
        if (Gmenu != null) {
            Gmenu = player.getflower()
            Take = player.getpetal(Gmenu, "Take")
            Turn = player.getpetal(Gmenu, "Clean")
            Carve = player.getpetal(Gmenu, "Carve")
            PutOut = player.getpetal(Gmenu, "Put Out")
            if (PutOut != null) {
                player.closeFmenu()
                sleep(1000)
            }
            if (Turn != null && Carve == null) {
                log.info("IM TURNING IT")
                //player.areaSay("I SEE MEAT ON SPIT")
                player.ClickFlower("Turn")
                Hourglass = player.getHourglass()
                if (Hourglass == -1) {
                    a = 0
                    Hourglass = player.getHourglass()
                    while (Hourglass == -1) {
                        dist = player.distanceTo(Good[e])
                        if (dist != 11){
                            if (a == 10) {
                                break
                            }
                            sleep(200)
                            dist = player.distanceTo(Good[e])
                            a++
                        }
                        Hourglass = player.getHourglass()
                        if (Hourglass != -1) break
                    }
                }
                if (Hourglass != -1) {
                    Hourglass = player.getHourglass()
                    while (Hourglass != -1) {
                        Hourglass = player.getHourglass()
                        a = player.getOverlay(Good[e], "roastspit")
                        player.ClickObjectUnderGob(Good[e], a)
                        Gmenu = player.getflower()
                        if (Gmenu == null) {
                            while (Gmenu == null) {
                                player.ClickObjectUnderGob(Good[e], a)
                                sleep(200)
                                Gmenu = player.getflower()
                                if (Gmenu != null) break
                            }
                        }
                        player.closeFmenu()
                        Carve = player.getpetal(Gmenu, "Carve")
                        sleep(1000)
                        if (Carve != null) {
                            break
                        }
                    }
                }
            } else if (Carve != null) {
                log.info("IM ITS READY")
                player.closeFmenu()
                sleep(1000)
            } else {
                player.closeFmenu()
                sleep(1000)
            }
        }
        sleep(1000)
        player.moveToNoPF(newpos)
    }
}