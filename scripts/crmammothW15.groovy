@groovy.transform.Canonical
class Account {
    String login = ""
    String password = ""
    String character = ""
}

Account[] accounts = [
        ["X", "X","MammothBot"],
]
@groovy.transform.Canonical
class Image {
    String image = ""
}
def ignorelist = []
agg = false
once = 0
ignorelistp = []

animals = ["mammoth"] //живность которую будем искать, можно через запятую после ковычек
goby = []
def names = ["A","B"]
road = ["gfx/terobjs/road/milestone-stone-e"] // ресурс кросса
def rrd = player.findObjects(road) //ищет кросс
def Wcpb
start = player.getPlayerCoords() // стартовые коорды чара
next = start.add(0.0, 0.0) // они теперь 0.0
newpose = next
int m = 0
    p = 0
l = 0
agg = false
    hf = player.findObjects("pow")

    for (m = 0; m < hf.size();m++){
        di = player.distanceTo(hf[m])
        if (di == 0) {
            player.discordSay("TURNING OFF CLIENT CUZ AT HF!")
            player.closeGameWindow()
            break
        }
    }

    for (s = 0; s < names.size(); s++) {
        for (a = 0; a < rrd.size(); a++) {
            if (Wcpb != null) {
                player.closewindow(Wcpb)
                sleep(200)
            }
            aggroedb = isAggroed()
            if (aggroedb == true) {
                player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                player.closeGameWindow()
            }
            player.RightClickGobnomove(rrd[a])
            sleep(1000)
            Wcpb = player.waitForNewWindowTest("Milestone") // ждёт окно кросса
            label = player.getWindowLabels(Wcpb)
            if (!label[0].contains(names[s])){
                player.closewindow(Wcpb)
                continue
            }
            label = player.getWindowLabels(Wcpb)
            if (label[0].contains(names[s])){
                if (label[0].contains("B")){
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    start = player.getPlayerCoords() // стартовые коорды чара
                    next = start.add(0.0, 0.0) // они теперь 0.0
                    newpos = next
                    newpos = newpos.add(0, 22)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                }
                if (label[0].contains("C")){
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    start = player.getPlayerCoords() // стартовые коорды чара
                    next = start.add(0.0, 0.0) // они теперь 0.0
                    newpos = next
                    newpos = newpos.add(0, 22)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                }
                if (label[0].contains("G")){
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    start = player.getPlayerCoords() // стартовые коорды чара
                    next = start.add(0.0, 0.0) // они теперь 0.0
                    newpos = next
                    newpos = newpos.add(-11, 0)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                    newpos = newpos.add(0, 22)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                    newpos = newpos.add(-55, 0)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                }
                if (label[0].contains("H")){
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    start = player.getPlayerCoords() // стартовые коорды чара
                    next = start.add(0.0, 0.0) // они теперь 0.0
                    newpos = next
                    newpos = newpos.add(22, 0)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                }
                if (label[0].contains("I")){
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    start = player.getPlayerCoords() // стартовые коорды чара
                    next = start.add(0.0, 0.0) // они теперь 0.0
                    newpos = next
                    newpos = newpos.add(22, 0)
                    player.moveToNoPF(newpos)
                    movedist = player.SdistanceBetweenCoords(newpos)
                    while (movedist != 0) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.moveToNoPF(newpos)
                        movedist = player.SdistanceBetweenCoords(newpos)
                        sleep(50)
                        if (movedist == 0) {
                            player.RightClickGobnomove(rrd[a])
                            break
                        }
                    }
                }
                Wcpb = player.waitForNewWindowTest("Milestone") // ждёт окно кросса
                def buttons = player.getWindowButtons(Wcpb, "Travel") // получает кнопки Travel
                log.info("${buttons}") // показывает кнопки с трэвелом
                for (int b = 0; b < buttons.size(); b++) {
                    log.info("${b}")
                    player.ButtonMenuClick("Milestone", "Travel", b)
                    Hourglass = player.getHourglass()
                    if (Hourglass == -1) {
                        while (Hourglass == -1){
                            aggroedb = isAggroed()
                            if (aggroedb == true) {
                                player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                                player.closeGameWindow()
                            }
                            Hourglass = player.getHourglass()
                            if (Hourglass != -1) break
                        }
                    }
                    Hourglass = player.getHourglass()
                    if (Hourglass != -1){
                        while (Hourglass != -1) {
                            aggroedb = isAggroed()
                            if (aggroedb == true) {
                                player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                                player.closeGameWindow()
                            }
                            Hourglass = player.getHourglass()
                            if (Hourglass == -1) break
                        }
                    }
                    sleep(2000)
                    aggroedb = isAggroed()
                    if (aggroedb == true) {
                        player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                        player.closeGameWindow()
                    }
                    findAnimals(accounts[0].character, label[l], ignorelist) // чекает живность
                    ////////
                    labelcheck = label[l]
                    findpersons(labelcheck)
                    ////////
                    player.cancel() // отменяем прыжок пкмом
                    sleep(600)
                    if (rrd[a] != null) {
                        player.RightClickGobnomove(rrd[a]) // Кликаем по кроссу
                    }
                    if (rrd[a] == null) {
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        log.info("gob[null], waiting 5 sec and trying to click gob again")
                        sleep(5000)
                        aggroedb = isAggroed()
                        if (aggroedb == true) {
                            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
                            player.closeGameWindow()
                        }
                        player.RightClickGobnomove(rrd[a])
                    }
                    p++
                    l++
                }
                l = 0
                player.closewindow(Wcpb)
                break
            }
        }
    }
    start = player.getPlayerCoords() // стартовые коорды чара
