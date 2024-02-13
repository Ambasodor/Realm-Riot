@groovy.transform.Canonical
class Account {
    String login = ""
    String password = ""
    String character = ""
}

Account[] accounts = [
        ["X", "X", "Dream"]
]

dreams = ["gfx/terobjs/dreca"]
cupb = ["gfx/terobjs/cupboard"]


while (true) {
    player.login(accounts[0].login, accounts[0].password, accounts[0].character)
	sleep(4000)
	def start = player.getPlayerCoords()
    def next = start.add(0.0, 0.0)
    def newpos = next
    //-start
    GoToDreams()
    GoToCupboard(start)
    //-end
    log.info("finally")
    player.moveTo(newpos)
    player.unsafeLogout()
    return false
}

def GoToDreams(){
def list = []
    int invent = 35

    int Space = inventory.getItemCount("A Beautiful Dream")
    if (Space == null){
        Space = inventory.getItemCount("A Beautiful Dream")
    }
    int Freespace = inventory.getFreeSpace()
    if (Freespace == null){
        Freespace = inventory.getFreeSpace()
    }
	
    def catchers = player.findObjects(dreams)
    if (catchers.size != 0) {
        log.info("${catchers.size}")
    }
    for (int i = 0; i < catchers.size(); i++) {
        b = player.CheckGobByFlowerMenu(catchers[i], "Harvest")
        if (!b) {
            log.info("Empty catcher.")
        }
        if (b) {
            log.info("${b} Not empty - FOR USE!")
            list.add(b)
        }
    }

    def invDream = inventory.getItems("A Beautiful Dream")
    if (invDream == null){
        invDream = inventory.getItems("A Beautiful Dream")
    }

    log.info("${list}")

    for (int i = 0; i < list.size(); i++) {
	if (list.size != 0) {
        if (Freespace == 0 || invDream.size() == invent) break
        player.moveToNoPF(list[i])
        sleep(500)
        log.info("Moving to catcher." + list[i])
        sleep(500)

        for (int b = 0; b != 2; b++){
            invDream = inventory.getItems("A Beautiful Dream")
            Freespace = inventory.getFreeSpace()
            if (Freespace == 0 || invDream.size() == invent) break
            player.RightClickGob(list[i])
            player.FlowerMenuClick("Harvest")
            log.info("${b}")
        }

        if (Freespace == 0 || invDream.size() == invent) break
        Freespace = inventory.getFreeSpace()
        invDream = inventory.getItems("A Beautiful Dream")
        if (invDream.size() != invent) {
            log.info("${invDream.size()}/${invent} in inventory.")
        }
        if (Freespace == 0 || invDream.size() == invent) {
            log.info("${invDream.size()}/${invent} - full inventory")
        }
        sleep(500)
		}
		if (list.size == 0) {
        break
        }
    }
}

def GoToCupboard(def coords) {
    int inventcupboard = 64

    int Space = inventory.getItemCount("A Beautiful Dream")
    if (Space == null) {
        Space = inventory.getItemCount("A Beautiful Dream")
    }
	
    def invDream = inventory.getItems("A Beautiful Dream")
    if (invDream == null) {
        invDream = inventory.getItems("A Beautiful Dream")
    }
	
    int Freespace = inventory.getFreeSpace()
    if (Freespace == null) {
        Freespace = inventory.getFreeSpace()
    }

    Space = inventory.getItemCount("A Beautiful Dream")
    log.info("${Space} Dreams in inventory")
    def cpb = player.findObjects(cupb)
    if (cpb == null) {
        log.info("No cupboards")
    }
    invDream = inventory.getItems("A Beautiful Dream")
    if (invDream.size() != 0 && cpb != null) {
        log.info("Moving to cupboard for transfer dream into. ${cpb[0]}")
        for (int n = 0; n < cpb.size(); n++) {
            invDream = inventory.getItems("A Beautiful Dream")
            player.moveTo(coords)
            if (invDream.size() == 0) break
            player.RightClickGob(cpb[n])
            def Wcpb = player.waitForNewWindowTest("Cupboard")
            invDream = inventory.getItems("A Beautiful Dream")

            log.info("${invDream}")
            if (invDream.size() != 0) {
                invDream = inventory.getItems("A Beautiful Dream")
                Wcpb = player.waitForNewWindowTest("Cupboard")
                log.info("${Wcpb}")
                def cpbDream = inventory.getItems(Wcpb, "A Beautiful Dream")
                log.info("${cpbDream.size()} dreams inside cupboard.")
                if (cpbDream.size() == 64) {
                    log.info("${cpbDream.size()}/${inventcupboard} - full cupboard.")
                    continue
                }
                if (invDream.size() == 0) break
                cpbDream = inventory.getItems(Wcpb, "A Beautiful Dream")
                if (cpbDream.size() == 0) {
                    sleep(200)
                    Wcpb = player.waitForNewWindowTest("Cupboard")
                    cpbDream = inventory.getItems(Wcpb, "A Beautiful Dream")
                    if (cpbDream.size() == 64) {
                        log.info("${cpbDream.size()}/${inventcupboard} - full cupboard.")
                        continue
                    }
                }
                log.info("${cpbDream.size()}/${inventcupboard} before.")
                inventory.transferNew(invDream[0])
                sleep(200)
                cpbDream = inventory.getItems(Wcpb, "A Beautiful Dream")
                log.info("${cpbDream.size()}/${inventcupboard} after.")
            }
        }
    }
}
