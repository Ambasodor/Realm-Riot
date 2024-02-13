import static java.lang.System.*;
def start = player.getPlayerCoords()
def next = start.add(0.0, 0.0)
def newpos = next

def food = "Roast"
def barrels = player.findObjects("barrel")
def toRandom = 0

def getnearestgob(def gobs){
    if (!gobs.isEmpty()) {
        def nearlist = []
        for (d = 0; d < gobs.size(); d++) {
            dist = player.distanceTo(gobs[d])
            nearlist.add(dist)
        }
        min = Collections.min(nearlist)
        numberinlist = nearlist.indexOf(min)
        return gobs[numberinlist]
    }
}
def GetResName(gob){
    if (gob != null) {
        return player.getGobResName(gob)
    }
    return null
}

def getnearestfromFour(def a,def b,def c,def d){
    def list = []
    def minlist = []
    if (getnearestgob(a) != null) {
        list.add(getnearestgob(a))
    }
    if (getnearestgob(b) != null) {
        list.add(getnearestgob(b))
    }
    if (getnearestgob(c) != null) {
        list.add(getnearestgob(c))
    }
    if (getnearestgob(d) != null) {
        list.add(getnearestgob(d))
    }
    for (i = 0; i < list.size(); i++) {
        dist = player.distanceTo(list[i])
        minlist.add(dist)
    }
    min = Collections.min(minlist)
    numberinlist = minlist.indexOf(min)
    log.info("${list}")
    return list[numberinlist]
}
def Hourglass(bool, start, toRandom){
        houglass = player.getHourglass()
        if (houglass == -1.0) {
            while (houglass == -1.0) {
                houglass = player.getHourglass()
                if (houglass != -1) break
            }
        }
        if (houglass != -1.0) {
            houglass = player.getHourglass();
            while (houglass != -1.0) {
                houglass = player.getHourglass();
                Gmenu = player.getflower()
                if (Gmenu != null){
                    player.closeFmenu()
                }
                if (bool == true){
                    DropBlocks()
                }
                if (bool != "nd") {
                    stateDrink = Drink(start, toRandom)
                    stateFood = Eat(start,toRandom)
                    if (stateDrink == "nowater"){
                        return "nowater"
                    }
                    if (stateFood == "nofood"){
                        return "nofood"
                    }
                }
                houglass = player.getHourglass();
                log.info("${houglass} Hourglass progress");
                sleep(200);
                if (houglass == -1.0) {
                    out.println("${houglass} +  Hourglass progress ended");
                    break
                }
            }
        }
}
def Eat(def start, toRandom){
    energy = player.getEnergyMeter()
    if (energy <= 3000){
        moveToNoPF(start, toRandom)
        def chests = player.findObjects("largechest")
        for (c = 0; c < chests.size(); c++) {
            player.RightClickGobNoWait(chests[c])
            cratewindow = player.waitForNewWindowTest("Large Chest")
            if (cratewindow == null) {
                while (cratewindow == null) {
                    player.RightClickGobNoWait(chests[c])
                    cratewindow = player.waitForNewWindowTest("Large Chest")
                    if (cratewindow != null) break
                }
            }
            cratewindow = player.waitForNewWindowTest("Large Chest")
            if (cratewindow != null){
                Cpblee = inventory.getItemsContains(cratewindow, "Roast")
                for (v = 0; v < Cpblee.size(); v++){
                    Cpblees = inventory.getItemsContains(cratewindow, "Roast")
                    if (Cpblees.size() == 0){
                        break
                    }
                    energy = player.getEnergyMeter()
                    player.activateItem(Cpblee[v])
                    Gmenu = player.getflower()
                    if (Gmenu == null){
                        while (Gmenu == null){
                            sleep(25)
                            Gmenu = player.getflower()
                            if (Gmenu != null) break
                        }
                    }
                    if (Gmenu != null){
                        Gmenu = player.getflower()
                        Eat = player.getpetal(Gmenu, "Eat")
                        player.ClickFlower("Eat")
                        energy = player.getEnergyMeter()
                        if (energy >= 8000) {
                            break
                        }
                    }
                }
            }
            moveToNoPF(start, toRandom)
        }
        energy = getenergy()
        if (energy < 3000){
            return "nofood"
        }
    }
}

