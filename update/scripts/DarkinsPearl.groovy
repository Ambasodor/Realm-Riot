import groovy.time.TimeCategory
import groovy.time.TimeDuration
import groovy.time.*

import java.text.SimpleDateFormat;



bioms = ["FINDING FORAGE - CAVE", "FINDING FORAGE - MOUNTAIN", "FINDING FORAGE - WATER", "FINDING FORAGE - NORMAL"] //ADD_IF_NEED_MORE_BIOMS_THEN_CHANGE_IN_|FOR|_|BEFORE|
def ignore = []
def coord = [[0,-31],[21,-21],[10,9],[27,-27],[0,-8],[-38,0],[-35,5],[-14,9],[-53,53],[-46,46],[0,11],[-36,36],[-17,6],[-36,36],[-6,0],[-38,38],[5,44],[0,38],[-3,10],[0,30],[4,33],[0,4],[11,14],[0,4],[17,26],[65,65],[50,50],[41,41],[3,12],[0,19],[53,53],[19,19],[0,11],[9,9]]
def coord2 = [[0,-31],[21,-21],[10,9],[27,-27],[0,-8],[-38,0],[-35,5],[-14,9],[-53,53],[-46,46],[0,11],[-36,36],[-17,6],[-36,36],[-6,0],[-38,38],[-18,13],[-12,0],[-8,3],[-13,4],[-15,2],[-12,2],[-35,0],[-9,-4],[-4,0],[-1,1],[-18,0],[-17,-10],[-8,-8],[-8,8],[-15,0],[-7,5],[-22,0],[-24,8],[-7,7],[-3,0],[-35,-15],[-20,0],[-8,4],[-25,25],[-25,-20],[-20,-25],[-9,-14],[-20,-18],[-30,-28],[-19,-19],[-23,0],[-27,27],[-20,20],[-10,10]]
def coord3 = [[0,-31],[21,-21],[10,10],[25,-25],[0,73],[61,61],[42,42],[5,0],[0,-14],[-46,-46],[-9,-15],[7,-9],[6,-13],[4,-4],[20,0],[14,-9],[9,0],[0,-33],[4,-6],[-5,-6],[12,-16],[12,-15],[0,-18],[8,-13],[0,-13],[8,-8],[-10,-10],[0,-7],[-9,-9],[0,-9],[5,-5],[0,-19],[55,-55],[31,-33],[0,-12],[52,-52]]
def coord4 = [[-10, 0],[-15, 15],[-1, 7],[-6, 13],[3, 4],[7, 4],[4, 5],[-2, 5],[7, 5],[6, 2],[6, 6],[8, -1],[13, 11],[7, 2],[8, 10],[16, 1],[4, 10],[0, 32],[-1, 10],[-2, 15],[-2, 9],[1, 9],[-5, 8],[-1, 8],[-9, 9],[0, 12],[8, 10],[0, 7],[8, 4],[16, 0],[18, 1],[18, 13],[7, -6],[4, -7],[7, 3],[15, 15],[11, -9],[17, 16],[19, 19],[8, 2],[13, 1],[8, 0],[7, -5],[28, 7],[18, 0],[26, 26],[15, 0],[8, -4],[12, 11],[10, 2],[45, -44],[4, -12],[7, -11],[3, -12],[5, -9],[7, -9],[0, -37],[0, -27],[-4, -14],[3, -9],[2, -3],[18, -18],[1, -5],[9, -10],[0, -23],[28, -47],[12, -25],[12, -28],[9, -24],[9, -24],[5, -17],[0, -19]]
def exitboat = [[8,0]]
def exitboat2 = [[-6,0]]
def exitboat3 = [[-3,0]]
def exitboat4 = [[-3,0]]
def aggroaway = [[-41,41]]
def outboate = [[-1,0]]
def outcave = [[0,-2],[-4,0],[0, 49],[-1,7]]
def homerun = [[-9,0]]
//def river = [[0,-31],[21,-21],[10,9],[0,13]]
//def river2 = [[-7,-12],[7,-70],[70,-32]]
def chesters = [[4,0],[0,-2]]
def cratespace = 64
int AFK
boolean dur1 = true
boolean dur2 = true
boolean dur3 = true
boolean dur4 = true
def toRandom = 0
int d = 0
def newsoondate
def newsoondate1
def newsoondate2
def newsoondate3
int l = 0
int k = 0

def ignorePlayerA = []
def unknow

def ignorePlayerAg = []
def enemys


