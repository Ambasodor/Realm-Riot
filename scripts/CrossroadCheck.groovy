
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

Image[] images = [
["https://imgur.com/WpFS3pc"],
["https://imgur.com/4THvZXA"],
["https://imgur.com/Aqsye2d"]
]
	
animals = ["mammoth"] //живность которую будем искать, можно через запятую после ковычек
road = ["gfx/terobjs/road/milestone-stone-e"] // ресурс кросса
while (true) {
	//
    player.login(accounts[0].login, accounts[0].password, accounts[0].character) // логинит игрока
	//
    def start = player.getPlayerCoords() // стартовые коорды чара
    def next = start.add(0.0, 0.0) // они теперь 0.0
    def newpos = next

    def rrd = player.findObjects(road) //ищет кросс
    if (rrd[0] != null) {
        log.info("opening road. ${rrd[0]}")
        for (int n = 0; n < rrd.size(); n++) {
            def now = player.getPlayerCoords()
            def nowW = now.add(0.0, 0.0)
            player.RightClickGobnomove(rrd[n]) // пкм клик по кроссу
            def Wcpb = player.waitForNewWindowTest("Milestone") // ждёт окно кросса
            def buttons = player.getWindowButtons(Wcpb, "Travel") // получает кнопки Travel
            log.info("${buttons}") // показывает кнопки с трэвелом
            for (int b = 0; b < buttons.size(); b++) {
                log.info("${b}")
                player.ButtonMenuClick("Milestone", "Travel", b)
                sleep(8000) // ждёт 8сек
                findAnimals(accounts[0].character, images[b].image) // чекает живность
                player.cancel() // отменяем прыжок пкмом
				sleep(600)
                if (rrd[n] != null) {
                    player.RightClickGobnomove(rrd[n]) // Кликаем по кроссу
                }
                if (rrd[n] == null) {
                    log.info("gob[null], waiting 5 sec and trying to click gob again")
                    sleep(5000)
                    player.RightClickGobnomove(rrd[n])
                }
            }
        }
    }
    if (rrd[0] == null) {
        log.info("No roads")
    }
    player.moveTo(newpos) // возврат после всего
	if (player.moveTo(newpos) == null){
	player.moveTo(newpos) // перепроверка
	}
    log.info("finally")
	//
	player.unsafeLogout() // логофф
	//
return false
}

def findAnimals(def character, def image) {
    def found = false
    animals.each {
        animal ->
            {
                found = found || findAnimal(animal, character, image)
            }
    }
    return found;
}

def findAnimal(def animalName, def character, def image) {
    time = player.getServerTime()
    def animals = player.findCountOf(animalName)

    if (animals != 0) {
        log.info("[In-game time: ${time}] Found ${animals} ${animalName}.")
        player.discordSay("[In-game time: ${time}] ${character} found ${animals} ${animalName} ${image}.")
    }


    //---------------------------------------------------------------------------------------- + \Start second check
    if (animals == 0) {
        animals = player.findCountOf(animalName)

        if (animals != 0) {
            log.info("[In-game time: ${time}] Second check -> ${animals} ${animalName}.")
            player.discordSay("[In-game time: ${time}] ${character} found ${animals} ${animalName} ${image} in second check.")
        }
    }
    return animals != 0
    //---------------------------------------------------------------------------------------- + /Stop second check

}