def Drink(def start,toRandom){
    def nowater = []
    stam = player.getStaminaMeter()
    if (stam <= 40) {
        havew = DoHaveWater(nowater)
        if (!havew){
            fillandback(start, nowater, toRandom)
            havew = DoHaveWater(nowater)
            if (!havew){
                return "nowater"
            }
        }
        if (havew) {
            havew = DoHaveWater(nowater)
            player.doAct("drink")
            while (stam <= 40) {
                block = inventory.getItemsContain("Block")
                splinter = inventory.getItemsContain("Cruel splinter")
                if (block != null || splinter != null) {
                    DropBlocks()
                }
                stam = player.getStaminaMeter()
                sleep(100)
                if (stam >= 90) break
            }
        }
    }
}

def Destroy(start, gob,toRandom){
    def st = true
    while (true) {
        player.doAct("destroy")
        player.ClickGob(gob)
        player.beforerunclay()
        player.ClaywaitUntilStops()
        player.cancelAct()
        st = Hourglass("cur", start,toRandom)
        if (st == "nofood"){
            return "nofood"
        }
        if (st == "nowater"){
            return "nowater"
        }
        log.info("finished Destroying")
        return false;
    }
}

def Chop(start, gob,toRandom){
    def st = true
    while (true) {
        player.RightClickGobnomove(gob)
        Gmenu = player.getflower()
        if (Gmenu == null) {
            while (Gmenu == null) {
                sleep(200)
                Gmenu = player.getflower()
                if (Gmenu != null) break
            }
        }
        if (Gmenu != null) {
            Chopmenu = player.getpetal(Gmenu, "Chop")
            if(Chopmenu != null) {
                player.ClickFlower("Chop")
                player.beforerunclay()
                player.ClaywaitUntilStops()
                st = Hourglass(false, start,toRandom)
            }
        }
        if (st == "nofood"){
            return "nofood"
        }
        if (st == "nowater"){
            return "nowater"
        }
        log.info("finished Chopping")
        return false
    }
}

def ChopInBlocks(start, gob,toRandom){
    def st = true
        player.RightClickGobnomove(gob)
        Gmenu = player.getflower()
        if (Gmenu == null) {
            while (Gmenu == null) {
                sleep(200)
                Gmenu = player.getflower()
                if (Gmenu != null) break
            }
        }
        Gmenu = player.getflower()
        if (Gmenu != null) {
            Chopmenu = player.getpetal(Gmenu, "Chop into blocks")
            if (Chopmenu != null) {
                player.ClickFlower("Chop into blocks")
                player.beforerunclay()
                player.ClaywaitUntilStops()
                st = Hourglass(true, start,toRandom)
                if (st == "nofood"){
                    return "nofood"
                }
                if (st == "nowater"){
                    return "nowater"
                }
            }
        }
        log.info("finished Chopping to blocks")
}

def DropBlocks(){
    block = inventory.getItemsContain("Block")
    if (block != null) {
        for (n = 0; n < block.size(); n++) {
            player.dropItemFromInventory(block[n])
            sleep(20)
        }
    }
    splinter = inventory.getItemsContain("Cruel Splinter")
    if (splinter != null) {
        for (n = 0; n < splinter.size(); n++) {
            player.dropItemFromInventory(splinter[n])
            sleep(20)
        }
    }
}
def DoHaveWater(def nowater){
    waterskin = inventory.getItemsContain("Waterskin")
    for (a = 0; a < waterskin.size(); a++){
        info = inventory.getItemMeter(waterskin[a])
        log.info("${info}")
        if (info <= 0){
            nowater.add(waterskin[a])
        }
        if (info != 0) {
            return true
        }
        if (info == 0){
            return false
        }
    }
}

