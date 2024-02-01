@groovy.transform.Canonical
class Account {
    String login = ""
    String password = ""
    String character = ""
}

Account[] accounts = [
        ["X", "X","Angler1Baton"],
]

animals = ["caveangler"] //живность которую будем искать, можно через запятую после ковычек
def ignorelist = []

def names = ["A"]
road = ["gfx/terobjs/road/milestone-stone-e"] // ресурс кросса
def rrd = player.findObjects(road) //ищет кросс
def Wcpb
start = player.getPlayerCoords() // стартовые коорды чара
next = start.add(0.0, 0.0) // они теперь 0.0
newpose = next
int l = 0


    p = 0
    for (s = 0; s < names.size(); s++) {
        for (a = 0; a < rrd.size(); a++) {
            if (Wcpb != null) {
                player.closewindow(Wcpb)
                sleep(200)
            }
            player.RightClickGobnomove(rrd[a])
            sleep(1000)
            Wcpb = player.waitForNewWindowTest("Milestone") // ждёт окно кросса
            def label = player.getWindowLabels(Wcpb)
            if (!label[0].contains(names[s])){
                player.closewindow(Wcpb)
                continue
            }
            label = player.getWindowLabels(Wcpb)
            if (label[0].contains(names[s])){
                Wcpb = player.waitForNewWindowTest("Milestone") // ждёт окно кросса
                def buttons = player.getWindowButtons(Wcpb, "Travel") // получает кнопки Travel
                log.info("${buttons}") // показывает кнопки с трэвелом
                for (int b = 0; b < buttons.size(); b++) {
                    log.info("${b}")
                    player.ButtonMenuClick("Milestone", "Travel", b)
                    Hourglass = player.getHourglass()
                    if (Hourglass == -1) {
                        while (Hourglass == -1){
                            Hourglass = player.getHourglass()
                            if (Hourglass != -1) break
                        }
                    }
                    Hourglass = player.getHourglass()
                    if (Hourglass != -1){
                        while (Hourglass != -1) {
                            Hourglass = player.getHourglass()
                            if (Hourglass == -1) break
                        }
                    }
                    sleep(1500)
                    findAnimals(accounts[0].character, label[l], ignorelist) // чекает живность
                    player.cancel() // отменяем прыжок пкмом
                    sleep(600)
                    if (rrd[a] != null) {
                        player.RightClickGobnomove(rrd[a]) // Кликаем по кроссу
                    }
                    if (rrd[a] == null) {
                        log.info("gob[null], waiting 5 sec and trying to click gob again")
                        sleep(5000)
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
    player.moveToNoPF(newpose)
    movedist = player.SdistanceBetweenCoords(newpose)
    while (movedist != 0) {
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

def findAnimal(def animalName, def character, def label, def ignorelist) {
    time = player.getServerTime()
    def animals = player.findCountOf(animalName)
    def getobjanimals = player.findObjects(animalName)

    for (b = 0; b < getobjanimals.size(); b++) {
        if (animals != 0 && !ignorelist.contains(getgobid(getobjanimals[b]))) {
            if (b == 0) {
                player.discordSay("<@&1183574358782902322> ${character} found ${animals} ${animalName} - Crossroad: ${label}.")
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
                    player.discordSay("<@&1183574358782902322> ${character} found ${animals} ${animalName} - Crossroad: ${label}.")
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