while (true) {
    def date0 = new Date()
    if (newsoondate1 != null) {
        dur1 = date0.after(newsoondate1)
    }
    if(dur1) {
        takeboat()
        tphf()
        gotogates()
        opengates()
        goclosegate()
        starttopoint(homerun)
        //gotoriver(river)

        def timestart1 = new Date()

        fullbot(coord, ignore, 110, ignorePlayerA, unknow, enemys, ignorePlayerAg,l,k)
        endswim(exitboat)
        outboat(outboate)
        tphf()
        dropboatnearhouse()
        GoInHouseAndPut(cratespace)
        GoOutHouse()
        tphf()
        def timestop1 = new Date()
        int AFK1 = usetcat(timestop1,timestart1)
        newsoondate1 = newdate(AFK1)
    }
    def date01 = new Date()
    if (newsoondate != null) {
        dur2 = date01.after(newsoondate)
    }
    if (!coord2 .isEmpty() && dur2){
        takeboat()
        tphf()
        gotogates()
        opengates()
        goclosegate()
        starttopoint(homerun)
        //gotoriver(river2)
        def timeStart = new Date()
        fullbot(coord2, ignore,110, ignorePlayerA, unknow, enemys, ignorePlayerAg,l,k)
        endswim(exitboat2)
        outboat(outboate)
        tphf()
        dropboatnearhouse()
        GoInHouseAndPut(cratespace)
        GoOutHouse()
        tphf()
        def timeStop = new Date()
        int AFK2 = usetcat(timeStop,timeStart)
        newsoondate = newdate(AFK2)
    }
    def date02 = new Date()
    if (newsoondate2 != null) {
        dur3 = date02.after(newsoondate2)
    }
    if (!coord3 .isEmpty() && dur3){
        takeboat()
        tphf()
        gotogates()
        opengates()
        goclosegate()
        starttopoint(homerun)
        //gotoriver(river2)
        def timeStart2 = new Date()
        fullbot(coord3, ignore,110, ignorePlayerA, unknow, enemys, ignorePlayerAg,l,k)
        endswim(exitboat3)
        outboat(outboate)
        tphf()
        dropboatnearhouse()
        GoInHouseAndPut(cratespace)
        GoOutHouse()
        tphf()
        def timeStop2 = new Date()
        int AFK3 = usetcat(timeStop2,timeStart2)
        newsoondate2 = newdate(AFK3)
    }
    def date03 = new Date()
    if (newsoondate3 != null) {
        dur4 = date03.after(newsoondate3)
    }
    if (!coord4 .isEmpty() && dur4){
        takeboat()
        tphf()
        gotogates()
        opengates()
        goclosegate()
        starttopoint(homerun)
        //gotoriver(river2)
        def timeStart3 = new Date()
        fullbot(coord4, ignore,110, ignorePlayerA, unknow, enemys, ignorePlayerAg,l,k)
        endswim(exitboat4)
        outboat(outboate)
        tphf()
        dropboatnearhouse()
        GoInHouseAndPut(cratespace)
        GoOutHouse()
        tphf()
        def timeStop3 = new Date()
        int AFK4 = usetcat(timeStop3,timeStart3)
        newsoondate3 = newdate(AFK4)
    }
}


def DropSpiderAndWeb(){
        spider = inventory.getItems("Itsy Bitsy Spider")
        web = inventory.getItems("Itsy Bitsy's Web")
        if (spider != null) {
            for (n = 0; n < spider.size(); n++) {
                player.dropItemFromInventory(spider[n])
                sleep(20)
            }
        }
    if (web != null) {
        for (m = 0; m < web.size(); m++) {
            player.dropItemFromInventory(web[m])
            sleep(20)
        }
    }
}
int usetcat (timeStop,timeStart){
    use(TimeCategory) {
        def duration = timeStop - timeStart
        int AFK = 21 - duration.minutes
        return AFK
    }
}
def newdate (addMinutes){
    def currentDate  = new Date()
    use( TimeCategory ) {
        def newCurrentDate = currentDate + addMinutes.minutes
        return newCurrentDate
    }
}
def Afek(def date1, def date2, int AFK){
    use(TimeCategory) {
        def duration = date1 - date2
        AFK = 21 - duration.minutes
    }
    log.info("Sleeping: ${AFK} minutes")
    sleep(AFK*60000)
}
def fullbot(def points,def ignore, def disttomus, ignorePlayerA, unknow, enemys, ignorePlayerAg, l, k){
    def toRandom = 0
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def block = 0
    def aggro = false
    def nextas = bela.add(0.0, 0.0)
    def newposese = nextas
    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newposese = newposese.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newposese)
        log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
        player.moveToNoPF(newposese)
        movedist = player.SdistanceBetweenCoords(newposese)
        while (movedist != 0) {
            movedist = player.SdistanceBetweenCoords(newposese)
            olddist = player.SdistanceBetweenCoords(newposese)
            player.moveToNoPF(newposese)
            DropSpiderAndWeb()
            if (!aggro) {
                alarmunknown(ignorePlayerA, unknow, k)
            }
            aggro = player.isAggroed()
            if (!aggro) {
                PickupMussel(ignore, disttomus, enemys, ignorePlayerAg, l)
            }
            if (aggro){
                AlarmAggro(enemys, ignorePlayerAg, l)
            }
            sleep(100)
            stuckCoords = player.SdistanceBetweenCoords(newposese)
            if (olddist != stuckCoords){
                toRandom = 0
            }
            if (olddist == stuckCoords) {
                if (toRandom == 50) {
                    int multiplier = 650
                    player.randommove(multiplier)
                    player.MusselwaitUntilStops()
                    toRandom = 0
                }
                if (toRandom != 50) {
                    toRandom++
                }
            }
            if (movedist == 0) break
        }
    }
}

