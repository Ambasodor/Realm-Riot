import java.text.SimpleDateFormat

account = "X"
password = "X"
characters = ["1", "2", "3", "4", "5", "6", "7", "8", "9"]
images = ["https://i.imgur.com/92rcM1n.png", "https://i.imgur.com/6kEZtph.png", "https://i.imgur.com/Ao57OEj.png", "https://i.imgur.com/FPaiEUv.png", "https://i.imgur.com/CUoFJib.png", "https://i.imgur.com/Oao1uvy.png", "https://i.imgur.com/dYI15L2.png",  "https://i.imgur.com/ldojWjj.png", "https://i.imgur.com/zIxq7GT.png"]
animals = ["gfx/kritter/moose/moose", "gfx/kritter/bear/bear", "gfx/kritter/chasmconch/chasmconch", "gfx/kritter/caveangler/caveangler", "gfx/kritter/mammoth/mammoth"]
animalsNOTwritebl = ["gfx/kritter/boar/boar", "gfx/kritter/fox/fox", "gfx/kritter/badger/badger", "gfx/kritter/bat/bat", "gfx/kritter/ants/anthill", "gfx/kritter/lynx/lynx", "gfx/kritter/snake/snake", "gfx/kritter/aurochs", "gfx/kritter/mouflon", "gfx/kritter/stoat", "gfx/kritter/adder/adder", "gfx/kritter/beaver", "gfx/kritter/rat/caverat", "gfx/kritter/otter", "gfx/kritter/reindeer/reindeer", "gfx/kritter/swan", "gfx/kritter/wildhorse", "gfx/kritter/wolf", "gfx/kritter/goat/wildgoat", "gfx/kritter/cavelouse", "gfx/kritter/rat", "gfx/kritter/rabbit/rabbit", "gfx/terobjs/birdnest", "gfx/kritter/squirrel/squirrel", "gfx/kritter/mallard/mallard", "gfx/kritter/sheep", "gfx/kritter/magpie/magpie", "gfx/kritter/silkmoth/silkmoth", "gfx/kritter/wildbees/wildbeehive", "gfx/kritter/goldeneagle/goldeneagle", "gfx/kritter/boreworm/boreworm"]
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
log.info("LOGIN -> ${characters[0]}")
//---------------------------- NORMAL START
player.login(account, password, characters[0])
findAnimalsNew(characters[0], images[0])
findAnimals(characters[0], images[0])
//----------------------------
for (int i = 1; i < characters.size(); i++) {
log.info("------------------------------")
log.info("LOGIN -> ${characters[i]}")
player.login(account, password, characters[i])
findAnimalsNew(characters[i], images[i])
findAnimals(characters[i], images[i])
}
return false
}

def findAnimals(def character, def image) {
    sleep(100)
    animals.each { animal -> findAnimal(animal, character, image) }
    player.unsafeLogout()
}

def findAnimalsNew(def character, def image) {
    sleep(1000)
	animalsNOTwritebl.each { animal -> findAnimalNew(animal, character, image) }
}

def findAnimal(def animalName, def character, def image) {
    sleep(100)
	time = player.getServerTime()
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("animalLOG.txt")
    def animals = player.findCountOf(animalName)
	/*
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
	*/
    if (animals != 0) {
		log.info("[In-game time: ${time}] Found ${animals} ${animalName}.")
        player.discordSay("[In-game time: ${time}] ${character} found ${animals} ${animalName} ${image}.")
		yourFile << "\n${sdf.format(date)} - [In-game time: ${time}] - ${character} - ${animalName} - ${animals}."
    }
	
	
	//---------------------------------------------------------------------------------------- + \Start second check
	while (animals == 0)
	{

	animals = player.findCountOf(animalName)
	
	if (animals != 0) {
	log.info("Second check -> [In-game time: ${time}] ${animals} ${animalName}.")
	player.discordSay("[In-game time: ${time}] ${character} found ${animals} ${animalName} ${image} in second check.")
	}
	
	if (animals == 0) {
	}

	return false
	}

	//---------------------------------------------------------------------------------------- + /Stop second check
	
}

def findAnimalNew(def animalName, def character, def image) {
    sleep(100)
	time = player.getServerTime()
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("animalLOG.txt")
    def animalsNOTwritebl = player.findCountOf(animalName)
	/*
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
	*/
    if (animalsNOTwritebl != 0) {
		log.info("[In-game time: ${time}] Found ${animalsNOTwritebl} ${animalName}.")
		yourFile << "\n${sdf.format(date)} - [In-game time: ${time}] - ${character} - ${animalName} - ${animalsNOTwritebl}."
    }
	
	
	//---------------------------------------------------------------------------------------- + \Start second check
	while (animalsNOTwritebl == 0)
	{

	animalsNOTwritebl = player.findCountOf(animalName)
	
	if (animalsNOTwritebl != 0) {
	log.info("Second check -> [In-game time: ${time}] ${animalsNOTwritebl} ${animalName}.")
	}
	
	if (animals == 0) {
	}
	
	return false
	}

	//---------------------------------------------------------------------------------------- + /Stop second check
	
}