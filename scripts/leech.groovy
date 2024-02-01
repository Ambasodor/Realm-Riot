import java.text.SimpleDateFormat;
import haven.*;

Date date = new Date()

def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("ForageLog.txt")
if (yourFile == null) {
    yourFile.createNewFile()
}
yourFile << "\n${sdf.format(date)} - STARTED"

def Bodywindow
def bodyleechers = []
def players = []
def chests0 = []
def storage0 = ["gfx/terobjs/crate"]
def storage1 = ["gfx/terobjs/chest"]

def start = player.getPlayerCoords()
def next = start.add(0.0, 0.0)
def newpos = next
int cratespace = 15

def DropLeech(){
    leach = inventory.getItems("Leech")
    if (leach != null) {
        for (n = 0; n < leach.size; n++) {
            player.dropItemFromInventory(leach[n])
            sleep(20)
        }
    }
}
def DropLeechCrate(){
    leach = inventory.getItems("Leech")
    if (leach != null) {
        for (n = 0; n < leach.size; n++) {
            player.dropItemFromInventory(leach[n])
            sleep(20)
        }
    }
}

players = player.findObjects("gfx/borka/body")
if (players == null){
    log.info("Bro there's no bodies")
}
if (players != null) {
    for (n = 0; n < players.size(); n++) {
        dist = player.distanceTo(players[n])
        if (dist > 11 && dist < 15) {
            player.RightClickGob(players[n])
            player.FlowerMenuClick("Take")
            Bodywindow = player.waitForNewWindowTest("???'s equipment")
            if (Bodywindow == null) {
                while (Bodywindow == null) {
                    player.RightClickGob(players)
                    player.FlowerMenuClick("Take")
                    Bodywindow = player.waitForNewWindowTest("???'s equipment")
                    if (Bodywindow != null) break
                }
            }
            if (Bodywindow != null) break
        }
    }
}

Bodywindow = player.waitForNewWindowTest("???'s equipment")
bodyleechers = player.getEquipmentItems(Bodywindow, "Bloated Leech")
int Freespace = inventory.getFreeSpace()
if (Freespace == null){
    Freespace = inventory.getFreeSpace()
}
log.info("${bodyleechers}")
leach = inventory.getItems("Leech")
if (leach.size() != 0){
    DropLeech()
}
if (bodyleechers != null && bodyleechers.size() > 0) {
    log.info("${bodyleechers.size()}")
    for (int b = 0; b != bodyleechers.size(); b++) {
        log.info("I have free space: ${Freespace}")
        player.swapnek(bodyleechers[b])
        sleep(50)
        Freespace = inventory.getFreeSpace()
        log.info("Picking up leech. " + b)
        if (bodyleechers.size() == 0) break
        if (Freespace == 0) {
            log.info("I have free space: ${Freespace}")
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
    sleep(500)
    chests0 = player.findObjects(storage0)
    def lee = inventory.getItems("Bloated Leech")
    if (lee.size() != 0) {
        for (int n = 0; n < chests0.size(); n++) {
            lee = inventory.getItems("Bloated Leech")
            if (lee == null) {
                lee = inventory.getItems("Bloated Leech")
            }
            leach = inventory.getItems("Leech")
            if (leach.size() != 0){
                DropLeech()
            }
            log.info("Lechees in inventory: ${lee.size()}")
            player.RightClickGob(chests0[n])
            cratewindow = player.waitForNewWindowTest("Crate")
            Cpblee = inventory.getItems(cratewindow, "Bloated Leech")
            CpblowLee = inventory.getItems(cratewindow, "Leech")
            if (CpblowLee.size() != 0){
                DropLeechCrate()
            }
            log.info("${Cpblee.size()} Bloated Leech inside crate.")
            if (Cpblee.size() == 15) {
                log.info("${Cpblee.size()}/${cratespace} - full crate.")
                continue
            }
            if (lee.size() == 0) break
            Cpblee = inventory.getItems(cratewindow, "Bloated Leech")
            if (Cpblee.size() == 0) {
                sleep(200)
                cratewindow = player.waitForNewWindowTest("Crate")
                Cpblee = inventory.getItems(cratewindow, "Bloated Leech")
                if (Cpblee.size() == 15) {
                    log.info("${Cpblee.size()}/${cratespace} - full crate.")
                    continue
                }
            }
            cratewindow = player.waitForNewWindowTest("Crate")
            CpblowLee = inventory.getItems(cratewindow, "Leech")
            if (CpblowLee.size() != 0){
                DropLeechCrate()
            }
            lee = inventory.getItems("Bloated Leech")
            log.info("${Cpblee.size()}/${cratespace} before.")
            inventory.transferNew(lee[0])
            sleep(200)
            lee = inventory.getItems("Bloated Leech")
            Cpblee = inventory.getItems(cratewindow, "Bloated Leech")
            log.info("${Cpblee.size()}/${cratespace} after.")
            if (lee.size() == 0) break
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