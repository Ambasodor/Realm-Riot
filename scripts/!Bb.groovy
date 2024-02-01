import java.text.SimpleDateFormat;
import haven.*;

account = "X"
password = "X"
characters = ["X"]
images = ["https://i.imgur.com/Mq4gAKZ.png"]
forages = ["clay-cave", "cavebulb", "glimmermoss", "stalagoom"]
coords = ["10, 10", "10, 10"]

bioms = ["FINDING FORAGE - CAVE", "FINDING FORAGE - MOUNTAIN", "FINDING FORAGE - WATER", "FINDING FORAGE - NORMAL"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|

Date date = new Date()

def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("ForageLog.txt")
if (yourFile == null) {
yourFile.createNewFile()
}
yourFile << "\n${sdf.format(date)} - STARTED"


while (true) {
log.info(bioms[0])
log.info("LOGIN| ${characters[0]}")
//----------------------------
    //player.login(account, password, characters[0])
    findForages(characters[0], images[0])
//----------------------------
    return false
}

def findForages(def character, def image) {
    def start = player.getPlayerCoords()
	sleep(5000)
	
for (int i = 1; i < coords.size(); i++) {
player.moveTo(coords[i])
}
player.moveTo(start)
player.unsafeLogout()
}


def findForage(def forageName, def character, def image) {
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("ForageLog.txt")

    def foragess = player.findCountOf(forageName)

    if (foragess > 0) {
        log.info("Found ${forageName} ${foragess}")
        yourFile << "\n${sdf.format(date)} - ${character} - ${forageName} - ${foragess}"
        player.discordSay("${character} found ${forageName} ${foragess} ${image}")
    }
}




























/*

def findAnimals(def character, def image) {
    sleep(5000)
    animals.each { animal -> findAnimal(animal, character, image) }
    player.unsafeLogout()
}

def findAnimalsNew(def character, def image) {
    sleep(5000)
	animalsNOTwritebl.each { animal -> findAnimalNew(animal, character, image) }
}

def findAnimal(def animalName, def character, def image) {
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("animalLOG.txt")
    def animals = player.findCountOf(animalName)

    if (animals > 0) {
		log.info("----Found ${animals} ${animalName}")
		yourFile << "\n${sdf.format(date)} - ${character} - ${animalName} - ${animals}"
        player.discordSay("${character} found ${animals} ${animalName} ${image}")
    }
}

def findAnimalNew(def animalName, def character, def image) {
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("animalLOG.txt")
    def animalsNOTwritebl = player.findCountOf(animalName)
	
	if (animalsNOTwritebl > 0) {
	    yourFile << "\n${sdf.format(date)} - ${character} - ${animalName} - ${animalsNOTwritebl}"
		log.info("----Found ${animalsNOTwritebl} ${animalName}")
    }
}

*/