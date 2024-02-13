import haven.Coord2d
import haven.render.sl.BinOp

import static java.lang.System.out

def tile;
boolean tilex = false;
int NextLine = 0
int plusx = 11
int minusx = -11
def soil
def start = player.getPlayerCoords()
if (start == null){
    start = player.getPlayerCoords()
}
def next = start.add(0.0, 0.0)
def newpos = next

def DropClay(){
    clay = inventory.getItems("Acre Clay")
    soil = inventory.getItems("Soil")
    if (clay != null) {
        for (n = 0; n < clay.size(); n++) {
            player.dropItemFromInventory(clay[n])
            sleep(20)
        }
    }
}

def Hourglass(){
    def i = 0
    while(true){
        houglass = player.getHourglass()
        if (houglass == -1.0) {
            while (houglass == -1.0 && i != 30) {
                houglass = player.getHourglass()
                if (i == 30) break
                i++
            }
        }
        if (i == 30) break
        if (houglass != -1.0 && i != 30) {
            while (houglass != -1.0) {
                houglass = player.getHourglass();
                DropClay()
                log.info("${houglass} Hourglass progress");
                sleep(200);
                if (houglass == -1.0) {
                    out.println("${houglass} +  Hourglass progress ended");
                    DropClay()
                    break
                }
            }
        }
    }
}

def GetStamina(def back,def backback){
    g = 0
    def CoursItem
    stam = player.getStaminaMeter()
    if (stam < 40){
        waterskin = inventory.getItems("Waterskin")
            Gmenu = player.getflower()
            if (Gmenu == null) {
                while (Gmenu == null) {
                    player.activateItem(waterskin[0])
                    Gmenu = player.getflower()
                    sleep(500)
                    g++
                    if (g == 10){
                        break
                    }
                    if (Gmenu != null) {
                        g = 0
                        break
                    }
                }
                if (g == 10) {
                    player.cancelAct()
                    player.moveToNoPF(back)
                    movedist = player.SdistanceBetweenCoords(back)
                    while (movedist != 0) {
                        player.moveToNoPF(back)
                        movedist = player.SdistanceBetweenCoords(back)
                        sleep(50)
                        if (movedist == 0) break
                    }
                    waterskin = player.getItems("Waterskin")
                    wask = inventory.takeItem(waterskin[0])
                    CoursItem = inventory.getHoldingItem().isPresent()
                    if (!CoursItem) {
                        log.info("item isn't in hands")
                        while (!inventory.getHoldingItem().isPresent()) {
                            CoursItem = inventory.getHoldingItem().isPresent()
                            if (CoursItem) break
                            sleep(25)
                        }
                    }
                    if (CoursItem) {
                        log.info("item in hands")
                        bar = player.findObjects("barrel")
                        player.useOn(bar[0])
                        player.beforerunclay()
                        player.ClaywaitUntilStops()
                        //sleep(2000)
                        inventory.putToInventory()
                        //sleep(500)
                        player.moveToNoPF(back)
                        movedist = player.SdistanceBetweenCoords(back)
                        while (movedist != 0) {
                            player.moveToNoPF(back)
                            movedist = player.SdistanceBetweenCoords(back)
                            sleep(50)
                            if (movedist == 0) break
                        }
                    }
                    g = 0
                }
            }
        Gmenu = player.getflower()
            if (Gmenu == null || Gmenu != null) {
                while (Gmenu == null) {
                    waterskin = player.getItems("Waterskin")
                    player.activateItem(waterskin[0])
                    sleep(500)
                    Gmenu = player.getflower()
                    if (Gmenu != null) break
                }
                Gmenu = player.getflower()
                if (Gmenu != null) {
                    while (Gmenu != null) {
                        player.FlowerMenuClick("Drink")
                        houglass = player.getHourglass()
                        if (houglass == -1.0) {
                            player.FlowerMenuClick("Drink")
                            sleep(200)
                        }
                        Hourglass()
                        stam = player.getStaminaMeter()
                        if (stam > 60 || houglass == -1) break
                    }
                }
            }
        }
}

