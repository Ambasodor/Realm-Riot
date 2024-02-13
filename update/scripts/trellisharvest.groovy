player.lastmsgnull()
last = player.getlastmsgfromArea()
center = player.CentergetMytile()
player.clickcenter(center)
grape = player.findObjects("gfx/terobjs/plants/wine")
turnoff = false
start = player.CentergetMytile()
def farming() {
    while (true) {
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true) {
            return
        }
        gobs = getgobswithsdt()
        while (!gobs.isEmpty()) {
            gobs = getgobswithsdt()
            goeast()
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            for (a = 0; a < gobs.size(); a++) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                dist = player.distanceTo(gobs[a])
                log.info("${dist}")
                    if (dist <= 13.75 && dist != -1) {
                        harvest(gobs[a])
                        cent = player.CentergetMytile()
                        player.clickcenter(cent)
                    }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
            }
            if (gobs.isEmpty()) break
        }
        between = player.SdistanceBetweenCoords(start)
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        if (between != 0){
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            gotostart(center)
        }
        a = inventory.getItemsContain("Grapes, stack of")
        if (!a.isEmpty()){
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            createstockpile()
            grapestockpile()
        }
    }
}

def createstockpile(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    a = inventory.getItemsContain("Grapes, stack of")
    log.info("${a}")
    inventory.takeItem(a[0])
    player.makePile()
}
def grapestockpile(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    center = player.CentergetMytile()
    toDowner = 0
    player.placeShiftAllThing(-22,toDowner)
    w = player.getWindow("Stockpile")
    oc = player.isOccupied()
    if (w == null) {
        w = player.getWindow("Stockpile")
        while (true) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            w = player.getWindow("Stockpile")
            oc = player.isOccupied()
            if (oc == true){
                toDowner += 11
                player.placeShiftAllThing(-22,toDowner)
                oc = player.isOccupied()
            }
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) return
            w = player.getWindow("Stockpile")
            if (w != null) break
        }
    }
    between = player.SdistanceBetweenCoords(center)
    if (between != 0) {
        player.clickcenter(center)
    }
}
def getgobswithsdt(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    grape = player.findObjects("gfx/terobjs/plants/wine")
    def list = []
    for (a = 0; a < grape.size(); a++){
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true) {
            return
        }
        sdt = player.GetSdt(grape[a])
        if (sdt == 6){
            list.add(grape[a])
        }
    }
    return list
}
def harvest(gob){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true){
        return
    }
    co = player.CentergetMytile()
    player.RightClickGobnomove(gob)
    Gmenu = player.getflower()
    if (Gmenu == null) {
        while (Gmenu == null) {
            player.RightClickGobnomove(gob)
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            sleep(200)
            Gmenu = player.getflower()
            if (Gmenu != null) break
        }
    }
    Gmenu = player.getflower()
    if (Gmenu != null) {
        Gmenu = player.getflower()
        Chopmenu = player.getpetal(Gmenu, "Harvest")
        if (Chopmenu == null){
            while(Chopmenu == null){
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                Gmenu = player.getflower()
                Chopmenu = player.getpetal(Gmenu, "Harvest")
                if (Chopmenu != null) break
            }
        }
        if (turnoff == true){
            return
        }
        if (Chopmenu != null) {
            FreeSpace = inventory.getFreeSpace()
            player.ClickPetal(Chopmenu)
            NewSpace = inventory.getFreeSpace()
            while(FreeSpace == NewSpace){
                Chopmenu = player.getpetal(Gmenu, "Harvest")
                if (Chopmenu != null) {
                    player.ClickPetal(Chopmenu)
                } else if (Chopmenu == null) break
                NewSpace = inventory.getFreeSpace()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                sleep(200)
                if (NewSpace != FreeSpace) break
            }
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
        }
    }
}
def gotostart(start){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    player.moveToNoPF(start)
    movedist = player.SdistanceBetweenCoords(start)
    while (movedist != 0) {
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(start)
        movedist = player.SdistanceBetweenCoords(start)
        log.info("${movedist}")
        sleep(50)
        if (movedist == 0) break
    }
}
def goeast() {
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    co = player.CentergetMytile()
    co = co.add(11,0)
    player.moveToNoPF(co)
    movedist = player.SdistanceBetweenCoords(co)
    while (movedist != 0) {
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(co)
        movedist = player.SdistanceBetweenCoords(co)
        sleep(50)
        if (movedist == 0) break
    }
}
farming()