player.lastmsgnull()
last = player.getlastmsgfromArea()
options = []
build = false
chip = false
turnoff = false
crossbuild = false
tpcrossroad = false
sto = false
ignoregems = []
gemsforfind = ["Diamond", "Ruby", "Sapphire", "Jade"]
f = 0
player.setspeed(1)
setuping()
mining()
def setuping() {
    last = player.getlastmsgfromArea()
    if (!last.equals("east") || !last.equals("west") || !last.equals("north") || !last.equals("south")) {
        player.SystemMessage("Write the side: east, west, north, south")
        while (!last.equals("east") || !last.equals("west") || !last.equals("north") || !last.equals("south")) {
            last = player.getlastmsgfromArea()
            sleep(200)
            if (last.equals("east") || last.equals("west") || last.equals("north") || last.equals("south")) break
        }
    }
    if (last.equals("east") || last.equals("west") || last.equals("north") || last.equals("south")) {
        if (last.equals("east")) {
            options.add("east")
        } else if (last.equals("west")){
            options.add("west")
        } else if (last.equals("north")){
            options.add("north")
        } else if (last.equals("south")){
            options.add("south")
        }
        player.SystemMessage("Okay, you choosed ${options[0]}, next choose crossroad building: yes, no")
        while (!last.equals("yes") || !last.equals("no")) {
            last = player.getlastmsgfromArea()
            sleep(200)
            if (last.equals("yes") || last.equals("no")) break
        }
    }
        if (last.equals("yes") || last.equals("no")) {
            if (last.equals("yes")) {
                options.add("yes")
                player.SystemMessage("Okay, you choosed ${options[1]}. Rn choose big milestone travel button from 0-3")
            }
            if (last.equals("no")) {
                options.add("no")
                player.SystemMessage("Okay, you choosed ${options[1]}. Rn choose big milestone travel button from 0-3")
            }
        }
    if (!last.equals("0") || !last.equals("1") || !last.equals("2") || !last.equals("3")) {
        while (!last.equals("0") || !last.equals("1") || !last.equals("2") || !last.equals("3")) {
            last = player.getlastmsgfromArea()
            sleep(200)
            if (last.equals("0") || last.equals("1") || last.equals("2") || last.equals("3")) break
        }
    }
    if (last.equals("0") || last.equals("1") || last.equals("2") || last.equals("3")) {
        if (last.equals("0")) {
            options.add(0)
        } else if (last.equals("1")){
            options.add(1)
        } else if (last.equals("2")){
            options.add(2)
        } else if (last.equals("3")){
            options.add(3)
        }
        player.SystemMessage("Okay, you choosed ${options[2]}, next choose start bot with: start")
    }
}
def mining() {
    def x
    def y
    def rot;

    last = player.getlastmsgfromArea()

    while (!last.equals("start")) {
        last = player.getlastmsgfromArea()
        sleep(200)
        if (last.equals("start")) break
    }

    if (last.equals("start")) {
        player.SystemMessage("I started")
        while (!last.contains("stop") || turnoff == false) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            troll = player.findObjects("gfx/kritter/troll/troll")
            last = player.getlastmsgfromArea()
            d = getnearestsupport()
            dis = getsupportdist(d)
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            crs = getnearestcros()
            crsdist = getcrosdist(crs)
            rock = getlooserocknear()
            if (f > 0 && f != 4) {
                f++
            }
            if (f == 0 || f == 4) {
                checkforPreciousTiles()
                if (f == 0) {
                    f++
                } else if (f == 4) {
                    f = 1
                }
            }
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            if (bumldist < 14 && bumldist != -1) {
                if (turnoff == true) {
                    break
                }
                chip = true
                chipbuml(buml)
                chip = false
            }
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
            check1 = checkTileForSupport()
            if ((dis < 112 && dis != -1) || check1 == true) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                buml = getstonebumlingsnear()
                bumldist = getstonebumlingdist(buml)
                log.info("${bumldist}")
                if (bumldist < 14 && bumldist != -1){
                    chip = true
                    chipbuml(buml)
                    chip = false
                }
                minetheway()
                if (f == 0 || f == 4) {
                    checkforPreciousTiles()
                    if (f == 0) {
                        f++
                    } else if (f == 4) {
                        f = 1
                    }
                }
            }
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                return
            }
            metal = inventory.getItemsContain("Bar of")
            if (dis >= 116 && metal.isEmpty() || dis == -1 && metal.isEmpty()) {
                tpcrossroad = true
                coord = player.getCoordUnderme()
                gethardmetal()
                player.ClickCoord(coord)
                tpcrossroad = false
            }
            stn = getallstoneinv()
            if (dis >= 116 && stn < 30 || dis == -1 && stn < 30){
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                sto = true
                coord = player.getCoordUnderme()
                getston()
                player.ClickCoord(coord)
                sleep(1000)
                sto = false
            }
            troll = player.findObjects("gfx/kritter/troll/troll")
            last = player.getlastmsgfromArea()
            d = getnearestsupport()
            dis = getsupportdist(d)
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            crs = getnearestcros()
            crsdist = getcrosdist(crs)
            rock = getlooserocknear()
            check1 = checkTileForSupport()
            stn = getallstoneinv()
            if ((dis >= 116 || dis  == -1) && !metal.isEmpty() && check1 == false && stn >= 30) {
                if (turnoff == true) {
                    break
                }
                build = true
                log.info("${dis}")
                miningfun()
                build = false
                if (f == 0 || f == 4) {
                    checkforPreciousTiles()
                    if (f == 0) {
                        f++
                    } else if (f == 4) {
                        f = 1
                    }
                }
            }
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                return
            }
            stn = getallstoneinv()
            if (crsdist >= 300 && stn < 5){
                sto = true
                coord = player.getCoordUnderme()
                getston()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.ClickCoord(coord)
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                sleep(1000)
                sto = false
            }
            troll = player.findObjects("gfx/kritter/troll/troll")
            last = player.getlastmsgfromArea()
            d = getnearestsupport()
            dis = getsupportdist(d)
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            crs = getnearestcros()
            crsdist = getcrosdist(crs)
            rock = getlooserocknear()
            if (crsdist >= 300 && stn >= 5) {
                log.info("Mining for crossroad")
                if (turnoff == true){
                    return
                }
                crossbuild = true
                dise = checkTileForSupport()
                cris = checkTileForCrossroad()
                d = getnearestsupport()
                dis = getsupportdist(d)
                getnearcr = checkNearTileForCrossroad()
                build = true
                d = getnearestsupport()
                dis = getsupportdist(d)
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                buildcrossroad()
                if (f == 0 || f == 4) {
                    checkforPreciousTiles()
                    if (f == 0) {
                        f++
                    } else if (f == 4) {
                        f = 1
                    }
                }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                build = false
                crossbuild = false
            }
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) {
                return
            }
        }
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true) {
            return
        }
    }
}
def checkTileForSupport(){
    tile = player.CentergetMytile() // стартовые коорды чара
    if (options[0].equals("north")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
        x1 = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
        y1 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() - 11)
        if ((Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) && (x1.contains("gfx/tiles/mine") || x1.contains("gfx/tiles/deepcave") || x1.contains("gfx/tiles/wildcavern")) && (y1.contains("gfx/tiles/mine") || y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))) {
            return true
        } else if ((!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")) && (!x1.contains("gfx/tiles/mine") || !x1.contains("gfx/tiles/deepcave") || !x1.contains("gfx/tiles/wildcavern")) && (!y1.contains("gfx/tiles/mine") || !y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))){
            return false
        }
    } else if (options[0].equals("east")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
        x1 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
        y1 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() - 11)
        if ((Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) && (x1.contains("gfx/tiles/mine") || x1.contains("gfx/tiles/deepcave") || x1.contains("gfx/tiles/wildcavern")) && (y1.contains("gfx/tiles/mine") || y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))) {
            return true
        } else if ((!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")) && (!x1.contains("gfx/tiles/mine") || !x1.contains("gfx/tiles/deepcave") || !x1.contains("gfx/tiles/wildcavern")) && (!y1.contains("gfx/tiles/mine") || !y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))){
            return false
        }
    } else if (options[0].equals("south")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
        x1 = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
        y1 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() + 11)
        if ((Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) && (x1.contains("gfx/tiles/mine") || x1.contains("gfx/tiles/deepcave") || x1.contains("gfx/tiles/wildcavern")) && (y1.contains("gfx/tiles/mine") || y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))) {
            return true
        } else if ((!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")) && (!x1.contains("gfx/tiles/mine") || !x1.contains("gfx/tiles/deepcave") || !x1.contains("gfx/tiles/wildcavern")) && (!y1.contains("gfx/tiles/mine") || !y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))){
            return false
        }
    } else if (options[0].equals("west")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
        x1 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
        y1 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() + 11)
        if ((Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) && (x1.contains("gfx/tiles/mine") || x1.contains("gfx/tiles/deepcave") || x1.contains("gfx/tiles/wildcavern")) && (y1.contains("gfx/tiles/mine") || y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))) {
            return true
        } else if ((!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")) && (!x1.contains("gfx/tiles/mine") || !x1.contains("gfx/tiles/deepcave") || !x1.contains("gfx/tiles/wildcavern")) && (!y1.contains("gfx/tiles/mine") || !y1.contains("gfx/tiles/deepcave") || y1.contains("gfx/tiles/wildcavern"))){
            return false
        }
    }
}
def checkTileForCrossroad(){
    tile = player.CentergetMytile() // стартовые коорды чара
    if (options[0].equals("north")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() - 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("east")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() + 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("south")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() + 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("west")) {
        Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() - 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    }
}
def checkNearTileForCrossroad(){
    tile = player.CentergetMytile() // стартовые коорды чара
    if (options[0].equals("north")){
        Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("east")){
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("south")){
        Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    } else if (options[0].equals("west")){
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
        if (Checktile.contains("gfx/tiles/mine") || Checktile.contains("gfx/tiles/deepcave") || Checktile.contains("gfx/tiles/wildcavern")) {
            return true
        } else if (!Checktile.contains("gfx/tiles/mine") || !Checktile.contains("gfx/tiles/deepcave") || !Checktile.contains("gfx/tiles/wildcavern")){
            return false
        }
    }
}
def minetheway(){
    if (f == 0 || f == 4) {
        checkforPreciousTiles()
        if (f == 0) {
            f++
        } else if (f == 4) {
            f = 1
        }
    }
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        return
    }
    if (options[0].equals("east")){
        check1 = checkTileForSupport()
        if (check1 == false) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.doAct("mine")
            start = player.CentergetMytile() // стартовые коорды чара
            start = start.add(11, 0)
            Mine(start)
            log.info("im coming east")
            Hourglass()
            player.cancelAct()
            sleep(50)
        }
        rock = getlooserocknear()
        if (rock != null) {
            player.SystemMessage("DANGER, BREAKING SCRIPT")
            player.playAlarmsound()
            turnoff = true
            return
        }
        runfromtroll()
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        log.info("${bumldist}")
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        goeast()
    } else if (options[0].equals("west")){
        check1 = checkTileForSupport()
        if (check1 == false) {
            player.doAct("mine")
            start = player.CentergetMytile() // стартовые коорды чара
            start = start.add(-11, 0)
            Mine(start)
            log.info("im coming west")
            Hourglass()
            player.cancelAct()
            sleep(50)
        }
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        log.info("${bumldist}")
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        gowest()
    } else if (options[0].equals("north")){
        check1 = checkTileForSupport()
        if (check1 == false) {
            player.doAct("mine")
            start = player.CentergetMytile() // стартовые коорды чара
            start = start.add(0, -11)
            Mine(start)
            log.info("im coming north")
            Hourglass()
            player.cancelAct()
            sleep(50)
        }
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        log.info("${bumldist}")
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        gonorth()
    } else if (options[0].equals("south")){
        check1 = checkTileForSupport()
        if (check1 == false) {
            player.doAct("mine")
            start = player.CentergetMytile() // стартовые коорды чара
            start = start.add(0, 11)
            Mine(start)
            log.info("im coming east")
            Hourglass()
            player.cancelAct()
            sleep(50)
        }
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        log.info("${bumldist}")
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        gosouth()
    }
}

