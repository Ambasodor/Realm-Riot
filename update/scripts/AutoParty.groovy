def label = null
def window = null
def status

status = player.Party()
while (status == false) {
    if (status == false) {
        player.doAct("permshare")
        sleep(1000)
        status = player.Party()
        log.info("${status}")
    }
    if (status == true) break;
}
while (true){
while (label == null) {
    label = player.PartyCheck()
    if (label != null){
        log.info("${label}")
        break
    }
}
    if (label != null) {
        if (label != null && !label.contains("???") & !label.contains("his kin")) {
            player.ButtonMenuClick("Invitation", "Yes", 0)
            String result = label.substring(0, label.indexOf('\s'))
            player.discordSay("Accept party from: ${result}.")
            label = null
        }
        sleep(50)
        if (label != null && !label.contains("his kin") & label.contains("???")) {
            player.discordSay("Unknown tries to invite me to party. Declined")
            sleep(1000)
            player.ButtonMenuClick("Invitation", "No", 0)
            label = null
        }
        sleep(50)
        if (label != null && label.contains("his kin")) {
            player.areaSay("No!")
            sleep(50)
            player.ButtonMenuClick("Invitation", "No", 0)
            player.discordSay("Someone tries to kin me. Declined. Can be kinned only via hs!")
            label = null
        }
        sleep(200)
        label = null
    }
}

