    import static java.lang.System.out

@groovy.transform.Canonical
class Account {
    String login = ""
    String password = ""
    String character = ""
    String image = ""
}

Account[] accounts = [
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"],
        ["X", "X", "Salt"]
]
def nosalt = 0
def logout() {
    while (true) {
        player.logout()
        sleep(5000)
        break
    }
}
for (a = 0; a < accounts.size(); a++) {
    while (true) {
        player.login(accounts.login[a], accounts.password[a], accounts.character[a])
        sleep(5000)
        chara = player.GetAccountName();
        player.discordSay("${chara} Loginned")
        break
    }
    int Freespace = inventory.getFreeSpace()
    int msg = 0
    int fuk = 0
    def sal = inventory.getItems("Salt Crystals")
    if (sal.size() != 0) {
        log.info("I have salts: ${sal.size()}")
    }
    basin = player.findObjects("saltbasin")
    if (basin == null) {
        while (basin == null) {
            basin = player.findObjects("saltbasin")
            sleep(100)
            if (basin != null) break
        }
    }
    log.info("${basin}")
    sdt = player.GetSdt(basin[0])
    log.info("${sdt}")
    if (sdt != 0 && sdt != -10) {
        allhp = player.getHpMeter()
        int shp = allhp.split("/")[0].toInteger();
        if (shp == 0){
            while (shp == 0) {
                allhp = player.getHpMeter()
                shp = allhp.split("/")[0].toInteger();
                if (shp == 0){
                    player.discordSay(accounts.login[a] + " Have 0 hp, no salt harvesting, changing char...")
                    player.unsafeLogout()
                    continue
                }
            }
        }
        player.RightClickGob(basin[0])
        Gmenu = player.getflower()
        if (Gmenu == null) {
            while (Gmenu == null) {
                player.RightClickGob(basin[0])
                sleep(200)
                Gmenu = player.getflower()
                if (Gmenu != null) break
            }
        }
        if (Gmenu != null) {
            Gmenu = player.getflower()
            Take = player.getpetal(Gmenu, "Collect salt")
            if (Take != null){
                player.ClickFlower("Collect salt")
                houglass = player.getHourglass()
                if (houglass == -1.0) {
                    while (houglass == -1.0) {
                        c = player.isCollected()
                        if (c == true) {
                            player.unsafeLogout()
                            break
                        }
                        houglass = player.getHourglass()
                        if (houglass != -1.0) break
                    }
                }
                if (houglass != -1.0) {
                    while (houglass != -1) {
                        Gmenu = player.getflower()
                        if (Gmenu == null) {
                            while (Gmenu == null) {
                                houglass = player.getHourglass()
                                player.RightClickGob(basin[0])
                                sleep(200)
                                Gmenu = player.getflower()
                                if (Gmenu != null) break
                                if (houglass == -1.0) break
                            }
                        }
                        Gmenu = player.getflower()
                        if (Gmenu != null) {
                            player.closeFmenu()
                        }
                        houglass = player.getHourglass();
                        log.info("${houglass} Hourglass progress");
                        sleep(200);
                        if (houglass == -1.0) {
                            out.println("${houglass} +  Hourglass progress ended");
                            break
                        }
                    }
                }
            }
        }
        def sale = inventory.getItems("Salt Crystals")
        if (sale != null) {
            if (sal.size != sale.size) {
                //log.info(sale.size - sal.size)
                kek = sale.size - sal.size
                player.discordSay(accounts.login[a] + " I got: ${kek} Salt")
                //log.info("I got: ${kek} Salt")
            }
        }
    }
    if (sdt == 0 || sdt == -10) {
        if (msg == 0) {
            //player.discordSay("Salt not ready still, keep waiting for spawn")
            log.info("Salt not ready still, keep waiting for spawn")
            msg++
        }
        if (nosalt == 0) {
            for (n = 0; n < 121; n++) {
                // 60 means sixty seconds, it waits 60 seconds and check every 1 second
                sdt = player.GetSdt(basin[0])
                sleep(1000)
                sdt = player.GetSdt(basin[0])
                if (sdt != 0 && sdt != -10) {
                    fuk = 1
                    log.info("Salt are ready!")
                    break
                }
            }
        }
        if (fuk == 0){
            log.info("No salt after check, logoff")
            nosalt = 1
            def sale = inventory.getItems("Salt Crystals")
            if (sale != null) {
                if (sal.size != sale.size) {
                    //log.info(sale.size - sal.size)
                    kek = sale.size - sal.size
                    player.discordSay(accounts.login[a] + " I got: ${kek} Salt")
                    //log.info("I got: ${kek} Salt")
                }
                if (sal.size == sale.size){
                    player.discordSay(accounts.login[a] + " I got: ${sale.size} Salt")
                }
            }
            player.unsafeLogout()
        }
        if (fuk == 1) {
            if (sdt != 0 && sdt != -10) {
                allhp = player.getHpMeter()
                int shp = allhp.split("/")[0].toInteger();
                if (shp == 0){
                    while (shp == 0) {
                        allhp = player.getHpMeter()
                        shp = allhp.split("/")[0].toInteger();
                        if (shp == 0){
                            player.discordSay(accounts.login[a] + " Have 0 hp, no salt harvesting, changing char...")
                            player.unsafeLogout()
                            continue
                        }
                    }
                }
                player.RightClickGob(basin[0])
                Gmenu = player.getflower()
                if (Gmenu == null) {
                    while (Gmenu == null) {
                        player.RightClickGob(basin[0])
                        sleep(200)
                        Gmenu = player.getflower()
                        if (Gmenu != null) break
                    }
                }
                if (Gmenu != null) {
                    Gmenu = player.getflower()
                    Take = player.getpetal(Gmenu, "Collect salt")
                    if (Take != null){
                        player.ClickFlower("Collect salt")
                        houglass = player.getHourglass()
                        if (houglass == -1.0) {
                            while (houglass == -1.0) {
                                c = player.isCollected()
                                if (c == true) {
                                    player.unsafeLogout()
                                    break
                                }
                                houglass = player.getHourglass()
                                if (houglass != -1.0) break
                            }
                        }
                        if (houglass != -1.0) {
                            while (houglass != -1) {
                                Gmenu = player.getflower()
                                if (Gmenu == null) {
                                    while (Gmenu == null) {
                                        houglass = player.getHourglass()
                                        player.RightClickGob(basin[0])
                                        sleep(200)
                                        Gmenu = player.getflower()
                                        if (Gmenu != null) break
                                        if (houglass == -1.0) break
                                    }
                                }
                                player.closeFmenu()
                                houglass = player.getHourglass();
                                log.info("${houglass} Hourglass progress");
                                sleep(50);
                                if (houglass == -1.0) {
                                    out.println("${houglass} +  Hourglass progress ended");
                                    break
                                }
                            }
                        }
                    }
                }
                def sale = inventory.getItems("Salt Crystals")
                if (sale != null) {
                    if (sal.size != sale.size) {
                        //log.info(sale.size - sal.size)
                        kek = sale.size - sal.size
                        player.discordSay(accounts.login[a] + " I got: ${kek} Salt")
                        //log.info("I got: ${kek} Salt")
                    }
                    if (sal.size == sale.size){
                        player.discordSay(accounts.login[a] + " I got: ${sale.size} Salt")
                    }
                }
            }
        }
    }
    player.unsafeLogout()
}