def MoveSouthIFNextLineOne(){
    while(true) {
        ChecktileSouth = player.tileResnameAt(Coords.x.round().intValue(), Coords.y.round().intValue() + 22)
        if (ChecktileSouth.contains("gfx/tiles/dirt") && Check != 1) {
            next = next.add(0.0, 11.0)
            player.moveToNoPF(next)
            sleep(30000)
            break
        }
        ChecktileSouthOne = player.tileResnameAt(Coords.x.round().intValue(), Coords.y.round().intValue() + 11)
        if (!ChecktileSouth.contains("gfx/tiles/dirt")) {
            if (!ChecktileSouthOne.contains("gfx/tiles/dirt")){
                int breaks = 1
            }
            if (breaks != 1) {
                next = next.add(minusx, 0.0)
                player.moveToNoPF(next)
                sleep(30000)
                Check++
            }
        }
        if (Check == 1 || breaks == 1) {
            ChecktileWest = player.tileResnameAt(Coords.x.round().intValue() - 22, Coords.y.round().intValue())
            if (ChecktileWest.contains("gfx/tiles/dirt")){
                next = next.add(minusx, 0.0)
                player.moveToNoPF(next)
                sleep(30000)
                Check = 0
                breaks = 0
            }
        }
    }
}

while (true) {
    def breaks = 0
    Coords = player.getPlayerCoords()
    next = Coords.add(0.0, 0.0)
    next = next
    tile = player.tileResnameAt(Coords.x.round().intValue(), Coords.y.round().intValue())
    while (NextLine != 1){
        if (tile.contains("gfx/tiles/dirt")) {
            if (NextLine != 1) {
                log.info("${next}")
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist != 0) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist == 0) break
                }
                log.info("There's clay or dirt")
                player.doAct("dig")
                player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() - 4, 1, 0) //LEFT UP CORNER
                //hourglass start
                sleep(1000)
                Hourglass()
                Coords = player.getPlayerCoords()
                backback = Coords.add(0.0, 0.0)
                backback = backback
                GetStamina(newpos, backback)
                //hourglass end
                //if hourglass end, cancel act
                player.cancelAct()
                //move back to center
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist != 0) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist == 0) break
                }
                soil = inventory.getItems("Soil")
                    if (soil.size() > 0) {
                        for (n = 0; n < soil.size(); n++) {
                            player.dropItemFromInventory(soil[n])
                            sleep(20)
                        }
                        NextLine = 1
                        break
                    }
                sleep(200)
                player.doAct("dig")
                player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() - 4, 1, 0) //RIGHT UP CORNER
                //hourglass start
                sleep(1000)
                Hourglass()
                Coords = player.getPlayerCoords()
                backback = Coords.add(0.0, 0.0)
                backback = backback
                GetStamina(newpos, backback)
                //hourglass end
                //if hourglass end, cancel act
                player.cancelAct()
                //move back to center
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist != 0) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist == 0) break
                }
                DropClay()
                soil = inventory.getItems("Soil")
                if (soil != null) {
                    if (soil.size() > 0) {
                        for (n = 0; n < soil.size(); n++) {
                            player.dropItemFromInventory(soil[n])
                            sleep(20)
                        }
                        NextLine = 1
                        break
                    }
                }
                sleep(200)
                player.doAct("dig")
                player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() + 4, 1, 0) //LEFT DOWN CORNER
                //hourglass start
                sleep(1000)
                Hourglass()
                Coords = player.getPlayerCoords()
                backback = Coords.add(0.0, 0.0)
                backback = backback
                GetStamina(newpos, backback)
                //hourglass end
                //if hourglass end, cancel act
                player.cancelAct()
                //move back to center
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist != 0) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist == 0) break
                }
                DropClay()
                soil = inventory.getItems("Soil")
                    if (soil.size() > 0) {
                        for (n = 0; n < soil.size(); n++) {
                            player.dropItemFromInventory(soil[n])
                            sleep(20)
                        }
                        NextLine = 1
                        break
                }
                sleep(200)
                player.doAct("dig")
                player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() + 4, 1, 0)
                //RIGHT DOWN CORNER
                //hourglass start
                sleep(1000)
                Hourglass()
                Coords = player.getPlayerCoords()
                backback = Coords.add(0.0, 0.0)
                backback = backback
                GetStamina(newpos, backback)
                //hourglass end
                //if hourglass end, cancel act
                player.cancelAct()
                //move back to center
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist != 0) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist == 0) break
                }
                DropClay()
                soil = inventory.getItems("Soil")
                    if (soil.size() > 0) {
                        for (n = 0; n < soil.size(); n++) {
                            player.dropItemFromInventory(soil[n])
                            sleep(20)
                        }
                        NextLine = 1
                        break
                    }
            }
            //next 2 tiles check for dirt
            Checktile = player.tileResnameAt(next.x.round().intValue() + 22, next.y.round().intValue())
            if (Checktile.contains("gfx/tiles/dirt") && NextLine != 1) {
                //moving to next tile
                next = next.add(plusx, 0.0)
                player.moveToNoPF(next)
                sleep(1000)
            }
            if (!Checktile.contains("gfx/tiles/dirt") && NextLine != 1) {
                NextLine++
            }
        }
}
    if (NextLine == 1) {
        int Check = 0
        while (true) {
            ChecktileSouth = player.tileResnameAt(next.x.round().intValue(), next.y.round().intValue() + 22)
            if (ChecktileSouth.contains("gfx/tiles/dirt") && Check != 1) {
                next = next.add(0.0, 11.0)
                player.moveToNoPF(next)
                sleep(1000)
                break
            }
            ChecktileSouthOne = player.tileResnameAt(next.x.round().intValue(), next.y.round().intValue() + 11)
            if (!ChecktileSouth.contains("gfx/tiles/dirt")) {
                if (!ChecktileSouthOne.contains("gfx/tiles/dirt")) {
                    breaks = 1
                }
                if (breaks != 1) {
                    next = next.add(minusx, 0.0)
                    player.moveToNoPF(next)
                    sleep(1000)
                    Check++
                }
            }
            if (Check == 1 || breaks == 1) {
                ChecktileWest = player.tileResnameAt(next.x.round().intValue() - 22, next.y.round().intValue())
                if (ChecktileWest.contains("gfx/tiles/dirt")) {
                    next = next.add(minusx, 0.0)
                    player.moveToNoPF(next)
                    sleep(1000)
                    Check = 0
                    breaks = 0
                }
            }
        }
        while (NextLine != 0) {
            log.info("${next}")
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist != 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist == 0) break
            }
            log.info("There's clay or dirt")
            player.doAct("dig")
            //player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() - 4, 1, 0) //LEFT UP CORNER
            player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() + 4, 1, 0) //LEFT DOWN CORNER
            //hourglass start
            sleep(1000)
            Hourglass()
            Coords = player.getPlayerCoords()
            backback = Coords.add(0.0, 0.0)
            backback = backback
            GetStamina(newpos,backback)
            //hourglass end
            //if hourglass end, cancel act
            player.cancelAct()
            //move back to center
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist != 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist == 0) break
            }
            soil = inventory.getItems("Soil")
                if (soil.size() > 0) {
                    for (n = 0; n < soil.size(); n++) {
                        player.dropItemFromInventory(soil[n])
                        sleep(20)
                    }
                    NextLine = 0
                    break
                }
            sleep(200)
            player.doAct("dig")
            //player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() - 4, 1, 0) //RIGHT UP CORNER
            player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() + 4, 1, 0) // RIGHT DOWN CORNER
            //hourglass start
            sleep(1000)
            Hourglass()
            Coords = player.getPlayerCoords()
            backback = Coords.add(0.0, 0.0)
            backback = backback
            GetStamina(newpos,backback)
            //hourglass end
            //if hourglass end, cancel act
            player.cancelAct()
            //move back to center
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist != 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist == 0) break
            }
            soil = inventory.getItems("Soil")
                if (soil.size() > 0) {
                    for (n = 0; n < soil.size(); n++) {
                        player.dropItemFromInventory(soil[n])
                        sleep(20)
                    }
                    NextLine = 0
                    break
                }
            sleep(200)
            player.doAct("dig")
            player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() - 4, 1, 0) //LEFT UP CORNER
            //player.mapClick(next.x.round().intValue() - 4, next.y.round().intValue() + 4, 1, 0) //LEFT DOWN CORNER
            //hourglass start
            sleep(1000)
            Hourglass()
            Coords = player.getPlayerCoords()
            backback = Coords.add(0.0, 0.0)
            backback = backback
            GetStamina(newpos,backback)
            //hourglass end
            //if hourglass end, cancel act
            player.cancelAct()
            //move back to center
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist != 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist == 0) break
            }
            soil = inventory.getItems("Soil")
                if (soil.size() > 0) {
                    for (n = 0; n < soil.size(); n++) {
                        player.dropItemFromInventory(soil[n])
                        sleep(20)
                    }
                    NextLine = 0
                    break
                }
            sleep(200)
            player.doAct("dig")
            player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() - 4, 1, 0) //RIGHT UP CORNER
            //player.mapClick(next.x.round().intValue() + 4, next.y.round().intValue() + 4, 1, 0) // RIGHT DOWN CORNER
            //RIGHT DOWN CORNER
            //hourglass start
            sleep(1000)
            Hourglass()
            Coords = player.getPlayerCoords()
            backback = Coords.add(0.0, 0.0)
            backback = backback
            GetStamina(newpos,backback)
            //hourglass end
            //if hourglass end, cancel act
            player.cancelAct()
            //move back to center
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist != 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist == 0) break
            }
            soil = inventory.getItems("Soil")
                if (soil.size() > 0) {
                    for (n = 0; n < soil.size(); n++) {
                        player.dropItemFromInventory(soil[n])
                        sleep(20)
                    }
                    NextLine = 0
                    break
                }
            sleep(200)
            //next 2 tiles check for dirt
            Checktile = player.tileResnameAt(next.x.round().intValue() - 22, next.y.round().intValue())
            if (Checktile.contains("gfx/tiles/dirt") && NextLine != 0) {
                //moving to next tile
                next = next.add(minusx, 0.0)
                player.moveToNoPF(next)
                sleep(1000)
            }
            if (!Checktile.contains("gfx/tiles/dirt") && NextLine != 0) {
                NextLine--
            }
        }
    }
    if (NextLine == 0){
        int Check = 0
        while(true) {
            ChecktileSouth = player.tileResnameAt(next.x.round().intValue(), next.y.round().intValue() + 22)
            if (ChecktileSouth.contains("gfx/tiles/dirt") && Check != 1) {
                next = next.add(0.0, 11.0)
                player.moveToNoPF(next)
                sleep(1000)
                break
            }
            ChecktileSouthOne = player.tileResnameAt(next.x.round().intValue(), next.y.round().intValue() + 11)
            if (!ChecktileSouth.contains("gfx/tiles/dirt")) {
                if (!ChecktileSouthOne.contains("gfx/tiles/dirt")){
                    breaks = 1
                }
                if (breaks != 1) {
                    next = next.add(plusx, 0.0)
                    player.moveToNoPF(next)
                    sleep(1000)
                    Check++
                }
            }
            if (Check == 1 || breaks == 1) {
                ChecktileEast = player.tileResnameAt(next.x.round().intValue() + 22, next.y.round().intValue())
                if (ChecktileEast.contains("gfx/tiles/dirt")){
                    next = next.add(plusx, 0.0)
                    player.moveToNoPF(next)
                    sleep(1000)
                    Check = 0
                    breaks = 0
                }
            }
        }
    }
    }

log.info("${next.x.round()} , ${next.y.round()}. Resource name: ${tile}")

