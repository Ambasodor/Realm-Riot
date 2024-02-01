def pickbarrel(){
    bar = getnearestbarrel()
    start = player.CentergetMytile()
    player.doAct("carry")
    player.PickupGob(bar)
    dist = player.distanceTo(bar)
    while(dist != 0){
        dist = player.distanceTo(bar)
        if (dist == 0) break
    }
    moveTo(startpos)
}

def moveTo(coords){
    player.moveToNoPF(coords)
    movedist = player.SdistanceBetweenCoords(coords)
    while (movedist != 0) {
        player.moveToNoPF(coords)
        movedist = player.SdistanceBetweenCoords(coords)
        sleep(50)
        if (movedist == 0) break
    }
}

def getnearestbarrel(){
    gobs = player.findObjects("barrel")
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

def getcowdist(gobs){
    distance = player.distanceTo(gobs)
    return distance
}

def moveandmilk(){
    akek = 0
    start = player.CentergetMytile()
    for(a = 0; a < pens; a++) {
        if (once == 0) {
            next = start.add(0, -22)
            moveTo(next)
            once++
            akek = 1
        }
        if (once != 0 && akek == 0){
            next = next.add(0, -22)
            moveTo(next)
        }
        akek = 0
        cow = getnearestcow()
        before = getcowdist(cow)
        player.rightclickgob(cow)
        player.beforerunclay()
        player.ClaywaitUntilStops()
        after = getcowdist(cow)
        moveTo(next)
        if (player.isOverlay(bar, "milk") && before != after) {
            milk++
        }
        point = player.CentergetMytile()
        bar = getnearestbarrel()
            if (milk > 3){
            moveTo(startpos)
            putincistern()
            milk = 0
        }
    }
    moveTo(startpos)
    if (secondpart == true){
        start = player.CentergetMytile()
        once = 0
        start = start.add(11,0)
        moveTo(start)
        for(a = 0; a < pens; a++) {
            if (once == 0) {
                next = start.add(0, -22)
                moveTo(next)
                once++
                akek = 1
            }
            if (once != 0 && akek == 0){
                next = next.add(0, -22)
                moveTo(next)
            }
            akek = 0
            cow = getnearestcow()
            before = getcowdist(cow)
            player.rightclickgob(cow)
            player.beforerunclay()
            player.ClaywaitUntilStops()
            after = getcowdist(cow)
            moveTo(next)
            if (player.isOverlay(bar, "milk") && before != after) {
                    milk++
            }
            point = player.CentergetMytile()
            bar = getnearestbarrel()
            if (milk > 3){
                moveTo(startpos)
                putincistern()
                milk = 0
            }
        }
    }
    if (milk != 0){
        moveTo(startpos)
        putincistern()
        milk = 0
    }
    moveTo(startpos)
}

def putincistern(){
    cist = getcistern()
    player.rightclickgob(cist)
    player.beforerunclay()
    player.ClaywaitUntilStops()
}

def getcistern(){
    gobs = player.findObjects("cistern")
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
def getnearestcow(){
        gobs = player.findObjects("cattle/cattle")
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

startpos = player.CentergetMytile()
while(true) {
    player.setspeed(1)
    pens = 26
    milk = 0
    once = 0
    secondpart = true
    moveTo(startpos)
    pickbarrel()
    moveandmilk()
    moveTo(startpos)
    sleep(1800000)
}