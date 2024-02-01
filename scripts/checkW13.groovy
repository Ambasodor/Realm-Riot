import java.text.SimpleDateFormat

account = "X"
password = "X"
characters = ["1", "2", "3", "4", "5", "6", "7", "8", "9"]
account1 = "X"
password1 = "X"
characters1 = ["1SM", "2.1SM", "3SM", "4SM", "5SM"]
account2 = "X"
password2 = "X"
characters2 = ["angler1", "angler2", "angler3"]
images = ["https://i.imgur.com/92rcM1n.png", "https://i.imgur.com/6kEZtph.png", "https://i.imgur.com/Ao57OEj.png", "https://i.imgur.com/FPaiEUv.png", "https://i.imgur.com/CUoFJib.png", "https://i.imgur.com/Oao1uvy.png", "https://i.imgur.com/dYI15L2.png",  "https://i.imgur.com/ldojWjj.png", "https://i.imgur.com/zIxq7GT.png"]
images1 = ["https://imgur.com/6qb28l6", "https://imgur.com/h4T989z", "https://imgur.com/7nJVR2d", "https://imgur.com/hdF9Z9y", "https://imgur.com/fjOSjN0"]
images2 = ["https://imgur.com/IzfcA3U", "https://imgur.com/cs1Itb2", "https://imgur.com/XHCvegD"]
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
log.info(bioms[3])
log.info("LOGIN| ${characters[0]}")
//---------------------------- NORMAL START
player.login(account1, password1, characters1[0])
findAnimalsNew(characters1[0], images[0])
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
//---------------------------- MOUNTAIN START
log.info(bioms[1])
log.info("LOGIN| ${characters1[0]}")
//----------------------------
player.login(account1, password1, characters1[0])
findAnimalsNew(characters1[0], images1[0])
log.info("------------------------------")
findAnimals(characters1[0], images1[0])
//----------------------------
for (int i = 1; i < characters1.size(); i++) {
log.info("LOGIN| ${characters1[i]}")
player.login(account1, password1, characters1[i])
findAnimalsNew(characters1[i], images1[i])
log.info("------------------------------")
findAnimals(characters1[i], images1[i])
}
//---------------------------- Angler CAVE START
log.info(bioms[0])
log.info("LOGIN| ${characters2[0]}")
//----------------------------
player.login(account2, password2, characters2[0])
findAnimalsNew(characters2[0], images2[0])
log.info("------------------------------")
findAnimals(characters2[0], images2[0])
//----------------------------
for (int i = 1; i < characters2.size(); i++) {
log.info("LOGIN| ${characters2[i]}")
player.login(account2, password2, characters2[i])
findAnimalsNew(characters2[i], images2[i])
log.info("------------------------------")
findAnimals(characters2[i], images2[i])
}
//----------------------------
return false
}

def findAnimals(def character, def image) {
    sleep(100)
    animals.each { animal -> findAnimal(animal, character, image) }
    player.unsafeLogout()
}

def findAnimalsNew(def character, def image) {
    sleep(5000)
	animalsNOTwritebl.each { animal -> findAnimalNew(animal, character, image) }
}

def findAnimal(def animalName, def character, def image) {
    sleep(100)
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
	sleep(100)
		log.info("----Found ${animals} ${animalName}")
		yourFile << "\n${sdf.format(date)} - ${character} - ${animalName} - ${animals}"
        player.discordSay("${character} found ${animals} ${animalName} ${image}")
    }
}

def findAnimalNew(def animalName, def character, def image) {
    sleep(100)
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
	sleep(100)
	    yourFile << "\n${sdf.format(date)} - ${character} - ${animalName} - ${animalsNOTwritebl}"
		log.info("----Found ${animalsNOTwritebl} ${animalName}")
    }
}