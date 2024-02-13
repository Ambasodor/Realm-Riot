account = "X"
password = "X"
account1 = "X"
password1 = "X"
account2 = "X"
password2 = "X"
characters = ["Fish South Base", "Fish", "Fish NORTH BASE", "Fish NORTH NORTH BASE"]
characters1 = ["GRID #1", "GRID #2.1", "GRID #3", "GRID #4", "GRID #5", "GRID #6", "GRID #7", "GRID #8", "GRID #9"]
characters2 = ["1 GRID", "2 GRID", "3 GRID", "4 GRID", "5 GRID"]	
images = ["https://prnt.sc/sqtvho", "https://prnt.sc/sqtvb1", "https://prnt.sc/sqtv0g", "https://prnt.sc/sqtuii"]
images1 = ["https://prnt.sc/stdaq2", "https://prnt.sc/stdawg", "https://prnt.sc/stdahm", "https://prnt.sc/stdb3a", "https://prnt.sc/stdbao", "https://prnt.sc/stdbi2", "https://prnt.sc/stdbox", "https://prnt.sc/stdbx2", "https://prnt.sc/stdc5r"]
images2 = ["https://prnt.sc/stdv46", "https://prnt.sc/stdvgx", "https://prnt.sc/stdvp1", "https://prnt.sc/stdvy3", "https://prnt.sc/stdw5t"]
animals = ["walrus", "caveangler", "seal", "bear", "moose", "mammoth", "boreworm", "chasmconch"]
int b = 0 //DONT_CHANGE_DEBUG
bioms = ["FINDING ANIMALS - CAVE", "FINDING ANIMALS - MOUNTAIN", "FINDING ANIMALS - WATER"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|
while (true) {
log.info(bioms[0])
log.info("LOGIN| ${characters[0]}")
    findAnimals(characters[0], images[0])
    //--------------------------------------------CAVE
    for (int i = 1; i < characters.size(); i++) {
    player.login(account, password, characters[i])
	log.info("LOGIN| ${characters[i]}")
    findAnimals(characters[i], images[i])
    }
	//--------------------------------------------CAVE
	player.login(account1, password1, characters1[0])
    log.info(bioms[1])
    log.info("LOGIN| ${characters1[0]}")
    findAnimals(characters1[0], images1[0])
	//--------------------------------------------MAMMOTH
	for (int i = 1; i < characters1.size(); i++) {
    player.login(account1, password1, characters1[i])
    log.info("LOGIN| ${characters1[i]}")
    findAnimals(characters1[i], images1[i])
    }
	//--------------------------------------------MAMMOTH
	player.login(account2, password2, characters2[0])
    log.info(bioms[0])
    log.info("LOGIN| ${characters2[0]}")
    findAnimals(characters2[0], images2[0])
	//--------------------------------------------CAVE
	for (int i = 1; i < characters2.size(); i++) {
    player.login(account2, password2, characters2[i])
    log.info("LOGIN| ${characters2[i]}")
    findAnimals(characters2[i], images2[i])
    }
	//--------------------------------------------CAVE
	b++;
	    log.info("----SLEEP 18MINS")
    sleep(480000)
		log.info("----10MINS LEFT")
    sleep(300000)
		log.info("----5MINS LEFT")
    sleep(300000)
    player.login(account, password, characters[0])
	
	 log.info("${b} cycles already.")
}

def findAnimals(def character, def image) {
    sleep(5000)
    animals.each { animal -> findAnimal(animal, character, image) }
    player.unsafeLogout()
}

def findAnimal(def animalName, def character, def image) {
    def animals = player.findCountOf(animalName)
    if (animals > 0) {
		log.info("----Found ${animals} ${animalName}")
        player.discordSay("${character} found ${animals} ${animalName} ${image}")
    }
}