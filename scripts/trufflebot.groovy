
@groovy.transform.Canonical
class Account {
	String login = ""
	String password = ""
	String character = ""
}

Account[] accounts = [
		["X", "X", "Baton"]
]


def unstuck(def coords, def stuckCoords, def toRandom){
	if (coords == stuckCoords) {
		if (toRandom == 50) {
			int multiplier = 650
			player.randommove(multiplier)
			player.ClaywaitUntilStops()
			toRandom = 0
		}
		if (toRandom != 50) {
			toRandom++
		}
	}
}

def waypointWalker(def points) {
	def absolute
	def stuckCoords
	int goRandom = 0
	def start = player.getPlayerCoords()
	if (start == null){
		start = player.getPlayerCoords()
	}
	def next = start.add(0.0, 0.0)
	def newpos = next
	for (def coord : points) {
		newpos = newpos.add(coord[0] * 11, coord[1] * 11)
		absolute = player.SdistanceBetweenCoords(newpos)
		log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
		SafeDistToPig()
		findTruffle()
		player.moveToNoPF(newpos)
		SafeDistToPig()
		findTruffle()
		while (absolute != 11) {
			SafeDistToPig()
			findTruffle()
			absolute = player.SdistanceBetweenCoords(newpos)
			player.moveToNoPF(newpos)
			log.info("distance left - ${absolute}")
			sleep(150)
			stuckCoords = player.SdistanceBetweenCoords(newpos)
			goRandom++
				if (goRandom == 100) {
					stuckCoords = player.SdistanceBetweenCoords(newpos)
					if (absolute == stuckCoords) {
					int multiplier = 650
					while(absolute == stuckCoords) {
						player.randommove(multiplier)
						sleep(500)
						goRandom = 0
						stuckCoords = player.SdistanceBetweenCoords(newpos)
						if (absolute != stuckCoords) break
					}
				}
			}
			if (absolute == 0) {
				log.info("I'm on point.")
				break
			}
		}
	}
}

def opengate(){
	def StartCoords
	def NextCoords
	def NewposCoords
	def gate
	def gatestatus
	def status // 2 - closed, 1 - open
	StartCoords = player.getPlayerCoords()
	NextCoords = StartCoords.add(0.0, 0.0)
	NewposCoords = NextCoords
	gate = player.isGob("polebiggate")
	status = player.getGateStatus()

	if (status == 2){
		player.RightClickGob(gate)
		sleep(1000)
		gatestatus = player.gateis(1) // if number == true, if !number == false
		status = player.getGateStatus()
		if (!gatestatus || status != 1){
			while(status != 1 || gatestatus) {
				gatestatus = player.gateis(1)
				player.RightClickGob(gate)
				if (status == 1 || gatestatus) break
			}
		}
	}
}

def closegate(){
	def gate
	def gatestatus
	def status // 2 - closed, 1 - open
	gate = player.isGob("polebiggate")
	status = player.getGateStatus()
	if (status == 1){
		player.RightClickGob(gate)
		sleep(1000)
		gatestatus = player.gateis(2) // if number == true, if !number == false
		status = player.getGateStatus()
		if (!gatestatus || status != 2){
			while(status != 1 || gatestatus) {
				gatestatus = player.gateis(2)
				player.RightClickGob(gate)
				if (status == 2 || gatestatus) break
			}
		}
	}
}

def pickupTruffle(){
	def truffles = player.isGob("truffle")
	int goRandom = 0
	if (truffles != null){
		while (truffles != null){
			absolute = player.distanceTo(truffles)
			player.PickupGob(truffles)
			sleep(150)
			goRandom++
			stuckCoords = player.distanceTo(truffles)
			truffles = player.isGob("truffle")
			if (goRandom == 100) {
				stuckCoords = player.distanceTo(truffles)
				if (absolute == stuckCoords) {
					int multiplier = 650
					while(absolute == stuckCoords) {
						player.randommove(multiplier)
						sleep(500)
						goRandom = 0
						stuckCoords = player.SdistanceBetweenCoords(newpos)
						if (absolute != stuckCoords) break
					}
				}
			}
			if (truffles == null) break
		}
		log.info("All truffles are picked.")
	}
}

