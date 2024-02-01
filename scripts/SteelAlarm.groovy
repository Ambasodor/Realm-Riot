import groovy.time.TimeCategory
def date
int end = 0
while (true) {
    window = player.getWindow("Steelbox")
    if (window != null) {
        def fuel = player.getFuel(window)
        def getwrought = inventory.getItems(window, "Bar of Wrought Iron")
        a = player.getItemMeters(getwrought[0])
        log.info("${a}")
        def getsteel = inventory.getItems(window, "Bar of Steel")
        if (!getsteel.isEmpty()) {
            player.discordSay("<@&1177320218889048114> STEEL ARE READY!")
            break
        }
        if (getwrought.isEmpty()){
            log.info("THERE'S NOTHING INSIDE")
            break
        }
        if (!getwrought.isEmpty() && getsteel.isEmpty()) {
            log.info("${fuel}")
            if (a == 0.0 && fuel == 0.0){
             player.discordSay("<@&1177320218889048114> YOU FAGGOTS, YOU FORGOT TO FUEL STEEL CRUCIBLES - IT ${fuel}% NOW, I HATE YOU ALL!")
                break;
            }
            if (a < 80 && a != -1) {
                if (fuel < 40) {
                    def date01 = new Date()
                    if (date != null && date01.after(date)) {
                        date = null
                    }
                    if (date == null) {
                        player.discordSay("<@&1177320218889048114> I HAVE ${fuel}% FUEL IN STEEL CRUCIBLES - WROUGHT AT ${a}%")
                    }
                    if (date == null) {
                        date = newdate(10)
                    }
                }
            }
            if (a >= 80 && a != -1){
                if (fuel < 100 && end == 0) {
                    player.discordSay("<@&1177320218889048114> STEEL IS MORE THAN 80%, BUT FUEL LOWER THAN 100%. FUEL IT TO 100 AND LEFT IT")
                    end++
                }
            }
        }
        sleep(1000)
    }
    if (window == null){
        break
    }
}

def newdate (addMinutes){
    def currentDate  = new Date()
    use( TimeCategory ) {
        def newCurrentDate = currentDate + addMinutes.minutes
        return newCurrentDate
    }
}