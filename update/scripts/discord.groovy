
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
def list = []

while (true) {
    player.login(accounts[0].login, accounts[0].password, accounts[0].character)
    sleep(5000)
    def catchers = player.findObjects(dreams)
    if (catchers.size != 0) {
        log.info("${catchers.size}")
    }
    def Space = inventory.getItems("A Beautiful Dream")
    if (Space == null){
        Space = inventory.getItems("A Beautiful Dream")
    }
    for (int i = 0; i < catchers.size(); i++) {
        b = player.CheckGobByFlowerMenu(catchers[i], "Harvest")
        if (!b) {
            log.info("Empty catcher.")
        }
        if (b) {
            log.info("${b} Not empty - FOR USE!")
            list.add(b)
        }
    }
    log.info("${list}")
    for (int c = 0; c < list.size(); c++) {
        if (list.size != 0) {
            player.moveTo(list[c])
            sleep(500)
            log.info("I'm near catcher")
            for (int d = 0; d != 2; d++) {
                Space = inventory.getItems("A Beautiful Dream")
                player.RightClickGobnomove(list[c])
                sleep(1000)
                log.info("I'm Click on Gob")
                def menu = player.getflower("Harvest")
                log.info("I'm get menu Harvest")
                if (menu != null) {
                    player.ClickFlower("Harvest")
                    log.info("I'm press menu")
                    Space
                    log.info("${Space}")
                    sleep(1000)
                }
            }
        }
        if (list.size == 0) {
            break
        }
    }
    Space = inventory.getItemCount("A Beautiful Dream")
    log.info("${list}")
    //log.info("${Space.size()}")
    return false
}
//log.info("${list[0]}")
//player.ClickFlower(a)
//log.info("${}")