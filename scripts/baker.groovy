import java.text.SimpleDateFormat

import static java.lang.System.out
import static java.lang.System.out

int branches = 4
def radius = 1.0
def ovens = []
def bake = []
def bake2 = []
def ovenwindow
def branchstockpile = []
int OvensQuantity
def houglass
int Freespace
int saveforovens
int doughleft
int lastoven
def containerswithout
def B = 0
def C = 0
def ABOBA = 0
def empty = 0
def arrayradius = []
def last
def first
def a = 0
def contains = []
def Gek
def Spam = 0
def toRandom
def containercoords = [[0,9]]
def unstuck(def coords, def stuckCoords, def toRandom){
    if (coords == stuckCoords) {
        if (toRandom == null){
            toRandom = 0
        }
        if (toRandom == 50) {
            int multiplier = 100
            player.randommove(multiplier)
            player.waitUntilStops()
            toRandom = 0
        }
        if (toRandom != 50) {
            toRandom++
        }
    }
}

Date date = new Date()
def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("animalLOG.txt")
if (yourFile == null) {
    yourFile.createNewFile()
}
yourFile << "\n${sdf.format(date)} - STARTED"

    def start = player.getPlayerCoords()
    def next = start.add(0.0, 0.0)
player.moveToNoPF(next)
movedist = player.SdistanceBetweenCoords(next)
while (movedist > 1){
    player.moveToNoPF(next)
    movedist = player.SdistanceBetweenCoords(next)
    sleep(50)
    if (movedist < 1) break
}
kek = player.waitForNewWindowTest("Inventory")
ovens = player.findObjects("oven")
container = player.findObjects("metalcabinet")
branchstockpile = player.findObjects("stockpile-branch")
def playercoords = player.getPlayerCoords()
containercoordes = playercoords.add(-0.2, -94.0)

    for (l = 0; l < container.size(); l++) {
        bake = player.GetItem("Dough")
        bake2 = player.GetItem("Unbaked")
        bake = bake + bake2
        if (ABOBA == 1 && Spam == 0){
            player.discordSay("There are dough only for 1 oven, more ovens need to be filled.")
            Spam = 1
        }
        if (ABOBA != 1) {
            if (a != 0) break
            for (c = 0; c < container.size; c++) {
                distance = player.distanceTo(container[c])
                if (distance > 110) continue
                if (a == 0) {
                    first = container[c]
                }
                if (contains[c] == false) continue
                if (container[c] == first) {
                    if (!arrayradius.contains(container[c])) {
                        arrayradius.add(container[c])
                    }
                }
                player.moveToNoPF(containercoordes)
                movedist = player.SdistanceBetweenCoords(containercoordes)
                while (movedist > 1) {
                    player.moveToNoPF(containercoordes)
                    movedist = player.SdistanceBetweenCoords(containercoordes)
                    sleep(50)
                    if (movedist < 1) break
                }
                player.RightClickGob(container[c])
                log.info("${container[c]}")
                containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                containertaken = player.GetItemWindow(containerwindow, "Dough")
                containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                containertaken = containertaken + containertaken1
                Freespace = inventory.getFreeSpace()
                if (contains.size() != container[c]) {
                    if (containertaken.size() == 0 && distance < 110) {
                        o = false
                        contains.add(o)
                        log.info("${container[c]} empty")
                        log.info("${contains}")
                    }
                    if (containertaken.size() != 0 && distance < 110){
                        d = true
                        if (contains.contains(true)){
                            contains.remove(true)
                        }
                        contains.add(d)
                        log.info("${container[c]} not empty")
                        log.info("${contains}")
                    }
                }
                if (containertaken.size != 0) {
                    if (Freespace != 0) {
                        for (k = 0; k < containertaken.size(); k++) {
                            Freespace = inventory.getFreeSpace()
                            if (Freespace != 0) {
                                bake = player.GetItem("Dough")
                                bake2 = player.GetItem("Unbaked")
                                bake = bake + bake2
                                if (containertaken[k] != null && containertaken.size() != 0) {
                                    containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                                    containertaken = player.GetItemWindow(containerwindow, "Dough")
                                    containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                                    containertaken = containertaken + containertaken1
                                    if (containertaken.size() == 0) {
                                        continue
                                    }
                                    Freespace = inventory.getFreeSpace()
                                    if (containertaken.size() != 0 && Freespace != 0 && containertaken[k] != null) {
                                        inventory.transferNewFast(containertaken[k])
                                    }
                                    if (containertaken.size() != 0 && Freespace != 0 && containertaken1[k] != null) {
                                        inventory.transferNewFast(containertaken1[k])
                                    }
                                    Gek = 0
                                }
                                containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                                containertaken = player.GetItemWindow(containerwindow, "Dough")
                                containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                                containertaken = containertaken + containertaken1
                                containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                                containertaken = player.GetItemWindow(containerwindow, "Dough")
                                containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                                containertaken = containertaken + containertaken1
                                if (contains.size() != container[c]) {
                                    if (containertaken.size() == 0 && distance < 110) {
                                        o = false
                                        if (contains[c] == true) {
                                            contains.remove(true)
                                        }
                                        contains.add(o)
                                        log.info("${container[c]} empty")
                                        log.info("${contains}")
                                    }
                                    if (containertaken.size() != 0) {
                                        d = true
                                        if (contains[c] != true) {
                                            contains.add(d)
                                            log.info("${container[c]} not empty")
                                        }
                                        log.info("${contains}")
                                    }
                                }
                            }
                        }
                        Freespace = inventory.getFreeSpace()
                        if (Freespace == 0) {
                            break
                        }
                    }
                }
                player.moveToNoPF(containercoordes)
                movedist = player.SdistanceBetweenCoords(containercoordes)
                while (movedist > 1) {
                    player.moveToNoPF(containercoordes)
                    movedist = player.SdistanceBetweenCoords(containercoordes)
                    sleep(50)
                    if (movedist < 1) break
                }
            }
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist > 1) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist < 1) break
            }
            if (saveforovens == null) {
                saveforovens = 0
            }
            if (saveforovens != ovens.size()) {
                for (int n = saveforovens; n < ovens.size(); n++) {
                    kek = player.waitForNewWindowTest("Inventory")
                    bake = player.GetItem("Dough")
                    bake2 = player.GetItem("Unbaked")
                    bake = bake + bake2
                    if (bake.size() == 0) break
                    log.info("${bake.size()}")
                    absolute = player.distanceTo(ovens[n])
                    player.RightClickGobNoWait(ovens[n])
                    while (absolute > 33){
                        def absolute = player.distanceTo(ovens[n])
                        if (absolute < 33) break
                        player.RightClickGobNoWait(ovens[n])
                        sleep(100)
                        def stuckCoords = player.distanceTo(ovens[n])
                        if (absolute == stuckCoords) {
                            if (toRandom == null){
                                toRandom = 0
                            }
                            if (toRandom == 50) {
                                int multiplier = 100
                                player.randommove(multiplier)
                                player.waitUntilStops()
                                toRandom = 0
                            }
                            if (toRandom != 50) {
                                toRandom++
                            }
                        }
                        if (absolute < 33) break
                    }
                    player.RightClickGob(ovens[n])
                    ovenwindow = player.waitForNewWindowTest("Oven")
                    for (int i = 0; i < bake.size(); i++) {
                        bake = player.GetItem("Dough")
                        bake2 = player.GetItem("Unbaked")
                        bake = bake + bake2
                        ovenslotstaken = player.GetItemWindow(ovenwindow, "Dough")
                        ovenslotstaken1 = player.GetItemWindow(ovenwindow, "Unbaked")
                        ovenslotstaken = ovenslotstaken + ovenslotstaken1
                        if (ovenslotstaken.size() != 8) {
                            if (bake[i] != null && ovenslotstaken != 8) {
                                inventory.transferNew(bake[i])
                            }
                        }
                        bake = player.GetItem("Dough")
                        bake2 = player.GetItem("Unbaked")
                        bake = bake + bake2
                        ovenslotstaken = player.GetItemWindow(ovenwindow, "Dough")
                        ovenslotstaken1 = player.GetItemWindow(ovenwindow, "Unbaked")
                        ovenslotstaken = ovenslotstaken + ovenslotstaken1
                        log.info("${ovenslotstaken.size()} Dough inside oven.")
                        if (ovenslotstaken.size() == 8 || bake.size() == 0) {
                            log.info("Filled")

                            saveforovens = n
                            if (Gek != 1 && ovenslotstaken.size() == 8) {
                                Gek = 0
                                log.info("Last oven filled")
                                bake = player.GetItem("Dough")
                                bake2 = player.GetItem("Unbaked")
                                bake = bake + bake2
                                if (bake.size() != 0) {
                                    doughleft = bake.size()
                                }
                            }
                            if (bake.size() == 0 && ovenslotstaken.size() != 8 || n != ovens.size() && bake.size() == 0 && ovenslotstaken.size() == 8) {
                                log.info("Dough end gonna take dough from container if we have")
                                bake = player.GetItem("Dough")
                                bake2 = player.GetItem("Unbaked")
                                bake = bake + bake2
                                Gek = 1
                            }
                            break
                        }
                    }
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    while (movedist > 1) {
                        player.moveToNoPF(next)
                        movedist = player.SdistanceBetweenCoords(next)
                        sleep(50)
                        if (movedist < 1) break
                    }
                    if (OvensQuantity == 0 && bake.size() == 0) {
                        ABOBA = 1
                    }
                        OvensQuantity++
                    if (Gek == 1){
                        OvensQuantity = OvensQuantity - 1
                    }
                    if (Gek == 1 && ovenslotstaken.size() != 8){
                        n = n - 1
                        log.info(n)
                    }
                }
            }
            if (saveforovens == ovens.size() && Gek == 0) break
            bake = player.GetItem("Dough")
            bake2 = player.GetItem("Unbaked")
            bake = bake + bake2
            if (bake.size() != 0) {
                while (bake.size() != 0) {
                    for (c = 0; c < container.size(); c++) {
                        bake = player.GetItem("Dough")
                        bake2 = player.GetItem("Unbaked")
                        bake = bake + bake2
                        distance = player.distanceTo(container[c])
                        if (distance > 110) continue
                        player.moveToNoPF(containercoordes)
                        movedist = player.SdistanceBetweenCoords(containercoordes)
                        while (movedist > 1) {
                            player.moveToNoPF(containercoordes)
                            movedist = player.SdistanceBetweenCoords(containercoordes)
                            sleep(50)
                            if (movedist < 1) break
                        }
                        player.RightClickGob(container[c])
                        containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                        containertaken = player.GetItemWindow(containerwindow, "Dough")
                        containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                        containertaken = containertaken + containertaken1
                        for (k = 0; k < bake.size(); k++) {
                            bake = player.GetItem("Dough")
                            bake2 = player.GetItem("Unbaked")
                            bake = bake + bake2
                            if (bake[k] != null && container.size() != 42) {
                                containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                                containertaken = player.GetItemWindow(containerwindow, "Dough")
                                containertaken1 = player.GetItemWindow(containerwindow, "Unbaked")
                                containertaken = containertaken + containertaken1
                                allspace = player.getItems(containerwindow)
                                if (containertaken.size() != 42 && bake[k] != null && allspace.size() != 42) {
                                    inventory.transferNew(bake[k])
                                }
                                if (containertaken.size() == 42) break
                            }
                            if (containertaken.size() == 0){
                                o = false
                                contains.add(o)
                            }
                            if (containertaken.size() != 0){
                                d = true
                                contains.add(d)
                            }
                        }
                    }
                    bake = player.GetItem("Dough")
                    bake2 = player.GetItem("Unbaked")
                    bake = bake + bake2
                    B = 1
                    if (bake.size() == 0) break
                }
            }
        }

        if (B == 1)break
    }
    if (OvensQuantity != 0) {
        player.moveToNoPF(next)
        movedist = player.SdistanceBetweenCoords(next)
        while (movedist > 1) {
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            sleep(50)
            if (movedist < 1) break
        }
        if (branchstockpile != null) {
            branchstockpile = player.findObjects("stockpile-branch")
            branch = inventory.getItems("Branch")
            Freespace = inventory.getFreeSpace()
            if (Freespace != 0) {
                for (s = 0; s < branchstockpile.size(); s++) {
                    stockdist = player.distanceTo(branchstockpile[s])
                    if (stockdist < 110) {
                        player.moveToNoPF(next)
                        movedist = player.SdistanceBetweenCoords(next)
                        while (movedist > 1) {
                            player.moveToNoPF(next)
                            movedist = player.SdistanceBetweenCoords(next)
                            sleep(50)
                            if (movedist < 1) break
                        }
                        player.RightClickGob(branchstockpile[s])
                        stockpilewindow = player.waitForNewWindowTest("Stockpile")
                        branch = inventory.getItems("Branch")
                        player.Click(branchstockpile[s], 3, 1)
                        while (branch == null) {
                            branch = inventory.getItems("Branch")
                            if (branch != null) break
                        }
                        Freespace = inventory.getFreeSpace()
                        if (Freespace == 0) break
                    }
                }
            }
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist > 1) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist < 1) break
            }
            log.info("Ovens to be fueled: $OvensQuantity")
        }
        for (int n = 0; n < OvensQuantity; n++) {
            branch = inventory.getItems("Branch")
            Freespace = inventory.getFreeSpace()
            if (branch.size() == 0) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist > 1) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist < 1) break
                }
                branchstockpile = player.findObjects("stockpile-branch")
                for (s = 0; s < branchstockpile.size(); s++) {
                    stockdist = player.distanceTo(branchstockpile[s])
                    if (stockdist < 110) {
                        Freespace = inventory.getFreeSpace()
                        if (Freespace != 0) {
                            player.moveToNoPF(next)
                            movedist = player.SdistanceBetweenCoords(next)
                            while (movedist > 1) {
                                player.moveToNoPF(next)
                                movedist = player.SdistanceBetweenCoords(next)
                                sleep(50)
                                if (movedist < 1) break
                            }
                            player.RightClickGob(branchstockpile[s])
                            stockpilewindow = player.waitForNewWindowTest("Stockpile")
                            branch = inventory.getItems("Branch")
                            player.Click(branchstockpile[s], 3, 1)
                            while (branch == null) {
                                branch = inventory.getItems("Branch")
                                if (branch != null) break
                            }
                        }
                    }
                }
            }
            if (ovens[n] != null) {
                branch = inventory.getItems("Branch")
                Freespace = inventory.getFreeSpace()
                if (branch.size() < 4){
                    branchstockpile = player.findObjects("stockpile-branch")
                    for (s = 0; s < branchstockpile.size(); s++) {
                        stockdist = player.distanceTo(branchstockpile[s])
                        if (stockdist < 110) {
                            Freespace = inventory.getFreeSpace()
                            if (Freespace != 0) {
                                player.RightClickGob(branchstockpile[s])
                                stockpilewindow = player.waitForNewWindowTest("Stockpile")
                                branch = inventory.getItems("Branch")
                                player.Click(branchstockpile[s], 3, 1)
                                Freespace = inventory.getFreeSpace()
                                branch = inventory.getItems("Branch")
                                if (branch.size() >= 4) break
                            }
                        }
                    }
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    while (movedist > 1) {
                        player.moveToNoPF(next)
                        movedist = player.SdistanceBetweenCoords(next)
                        sleep(50)
                        if (movedist < 1) break
                    }
                }
                branch = inventory.getItems("Branch")
                if (branch.size() >= 4) {
                    absolute = player.distanceTo(ovens[n])
                    player.RightClickGobNoWait(ovens[n])
                    while (absolute > 33){
                        def absolute = player.distanceTo(ovens[n])
                        if (absolute < 33) break
                        player.RightClickGobNoWait(ovens[n])
                        sleep(100)
                        def stuckCoords = player.distanceTo(ovens[n])
                        if (absolute == stuckCoords) {
                            if (toRandom == null){
                                toRandom = 0
                            }
                            if (toRandom == 50) {
                                int multiplier = 100
                                player.randommove(multiplier)
                                player.waitUntilStops()
                                toRandom = 0
                            }
                            if (toRandom != 50) {
                                toRandom++
                            }
                        }
                        if (absolute < 33) break
                    }
                    player.RightClickGob(ovens[n])
                    branchWas = inventory.getItems("Branch")
                    for (int b = 0; b < 4; b++) {
                        branch = inventory.getItems("Branch")
                        branch = inventory.getItems("Branch")
                        branchRn = inventory.getItems("Branch")
                        inventory.takeItem(branch[0])
                        player.useOn(ovens[n])
                        sleep(500)
                        branchRn = inventory.getItems("Branch")
                        log.info("branch was: ${branchWas.size()}, branch rn: ${branchRn.size()}.")
                    }
                }
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                while (movedist > 1) {
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    sleep(50)
                    if (movedist < 1) break
                }
            }
        }



        branch = inventory.getItems("Branch")
        if (branch.size() != 0) {
            inventory.takeItem(branch[0])
            player.makePile()
            sleep(50)
            player.placeThing(11, 0)
            sleep(200)
            occupied = player.isOccupied()
                branch = inventory.getItems("Branch")
                inventory.takeItem(branch[0])
                def CoursItem = inventory.getHoldingItem().isPresent()
                if (CoursItem) {
                    branchstockpile = player.findObjects("stockpile-branch")
                    for (b = 0; b < branchstockpile.size(); b++){
                        stockdist = player.distanceTo(branchstockpile[b])
                        if (stockdist < 22) {
                            CoursItem = inventory.getHoldingItem().isPresent()
                            if (CoursItem) {
                                player.useOnEverything(branchstockpile[b])
                            }
                            if (!CoursItem){
                                branch = inventory.getItems("Branch")
                                inventory.takeItem(branch[0])
                                player.useOnEverything(branchstockpile[b])
                            }
                            branch = inventory.getItems("Branch")
                            if (branch.size() == 0 && !CoursItem) break
                        }
                    }
            }
        }


        torchpost = player.findObjects("torchpost")
        player.RightClickGob(torchpost[0])
        sleep(200)
        brazier = player.findObjects("brazier")
        player.useOn(brazier[0])
        sleep(200)
        houglass = player.getHourglass()
        if (houglass == -1.0) {
            while (houglass == -1.0) {
                houglass = player.getHourglass()
                if (houglass != -1.0) break
            }
        }
        if (houglass != -1.0) {
            while (houglass == 0 || houglass != 1.00) {
                houglass = player.getHourglass();
                log.info("${houglass} Hourglass progress");
                sleep(200);
                if (houglass == -1.0) {
                    out.println("${houglass} +  Hourglass progress ended");
                    break
                }
            }
        }
        player.moveToNoPF(next)
        movedist = player.SdistanceBetweenCoords(next)
        while (movedist > 1) {
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            sleep(50)
            if (movedist < 1) break
        }

        for (int n = 0; n < OvensQuantity; n++) {
            if (ovens[n] != null) {
                player.useOn(ovens[n])
                houglass = player.getHourglass()
                if (houglass == -1.0) {
                    while (houglass == -1.0) {
                        houglass = player.getHourglass()
                        if (houglass != -1.0) break
                    }
                }
                if (houglass != -1.0) {
                    while (houglass == 0 || houglass != 1.00) {
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
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist > 1) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist < 1) break
            }
        }


        torchpost = player.findObjects("torchpost")
        inventory.putToInventory()
        torchek = player.GetItem("Torch")
        inventory.takeItem(torchek[0])
        sleep(500)
        player.useOn(torchpost[0])
        player.waitUntilStops()
        player.moveToNoPF(next)
        movedist = player.SdistanceBetweenCoords(next)
        while (movedist > 1) {
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            sleep(50)
            if (movedist < 1) break
        }
        player.discordSay("Ovens are used: ${OvensQuantity}")
        sleep(1200000) // 20 минут

        for (int n = 0; n < OvensQuantity; n++) {
            lastoven = n
            if (ovens[n] != null){
                player.Click(ovens[n], 3, 1)
                player.RightClickGobNoWait()
                containerwindow = player.waitForNewWindowTest("Oven")
                allspace = player.getItems(containerwindow)
            Freespace = inventory.getFreeSpace()
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist > 1) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist < 1) break
            }
            Freespace = inventory.getFreeSpace()
            if (Freespace == 0) {
                C = 1
                for (c = 0; c < container.size; c++) {
                    distance = player.distanceTo(container[c])
                    if (distance > 110) continue
                    player.moveToNoPF(containercoordes)
                    movedist = player.SdistanceBetweenCoords(containercoordes)
                    while (movedist > 1) {
                        player.moveToNoPF(containercoordes)
                        movedist = player.SdistanceBetweenCoords(containercoordes)
                        sleep(50)
                        if (movedist < 1) break
                    }
                    player.RightClickGob(container[c])
                    bakeswindow = player.waitForNewWindowTest("Inventory")
                    bakes = inventory.getItems(bakeswindow)
                    for (k = 0; k < 1; k++) {
                        bakeswindow = player.waitForNewWindowTest("Inventory")
                        bakes = inventory.getItems(bakeswindow)
                        if (bakes[k] != null && container.size() != 42 && bakes.size() != 0) {
                            bakeswindow = player.waitForNewWindowTest("Inventory")
                            bakes = inventory.getItems(bakeswindow)
                            containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                            bakescont = inventory.getItems(containerwindow)
                            if (bakescont.size() != 42 && bakes[k] != null) {
                                inventory.transferNewFast(bakes[k])
                            }
                        }
                    }
                }
            }

            sleep(200);
            player.moveToNoPF(next)
            movedist = player.SdistanceBetweenCoords(next)
            while (movedist > 1) {
                player.moveToNoPF(next)
                movedist = player.SdistanceBetweenCoords(next)
                sleep(50)
                if (movedist < 1) break
            }
            if (C == 1) {
                if (lastoven != 0) {
                    player.Click(ovens[lastoven], 3, 1)
                    C = 0
                    player.moveToNoPF(next)
                    movedist = player.SdistanceBetweenCoords(next)
                    while (movedist > 1) {
                        player.moveToNoPF(next)
                        movedist = player.SdistanceBetweenCoords(next)
                        sleep(50)
                        if (movedist < 1) break
                    }
                }
            }
            bakeswindow = player.waitForNewWindowTest("Inventory")
            bakes = inventory.getItems(bakeswindow)
                Freespace = inventory.getFreeSpace()
            if (bakes.size() != 0 && allspace.size() != 0 || bakes.size() != 0 && allspace.size() != 0 && Freespace == 0 || bakes.size() != 0 && allspace.size() == 0 && Freespace == 0) {
                C = 1
                Freespace = inventory.getFreeSpace()
                    for (c = 0; c < container.size; c++) {
                        distance = player.distanceTo(container[c])
                        if (distance > 110) continue
                        player.moveToNoPF(containercoordes)
                        movedist = player.SdistanceBetweenCoords(containercoordes)
                        while (movedist > 1) {
                            player.moveToNoPF(containercoordes)
                            movedist = player.SdistanceBetweenCoords(containercoordes)
                            sleep(50)
                            if (movedist < 1) break
                        }
                        player.RightClickGob(container[c])
                        bakeswindow = player.waitForNewWindowTest("Inventory")
                        bakes = inventory.getItems(bakeswindow)
                        for (k = 0; k < bakes.size(); k++) {
                            bakeswindow = player.waitForNewWindowTest("Inventory")
                            bakes = inventory.getItems(bakeswindow)
                            if (bakes[k] != null && container.size() != 42 && bakes.size() != 0) {
                                bakeswindow = player.waitForNewWindowTest("Inventory")
                                bakes = inventory.getItems(bakeswindow)
                                containerwindow = player.waitForNewWindowTest("Metal Cabinet")
                                bakescont = inventory.getItems(containerwindow)
                                if (bakescont.size() != 42 && bakes[k] != null) {
                                    inventory.transferNew(bakes[k])
                                }
                            }
                        }
                    }
            }
        }
            }

            player.discordSay("Your PepePopo products are ready!")
    }
    mypos = player.getPlayerCoords()
    player.moveToNoPF(next)
    movedist = player.SdistanceBetweenCoords(next)
    while (movedist > 1) {
        player.moveToNoPF(next)
        movedist = player.SdistanceBetweenCoords(next)
        sleep(50)
        if (movedist < 1) break
    }



