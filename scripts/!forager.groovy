import java.text.SimpleDateFormat;

@groovy.transform.Canonical
class Account {
String login = ""
String password = ""
String character = ""
String image = ""
}

Account[] accounts = [
["X", "X", "�����", "https://imgur.com/Q9kd829"],
["X", "X", "�����2", "https://imgur.com/mU6IGam"]
]

forages = ["clay-cave"]
Test = ["lampstalk"]

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
    log.info("LOGIN -> ${accounts[0].character}")
//----------------------------
    player.login(accounts[0].login, accounts[0].password, accounts[0].character)
    findForagesTEST(accounts[0].character, accounts[0].image)
    findForages(accounts[0].character, accounts[0].image)
//----------------------------
    log.info("LOGIN -> ${accounts[1].character}")
//----------------------------
    player.login(accounts[1].login, accounts[1].password, accounts[1].character)
    findForagesTEST(accounts[1].character, accounts[1].image)
    findForages(accounts[1].character, accounts[1].image)
//----------------------------
    return false
}

def findForages(def character, def image) {
    def start = player.getPlayerCoords()
    sleep(5000)
	int Freespace = inventory.getFreeSpace()
    if (Freespace == null) {
        Freespace = inventory.getFreeSpace()
    }
	if (Freespace != 0){
    forages.each { forage -> findForage(forage, character, image) }
    sleep(100)
    forages.each { forage -> player.pickClosest(forage) }
    player.moveTo(start)
	}
	if (Freespace == 0){
	player.discordSay("<@219213705919987712>,<@272403115351867392>, � ${character} ������ ���������, �������� �� ��� � ������!")
	}
    player.unsafeLogout()
}

def findForagesTEST(def character, def image) {
    sleep(1000)
    Test.each { forage -> findForageTEST(forage, character, image) }
}


def findForage(def forageName, def character, def image) {
    int Freespace = inventory.getFreeSpace()
    if (Freespace == null) {
        Freespace = inventory.getFreeSpace()
    }
    time = player.getServerTime()
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("ForageLog.txt")
	
    def forages = player.findCountOf(forageName)
    forageName = forageName.replace("gfx/terobjs/herbs/", "")
    if (forages != 0) {
        log.info("[In-game time: ${time}] Found ${forageName} ${forages}")
        yourFile << "\n${sdf.format(date)} - [In-game time: ${time}] - ${character} - ${forageName} - ${forages}"
        player.discordSay("[In-game time: ${time}] ${character} found ${forageName} ${forages} ${image}")
		while (forages != 0) {
		Freespace = inventory.getFreeSpace()
		log.info("Free space in inventory - ${Freespace}")
		if (Freespace == 0) {
		log.info("Free space is - ${Freespace}")
		player.discordSay("<@219213705919987712>,<@272403115351867392>, � ${character} ������ ���������, �������� �� ��� � ������!")
		break
		}
		player.pickClosest(forageName)
		forages = forages - 1
		log.info("${forages} ${forageName} left.")
		Freespace = inventory.getFreeSpace()
		log.info("Free space is - ${Freespace}")
		if (forages == 0) {return false}
		}
		log.info("No more ${forageName} left.")
    }
}

def findForageTEST(def forageName, def character, def image) {
	time = player.getServerTime()
    Date date = new Date()
    def sdf = new SimpleDateFormat("HH:mm:ss")
    yourFile = new File("ForageLog.txt")

    def Test = player.findCountOf(forageName)
    forageName = forageName.replace("gfx/terobjs/herbs/", "")
    if (Test > 0) {
        log.info("[In-game time: ${time}] Found ${forageName} ${Test}")
        yourFile << "\n${sdf.format(date)} - [In-game time: ${time}] - ${character} - ${forageName} - ${Test}"
    }
}