def movetoTruffle(){
	def a
	def start
	def next
	def newpos
	def truffles
	def DistanceToTruffle
	int goRandom = 0
	start = player.getPlayerCoords()
	if (start == null){
		start = player.getPlayerCoords()
	}
	next = start.add(0.0, 0.0)
	newpos = next
	truffles = player.isGob("truffle")
	DistanceToTruffle = player.distanceTo(truffles)
	absolute = player.distanceTo(truffles)
	player.LeftClickGob(truffles)
	while(DistanceToTruffle != 11){
		absolute = player.distanceTo(truffles)
		log.info("distance left - ${DistanceToTruffle}")
		sleep(150)
		goRandom++
		DistanceToTruffle = player.distanceTo(truffles)
		stuckCoords = player.distanceTo(truffles)
		player.LeftClickGob(truffles)
		if (goRandom == 100) {
			stuckCoords = player.distanceTo(truffles)
			if (absolute == stuckCoords) {
				int multiplier = 650
				while(absolute == stuckCoords) {
					player.randommove(multiplier)
					sleep(500)
					goRandom = 0
					stuckCoords = player.SdistanceBetweenCoords(newpos)
					if (absolute != stuckCoords) break
				}
			}
		}
		if (DistanceToTruffle == 0){
			log.info("I'm on truffle place.")
			pickupTruffle()
			break
		}
	}
	a = player.SdistanceBetweenCoords(newpos)
	SafeDistToPig()
	player.moveToNoPF(newpos)
	while (a != 11){
		a = player.SdistanceBetweenCoords(newpos)
		SafeDistToPig()
		player.moveToNoPF(newpos)
		absolute = player.SdistanceBetweenCoords(newpos)
		sleep(150)
		stuckCoords = player.SdistanceBetweenCoords(newpos)
		goRandom++
		if (goRandom == 100) {
			stuckCoords = player.SdistanceBetweenCoords(newpos)
			if (absolute == stuckCoords) {
				int multiplier = 650
				while(absolute == stuckCoords) {
					player.randommove(multiplier)
					sleep(500)
					goRandom = 0
					stuckCoords = player.SdistanceBetweenCoords(newpos)
					if (absolute != stuckCoords) break
				}
			}
		}
		if (a == 0) {
			log.info("I'm on point.")
			break
		}
	}
}

def findTruffle() {
	def truffles = player.isGob("truffle")
	if (truffles != null){
		log.info("i found truffle, trying to pickup!")
		def TruffleQuantity = player.findCountOf("truffle")
		log.info("Truffles found: ${TruffleQuantity}")
		movetoTruffle()
	}
}

def RopeOnCoursor(){
	def rope
	def CoursItem
	rope = inventory.getItems("Rope")
	inventory.takeItem(rope)
	CoursItem = inventory.getHoldingItem().isPresent()
	if (!CoursItem) {
		log.info("item isn't in hands")
		while (!inventory.getHoldingItem().isPresent()){
			CoursItem = inventory.getHoldingItem().isPresent()
			if (CoursItem) break
			sleep(25)
		}
	}
	if (CoursItem){
		log.info("item in hands")
	}
}

def ClickOnPig(){
	def pig
	def leashed
	pig = player.isGob("pig")
	player.useOn(pig)
	player.ClaywaitUntilStops()
	sleep(250)
	leashed = inventory.CoursorIsLeashed()
	if (leashed == null){
		leashed = inventory.CoursorIsLeashed()
	}
	if (leashed){
		log.info("Pig is leashed.")
	}
	if (!leashed){
		while (!leashed){
			player.useOn(pig)
			player.ClaywaitUntilStops()
			if (leashed){
				log.info("Pig is leashed.")
				break
			}
		}
	}
}

def PutRopeInv(){
	def rope
	def leash
	inventory.putToInventory()
	sleep(500)
	rope = inventory.getItems("Rope")
	leash = inventory.isLeashed(rope)
	if (!leash){
		RopeOnCoursor()
		ClickOnPig()
	}
}

def SafeDistToPig(){
	def playercoord
	def pig
	def distance
	pig = player.isGob("pig")
	distance = player.distanceTo(pig)
	if (distance > 99){
		playercoord = player.getPlayerCoords()
		def next = playercoord.add(0.0, 0.0)
		def newpos = next
		findTruffle()
		player.moveToNoPF(newpos)
		while (distance > 99){
			distance = player.distanceTo(pig)
			findTruffle()
			sleep(25)
			if (distance < 88) break
		}
	}
}

def UnleashRope(){
	def rope
	rope = inventory.getItems("Rope")
	inventory.unleash(rope)
}

def isChest(def StartCoords) {
	def calculatetruffles = 0
	def chest
	def chests
	def chestcount
	def freespace
	def TruffleInInventory
	chest = player.isGob("gfx/terobjs/chest")
	if (chest != null) {
		chestcount = player.findCountOf(chest)
		log.info("I found: ${chestcount} chests.")
		gotoChest(StartCoords, calculatetruffles)
		player.moveTo(StartCoords)
		freespace = inventory.getFreeSpace()
		if (freespace == 0){
			player.discordSay("Truffle Bot have 0 free space, all chests are full.")
		}
		TruffleInInventory = inventory.getItems("Black Truffles")
		if (TruffleInInventory.size() == 0){
			player.discordSay("Truffles total in chests: ${calculatetruffles}.")
		}
	}
	if(chest == null){
		log.info("No regular chests around, bot gonna check if full inventory.")
		freespace = inventory.getFreeSpace()
		if (freespace == 0){
			player.discordSay("Truffle Bot have 0 free space, there's no chests.")
		}
	}
}