def miningfun(){
    if (f == 0 || f == 4) {
        checkforPreciousTiles()
        if (f == 0) {
            f++
        } else if (f == 4) {
            f = 1
        }
    }
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    check1 = checkTileForSupport()
    if (check1 == false) {
        if (options[0].equals("east")) {
            tile = player.CentergetMytile()
            Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
            if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                player.doAct("mine")
                start = player.getMytile() // стартовые коорды чара
                start = start.add(0, -11)
                Mine(start)
                Hourglass()
                player.cancelAct()
                sleep(100)
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                buml = getstonebumlingsnear()
                bumldist = getstonebumlingdist(buml)
                if (bumldist < 14 && bumldist != -1) {
                    if (turnoff == true) {
                        return
                    }
                    chip = true
                    chipbuml(buml)
                    chip = false
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            log.info("${bumldist}")
            if (bumldist < 14 && bumldist != -1){
                chip = true
                chipbuml(buml)
                chip = false
            }
            co = player.CentergetMytile()
            check1 = checkTileForSupport()
            buildsupport()
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
            d = getnearestsupport()
            dis = getsupportdist(d)
        }
        if (options[0].equals("west")) {
            tile = player.CentergetMytile()
            Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
            if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                player.doAct("mine")
                start = player.getMytile() // стартовые коорды чара
                start = start.add(0, 11)
                Mine(start)
                Hourglass()
                player.cancelAct()
                sleep(100)
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                buml = getstonebumlingsnear()
                bumldist = getstonebumlingdist(buml)
                if (bumldist < 14 && bumldist != -1) {
                    if (turnoff == true) {
                        return
                    }
                    chip = true
                    chipbuml(buml)
                    chip = false
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            log.info("${bumldist}")
            if (bumldist < 14 && bumldist != -1){
                chip = true
                chipbuml(buml)
                chip = false
            }
            co = player.CentergetMytile()
            buildsupport()
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
            d = getnearestsupport()
            dis = getsupportdist(d)
        }
        if (options[0].equals("north")) {
            tile = player.CentergetMytile()
            Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
            if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                player.doAct("mine")
                start = player.getMytile() // стартовые коорды чара
                start = start.add(-11, 0)
                Mine(start)
                Hourglass()
                player.cancelAct()
                sleep(100)
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                buml = getstonebumlingsnear()
                bumldist = getstonebumlingdist(buml)
                if (bumldist < 14 && bumldist != -1) {
                    if (turnoff == true) {
                        return
                    }
                    chip = true
                    chipbuml(buml)
                    chip = false
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            log.info("${bumldist}")
            if (bumldist < 14 && bumldist != -1){
                chip = true
                chipbuml(buml)
                chip = false
            }
            co = player.CentergetMytile()
            buildsupport()
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
            d = getnearestsupport()
            dis = getsupportdist(d)
        }
        if (options[0].equals("south")) {
            tile = player.CentergetMytile()
            Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
            if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                player.doAct("mine")
                start = player.getMytile() // стартовые коорды чара
                start = start.add(11, 0)
                Mine(start)
                Hourglass()
                player.cancelAct()
                sleep(100)
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                buml = getstonebumlingsnear()
                bumldist = getstonebumlingdist(buml)
                if (bumldist < 14 && bumldist != -1) {
                    if (turnoff == true) {
                        return
                    }
                    chip = true
                    chipbuml(buml)
                    chip = false
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            log.info("${bumldist}")
            if (bumldist < 14 && bumldist != -1){
                chip = true
                chipbuml(buml)
                chip = false
            }
            co = player.CentergetMytile()
            buildsupport()
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
            d = getnearestsupport()
            dis = getsupportdist(d)
        }
    }
}

def buildcrossroad(){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    if (options[0].equals("east") && options[1].equals("yes")) {
        x = 0
        y = 1
        rot = -30720
        crs = getnearestcros()
        crsdist = getcrosdist(crs)
        if (crsdist >= 300) {
            log.info("Mining for crossroad")
            play = player.CentergetMytile()
            starting = play
            play = play.add(0, 0)
            ogm = checkNearTileForCrossroad()
            bnm = getnearestsupport()
            bnmdist = getsupportdist(bnm)
            if (ogm == false && bnmdist <= 122) {
                minecross()
            } else if (ogm == false && bnmdist > 122){
                while (ogm == false && bnmdist > 122){
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    ogm = checkNearTileForCrossroad()
                    bnm = getnearestsupport()
                    bnmdist = getsupportdist(bnm)
                    gowest()
                    if (ogm == true) break
                    if (ogm == false && bnmdist <= 122) {
                        minecross()
                    }
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            if (bumldist < 14 && bumldist != -1) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                chip = true
                chipbuml(buml)
                chip = false
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(play)
                movedist = player.SdistanceBetweenCoords(play)
                sleep(50)
                if (movedist == 0) break
            }
            wayX = -9999999
            wayY = 0
            crossroadcoords = play.add(wayX, wayY)
            player.moveToNoPF(crossroadcoords)
            movedist = player.SdistanceBetweenCoords(crossroadcoords)
            while (movedist != 0) {
                crs = getnearestcros()
                crsdist = getcrosdist(crs)
                last = player.getlastmsgfromArea()
                dcr = player.distanceTo(crs)
                if (dcr < 12) {
                    break
                }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(crossroadcoords)
                movedist = player.SdistanceBetweenCoords(crossroadcoords)
                sleep(50)
                if (movedist == 0) break
            }
            player.clickuderme()
            player.RightClickGobNoWait(crs)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Extend", 0)
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(play)
                    movedist = player.SdistanceBetweenCoords(play)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            we = player.CentergetMytile()
            player.buildrotate(x, y, rot)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Build", 0)
            }
            Hourglass()
            player.moveToNoPF(we)
            movedist = player.SdistanceBetweenCoords(we)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(we)
                    movedist = player.SdistanceBetweenCoords(we)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            player.unplaceThing()
        }
    }
    if (options[0].equals("west") && options[1].equals("yes")){
        x = 0
        y = -1
        rot = 2048;
        crs = getnearestcros()
        crsdist = getcrosdist(crs)
        if (crsdist >= 300) {
            log.info("Mining for crossroad")
            play = player.CentergetMytile()
            starting = play
            play = play.add(0, 0)
            ogm = checkNearTileForCrossroad()
            bnm = getnearestsupport()
            bnmdist = getsupportdist(bnm)
            if (ogm == false && bnmdist <= 122) {
                minecross()
            } else if (ogm == false && bnmdist > 122){
                while (ogm == false && bnmdist > 122){
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    ogm = checkNearTileForCrossroad()
                    bnm = getnearestsupport()
                    bnmdist = getsupportdist(bnm)
                    goeast()
                    if (ogm == true) break
                    if (ogm == false && bnmdist <= 122) {
                        minecross()
                    }
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            if (bumldist < 14 && bumldist != -1) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                chip = true
                chipbuml(buml)
                chip = false
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(play)
                movedist = player.SdistanceBetweenCoords(play)
                sleep(50)
                if (movedist == 0) break
            }
            wayX = 9999999
            wayY = 0
            crossroadcoords = play.add(wayX, wayY)
            player.moveToNoPF(crossroadcoords)
            movedist = player.SdistanceBetweenCoords(crossroadcoords)
            while (movedist != 0) {
                crs = getnearestcros()
                crsdist = getcrosdist(crs)
                last = player.getlastmsgfromArea()
                dcr = player.distanceTo(crs)
                if (dcr < 12) {
                    break
                }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(crossroadcoords)
                movedist = player.SdistanceBetweenCoords(crossroadcoords)
                sleep(50)
                if (movedist == 0) break
            }
            player.clickuderme()
            player.RightClickGobNoWait(crs)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Extend", 0)
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(play)
                    movedist = player.SdistanceBetweenCoords(play)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            we = player.CentergetMytile()
            player.buildrotate(x, y, rot)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Build", 0)
            }
            Hourglass()
            player.moveToNoPF(we)
            movedist = player.SdistanceBetweenCoords(we)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(we)
                    movedist = player.SdistanceBetweenCoords(we)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            player.unplaceThing()
        }
    }
    if (options[0].equals("north") && options[1].equals("yes")){
        x = 1
        y = 0
        rot = 18432;
        crs = getnearestcros()
        crsdist = getcrosdist(crs)
        if (crsdist >= 300) {
            log.info("Mining for crossroad")
            play = player.CentergetMytile()
            starting = play
            play = play.add(0, 0)
            d = getnearestsupport()
            dis = getsupportdist(d)
            til = checkNearTileForCrossroad()
            ogm = checkNearTileForCrossroad()
            bnm = getnearestsupport()
            bnmdist = getsupportdist(bnm)
            if (ogm == false && bnmdist <= 122) {
                minecross()
            } else if (ogm == false && bnmdist > 122){
                while (ogm == false && bnmdist > 122){
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    ogm = checkNearTileForCrossroad()
                    bnm = getnearestsupport()
                    bnmdist = getsupportdist(bnm)
                    gosouth()
                    if (ogm == true) break
                    if (ogm == false && bnmdist <= 122) {
                        minecross()
                    }
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            if (bumldist < 14 && bumldist != -1) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                chip = true
                chipbuml(buml)
                chip = false
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(play)
                movedist = player.SdistanceBetweenCoords(play)
                sleep(50)
                if (movedist == 0) break
            }
            wayX = 0
            wayY = 9999999
            crossroadcoords = play.add(wayX, wayY)
            player.moveToNoPF(crossroadcoords)
            movedist = player.SdistanceBetweenCoords(crossroadcoords)
            while (movedist != 0) {
                crs = getnearestcros()
                crsdist = getcrosdist(crs)
                last = player.getlastmsgfromArea()
                dcr = player.distanceTo(crs)
                if (dcr < 12) {
                    break
                }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(crossroadcoords)
                movedist = player.SdistanceBetweenCoords(crossroadcoords)
                sleep(50)
                if (movedist == 0) break
            }
            player.clickuderme()
            player.RightClickGobNoWait(crs)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Extend", 0)
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(play)
                    movedist = player.SdistanceBetweenCoords(play)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            we = player.CentergetMytile()
            player.buildrotate(x, y, rot)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Build", 0)
            }
            Hourglass()
            player.moveToNoPF(we)
            movedist = player.SdistanceBetweenCoords(we)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(we)
                    movedist = player.SdistanceBetweenCoords(we)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            player.unplaceThing()
        }
    }
    if (options[0].equals("south") && options[1].equals("yes")){
        x = -1
        y = 0
        rot = -14336;
        crs = getnearestcros()
        crsdist = getcrosdist(crs)
        if (crsdist >= 300) {
            log.info("Mining for crossroad")
            play = player.CentergetMytile()
            starting = play
            play = play.add(0, 0)
            ogm = checkNearTileForCrossroad()
            bnm = getnearestsupport()
            bnmdist = getsupportdist(bnm)
            if (ogm == false && bnmdist <= 122) {
                minecross()
            } else if (ogm == false && bnmdist > 122){
                while (ogm == false && bnmdist > 122){
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    ogm = checkNearTileForCrossroad()
                    bnm = getnearestsupport()
                    bnmdist = getsupportdist(bnm)
                    gonorth()
                    if (ogm == true) break
                    if (ogm == false && bnmdist <= 122) {
                        minecross()
                    }
                }
            }
            buml = getstonebumlingsnear()
            bumldist = getstonebumlingdist(buml)
            if (bumldist < 14 && bumldist != -1) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                chip = true
                chipbuml(buml)
                chip = false
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(play)
                movedist = player.SdistanceBetweenCoords(play)
                sleep(50)
                if (movedist == 0) break
            }
            wayX = 0
            wayY = -9999999
            crossroadcoords = play.add(wayX, wayY)
            player.moveToNoPF(crossroadcoords)
            movedist = player.SdistanceBetweenCoords(crossroadcoords)
            while (movedist != 0) {
                crs = getnearestcros()
                crsdist = getcrosdist(crs)
                last = player.getlastmsgfromArea()
                dcr = player.distanceTo(crs)
                if (dcr < 12) {
                    break
                }
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(crossroadcoords)
                movedist = player.SdistanceBetweenCoords(crossroadcoords)
                sleep(50)
                if (movedist == 0) break
            }
            player.clickuderme()
            player.RightClickGobNoWait(crs)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Extend", 0)
            }
            player.moveToNoPF(play)
            movedist = player.SdistanceBetweenCoords(play)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(play)
                    movedist = player.SdistanceBetweenCoords(play)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            we = player.CentergetMytile()
            player.buildrotate(x, y, rot)
            Crossroad = player.getWindow("Milestone")
            while (Crossroad == null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                Crossroad = player.getWindow("Milestone")
                if (Crossroad != null) break
            }
            if (Crossroad != null) {
                player.ButtonMenuClick("Milestone", "Build", 0)
            }
            Hourglass()
            player.moveToNoPF(we)
            movedist = player.SdistanceBetweenCoords(we)
            while (movedist != 0) {
                if (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    if (turnoff == true) {
                        return
                    }
                    player.moveToNoPF(we)
                    movedist = player.SdistanceBetweenCoords(we)
                    sleep(50)
                    if (movedist == 0) break
                }
            }
            player.unplaceThing()
        }
    }
}

def Mine(one){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    player.mine(one)
    dropitemoncoursor()
    dropSlots()
}

def goeast() {
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    co = player.CentergetMytile()
    co = co.add(11,0)
    buml = getstonebumlingsnear()
    bumldist = getstonebumlingdist(buml)
    log.info("${bumldist}")
    if (bumldist < 14 && bumldist != -1){
        chip = true
        chipbuml(buml)
        chip = false
    }
    player.moveToNoPF(co)
    movedist = player.SdistanceBetweenCoords(co)
    while (movedist != 0) {
        rock = getlooserocknear()
        if (rock != null) {
            player.SystemMessage("DANGER, BREAKING SCRIPT")
            player.playAlarmsound()
            turnoff = true
            return
        }
        runfromtroll()
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(co)
        movedist = player.SdistanceBetweenCoords(co)
        sleep(50)
        if (movedist < 3) break
    }
}

def gowest(){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    co = player.CentergetMytile()
    co = co.add(-11,0)
    buml = getstonebumlingsnear()
    bumldist = getstonebumlingdist(buml)
    log.info("${bumldist}")
    if (bumldist < 14 && bumldist != -1){
        chip = true
        chipbuml(buml)
        chip = false
    }
    player.moveToNoPF(co)
    movedist = player.SdistanceBetweenCoords(co)
    while (movedist != 0) {
        rock = getlooserocknear()
        if (rock != null) {
            player.SystemMessage("DANGER, BREAKING SCRIPT")
            player.playAlarmsound()
            turnoff = true
            return
        }
        runfromtroll()
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(co)
        movedist = player.SdistanceBetweenCoords(co)
        sleep(50)
        if (movedist < 3) break
    }
}

def gosouth(){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    co = player.CentergetMytile()
    co = co.add(0,11)
    buml = getstonebumlingsnear()
    bumldist = getstonebumlingdist(buml)
    log.info("${bumldist}")
    if (bumldist < 14 && bumldist != -1){
        chip = true
        chipbuml(buml)
        chip = false
    }
    player.moveToNoPF(co)
    movedist = player.SdistanceBetweenCoords(co)
    while (movedist != 0) {
        rock = getlooserocknear()
        if (rock != null) {
            player.SystemMessage("DANGER, BREAKING SCRIPT")
            player.playAlarmsound()
            turnoff = true
            return
        }
        runfromtroll()
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(co)
        movedist = player.SdistanceBetweenCoords(co)
        sleep(50)
        if (movedist < 3) break
    }
}

def gonorth(){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    co = player.CentergetMytile()
    co = co.add(0,-11)
    buml = getstonebumlingsnear()
    bumldist = getstonebumlingdist(buml)
    log.info("${bumldist}")
    if (bumldist < 14 && bumldist != -1){
        chip = true
        chipbuml(buml)
        chip = false
    }
    player.moveToNoPF(co)
    movedist = player.SdistanceBetweenCoords(co)
    while (movedist != 0) {
        rock = getlooserocknear()
        if (rock != null) {
            player.SystemMessage("DANGER, BREAKING SCRIPT")
            player.playAlarmsound()
            turnoff = true
            return
        }
        runfromtroll()
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true){
            return
        }
        player.moveToNoPF(co)
        movedist = player.SdistanceBetweenCoords(co)
        sleep(50)
        if (movedist < 3) break
    }
}

def Hourglass(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    houglass = player.getHourglass()
    if (houglass == -1.0) {
        while (houglass == -1.0) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true){
                return
            }
            if (options[0].equals("east") && build == false && chip == false && crossbuild == false && tpcrossroad == false && sto == false) {
                tile = player.CentergetMytile()
                Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
                if (Checktile.contains("gfx/tiles/mine")) break
                if (Checktile.contains("gfx/tiles/deepcave")) break
                if (Checktile.contains("gfx/tiles/wildcavern")) break
            } else if (options[0].equals("west") && build == false && chip == false && crossbuild == false && tpcrossroad == false && sto == false){
                tile = player.CentergetMytile()
                Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
                if (Checktile.contains("gfx/tiles/mine")) break
                if (Checktile.contains("gfx/tiles/deepcave")) break
                if (Checktile.contains("gfx/tiles/wildcavern")) break
            } else if (options[0].equals("north") && build == false && chip == false && crossbuild == false && tpcrossroad == false && sto == false){
                tile = player.CentergetMytile()
                Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
                if (Checktile.contains("gfx/tiles/mine")) break
                if (Checktile.contains("gfx/tiles/deepcave")) break
                if (Checktile.contains("gfx/tiles/wildcavern")) break
            } else if (options[0].equals("south") && build == false && chip == false && crossbuild == false && tpcrossroad == false && sto == false){
                tile = player.CentergetMytile()
                Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
                if (Checktile.contains("gfx/tiles/mine")) break
                if (Checktile.contains("gfx/tiles/deepcave")) break
                if (Checktile.contains("gfx/tiles/wildcavern")) break
            }
            houglass = player.getHourglass()
            if (houglass != -1) break
        }
    }
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true){
        return
    }
    if (houglass != -1.0) {
        houglass = player.getHourglass();
        while (houglass != -1.0) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            dropitemoncoursor()
            checkgems()
            dropStuff()
            dropSlots()
            last = player.getlastmsgfromArea()
            if (last.equals("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true){
                return
            }
            houglass = player.getHourglass();
            log.info("${houglass} Hourglass progress");
            sleep(200);
            if (houglass == -1.0) {
                log.info("${houglass} +  Hourglass progress ended");
                dropitemoncoursor()
                dropSlots()
                break
            }
        }
    }
}

