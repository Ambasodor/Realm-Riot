
coords = [[124,0],[0,3],[6,0],[0,-3],[124,0],[0,120],[-3,0],[0,10],[3,0],[0,124],[-124,0],[0,-3],[-10,0],[0,3],[-99,0],[0,-20],[-21,0],[0,-104],[3,0],[0,-6],[-3,0],[0,-124]]
@groovy.transform.Canonical
class Account {
    String login = ""
    String password = ""
    String character = ""
}

Account[] accounts = [
        ["x", "x", "batoshka"]
]
player.login(accounts[0].login, accounts[0].password, accounts[0].character)
waypointWalker(coords, accounts[0].character)
player.unsafeLogout()

def waypointWalker(def points, def character) {
	def energy = player.getEnergyMeter()
	if (energy == 0 || energy == null) {
	energy = player.getEnergyMeter()
	}
	if (energy < 2500 && energy != 0) {
	            player.discordSay("<@219213705919987712> ��������� ${character}, � ������. ������� �������� ${energy}%")
                sleep(3600000)
	}
    def start = player.getPlayerCoords()
	if (start == null){
	start = player.getPlayerCoords()
	}
    def next = start.add(0.0, 0.0)
    def newpos = next
    def e = []
    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newpos = newpos.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newpos)
        log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
        player.moveToNoPF(newpos)
        def d = player.GetEnemyKinsNearby()
        while (a != 11) {
		energy = player.getEnergyMeter()
            d = player.GetEnemyKinsNearby()
            //log.info("${d}")        INFO ABOUT NEAREST PPL
            if (d != null) {
                if (!e.contains(d) && !d.isEmpty()) {
                    log.info("${e} ${d.isEmpty()}")
                    if (!e.contains(d)) {
                        e << d
                    }
                if (e.size() != 0) {
                    player.discordSay("@here THERE'S ${e} NEXT TO WALLS")
                }
                }
            }
			if (energy == 0 || energy == null) {
				energy = player.getEnergyMeter()
			}
			
            if (energy < 2500 && energy != 0){
                player.discordSay("<@219213705919987712> ��������� ${character}, � ������. ������� �������� ${energy}%")
                sleep(3600000)
            }
            a = player.SdistanceBetweenCoords(newpos)
            player.moveToNoPF(newpos)
            log.info("distance left - ${a}")
            sleep(200)
            if (a == 0) {
                if (!e.isEmpty()) {
                    log.info("${e}")
                }
                log.info("I'm on position.")
                break
            }
        }
    }
    log.info("${e}")
}