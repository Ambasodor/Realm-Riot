int once = 0
def logout() {
    while (true) {
        player.logout()
        sleep(5000)
        break
    }
}

while (true) {
    def lul = 0
    account = "Salt1Baton"
    password = "taw36gT32"
    characters = ["Salt"]
    while (true) {
        player.login(account, password, characters[0])
        sleep(5000)
        def chara = player.GetAccountName();
        break
    }
    basin = player.findObjects("gfx/terobjs/saltbasin")
    if (basin[0] == null) {
        while (true) {
            basin = player.findObjects("gfx/terobjs/saltbasin")
            if (basin[0] != null) break
        }
    }
    int kape = 0
    player.doAct("inspect")
    while (true) {
        if(basin[0] != null) {
            player.Click(basin[0], 1, 0)
        }
        a = player.getTimeInspect()
        b = player.getLastTimeInspectString()
        if(a == -10 || b == ""){
            while(a == -10 || b == "") {
                player.Click(basin[0], 1, 0)
                a = player.getTimeInspect()
                b = player.getLastTimeInspectString()
                if (a != -10 || b != "")break
            }
        }
        c = player.getTimeInspect()
        d = player.getLastTimeInspectString()
        if(c == -10 || d == ""){
            while(c == -10 || d == "") {
                player.Click(basin[0], 1, 0)
                c = player.getTimeInspect()
                d = player.getLastTimeInspectString()
                if (c != -10 || d != "")break
            }
        }
        if (kape == 0) {
            log.info("${a} ${b} left")
        }
        /*
        if (a == c && b == d) {
            kape = 1
        }
        if (a != c && b != d) {
            kape = 0
        }

         */
        sleep(1000)
        if (b.equals("days") && a > 2) {
            log.info("Time left for salt: ${a} ${b}")
            lul = 1
            logout()
            break
        }
        if (b.equals("days") && a == 2){
            log.info("Time left for salt: ${a} ${b}")
            if (once == 0) {
                player.discordSay("Time left for salt: ${a} ${b}")
                once++
            }
            lul = 1
            logout()
            break
        }
        if (b.equals("hours") && a > 6){
            log.info("Time left for salt: ${a} ${b}")
            lul = 1
            logout()
            break
        }
        if (b.equals("hours") && a <= 6 && a > 2){
            log.info("Time left for salt: ${a} ${b}")
            player.discordSay("<@219213705919987712> Time left for salt: ${a} ${b}")
            lul = 1
            logout()
            break
        }
        if (a <= 60 && b.equals("minutes") && a > 30 || b.equals("hours") && a == 2) {
            player.discordSay("<@219213705919987712> Time left for salt: ${a} ${b}")
            lul = 2
            logout()
            break
        }
        if (a < 30 && b.equals("minutes") && a > 5) {
            //player.discordSay("<@219213705919987712> Time left for salt: ${a} ${b}")
            log.info("minutes left: ${a}")
            lul = 3
            logout()
            break
        }
        if (a <= 5 && b.equals("minutes") && a > 2) {
            //player.discordSay("<@219213705919987712> Time left for salt: ${a} ${b}")
            log.info("minutes left: ${a}")
            lul = 4
            logout()
            break
        }
        if (a <= 60 && b.equals("seconds") || a == 2 && b.equals("minutes")) {
            player.discordSay("<@219213705919987712> Time left for salt: ${a} ${b}")
            lul = 5
            logout()
            player.discordSay("<@828099600123428864> run findsalt")
            break
        }
    }
    if (lul == 1) {
        log.info("Sleeping 1h")
        sleep(3600000)
    }
    if (lul == 2) {
        log.info("Sleeping 15m")
        sleep(900000)
    }
    if (lul == 3) {
        log.info("Sleeping 1m")
        sleep(60000)
    }
    if (lul == 4) {
        log.info("Sleeping 30sec")
        sleep(30000)
    }
    if (lul == 5) break
}