def getlooserocknear(){
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true){
        return
    }
    gobs = player.findObjects("gfx/terobjs/looserock")
    if (!gobs.isEmpty()) {
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        def nearlist = []
        for (d = 0; d < gobs.size(); d++) {
            dist = player.distanceTo(gobs[d])
            nearlist.add(dist)
        }
        min = Collections.min(nearlist)
        numberinlist = nearlist.indexOf(min)
        return gobs[numberinlist]
    }
}

def getstonebumlingsnear(){
    gobs = player.findObjects("gfx/terobjs/bumlings/")
    if (!gobs.isEmpty()) {
        def nearlist = []
        for (d = 0; d < gobs.size(); d++) {
            dist = player.distanceTo(gobs[d])
            nearlist.add(dist)
        }
        min = Collections.min(nearlist)
        numberinlist = nearlist.indexOf(min)
        return gobs[numberinlist]
    }
}

def getstonebumlingdist(gobs){
    distance = player.distanceTo(gobs)
    return distance
}

def getsupportdist(gobs){
    if (gobs != null) {
        distance = player.distanceTo(gobs)
        return distance
    }
    return -1
}

def getnearestsupport(){
    gobs = player.findObjects("column")
        if (!gobs.isEmpty()) {
            def nearlist = []
            for (d = 0; d < gobs.size(); d++) {
                dist = player.distanceTo(gobs[d])
                nearlist.add(dist)
            }
            min = Collections.min(nearlist)
            numberinlist = nearlist.indexOf(min)
            return gobs[numberinlist]
        }
    return null
}

