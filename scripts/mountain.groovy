import java.text.SimpleDateFormat

account = "X"
password = "X"
characters = ["1SM", "2.1SM", "3SM", "4SM", "5SM"]
images = ["https://imgur.com/6qb28l6", "https://imgur.com/h4T989z", "https://imgur.com/7nJVR2d", "https://imgur.com/hdF9Z9y", "https://imgur.com/fjOSjN0"]
animals = ["gfx/kritter/moose/moose", "gfx/kritter/bear/bear", "gfx/kritter/chasmconch/chasmconch", "gfx/kritter/caveangler/caveangler", "gfx/kritter/reddeer", "gfx/kritter/mammoth/mammoth"]
animalsNOTwritebl = ["gfx/kritter/boar", "gfx/kritter/fox", "gfx/kritter/badger", "gfx/kritter/bat", "gfx/kritter/ants/anthill", "gfx/kritter/lynx", "gfx/kritter/snake", "gfx/kritter/aurochs", "gfx/kritter/mouflon", "gfx/kritter/stoat", "gfx/kritter/adder", "gfx/kritter/beaver", "gfx/kritter/caverat", "gfx/kritter/goldeneagle", "gfx/kritter/otter", "gfx/kritter/reindeer", "gfx/kritter/swan", "gfx/kritter/wildhorse", "gfx/kritter/wolf", "gfx/kritter/wildgoat", "gfx/kritter/cavelouse", "gfx/kritter/rat", "gfx/kritter/rabbit", "gfx/terobjs/birdnest", "gfx/kritter/squirrel/squirrel", "gfx/kritter/mallard", "gfx/kritter/sheep", "gfx/kritter/magpie", "gfx/kritter/silkmoth", "gfx/kritter/wildbees/wildbeehive", "gfx/kritter/goldeneagle", "gfx/kritter/boreworm"]
int b = 0 //DONT_CHANGE_DEBUG
bioms = ["FINDING ANIMALS - CAVE", "FINDING ANIMALS - MOUNTAIN", "FINDING ANIMALS - WATER", "FINDING ANIMALS - NORMAL"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|

Date date = new Date()

def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("animalLOG.txt")
if (yourFile == null) {
yourFile.createNewFile()
}
yourFile << "\n${sdf.format(date)} - STARTED"

while (true) {
log.info(bioms[1])
log.info("LOGIN| ${characters[0]}")
//---------------------------- NORMAL START
player.login(account, password, characters[0])
findAnimalsNew(characters[0], images[0])
log.info("------------------------------")
findAnimals(characters[0], images[0])
//----------------------------
for (int i = 1; i < characters.size(); i++) {
log.info("LOGIN| ${characters[i]}")
player.login(account, password, characters[i])
findAnimalsNew(characters[i], images[i])
log.info("------------------------------")
findAnimals(characters[i], images[i])
}
return false
}

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
    animalName = animalName.replace("gfx/kritter/", "")
	animalName = animalName.replace("gfx/terobjs/", "")
	animalName = animalName.replace("wildbees/", "")
	animalName = animalName.replace("ants/", "")
	animalName = animalName.replace("squirrel/", "")
	animalName = animalName.replace("caveangler/", "")
	animalName = animalName.replace("mammoth/", "")
	animalName = animalName.replace("chasmconch/", "")
	animalName = animalName.replace("moose/", "")
	animalName = animalName.replace("bear/", "")
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
    animalName = animalName.replace("gfx/kritter/", "")
	animalName = animalName.replace("gfx/terobjs/", "")
	animalName = animalName.replace("wildbees/", "")
	animalName = animalName.replace("ants/", "")
	animalName = animalName.replace("squirrel/", "")
	animalName = animalName.replace("caveangler/", "")
	animalName = animalName.replace("mammoth/", "")
	animalName = animalName.replace("chasmconch/", "")
	animalName = animalName.replace("moose/", "")
	animalName = animalName.replace("bear/", "")
	if (animalsNOTwritebl > 0) {
	    yourFile << "\n${sdf.format(date)} - ${character} - ${animalName} - ${animalsNOTwritebl}"
		log.info("----Found ${animalsNOTwritebl} ${animalName}")
    }
}