def alarmunknown(def ignorePlayerA, def unknow, k){
    unknow = player.describeUnknowns()
    if (unknow.size() != 0) {
        for (u = 0; u < unknow.size(); u++) {
            if (!ignorePlayerA.contains(unknow[u])) {
                if (k == 0) {
                    player.discordSay("<@219213705919987712> DarkinsMusselbot meet ${unknow.size()} player(s)")
                    k++
                }
                player.discordSay("${unknow[u]} - player number: ${u + 1}")
                if (!ignorePlayerA.containsAll(unknow[u])) {
                    ignorePlayerA.add(unknow[u])
                }
            }
        }
    }
    unknow = player.describeUnknowns()
    if (unknow.size() == 0) {
        unknow = null
        ignorePlayerA = []
        k = 0
    }
}

def AlarmAggro(def ignorePlayerAg, def enemys, l){
    enemys = player.GetGearByFight()
    if (enemys.size() != 0) {
        for (u = 0; u < enemys.size(); u++) {
            if (!ignorePlayerAg.contains(enemys[u])) {
                if (l == 0) {
                    player.discordSay("<@219213705919987712> <@267019592348467201> DarkinsMusselbot attacked by ${enemys.size()} player(s)")
                    l++
                }
                player.discordSay("${enemys[u]} - player number: ${u + 1}")
                if (!ignorePlayerAg.containsAll(enemys[u])) {
                    ignorePlayerAg.add(enemys[u])
                }
            }
        }
    }
    enemys = player.GetGearByFight()
    if (enemys.size() == 0) {
        enemys = null
        ignorePlayerAg = []
        l = 0
    }
}