def chipbuml(gob){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    dropitemoncoursor()
    if (turnoff == true){
        return
    }
    co = player.CentergetMytile()
    player.RightClickGobnomove(gob)
    Gmenu = player.getflower()
    if (Gmenu == null) {
        while (Gmenu == null) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.RightClickGobnomove(gob)
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            sleep(200)
            Gmenu = player.getflower()
            if (Gmenu != null) break
        }
    }
    Gmenu = player.getflower()
    if (Gmenu != null) {
        Gmenu = player.getflower()
        Chopmenu = player.getpetal(Gmenu, "Chip stone")
        if (Chopmenu == null){
            while(Chopmenu == null){
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                Gmenu = player.getflower()
                Chopmenu = player.getpetal(Gmenu, "Сhip stone")
                if (Chopmenu != null) break
            }
        }
        if (turnoff == true){
            return
        }
        if (Chopmenu != null) {
            player.ClickPetal(Chopmenu)
            Hourglass()
            player.moveToNoPF(co)
            movedist = player.SdistanceBetweenCoords(co)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                player.moveToNoPF(co)
                movedist = player.SdistanceBetweenCoords(co)
                sleep(50)
                if (movedist == 0) break
            }
        }
    }
    log.info("finished Chipping")
    dropitemoncoursor()
    dropSlots()
}

