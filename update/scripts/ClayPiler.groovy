def Clay = player.findObjects("clay-acre")
def ClayStockpile = player.findObjects("stockpile-clay")
while (Clay.size != 0) {
    Clay = player.findObjects("clay-acre")
    if (Clay.size == 0) break
    Clay = player.findObjects("clay-acre")
    Freespace = inventory.getFreeSpace()
    if (Clay != null) {
        for (a = 0; a < Clay.size(); a++) {
            Freespace = inventory.getFreeSpace()
            OldSpace = Freespace
            if (Freespace == 0) break
            player.PickupGob(Clay[a])
            while (Freespace == OldSpace) {
                Freespace = inventory.getFreeSpace()
                if (Freespace != OldSpace) break
            }
        }
        Clay = player.findObjects("clay-acre")
        log.info("All clay are picked.")
        if (ClayStockpile != null) {
            Freespace = inventory.getFreeSpace()
            slay = inventory.getItems("Acre Clay")
            if (slay.size == 0) {
                log.info("no clay)")
            }
            if (slay.size != 0) {
                if (slay.size != 0) {
                    for (s = 0; s < ClayStockpile.size(); s++) {
                        slay = inventory.getItems("Acre Clay")
                        if (slay.size != 0) {
                            player.RightClickGobnomove(ClayStockpile[s])
                            player.waitForNewWindowTest("Stockpile")
                            if (slay.size != 0) {;
                                for (f = 0; f < slay.size; f++) {
                                    log.info("${slay.size}")
                                    if (slay.size == 0) break
                                    Cl = player.waitForNewWindowTest("Stockpile")
                                    Cd = player.GetItemWindow(Cl, "Acre Clay")
                                    if (Cd.size() == 80){
                                        log.info("LOL")
                                    }
                                    inventory.transferNew(slay[f])
                                    slay = inventory.getItems("Acre Clay")
                                    Cl = player.waitForNewWindowTest("Stockpile")
                                    log.info("${slay.size}")
                                }
                                if (slay.size != 0) break
                            }
                            if (slay.size == 0) break
                        }
                    }
                }
            }
        }
    }
    Clay = player.findObjects("clay-acre")
    if (Clay == null)break
}