/*
    next = start.add(0.0, 0.0) // они теперь 0.0
    newpos = next
    newposa = newpos.add(-55, 0)
    player.moveToNoPF(newposa)
    movedist = player.SdistanceBetweenCoords(newposa)
    while (movedist != 0) {
        player.moveToNoPF(newposa)
        movedist = player.SdistanceBetweenCoords(newposa)
        sleep(50)
        if (movedist == 0) break
    }
    newposa = newposa.add(-22, 0)
    player.moveToNoPF(newposa)
    movedist = player.SdistanceBetweenCoords(newposa)
    while (movedist != 0) {
        player.moveToNoPF(newposa)
        movedist = player.SdistanceBetweenCoords(newposa)
        sleep(50)
        if (movedist == 0) break
    }

 */
    player.moveToNoPF(newpose)
    movedist = player.SdistanceBetweenCoords(newpose)
    while (movedist != 0) {
        p = isAggroed()
        if (p == true) {
            player.discordSay("TURNING OFF CLIENT CUZ AGGROED!")
            player.closeGameWindow()
        }
        player.moveToNoPF(newpose)
        movedist = player.SdistanceBetweenCoords(newpose)
        sleep(50)
        if (movedist == 0) break
    }


def findAnimals(def character, def label, def ignorelist) {
    def found = false
    animals.each {
        animal ->
            {
                found = found || findAnimal(animal, character, label, ignorelist)
            }
    }
    return found;
}

def getgobid(gob){
    return player.gobtoId(gob)
}

def findpersons(label){
    playerlist = player.FindAnotherPlayers()
    if (!playerlist.isEmpty()){
        for (n = 0; n < playerlist.size(); n++){
            get = player.DescribePerson(playerlist[n])
            if (!get.isEmpty()) {
                if (once == 0){
                    player.discordSendScreen("<@&1184631814824931338> I FOUND SOMEBODY AT CROSSROAD ${label}","res/saved.png")
                    once++
                }
                player.discordSay("Player have gear: ${get}")
            }
        }
    }
    once = 0
}

def findAnimal(def animalName, def character, def label, def ignorelist) {
    time = player.getServerTime()
    def animals = player.findCountOf(animalName)
    def getobjanimals = player.findObjects(animalName)

    for (b = 0; b < getobjanimals.size(); b++) {
        if (animals != 0 && !ignorelist.contains(getgobid(getobjanimals[b]))) {
            if (b == 0) {
                player.discordSay("<@&1183574168189546546> ${character} found ${animals} ${animalName} - Crossroad: ${label}.")
                player.playAlarmsound()
                player.discordSendScreen("res/saved.png")
            }
            ignorelist.add(player.gobtoId(getobjanimals[b]))
        }
    }


    //---------------------------------------------------------------------------------------- + \Start second check
    if (animals == 0) {
        animals = player.findCountOf(animalName)
        for (b = 0; b < getobjanimals.size(); b++) {
            if (animals != 0 && !ignorelist.contains(getgobid(getobjanimals[b]))) {
                if (b == 0) {
                    player.discordSay("<@&1183574168189546546> ${character} found ${animals} ${animalName} - Crossroad: ${label}.")
                    player.playAlarmsound()
                    player.discordSendScreen("res/saved.png")
                }
                ignorelist.add(player.gobtoId(getobjanimals[b]))
            }
        }
    }
    return animals != 0
    //---------------------------------------------------------------------------------------- + /Stop second check

}

def isAggroed(){
    agg = player.isAggroed();
    name = player.GetAccountName()
    if (agg){
        player.discordSay("${name} - I AGGROED, SCRIPT GONNA BREAK")
        goby = player.getGobbyFight()
        if (player.getGobResName(goby[0]).contains("borka")){
            if (!player.GetGearByFight().isEmpty()) {
                player.discordSay("${name} - It's player! - ${player.GetGearByFight()}")
                player.discordSendScreen("res/saved.png");
                return true
            }
            if (player.GetGearByFight().isEmpty()) {
                player.discordSay("${name} - It's naked player!")
                player.discordSendScreen("res/saved.png");
                return true
            }
        }
        if (player.getGobResName(goby[0]).contains("kritter")){
            player.discordSay("${name} - It's animal! - ${player.GetAnimalByFight(goby[0]).tokenize('/')[2]}")
            player.discordSendScreen("res/saved.png");
            return true
        }
    }
    return false
}