def dropitemoncoursor(){
    item = inventory.getHoldingItem()
    if (item.isPresent()) {
        player.dropitemcour()
    }
}

def gethardmetal(){
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    free = inventory.getFreeSpace()
    if (free < 10){
        shit = inventory.getItemsContain("stack of")
        for (s = 0; s < 10; s++){
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            player.dropItemFromInventory(shit[s])
            free = inventory.getFreeSpace()
            if (free >= 10) break
        }
    }
    int wayX
    int wayY
    if (options[0].equals("east")) {
        if (options[1].equals("no")) {
            wayX = -99999999999
            wayY = 0
        }
    } else if (options[0].equals("west")){
        if (options[1].equals("no")){
            wayX = 99999999999
            wayY = 0
        }
    }else if (options[0].equals("north")){
        if (options[1].equals("no")){
            wayX = 0
            wayY = 99999999999
        }
    }else if (options[0].equals("south")){
        if (options[1].equals("no")){
            wayX = 0
            wayY = -99999999999
        }
    }
        goback = player.CentergetMytile()
    if (options[1].equals("no")) {
        gofor = goback.add(wayX, wayY)
        player.moveToNoPF(gofor)
        movedist = player.SdistanceBetweenCoords(gofor)
        while (movedist != 0) {
            chest = player.findObjects("chest")
            d = player.distanceTo(chest[0])
            if (chest != null) {
                d = player.distanceTo(chest[0])
                if (d <= 16 && d != -1) break
            }
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            player.moveToNoPF(gofor)
            movedist = player.SdistanceBetweenCoords(gofor)
            sleep(50)
            if (movedist == 0) {
                turnoff == true
                return
            }
        }
    }
    if (options[1].equals("yes")){
        tpcross(0, "small")
    }
        if (d < 22 && d != -1) {
            checkpl = player.getMyself()
            chest = player.findObjects("chest")
            d = player.distanceTo(chest[0])
            gooffchest = player.CentergetMytile()
            player.RightClickGobNoWait(chest[0])
            chest1 = player.getWindow("Chest")
            chest2 = player.getWindow("Exquisite Chest")
            chest3 = player.getWindow("Large Chest")
            if (chest1 == null && chest2 == null && chest3 == null) {
                while (chest1 == null && chest2 == null && chest3 == null) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    chest1 = player.getWindow("Chest")
                    chest2 = player.getWindow("Exquisite Chest")
                    chest3 = player.getWindow("Large Chest")
                    if (chest1 != null || chest2 != null || chest3 != null) break
                }
            }
            if (chest1 != null) {
                sleep(1000)
                met = inventory.getItemsContains(chest1, "Bar of")
                if (!met.isEmpty()) {
                    if (met.size() < 10) {
                        turnoff = true
                        return
                    }
                    for (a = 0; a < 10; a++) {
                        last = player.getlastmsgfromArea()
                        if (last.contains("stop")) {
                            turnoff = true
                            return
                        }
                        inventory.transfer(met[a])
                        sleep(100)
                    }
                }
            } else if (chest2 != null) {
                sleep(1000)
                met = inventory.getItemsContains(chest2, "Bar of")
                if (!met.isEmpty()) {
                    if (met.size() < 10) {
                        turnoff = true
                        return
                    }
                    for (a = 0; a < 10; a++) {
                        last = player.getlastmsgfromArea()
                        if (last.contains("stop")) {
                            turnoff = true
                            return
                        }
                        inventory.transfer(met[a])
                        sleep(100)
                    }
                }
            } else if (chest3 != null) {
                sleep(1000)
                met = inventory.getItemsContains(chest3, "Bar of")
                if (!met.isEmpty()) {
                    if (met.size() < 10) {
                        turnoff = true
                        return
                    }
                    for (a = 0; a < 10; a++) {
                        last = player.getlastmsgfromArea()
                        if (last.contains("stop")) {
                            turnoff = true
                            return
                        }
                        inventory.transfer(met[a])
                        sleep(100)
                    }
                }
            }
            player.moveToNoPF(gooffchest)
            movedist = player.SdistanceBetweenCoords(gooffchest)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                movedist = player.SdistanceBetweenCoords(gooffchest)
                if (movedist == 0) break
            }
            if (options[1].equals("no")) {
                player.moveToNoPF(goback)
                movedist = player.SdistanceBetweenCoords(goback)
                while (movedist != 0) {
                    last = player.getlastmsgfromArea()
                    if (last.contains("stop")) {
                        turnoff = true
                        return
                    }
                    movedist = player.SdistanceBetweenCoords(goback)
                    if (movedist == 0) break
                }
            }
            if (options[1].equals("yes")){
                if (options[0].equals("north")) {
                    tpcross(options[2], "big")
                } else if (options[0].equals("east")){
                    tpcross(options[2], "big")
                } else if (options[0].equals("south")){
                    tpcross(options[2], "big")
                } else if (options[0].equals("west")){
                    tpcross(options[2], "big")
                }
            }
        }
    }
