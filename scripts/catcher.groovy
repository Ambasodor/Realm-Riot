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
    dcatch = []
    def start = player.getPlayerCoords()
    def next = start.add(0.0, 0.0)
    def newpos = next
    //-start
    GoToDreams(dcatch)
    player.moveTo(newpos)
    GoToCupboard()
    //-end
    player.moveTo(newpos)
}

def GoToDreams(def dcatch) {
    dcatch = []
    def start = player.getPlayerCoords()
    def next = start.add(0.0, 0.0)
    def newpos = next
    def bcatch
    int invent = 35

    int Space = inventory.getItemCount("A Beautiful Dream")
    if (Space == null) {
        Space = inventory.getItemCount("A Beautiful Dream")
    }
    int Freespace = inventory.getFreeSpace()
    if (Freespace == null) {
        Freespace = inventory.getFreeSpace()
    }

    def drm = player.findObjects(dreams)

    def invDream = inventory.getItems("A Beautiful Dream")
    if (invDream == null) {
        invDream = inventory.getItems("A Beautiful Dream")
    }


    for (int i = 0; i < drm.size(); i++) {
        tryc = 0
        if (Freespace == 0 || invDream.size() == invent) break
        player.RightClickGobNoWait(drm[i])
        Gmenu = player.getflower()
        if (Gmenu == null) {
            while (Gmenu == null) {
                sleep(25)
                Gmenu = player.getflower()
                if (Gmenu != null) break
                if (tryc >= 10) {
                    tryc = 0
                    break
                }
                tryc++
            }
        }
        if (Gmenu != null){
            if (Gmenu != null){
                while (Gmenu != null) {
                    player.closeFmenu()
                    sleep(250)
                    Gmenu = player.getflower()
                    if (Gmenu == null) {
                        bcatch = drm[i]
                        dcatch.add(bcatch)
                        log.info("${dcatch}")
                        break
                    }
                }
            }
        }
    }
    if (!dcatch.isEmpty()) {
        for (d = 0; d < dcatch.size(); d++) {
            Gmenu = player.getflower()
            if (Gmenu == null) {
                while (Gmenu == null) {
                    player.RightClickGobNoWait(dcatch[d])
                    sleep(500)
                    Gmenu = player.getflower()
                    if (Gmenu != null) break
                }
            }
            if (Gmenu != null) {
                Harvest = player.getpetal(Gmenu, "Harvest")
                if (Harvest != null) {
                    while (Harvest != null) {
                        Freespace = inventory.getFreeSpace()
                        invDream = inventory.getItems("A Beautiful Dream")
                        if (Freespace == 0 || invDream.size() == invent) break
                        player.ClickFlower("Harvest")
                        newdream = inventory.getItems("A Beautiful Dream")
                        if (newdream == null){
                            while (newdream == null){
                                newdream = inventory.getItems("A Beautiful Dream")
                                if (newdream != null)break
                            }
                        }
                        if (newdream != null) {
                            if (newdream.size() > invDream.size()) break
                            if (newdream.size() == invDream.size()) {
                                newdream = inventory.getItems("A Beautiful Dream")
                                while (newdream.size() == invDream.size()) {
                                    newdream = inventory.getItems("A Beautiful Dream")
                                    if (newdream.size() > invDream.size()) break
                                }
                            }
                        }
                        Gmenu = player.getflower()
                        if (Gmenu == null) break
                    }
                }
            }
            player.moveTo(newpos)
            if (Freespace == 0 || invDream.size() == invent) break
            Freespace = inventory.getFreeSpace()
            invDream = inventory.getItems("A Beautiful Dream")
            if (invDream.size() != invent) {
                log.info("${invDream.size()}/${invent} in inventory.")
            }
            if (Freespace == 0 || invDream.size() == invent) {
                log.info("${invDream.size()}/${invent} - full inventory")
            }
        }
        dcatch = []
        log.info("${dcatch.isEmpty()}")
    }
}

    def GoToCupboard() {
        int inventcupboard = 64

        int Space = inventory.getItemCount("A Beautiful Dream")
        if (Space == null) {
            Space = inventory.getItemCount("A Beautiful Dream")
        }
        def drm = player.findObjects(dreams)
        def invDream = inventory.getItems("A Beautiful Dream")
        if (invDream == null) {
            invDream = inventory.getItems("A Beautiful Dream")
        }
        int Freespace = inventory.getFreeSpace()
        if (Freespace == null) {
            Freespace = inventory.getFreeSpace()
        }

        Space = inventory.getItemCount("A Beautiful Dream")
        def cpb = player.findObjects(cupb)
        if (cpb == null) {
            log.info("No cupboards")
        }
        invDream = inventory.getItems("A Beautiful Dream")
        if (invDream.size() != 0 && cpb != null) {
            log.info("Moving to cupboard for transfer dream into. ${cpb[0]}")
            for (int n = 0; n < cpb.size(); n++) {
                def now = player.getPlayerCoords()
                def nowW = now.add(0.0, 0.0)
                def tickai = nowW
                invDream = inventory.getItems("A Beautiful Dream")
                player.moveTo(tickai)
                if (invDream.size() == 0) break
                player.RightClickGob(cpb[n])
                def Wcpb = player.waitForNewWindowTest("Cupboard")
                if (Wcpb == null){
                    while (Wcpb == null) {
                        Wcpb = player.waitForNewWindowTest("Cupboard")
                        if (Wcpb != null) break
                    }
                }
                invDream = inventory.getItems("A Beautiful Dream")

                log.info("${invDream}")
                if (invDream.size() != 0) {
                    invDream = inventory.getItems("A Beautiful Dream")
                    Wcpb = player.waitForNewWindowTest("Cupboard")
                    log.info("${Wcpb}")
                    Wcpb = player.waitForNewWindowTest("Cupboard")
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
