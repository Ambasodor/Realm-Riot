import java.text.SimpleDateFormat;


bioms = ["FINDING FORAGE - CAVE", "FINDING FORAGE - MOUNTAIN", "FINDING FORAGE - WATER", "FINDING FORAGE - NORMAL"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|
Date date = new Date()
def ignore = []
def coord = [[52,-52]]
def fullbot(def points, ignore){

    def bel = player.getPlayerCoords()
    if (bel == null){
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newpose = newpose.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newpose)
        log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
        player.moveToNoPF(newpose)
        movedist = player.SdistanceBetweenCoords(newpose)
        while (movedist != 0) {
            player.moveToNoPF(newpose)
            movedist = player.SdistanceBetweenCoords(newpose)
            sleep(50)
            if (movedist == 0) break
        }
    }
}


fullbot(coord,ignore)