def tpcross(number, string){
    if (string == "small") {
        cr = getnearestcros()
    } else if (string == "big"){
        cr = getnearestmilestone()
    }
    player.RightClickGobNoWait(cr)
    Crossroad = player.getWindow("Milestone")
    while (Crossroad == null) {
        last = player.getlastmsgfromArea()
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        Crossroad = player.getWindow("Milestone")
        if (Crossroad != null) break
    }
    if (Crossroad != null) {
        player.ButtonMenuClick("Milestone", "Travel", number)
    }
    Hourglass()
    player.mapClick(0,0,1,0)
    checkpl = player.getMyself()
    if (checkpl == null){
        while (checkpl == null){
            checkpl = player.getMyself()
            if (checkpl != null) break
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
        }
    }
}

def getnearestcros(){
    gobs = player.findObjects("gfx/terobjs/road/milestone-stone-m")
    if (!gobs.isEmpty()) {
        def nearlist = []
        for (d = 0; d < gobs.size(); d++) {
            dist = player.distanceTo(gobs[d])
            nearlist.add(dist)
        }
        min = Collections.min(nearlist)
        numberinlist = nearlist.indexOf(min)
        return gobs[numberinlist]
    }
}

def getnearestmilestone(){
    gobs = player.findObjects("gfx/terobjs/road/milestone-stone-e")
    if (!gobs.isEmpty()) {
        def nearlist = []
        for (d = 0; d < gobs.size(); d++) {
            dist = player.distanceTo(gobs[d])
            nearlist.add(dist)
        }
        min = Collections.min(nearlist)
        numberinlist = nearlist.indexOf(min)
        return gobs[numberinlist]
    }
}

def getcrosdist(gobs){
    distance = player.distanceTo(gobs)
    return distance
}

def minecross(){

    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (options[0].equals("east") && options[1].equals("yes")) {
        tile = player.CentergetMytile()
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
        if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.doAct("mine")
            a = getnearestcros()
            dist = getcrosdist(a)
            if (dist >= 300) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                start = player.CentergetMytile() // стартовые коорды чара
                start = start.add(0, 11)
                Mine(start)
                Hourglass()
                dropSlots()
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
            }
            player.cancelAct()
        }
    }
    if (options[0].equals("west") && options[1].equals("yes")) {
        tile = player.CentergetMytile()
        Checktile = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
        if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.doAct("mine")
            a = getnearestcros()
            dist = getcrosdist(a)
            if (dist >= 300) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                start = player.CentergetMytile() // стартовые коорды чара
                start = start.add(0, -11)
                Mine(start)
                Hourglass()
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
            }
            player.cancelAct()
        }
    }
    if (options[0].equals("north") && options[1].equals("yes")) {
        tile = player.CentergetMytile()
        Checktile = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
        if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.doAct("mine")
            a = getnearestcros()
            dist = getcrosdist(a)
            if (dist >= 300) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                start = player.CentergetMytile() // стартовые коорды чара
                start = start.add(11, 0)
                Mine(start)
                Hourglass()
                rock = getlooserocknear()
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
            }
            player.cancelAct()
        }
    }
    if (options[0].equals("south") && options[1].equals("yes")) {
        tile = player.CentergetMytile()
        Checktile = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
        if (!Checktile.contains("gfx/tiles/mine") && !Checktile.contains("gfx/tiles/deepcave") && !Checktile.contains("gfx/tiles/wildcavern")) {
            rock = getlooserocknear()
            if (rock != null) {
                player.SystemMessage("DANGER, BREAKING SCRIPT")
                player.playAlarmsound()
                turnoff = true
                return
            }
            runfromtroll()
            player.doAct("mine")
            a = getnearestcros()
            dist = getcrosdist(a)
            if (dist >= 300) {
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
                start = player.CentergetMytile() // стартовые коорды чара
                start = start.add(-11, 0)
                Mine(start)
                Hourglass()
                rock = getlooserocknear()
                if (rock != null) {
                    player.SystemMessage("DANGER, BREAKING SCRIPT")
                    player.playAlarmsound()
                    turnoff = true
                    return
                }
                runfromtroll()
            }
            player.cancelAct()
        }
    }
}

def buildsupport(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) {
        return
    }
    dropitemoncoursor()
    metal = inventory.getItemsContain("Bar of")
    if (metal.isEmpty()){
        turnoff = true
        return
    }
    if (options[0].equals("east")) {
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        player.ActSupport()
        a = player.CentergetMytile()
        a = a.add(0, -11)
        player.placecenter(a)
        column = player.getWindow("Stone Column")
        while (column == null) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            column = player.getWindow("Stone Column")
            if (column != null) {
                sleep(1000)
                break
            }
        }
        player.ButtonMenuClick("Stone Column", "Build", 0)
        Hourglass()
    }else if (options[0].equals("west")){
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        player.ActSupport()
        a = player.CentergetMytile()
        a = a.add(0, 11)
        player.placecenter(a)
        column = player.getWindow("Stone Column")
        while (column == null) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            column = player.getWindow("Stone Column")
            if (column != null) {
                sleep(1000)
                break
            }
        }
        player.ButtonMenuClick("Stone Column", "Build", 0)
        Hourglass()
    }else if (options[0].equals("north")){
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        player.ActSupport()
        a = player.CentergetMytile()
        a = a.add(-11, 0)
        player.placecenter(a)
        column = player.getWindow("Stone Column")
        while (column == null) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            column = player.getWindow("Stone Column")
            if (column != null) {
                sleep(1000)
                break
            }
        }
        player.ButtonMenuClick("Stone Column", "Build", 0)
        Hourglass()
    }else if (options[0].equals("south")){
        buml = getstonebumlingsnear()
        bumldist = getstonebumlingdist(buml)
        if (bumldist < 14 && bumldist != -1){
            chip = true
            chipbuml(buml)
            chip = false
        }
        player.ActSupport()
        a = player.CentergetMytile()
        a = a.add(11, 0)
        player.placecenter(a)
        column = player.getWindow("Stone Column")
        while (column == null) {
            last = player.getlastmsgfromArea()
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            column = player.getWindow("Stone Column")
            if (column != null) {
                sleep(1000)
                break
            }
        }
        player.ButtonMenuClick("Stone Column", "Build", 0)
        Hourglass()
    }
    player.cancelAct()
    }