def moveToNoPF(def start, toRandom){
    player.moveToNoPF(start)
    movedist = player.SdistanceBetweenCoords(start)
    while (movedist != 0) {
        player.moveToNoPF(start)
        movedist = player.SdistanceBetweenCoords(start)
        sleep(50)
        stco = player.SdistanceBetweenCoords(start)
        if (movedist == stco) {
            if (toRandom == 150) {
                int multiplier = 650
                player.randommove(multiplier)
                player.ClaywaitUntilStops()
                toRandom = 0
            }
            if (toRandom != 150) {
                toRandom++
            }
        }
        if (movedist == 0) break
    }
}
def fillandback(def start, nowater, toRandom) {
    moveToNoPF(start, toRandom)
    if (!nowater.isEmpty()) {
        for (w = 0; w < nowater.size(); w++) {
            wask = inventory.takeItem(nowater[w])
            def CoursItem = inventory.getHoldingItem().isPresent()
            if (!CoursItem) {
                log.info("item isn't in hands")
                while (!inventory.getHoldingItem().isPresent()) {
                    wask = inventory.takeItem(nowater[w])
                    CoursItem = inventory.getHoldingItem().isPresent()
                    if (CoursItem) break
                    sleep(25)
                }
            }
            if (CoursItem) {
                log.info("item in hands")
                bar = player.findObjects("barrel")
                full = []
                notempty = []
                for (n = 0; n < bar.size(); n++) {
                    if (player.isOverlay(bar[n], "water")) {
                        notempty.add(bar[n])
                    }
                }
                if (notempty.isEmpty()){
                    inventory.putToInventory()
                    if (inventory.getHoldingItem().isPresent()) {
                        while (inventory.getHoldingItem().isPresent()) {
                            inventory.putToInventory()
                            if (!inventory.getHoldingItem().isPresent()) break
                        }
                    }
                }
                if (!notempty.isEmpty()) {
                    for (m = 0; m < notempty.size(); m++) {
                        player.useOn(notempty[m])
                        player.beforerunclay()
                        player.ClaywaitUntilStops()
                        //sleep(2000)
                        inventory.putToInventory()
                        if (inventory.getHoldingItem().isPresent()) {
                            while (inventory.getHoldingItem().isPresent()) {
                                inventory.putToInventory()
                                if (!inventory.getHoldingItem().isPresent()) break
                            }
                        }
                        w = DoHaveWater(nowater)
                        if (w == true) {
                            moveToNoPF(start, toRandom)
                            break
                        }
                    }
                }
            }
            w = DoHaveWater(nowater)
            if (w == true) break
            if (w == false){
                break
            }
        }
        //sleep(500)
    }
}
def getenergy(){
    return player.getEnergyMeter()
}
def u
while (true) {
    def a = player.findAllTrees()
    def b = player.findAllLogs()
    def c = player.findAllBushes()
    def d = player.findAllStumps()
    id = []
    nearest = getnearestfromFour(a, b, c, d)
    if (u == "nowater") {
        player.discordSay("Lumberjack bot have no water, bring barrels and rerun me!.")
        player.unsafeLogout()
        break
    }
    if (u == "nofood") {
        player.discordSay("Lumberjack bot have no food, bring roast meat and rerun me!.")
        player.unsafeLogout()
        break
    }
    if (nearest == null) {
        player.SystemMessage("NO CHOPPABLE FOUND")
        break
    }

    if (nearest != null) {
        if (GetResName(nearest).contains("bushes")) {
            u = Chop(start, nearest, toRandom)
        }
        if (!GetResName(nearest).contains("log") && GetResName(nearest).contains("gfx/terobjs/trees/") && !GetResName(nearest).contains("stump") && !GetResName(nearest).contains("oldstump")) {
            u = Chop(start, nearest, toRandom)
        }
        if (GetResName(nearest).endsWith("log") == true || GetResName(nearest).endsWith("oldtrunk") == true) {
            u = ChopInBlocks(newpos, nearest, toRandom)
        }
        if (GetResName(nearest).contains("stump") || GetResName(nearest).contains("oldstump")) {
            u = Destroy(start, nearest, toRandom)
        }
    }
}