def gotoChest(def StartCoords, def calculatetruffles){
	def chests
	def currentchest
	def TruffleInInventory
	chests = player.findObjects("gfx/terobjs/chest")
	log.info("Moving to chests for transfer truffle into. ${chests[0]}.")
	for (int n = 0; n < chests.size(); n++) {
		TruffleInInventory = inventory.getItems("Black Truffles")
		currentchest = chests[n]
		player.moveTo(StartCoords)
		player.moveTo(chests[n])
		openChest(currentchest, calculatetruffles)
		if (TruffleInInventory.size == 0){
			log.info("No truffles in inventory.")
			break
		}
	}
}

def openChest(def currentchest, def calculatetruffles){
	def ChestWindow
	def TruffleInInventory
	if (currentchest != null) {
		player.RightClickGob(currentchest)
		ChestWindow = player.waitForNewWindowTest("Chest")
		log.info("${ChestWindow}")
		TruffleInInventory = inventory.getItems("Black Truffles")
		if (TruffleInInventory.size() != 0) {
			putTruffleInChest(ChestWindow, calculatetruffles)
		}
		calculatetruffles = calculatetruffles + TrufflesInChest.size()
	}
	if (currentchest == null){
		log.info("Ooops, current chest is null. happens -_-")
	}
}

def putTruffleInChest(def chestwindow){
	def TrufflesInChest
	def TrufflesInInventory
	def SpaceInChest = 36
	TrufflesInInventory = inventory.getItems("Black Truffles")
	if (chestwindow != null) {
		TrufflesInChest = inventory.getItems(chestwindow, "Black Truffles")
		if (TrufflesInChest.size() != 36) {
			if (TrufflesInInventory.size() != 0 || TrufflesInInventory.size() != null) {
				log.info("Truffles in inventory: ${TrufflesInInventory.size}.")
				log.info("${TrufflesInChest.size()}/${SpaceInChest} before.")
				inventory.transferNew(TrufflesInInventory[0])
				sleep(200)
				TrufflesInChest = inventory.getItems(chestwindow, "Black Truffles")
				log.info("${TrufflesInChest.size()}/${SpaceInChest} after.")
			}
		}
		TrufflesInChest = inventory.getItems(chestwindow, "Black Truffles")
		if (TrufflesInChest.size() == 36) {
			log.info("${TrufflesInChest.size()}/${SpaceInChest} - full chest.")
		}
	}
	if (chestwindow == null){
		log.info("Ooops, current chest window is null. happens -_-")
	}
}

def LogIn(){
	player.login(accounts[0].login, accounts[0].password, accounts[0].character)
	sleep(4000)
}

def GoOutHouse(){
	def cupb = []
	logcabinet = player.findObjects("logcabin-door")
	player.RightClickGobNoWait(logcabinet[0])
	cupb = player.findObjects("cupboard")
	if (!cupb.isEmpty()){
		while (!cupb.isEmpty()){
			player.RightClickGobNoWait(logcabinet[0])
			cupb = player.findObjects("cupboard")
			sleep(200)
			if (cupb.isEmpty()) break
		}
	}
}

def GoInHouseAndPut() {
	def cupb = []
	logcabinet = player.findObjects("logcabin")
	player.UseDoor(logcabinet)
	cupb = player.findObjects("cupboard")
	if (cupb.isEmpty()) {
		while (cupb.isEmpty()) {
			player.UseDoor(logcabinet)
			cupb = player.findObjects("cupboard")
			sleep(200)
			if (!cupb.isEmpty()) break
		}
	}
}

def start(){
	GoOutHouse()
	def coords = [[0,-100],[0,-19],[-50,0],[0,119],[50,0]]
	def coords1 = [[-17,0]]
	def closegateCoords = [[0.0, -8.0]]
	def opengateCoords = [[0.0, 10.0]]
	def StartCoords
	def NextCoords
	def NewposCoords
	waypointWalker(coords1)
	//LogIn()
	StartCoords = player.getPlayerCoords()
	NextCoords = StartCoords.add(0.0, 0.0)
	NewposCoords = NextCoords
	//opengate() ##no gates in w15
	//goouthouse()
	//RopeOnCoursor()
	//ClickOnPig()
	//PutRopeInv()
	/*
	player.moveTo(NewposCoords)
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while(a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}
	waypointWalker(closegateCoords)
	player.moveTo(NewposCoords)
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while(a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}
	closegate()
	player.moveTo(NewposCoords)
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while(a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}

	 */
	waypointWalker(coords)
	/*
	player.moveTo(NewposCoords)
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while(a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}
	opengate()
	waypointWalker(opengateCoords)
	player.moveTo(NewposCoords)
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while(a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}
	closegate()
	UnleashRope()
	 */
	player.moveToNoPF(NewposCoords)
	a = player.SdistanceBetweenCoords(NewposCoords)
	while (a != 11){
		a = player.SdistanceBetweenCoords(NewposCoords)
		sleep(100)
		player.moveToNoPF(NewposCoords)
		if (a == 0){
			break
		}
	}
	//isChest(NewposCoords)
	GoInHouseAndPut()
}
player.setspeed(1)
start()