def dropSlots(){
space = inventory.getFreeSpace()
    if (space < 2){
        space = inventory.getFreeSpace()
        st = inventory.getItemsContain("stack of")
        if (space < 2) {
            if (!st.isEmpty()) {
                player.dropItemFromInventory(st[0])
            }
        }
    }
}
def dropStuff(){
    last = player.getlastmsgfromArea()
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    pet = inventory.getItemsContain("Petrified")
    if (!pet.isEmpty()){
        player.dropItemFromInventory(pet[0])
    }
    gem1 = inventory.getItemsContain("Tiny")
    if (!gem1.isEmpty()){
        player.dropItemFromInventory(gem1[0])
    }
    gem2 = inventory.getItemsContain("Small")
    if(!gem2.isEmpty()){
        player.dropItemFromInventory(gem2[0])
    }
    strange = inventory.getItemsContain("Strange")
    if (!strange.isEmpty()){
        player.dropItemFromInventory(strange[0])
    }
}

def checkforPreciousTiles(){
    tile = player.CentergetMytile() // стартовые коорды чара
    n = 0
    t1 = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() - 11)
    t2 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() - 11)
    t3 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger())
    t4 = player.tileResnameAt(tile.x.round().toInteger() + 11, tile.y.round().toInteger() + 11)
    t5 = player.tileResnameAt(tile.x.round().toInteger(), tile.y.round().toInteger() + 11)
    t6 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() + 11)
    t7 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger())
    t8 = player.tileResnameAt(tile.x.round().toInteger() - 11, tile.y.round().toInteger() - 11)
    if (t1.contains("gfx/tiles/rocks/argentite") || t1.contains("gfx/tiles/rocks/galena") || t1.contains("gfx/tiles/rocks/hornsilver") || t1.contains("gfx/tiles/rocks/nagyagite") || t1.contains("gfx/tiles/rocks/petzite") || t1.contains("gfx/tiles/rocks/sylvanite")){
        if (t1.contains("gfx/tiles/rocks/argentite") || t1.contains("gfx/tiles/rocks/galena") || t1.contains("gfx/tiles/rocks/hornsilver")){
            player.MarkOnMe("Silver Ore")
            n++
        } else if(t1.contains("gfx/tiles/rocks/nagyagite") || t1.contains("gfx/tiles/rocks/petzite") || t1.contains("gfx/tiles/rocks/sylvanite")){
            player.MarkOnMe("Gold Ore")
            n++
        }
    }
    if (t2.contains("gfx/tiles/rocks/argentite") || t2.contains("gfx/tiles/rocks/galena") || t2.contains("gfx/tiles/rocks/hornsilver") || t2.contains("gfx/tiles/rocks/nagyagite") || t2.contains("gfx/tiles/rocks/petzite") || t2.contains("gfx/tiles/rocks/sylvanite")) {
        if (n == 0) {
            if (t2.contains("gfx/tiles/rocks/argentite") || t2.contains("gfx/tiles/rocks/galena") || t2.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t2.contains("gfx/tiles/rocks/nagyagite") || t2.contains("gfx/tiles/rocks/petzite") || t2.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t3.contains("gfx/tiles/rocks/argentite") || t3.contains("gfx/tiles/rocks/galena") || t3.contains("gfx/tiles/rocks/hornsilver") || t3.contains("gfx/tiles/rocks/nagyagite") || t3.contains("gfx/tiles/rocks/petzite") || t3.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t3.contains("gfx/tiles/rocks/argentite") || t3.contains("gfx/tiles/rocks/galena") || t3.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t3.contains("gfx/tiles/rocks/nagyagite") || t3.contains("gfx/tiles/rocks/petzite") || t3.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t4.contains("gfx/tiles/rocks/argentite") || t4.contains("gfx/tiles/rocks/galena") || t4.contains("gfx/tiles/rocks/hornsilver") || t4.contains("gfx/tiles/rocks/nagyagite") || t4.contains("gfx/tiles/rocks/petzite") || t4.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t4.contains("gfx/tiles/rocks/argentite") || t4.contains("gfx/tiles/rocks/galena") || t4.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t4.contains("gfx/tiles/rocks/nagyagite") || t4.contains("gfx/tiles/rocks/petzite") || t4.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t5.contains("gfx/tiles/rocks/argentite") || t5.contains("gfx/tiles/rocks/galena") || t5.contains("gfx/tiles/rocks/hornsilver") || t5.contains("gfx/tiles/rocks/nagyagite") || t5.contains("gfx/tiles/rocks/petzite") || t5.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t5.contains("gfx/tiles/rocks/argentite") || t5.contains("gfx/tiles/rocks/galena") || t5.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t5.contains("gfx/tiles/rocks/nagyagite") || t5.contains("gfx/tiles/rocks/petzite") || t5.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t6.contains("gfx/tiles/rocks/argentite") || t6.contains("gfx/tiles/rocks/galena") || t6.contains("gfx/tiles/rocks/hornsilver") || t6.contains("gfx/tiles/rocks/nagyagite") || t6.contains("gfx/tiles/rocks/petzite") || t6.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t6.contains("gfx/tiles/rocks/argentite") || t6.contains("gfx/tiles/rocks/galena") || t6.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t6.contains("gfx/tiles/rocks/nagyagite") || t6.contains("gfx/tiles/rocks/petzite") || t6.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t7.contains("gfx/tiles/rocks/argentite") || t7.contains("gfx/tiles/rocks/galena") || t7.contains("gfx/tiles/rocks/hornsilver") || t7.contains("gfx/tiles/rocks/nagyagite") || t7.contains("gfx/tiles/rocks/petzite") || t7.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t7.contains("gfx/tiles/rocks/argentite") || t7.contains("gfx/tiles/rocks/galena") || t7.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t7.contains("gfx/tiles/rocks/nagyagite") || t7.contains("gfx/tiles/rocks/petzite") || t7.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    if (t8.contains("gfx/tiles/rocks/argentite") || t8.contains("gfx/tiles/rocks/galena") || t8.contains("gfx/tiles/rocks/hornsilver") || t8.contains("gfx/tiles/rocks/nagyagite") || t8.contains("gfx/tiles/rocks/petzite") || t8.contains("gfx/tiles/rocks/sylvanite")){
        if (n == 0) {
            if (t8.contains("gfx/tiles/rocks/argentite") || t8.contains("gfx/tiles/rocks/galena") || t8.contains("gfx/tiles/rocks/hornsilver")) {
                player.MarkOnMe("Silver Ore")
                n++
            } else if (t8.contains("gfx/tiles/rocks/nagyagite") || t8.contains("gfx/tiles/rocks/petzite") || t8.contains("gfx/tiles/rocks/sylvanite")) {
                player.MarkOnMe("Gold Ore")
                n++
            }
        }
    }
    n = 0
}
def getallstoneinv(){
    int s = 0
    def ston = ["Alabaster","Apatite","Arkose","Basalt","Bat Rock","Black Coal","Black Ore","Bloodstone","Breccia","Cassiterite","Cat Gold","Chalcopyrite","Chert","Cinnabar","Diabase","Diorite","Direvein","Dolomite","Dross","Eclogite","Feldspar","Flint","Fluorospar","Gabbro","Galena","Gneiss","Granite","Graywacke","Greenschist","Heavy Earth","Horn Silver","Hornblende","Iron Ochre","Jasper","Korund","Kyanite","Lava Rock","Lead Glance","Leaf Ore","Limestone","Malachite","Marble","Meteorite","Mica","Microlite","Obsidian","Olivine","Orthoclase","Peacock Ore","Pegmatite","Porphyry","Pumice","Quarryartz","Quartz","Rhyolite","Rock Crystal","Sandstone","Schist","Schrifterz","Serpentine","Shard of Conch","Silvershine","Slag","Slate","Soapstone","Sodalite","Sunstone","Wine Glance","Zincspar"]
    stone = inventory.getAllitemsquantityfromstack("stack of")
    for (a = 0; a < ston.size(); a++){
        s += inventory.getAllitemsquantitynotstack(ston[a])
    }
    stone = stone + s
    return stone
}
def OpenStoneStockpile(){
    if (turnoff == true){
        return
    }
    stock = player.findObjects("gfx/terobjs/stockpile-stone")
    if (!stock.isEmpty()) {
        player.RightClickGobnomove(stock[0])
        w = player.getWindow("Stockpile")
        if (w == null) {
            while (w = null) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) return
                w = player.getWindow("Stockpile")
                if (w != null) break
            }
        }
    } else if (stock.isEmpty){
        turnoff == true
        return
    }
}
def getstonefromstockpile() {
    if (last.contains("stop")) {
        turnoff = true
        return
    }
    if (turnoff == true) return
    def ston = ["Alabaster","Apatite","Arkose","Basalt","Bat Rock","Black Coal","Black Ore","Bloodstone","Breccia","Cassiterite","Cat Gold","Chalcopyrite","Chert","Cinnabar","Diabase","Diorite","Direvein","Dolomite","Dross","Eclogite","Feldspar","Flint","Fluorospar","Gabbro","Galena","Gneiss","Granite","Graywacke","Greenschist","Heavy Earth","Horn Silver","Hornblende","Iron Ochre","Jasper","Korund","Kyanite","Lava Rock","Lead Glance","Leaf Ore","Limestone","Malachite","Marble","Meteorite","Mica","Microlite","Obsidian","Olivine","Orthoclase","Peacock Ore","Pegmatite","Porphyry","Pumice","Quarryartz","Quartz","Rhyolite","Rock Crystal","Sandstone","Schist","Schrifterz","Serpentine","Shard of Conch","Silvershine","Slag","Slate","Soapstone","Sodalite","Sunstone","Wine Glance","Zincspar"]
    int stone = inventory.getAllitemsquantityfromstack("stack of")
    int s = 0
    for (v = 0; v < ston.size(); v++){
        s += inventory.getAllitemsquantitynotstack(ston[v])
    }
    stone = stone + s
    w = player.getWindow("Stockpile")
    if (w == null) {
        while (w == null) {
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) return
            w = player.getWindow("Stockpile")
            if (w != null) break;
        }
    }
    if (w != null) {
        m = 0
        s = 0
        b = inventory.getisboxes(w)
        while (stone != 30) {
            if (last.contains("stop")) {
                turnoff = true
                return
            }
            if (turnoff == true) return
            w = player.getWindow("Stockpile")
            if (w == null) break
            stone = inventory.getAllitemsquantityfromstack("stack of")
            for (a = 0; a < ston.size(); a++){
                s += inventory.getAllitemsquantitynotstack(ston[a])
            }
            stone = stone + s
            if (stone == 30) break
            if (w != null && stone != 30){
                inventory.transferfromstockpile(b[0], 1)
            }
            sleep(100)
            s = 0
            stone = inventory.getAllitemsquantityfromstack("stack of")
            for (a = 0; a < ston.size(); a++){
                s += inventory.getAllitemsquantitynotstack(ston[a])
            }
            stone = stone + s
            if (stone == 30) break
            b = inventory.getisboxes(w)
            //log.info("${stone}")
            s = 0
        }
    }
}
def getston() {
    rock = getlooserocknear()
    if (rock != null) {
        player.SystemMessage("DANGER, BREAKING SCRIPT")
        player.playAlarmsound()
        turnoff = true
        return
    }
    runfromtroll()
    if (turnoff == true){
        return
    }
    sn = getallstoneinv()
    if (sn < 30){
        if (options[1].equals("yes")){
            tpcross(0, "small")
        }
    }
    while (sn < 30) {
        if (last.contains("stop")) {
            turnoff = true
            return
        }
        if (turnoff == true) return
        OpenStoneStockpile()
        getstonefromstockpile()
        sn = getallstoneinv()
        if (sn == 30) {
            if (options[1].equals("yes")){
                if (options[0].equals("north")) {
                    tpcross(options[2], "big")
                } else if (options[0].equals("east")){
                    tpcross(options[2], "big")
                } else if (options[0].equals("south")){
                    tpcross(options[2], "big")
                } else if (options[0].equals("west")){
                    tpcross(options[2], "big")
                }
            }
            break
        }
    }
}