def endswim (def points){
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas
    aggro = player.isAggroed()
        for (def coord : points) {
            mypos = player.getPlayerCoords()
            newposes = newposes.add(coord[0] * 11, coord[1] * 11)
            a = player.SdistanceBetweenCoords(newposes)
            log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
            player.moveToNoPF(newposes)
            movedist = player.SdistanceBetweenCoords(newposes)
            while (movedist != 0) {
                player.moveToNoPF(newposes)
                movedist = player.SdistanceBetweenCoords(newposes)
                sleep(50)
                if (movedist == 0) break
            }
        }
}
def outboat(points){
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def boat = []
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas
    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newposes = newposes.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newposes)
        player.LeftClickCoordCTRL(newposes)
    }
    boat = player.findObjects("rowboat")
    sdt = player.GetSdt(boat[0])
    if (!boat.isEmpty()) {
        sdt = player.GetSdt(boat[0])
        player.doAct("carry")
        player.carrygob(boat[0])
        absolute = player.distanceTo(boat[0])
        if (sdt != 64) {
            while (sdt != 64) {
                sdt = player.GetSdt(boat[0])
                if (sdt == 64) break
                player.doAct("carry")
                player.carrygob(boat[0])
                sleep(200)
            }
        }
    }
}
def tphf(){
    reback = 0
    hf = player.findObjects("pow")
    while (hf.isEmpty() || player.distanceTo(hf[0]) != 0) {
        aggro = player.isAggroed()
        if (aggro == true){
            player.playAlarmsound()
            player.SystemMessage("I'm Aggroed, pls do something")
            sleep(500)
        }
        aggro = player.isAggroed()
        if (aggro == false) {
            player.act("travel", "hearth")
            Hourglass = player.getHourglass()
            if (Hourglass == -1) {
                while (Hourglass == -1) {
                    Hourglass = player.getHourglass()
                    aggro = player.isAggroed()
                    if (Hourglass != -1 || aggro == true) {
                        reback = 0
                        break
                    }
                    if (Hourglass == -1) {
                        player.act("travel", "hearth")
                        sleep(25)
                        reback++
                    }
                    if (reback >= 100) {
                        reback = 0
                        break
                    }
                }
            }
            Hourglass = player.getHourglass()
            if (Hourglass != -1) {
                while (Hourglass != -1) {
                    Hourglass = player.getHourglass()
                    sleep(200)
                    Hourglass = player.getHourglass()
                    if (Hourglass == -1) break
                }
            }
        }
        hf = player.findObjects("pow")
        if ((!hf.isEmpty() && player.GetSdt(hf[0]) == 17) && (player.distanceTo(hf[0]) == 0)) break
    }
    DropSpiderAndWeb()
}
def PickupMussel(ignore, disttomus, enemys, ignorePlayerAg, l){
    boat = player.findObjects("rowboat")
    def start = player.getPlayerCoords()
    def next = start.add(0.0, 0.0)
    def newpos = next
    kek = 0
    def ag = 0
    lel = 0
    def m = 0
    ignored = 0
    def picked = 0
    muses = inventory.getItems("mussels")
    mus = player.findObjects("mussels")
    if (mus != null) {
        for (a = 0; a < mus.size(); a++) {
            c = player.getGobCoords(mus[a])
            if (ignore.contains(mus[a]))continue
            nus = player.findObjects("mussels")
            if (!nus.contains(mus[a])) continue
            absolute = player.distanceBetween(boat[0],mus[a])
            def aggro = player.isAggroed()
            if (aggro){
                AlarmAggro(enemys, ignorePlayerAg, l)
                continue
            }
            if(absolute >= disttomus)continue
            if (absolute < disttomus) {
                aggro = player.isAggroed()
                if (aggro){
                    AlarmAggro(enemys, ignorePlayerAg, l)
                    continue
                }
                player.moveToMussel(c)
                d = player.distanceBetween(boat[0],mus[a])
                while (d >= 0.1){
                    aggro = player.isAggroed()
                    if (aggro){
                        AlarmAggro(enemys, ignorePlayerAg, l)
                        break
                    }
                    mis = player.distanceBetween(boat[0],mus[a])
                    player.moveToMussel(c)
                    mis = player.distanceBetween(boat[0],mus[a])
                    log.info("${mis} One")
                    if (mis >= 0.1) {
                        log.info("Smaller")
                        aggro = player.isAggroed()
                        if (aggro){
                            AlarmAggro(enemys, ignorePlayerAg, l)
                            break
                        }
                        while (mis >= 0.1) {
                            aggro = player.isAggroed()
                            if (aggro){
                                AlarmAggro(enemys, ignorePlayerAg, l)
                                break
                            }
                            mis = player.distanceBetween(boat[0],mus[a])
                            player.moveToMussel(c)
                            log.info("distance left - ${mis}")
                            sleep(200)
                            mis = player.distanceBetween(boat[0],mus[a])
                            if (mis == 0) {
                                log.info("I'm on position. DIST TO MUS 0")
                                break
                            }
                            if (lel == 5){
                                lel = 0
                                break
                            }
                            lel++
                        }
                    }
                    aggro = player.isAggroed()
                    if (aggro){
                        AlarmAggro(enemys, ignorePlayerAg, l)
                        continue
                    }
                    d = player.distanceBetween(boat[0],mus[a])
                    if (d == 0) break
                    log.info("${kek} KEK")
                    if (kek = 2) break
                    kek++
                }
                if (kek == 2) {
                    aggro = player.isAggroed()
                    if (aggro){
                        AlarmAggro(enemys, ignorePlayerAg, l)
                        continue
                    }
                    ignore.add(mus[a]);
                    kek = 0
                    player.moveToNoPF(newpos)
                    a = player.SdistanceBetweenCoords(newpos)
                    while (a != 0) {
                        a = player.SdistanceBetweenCoords(newpos)
                        olddist = player.SdistanceBetweenCoords(newpos)
                        player.moveToNoPF(newpos)
                        log.info("distance left - ${a}")
                        sleep(200)
                        stuckCoords = player.SdistanceBetweenCoords(newpos)
                        if (olddist != stuckCoords){
                            toRandom = 0
                        }
                        if (olddist == stuckCoords) {
                            if (toRandom == 50) {
                                int multiplier = 650
                                player.randommove(multiplier)
                                player.MusselwaitUntilStops()
                                toRandom = 0
                            }
                            if (toRandom != 50) {
                                toRandom++
                            }
                        }
                        if (a == 0) {
                            log.info("I'm on position. IGNORE")
                            ignored = ignored + 1
                            break
                        }
                    }
                    continue;
                }
                aggro = player.isAggroed()
                if (aggro){
                    AlarmAggro(enemys, ignorePlayerAg, l)
                    continue
                }
                nus = player.findObjects("mussels")
                if (!nus.contains(mus[a])) continue
                player.RightClickGobNoWait(mus[a])
                Gmenu = player.getflower()
                if (Gmenu == null) {
                    while (Gmenu == null) {
                        nus = player.findObjects("mussels")
                        if (!nus.contains(mus[a])) break
                        if (nus.contains(mus[a])) {
                            player.RightClickGobNoWait(mus[a])
                            sleep(200)
                            Gmenu = player.getflower()
                            if (Gmenu != null) break
                        }
                    }
                }
                if (Gmenu != null) {
                    player.ClickFlower("Pick")
                    nus = player.findObjects("mussels")
                    if (!nus.contains(mus[a])){
                        picked = picked + 1
                        continue
                    }
                    if (nus.contains(mus[a])) {
                        aggro = player.isAggroed()
                        if (aggro){
                            AlarmAggro(enemys, ignorePlayerAg, l)
                            continue
                        }
                        while (nus.contains(mus[a]) || m != 5) {
                            aggro = player.isAggroed()
                            if (m >= 5 && ignore.contains(mus[a])) break
                            if (aggro){
                                AlarmAggro(enemys, ignorePlayerAg, l)
                                continue
                            }
                            mis = player.distanceBetween(boat[0],mus[a])
                            while(mis != 0){
                                if (m >= 5 && !ignore.contains(mus[a])) {
                                    ignore.add(mus[a])
                                    m == 0
                                    break
                                }
                                aggro = player.isAggroed()
                                if (aggro){
                                    AlarmAggro(enemys, ignorePlayerAg, l)
                                    continue
                                }
                                mis = player.distanceBetween(boat[0],mus[a])
                                picked = picked + 1
                                sleep(500)
                                m++
                            }
                            if (mis == 0 && m >= 5 && !ignore.contains(mus[a])) {
                                ignore.add(mus[a])
                                m == 0
                                break
                            }
                            nus = player.findObjects("mussels")
                            if (!nus.contains(mus[a])) {
                                log.info("mussel picked up")
                                picked = picked + 1
                                Gmenu = player.getflower()
                                if (Gmenu != null) {
                                    player.closeFmenu()
                                }
                                break
                            }
                            m++
                        }
                    }
                }
            }
        }
        aggro = player.isAggroed()
        if (!aggro){
            ag = 0
        }
        if (picked != 0 || ignored != 0 || aggro && ag != 1 || m == 5) {
            bv = 0
            player.moveToNoPF(newpos)
            a = player.SdistanceBetweenCoords(newpos)
            while (a != 0) {
                aggro = player.isAggroed()
                if (aggro){
                    AlarmAggro(enemys, ignorePlayerAg, l)
                    ag = 1
                }
                if (!aggro){
                    ag = 0
                }
                a = player.SdistanceBetweenCoords(newpos)
                olddist = player.SdistanceBetweenCoords(newpos)
                player.moveToNoPF(newpos)
                log.info("distance left - ${a}")
                sleep(200)
                a = player.SdistanceBetweenCoords(newpos)
                olddist = player.SdistanceBetweenCoords(newpos)
                player.moveToNoPF(newpos)
                log.info("distance left - ${a}")
                sleep(200)
                stuckCoords = player.SdistanceBetweenCoords(newpos)
                if (olddist != stuckCoords){
                    toRandom = 0
                }
                if (olddist == stuckCoords) {
                    if (toRandom == 50) {
                        int multiplier = 650
                        player.randommove(multiplier)
                        player.MusselwaitUntilStops()
                        toRandom = 0
                    }
                    if (toRandom != 50) {
                        toRandom++
                    }
                }
                if (a == 0) {
                    log.info("I'm on position. Back to prePick position")
                    picked = 0
                    ignored = 0
                    m = 0
                    break
                }
                oldA = player.SdistanceBetweenCoords(newpos)
                if (a == oldA){
                    bv++
                    if (bv == 3){
                        player.moveToMussel(newpos)
                    }
                }
            }
        }
    }
    if (mus == null){
        sleep(200)
    }
}
def gotocave(def points){
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
def outcaves(){
    def bel = player.getPlayerCoords()
    if (bel == null){
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
    cvo = player.findObjects("caveout")
    log.info("${cvo}")
    if (cvo != null) {
        player.RightClickGobnomove(cvo)
        sleep(2000)
    }
}
def gotogates() {
    def bel = player.getPlayerCoords()
    if (bel == null) {
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
        mypos = player.getPlayerCoords()
        newpose = newpose.add(-5 * 11, 0.0)
        a = player.SdistanceBetweenCoords(newpose)
        player.moveToNoPF(newpose)
        movedist = player.SdistanceBetweenCoords(newpose)
        while (movedist != 0) {
            player.moveToNoPF(newpose)
            movedist = player.SdistanceBetweenCoords(newpose)
            log.info("${movedist}")
            sleep(50)
            if (movedist == 0) break
        }
    }
def opengates(){
    def StartCoords
    def NextCoords
    def NewposCoords
    def gates = []
    def gate
    def gatestatus
    def status // 2 - closed, 1 - open
    StartCoords = player.getPlayerCoords()
    NextCoords = StartCoords.add(0.0, 0.0)
    NewposCoords = NextCoords
    gates = player.findObjects("drystonewallgate")
    status = player.GetSdt(gates[0])

    if (status == 2){
        player.RightClickGob(gates[0])
        sleep(1000)
        status = player.GetSdt(gates[0])
        if (status != 1){
            status = player.GetSdt(gates[0])
            while (status != 1) {
                status = player.GetSdt(gates[0])
                if (status == 1) break
                player.RightClickGob(gates[0])
                sleep(1000)
            }
        }
    }
    player.moveToNoPF(NewposCoords)
    movedist = player.SdistanceBetweenCoords(NewposCoords)
    while (movedist != 0){
        player.moveToNoPF(NewposCoords)
        movedist = player.SdistanceBetweenCoords(NewposCoords)
        sleep(50)
        if (movedist == 0) break
    }
}

def goclosegate(){
    def StartCoords
    def NextCoords
    def NewposCoords
    def gate = []
    def gatestatus
    def status // 2 - closed, 1 - open
    StartCoords = player.getPlayerCoords()
    NextCoords = StartCoords.add(0.0, 0.0)
    NewposCoords = NextCoords
    def newpose = NewposCoords.add(-2.0 * 11, 0.0)
    player.moveToNoPF(newpose)
    movedist = player.SdistanceBetweenCoords(newpose)
    while (movedist != 0){
        player.moveToNoPF(newpose)
        movedist = player.SdistanceBetweenCoords(newpose)
        log.info("${movedist}")
        sleep(1000)
        if (movedist == 0) break
    }
    gate = player.findObjects("drystonewallgate")
    status = player.GetSdt(gate[0])

    if (status == 1){
        player.RightClickGob(gate[0])
        status = player.GetSdt(gate[0])
        sleep(1000)
        if (status != 2){
            while(status != 2) {
                status = player.GetSdt(gate[0])
                if (status == 2) break
                player.RightClickGob(gate[0])
                sleep(1000)
            }
        }
    }
    player.moveToNoPF(newpose)
    movedist = player.SdistanceBetweenCoords(newpose)
    while (movedist != 0){
        player.moveToNoPF(newpose)
        movedist = player.SdistanceBetweenCoords(newpose)
        sleep(50)
        if (movedist == 0) break
    }
}
def starttopoint(def points){
    def bel = player.getPlayerCoords()
    if (bel == null){
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
    def boat = []
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


    newpose = newpose.add(-1.0 * 11, 0.0)
    boat = player.findObjects("rowboat")
    if (!boat.isEmpty()) {
        player.rightclick(newpose)
        absolute = player.distanceTo(boat[0])
        if (absolute == 0) {
            while (absolute == 0) {
                absolute = player.distanceTo(boat[0])
                sleep(200)
                if (absolute != 0) break
            }
        }
        sleep(500)
        player.RightClickGobNoWait(boat[0])
        absolute = player.distanceTo(boat[0])
        if (absolute != 0) {
            while (absolute != 0) {
                absolute = player.distanceTo(boat[0])
                sleep(200)
                if (absolute == 0) {
                    break
                }
            }
        }
    }
}
def gotoriver(points){
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas
    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newposes = newposes.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newposes)
        log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
        player.moveToNoPF(newposes)
        movedist = player.SdistanceBetweenCoords(newposes)
        while (movedist != 0) {
            player.moveToNoPF(newposes)
            movedist = player.SdistanceBetweenCoords(newposes)
            sleep(50)
            if (movedist == 0) break
        }
    }
}
def dropboatonground() {
    def bel = player.getPlayerCoords()
    if (bel == null){
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
    def boate
    midpose = newpose.add(0.0, -2.0 * 11)
    hf = newpose.add(1 * 5.5, -4 * 11)
    boat = player.findObjects("rowboat")
    for (c = 0; c < boat.size(); c++){
        absolute = player.distanceTo(boat[c])
        if(absolute >= 22)continue
        if (absolute < 22){
            boate = boat[c]
            absolute = player.distanceTo(boate)
            log.info("${absolute}")
        }
    }
    if (boate != null) {
        player.rightclick(newpose)
        sdt = player.GetSdt(boate)
        if (sdt != 1) {
            sdt = player.GetSdt(boate)
            while (sdt != 1) {
                sdt = player.GetSdt(boate)
                sleep(50)
                if (sdt == 1)break
            }
        }
    }
    player.moveToNoPF(midpose)
    movedist = player.SdistanceBetweenCoords(midpose)
    while (movedist != 0) {
        player.moveToNoPF(midpose)
        movedist = player.SdistanceBetweenCoords(midpose)
        sleep(50)
        if (movedist == 0) break
    }
}
def gotochests(def points, cratespace){
    def che = []
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas

    for (def coord : points) {
        mypos = player.getPlayerCoords()
        newposes = newposes.add(coord[0] * 11, coord[1] * 11)
        a = player.SdistanceBetweenCoords(newposes)
        log.info("[${coord[0]},${coord[1]}] - Moving to this coords.")
        player.moveToNoPF(newposes)
        movedist = player.SdistanceBetweenCoords(newposes)
        while (movedist != 0) {
            player.moveToNoPF(newposes)
            movedist = player.SdistanceBetweenCoords(newposes)
            sleep(50)
            if (movedist == 0) break
        }
    }
    chests = player.findObjects("chest")
    for (b = 0; b < chests.size(); b++){
        absolute = player.distanceTo(chests[b])
        if(absolute >= 33)continue
        if (absolute < 33){
            che.add(chests[b])
        }
    }
    def muse = inventory.getItems("River Pearl Mussel, stack of")
    if (muse.size() != 0) {
        for (int n = 0; n < che.size(); n++) {
            muse = inventory.getItems("River Pearl Mussel, stack of")
            if (muse == null) {
                muse = inventory.getItems("River Pearl Mussel, stack of")
            }
            log.info("Mussels in inventory: ${muse.size()}")
            player.RightClickGob(che[n])
            cratewindow = player.waitForNewWindowTest("Chest")
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
            log.info("${Cpblee.size()} Mussels inside chest.")
            if (Cpblee.size() == 36) {
                log.info("${Cpblee.size()}/${cratespace} - full crate.")
                continue
            }
            if (muse.size() == 0) break
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
            if (Cpblee.size() == 0) {
                sleep(200)
                cratewindow = player.waitForNewWindowTest("Chest")
                Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
                if (Cpblee.size() == 36) {
                    log.info("${Cpblee.size()}/${cratespace} - full crate.")
                    continue
                }
            }
            cratewindow = player.waitForNewWindowTest("Chest")
            muse = inventory.getItems("River Pearl Mussel, stack of")
            log.info("${Cpblee.size()}/${cratespace} before.")
            inventory.transferNew(muse[0])
            sleep(200)
            muse = inventory.getItems("River Pearl Mussel, stack of")
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
            log.info("${Cpblee.size()}/${cratespace} after.")
            if (muse.size() == 0) break
        }
    }
}
def takeboat(){
    rem = 0
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas
    def boat = []
    boat = player.findObjects("rowboat")
    disttotake = player.distanceTo(boat[0])
    if (boat != null && disttotake > 33 || boat == null || boat.size() == 0 || boat.isEmpty()){
        while (boat != null && disttotake > 33 || boat == null || boat.size() == 0 || boat.isEmpty()) {
            if (rem == 0){
                player.discordSay("I have no boat near me, pls put boat here -> https://i.imgur.com/CWKfYAZ.png")
                rem++
            }
            boat = player.findObjects("rowboat")
            disttotake = player.distanceTo(boat[0])
            if (disttotake <= 33) break
        }
    }
    boat = player.findObjects("rowboat")
    disttotake = player.distanceTo(boat[0])
    if (boat.size() != 0 && disttotake <= 33) {
        if (rem != 0){
            rem = 0
        }
        player.doAct("carry")
        player.carrygob(boat[0])
        sdt = player.GetSdt(boat[0])
        disttotake = player.distanceTo(boat[0])
        if (sdt != 64 && disttotake != 0 || sdt == 64 && disttotake != 0) {
            while (sdt != 64 & disttotake != 0 || sdt == 64 && disttotake != 0) {
                absolute = player.distanceTo(boat[0])
                player.doAct("carry")
                player.carrygob(boat[0])
                sdt = player.GetSdt(boat[0])
                sleep(200)
                if (sdt == 64 & absolute == 0) {
                    player.cancel()
                    break
                }
            }
        }
    }
}
def GoInHouseAndPut(def cratespace){
    def cupb = []
    logcabinet = player.findObjects("timberhouse")
        player.UseDoor(logcabinet)
    cupb = player.findObjects("cupboard")
    if (cupb.isEmpty()){
        while (cupb.isEmpty()){
            player.UseDoor(logcabinet)
            cupb = player.findObjects("cupboard")
            sleep(200)
            if (!cupb.isEmpty()) break
        }
    }
    def bela = player.getPlayerCoords()
    if (bela == null){
        bela = player.getPlayerCoords()
    }
    def nextas = bela.add(0.0, 0.0)
    def newposes = nextas
    newposesa = newposes.add(-1.0 * 11, 0.0)
    player.moveTo(newposesa)
    cupb = player.findObjects("cupboard")
    def muse = inventory.getItems("River Pearl Mussel, stack of")
    if (muse.size() != 0) {
        for (int n = 0; n < cupb.size(); n++) {
            player.moveTo(newposesa)
            muse = inventory.getItems("River Pearl Mussel, stack of")
            if (muse == null) {
                muse = inventory.getItems("River Pearl Mussel, stack of")
            }
            log.info("Mussels in inventory: ${muse.size()}")
            player.RightClickGobNoWait(cupb[n])
            cratewindow = player.waitForNewWindowTest("Cupboard")
            if (cratewindow == null){
                while (cratewindow == null){
                    cratewindow = player.waitForNewWindowTest("Cupboard")
                    if (cratewindow != null) break
                }
            }
            cratewindow = player.waitForNewWindowTest("Cupboard")
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
                log.info("${Cpblee.size()} Mussels inside Cupboard.")
                if (Cpblee.size() == 64) {
                    log.info("${Cpblee.size()}/${cratespace} - full crate.")
                    continue
                }
            muse = inventory.getItems("River Pearl Mussel, stack of")
            if (muse.size() == 0) {
                player.moveTo(newposesa)
                break
            }
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
            if (Cpblee.size() == 0) {
                sleep(200)
                cratewindow = player.waitForNewWindowTest("Cupboard")
                if (cratewindow == null){
                    while (cratewindow == null){
                        cratewindow = player.waitForNewWindowTest("Cupboard")
                        if (cratewindow != null) break
                    }
                }

                Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
                if (Cpblee.size() == 64) {
                    log.info("${Cpblee.size()}/${cratespace} - full cupboard.")
                    continue
                }
            }
            cratewindow = player.waitForNewWindowTest("Cupboard")
            if (cratewindow == null){
                while (cratewindow == null){
                    cratewindow = player.waitForNewWindowTest("Cupboard")
                    if (cratewindow != null) break
                }
            }

            muse = inventory.getItems("River Pearl Mussel, stack of")
            log.info("${Cpblee.size()}/${cratespace} before.")
            inventory.transferNew(muse[0])
            sleep(200)
            muse = inventory.getItems("River Pearl Mussel, stack of")
            Cpblee = inventory.getItems(cratewindow, "River Pearl Mussel, stack of")
            log.info("${Cpblee.size()}/${cratespace} after.")
            if (muse.size() == 0) {
                player.moveTo(newposesa)
                break
            }
        }
    }
}
def GoOutHouse(){
    def cupb = []
    logcabinet = player.findObjects("timberhouse-door")
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
def dropboatnearhouse(){
    def bel = player.getPlayerCoords()
    if (bel == null){
        bel = player.getPlayerCoords()
    }
    def nexta = bel.add(0.0, 0.0)
    def newpose = nexta
    def boatcoord = newpose.add(2.0 * 5.5, 2.0 * -11)
    def boat = []
    hf = newpose
    boat = player.findObjects("rowboat")
    if (!boat.isEmpty()) {
        player.rightclick(boatcoord)
        boat = player.findObjects("rowboat")
        sdt = player.GetSdt(boat[0])
        if (sdt != 1) {
            boat = player.findObjects("rowboat")
            sdt = player.GetSdt(boat[0])
            while (sdt != 1) {
                boat = player.findObjects("rowboat")
                sdt = player.GetSdt(boat[0])
                sleep(50)
                if (sdt == 1)break
            }
        }
    }
    player.moveTo(hf)
    movedist = player.SdistanceBetweenCoords(hf)
    while (movedist != 0) {
        player.moveTo(hf)
        movedist = player.SdistanceBetweenCoords(hf)
        sleep(50)
        if (movedist == 0) break
    }
}