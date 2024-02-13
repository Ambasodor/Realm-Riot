import java.text.SimpleDateFormat

@groovy.transform.Canonical
class Account {
	String login = ""
	String password = ""
	String character = ""
	String image = ""
}

Account[] accounts = [
		["X", "X", "Angler1", "https://imgur.com/a/ui9QTcg"]
//["X", "X", "angler300++", "https://imgur.com/YpHcs4C"]
]


animals = ["caveangler"]
int b = 0 //DONT_CHANGE_DEBUG
bioms = ["Animal system - Cave", "FINDING ANIMALS - MOUNTAIN", "FINDING ANIMALS - WATER", "FINDING ANIMALS - NORMAL"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|
coords = [[6,21],[18,26],[15,11],[18,18]]
//coords1 = [[-18,20],[-18,20],[-6,20],[-7,16],[11,20],[7,20],[0,20],[-6,14]]
//coords2 = [[-6.5,8.5],[-6.5,8.5],[-13,8.5],[-13,8.5],[0,24.5],[0,24.5],[-16,8],[-16,8],[-4.5,-15.5],[-4.5,-15.5],[-6,20],[-15,26],[0,25],[-20,9],[-6,12],[0,10],[-9,9],[10,13],[0,15],[0,15],[0,15],[0,10],[-15,0],[-15,10],[-10,15],[-8,15],[0,15],[10,8],[15,0]]
Date date = new Date()
def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("animalLOG.txt")
if (yourFile == null) {
	yourFile.createNewFile()
}
yourFile << "\n${sdf.format(date)} - STARTED"

def waypointWalker(def points,def character, def image, def back) {
	def start = player.getPlayerCoords()
	def next = start.add(0.0, 0.0)
	def newpos = next

	def route = [];

	for(def coord : points) {
		mypos = player.getPlayerCoords()

		newpos = (back)
				? newpos.sub(coord[0]*11, coord[1]*11)
				: newpos.add(coord[0]*11, coord[1]*11)
		log.info("[${coord[0]},${coord[1]}] - ${character} Moving to this coords.")

		player.moveTo(newpos)

		route << coord

		if(!back && (findAnimals(character, image))){
			break
		}

	}
	if (!back) {
		route = route.reverse()
		waypointWalker(route, character, image, true)
	}
}

//-- anglerRiver1 start
player.login(accounts[0].login, accounts[0].password, accounts[0].character)
sleep(5000)
chara = player.GetAccountName();
def start = player.getPlayerCoords()
if (start == null) {
	start = player.getPlayerCoords()
}
def startN = start.add(0.0, 0.0)
def Starter = startN
waypointWalker(coords, accounts[0].character, accounts[0].image, false)
player.moveTo(Starter)
player.unsafeLogout()

//-- anglerRiver2 start
/*
player.login(accounts[1].login, accounts[1].password, accounts[1].character)
sleep(5000)
chara = player.GetAccountName();
start = player.getPlayerCoords()
if (start == null) {
	start = player.getPlayerCoords()
}
start = player.getPlayerCoords()
startN = start.add(0.0, 0.0)
Starter = startN
waypointWalker(coords1, accounts[1].character, accounts[1].image, false)
player.moveTo(Starter)
player.unsafeLogout()
/*
//-- angler300+ start
player.login(accounts[2].login, accounts[2].password, accounts[2].character)
sleep(5000)
chara = player.GetAccountName();
def start = player.getPlayerCoords()
if (start == null) {
	start = player.getPlayerCoords()
}
	start = player.getPlayerCoords()
	startN = start.add(0.0, 0.0)
	Starter = startN
waypointWalker(coords2, accounts[2].character, accounts[2].image, false)
player.moveTo(Starter)
player.unsafeLogout()
//
*/

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
	Date date = new Date()
	def sdf = new SimpleDateFormat("HH:mm:ss")
	yourFile = new File("animalLOG.txt")
	def animals = player.findCountOf(animalName)

	if (animals != 0) {
		log.info("[In-game time: ${time}] Found ${animals} ${animalName}.")
		player.discordSay("<@&1160672909937872916> [In-game time: ${time}] ${chara} found ${animals} ${animalName} ${image}.")
		yourFile << "\n${sdf.format(date)} - [In-game time: ${time}] - ${chara} - ${animalName} - ${animals}."
	}


	//---------------------------------------------------------------------------------------- + \Start second check
	if (animals == 0) {
		animals = player.findCountOf(animalName)

		if (animals != 0) {
			log.info("Second check -> [In-game time: ${time}] ${animals} ${animalName}.")
			player.discordSay("<@&1160672909937872916> [In-game time: ${time}] ${chara} found ${animals} ${animalName} ${image} in second check." + "\n" + "P.S DO NOT TAKE A BOAT FROM A BOT, DO NOT USE IT!")
		}
	}
	return animals != 0
	//---------------------------------------------------------------------------------------- + /Stop second check

}