def checkgems(){
    quality = 0
    for (g = 0; g < gemsforfind.size(); g++){
    gems = inventory.getItemsContain(gemsforfind[g])
    if (!gems.isEmpty() && !ignoregems.contains(gemsforfind[g])){
        for (h = 0; h < gems.size(); h++) {
            ignoregems.add(gems[g])
            if (gemsforfind[g] == "Diamond") {
                quality = inventory.getitemquality(gems[h])
                player.MarkOnMe("Diamond Spot. Quality: ${Math.round(quality)}")
            } else if (gemsforfind[g] == "Ruby") {
                quality = inventory.getitemquality(gems[h])
                player.MarkOnMe("Ruby Spot. Quality: ${Math.round(quality)}")
            } else if (gemsforfind[g] == "Sapphire") {
                quality = inventory.getitemquality(gems[h])
                player.MarkOnMe("Sapphire Spot. Quality: ${Math.round(quality)}")
            } else if (gemsforfind[g] == "Jade") {
                quality = inventory.getitemquality(gems[h])
                player.MarkOnMe("Jade Spot. Quality: ${Math.round(quality)}")
            }
        }
    }
}
}
def runfromtroll(){
    troll = player.findObjects("gfx/kritter/troll/troll")
    if (!troll.isEmpty()) {
        player.setspeed(2)
        player.playAlarmsound()
        if (options[0].equals("east")) {
            start = player.CentergetMytile()
            runtroll = start.add(-9999999, 0)
            player.moveToNoPF(runtroll)
            movedist = player.SdistanceBetweenCoords(runtroll)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(runtroll)
                movedist = player.SdistanceBetweenCoords(runtroll)
                sleep(50)
                if (movedist == 0) break
            }
        }
        if (options[0].equals("west")) {
            start = player.CentergetMytile()
            runtroll = start.add(9999999, 0)
            player.moveToNoPF(runtroll)
            movedist = player.SdistanceBetweenCoords(runtroll)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(runtroll)
                movedist = player.SdistanceBetweenCoords(runtroll)
                sleep(50)
                if (movedist == 0) break
            }
        }
        if (options[0].equals("north")) {
            start = player.CentergetMytile()
            runtroll = start.add(0, 9999999)
            player.moveToNoPF(runtroll)
            movedist = player.SdistanceBetweenCoords(runtroll)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(runtroll)
                movedist = player.SdistanceBetweenCoords(runtroll)
                sleep(50)
                if (movedist == 0) break
            }
        }
        if (options[0].equals("south")) {
            start = player.CentergetMytile()
            runtroll = start.add(0, -9999999)
            player.moveToNoPF(runtroll)
            movedist = player.SdistanceBetweenCoords(runtroll)
            while (movedist != 0) {
                last = player.getlastmsgfromArea()
                if (last.contains("stop")) {
                    turnoff = true
                    return
                }
                if (turnoff == true) {
                    return
                }
                player.moveToNoPF(runtroll)
                movedist = player.SdistanceBetweenCoords(runtroll)
                sleep(50)
                if (movedist == 0) break
            }
        }
    }
}
def fightslimes(){

}
def fightbats(){

}