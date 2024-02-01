while(true) {
    start = player.getPlayerCoords() // стартовые коорды чара
    a = player.getlastmsgfromPartyChat()
    if (a != null) {
        if (a.contains("move") && (a.tokenize(' ')[1] && a.tokenize(' ')[2]) != null){
            x = a.tokenize(' ')[1].toInteger()
            y = a.tokenize(' ')[2].toInteger()
            log.info("${x} + ${y}")
            next = start.add(0.0, 0.0) // они теперь 0.0
            next = start.add(x * 11, y * -11) // они теперь 0.0
            newpos = next
            player.moveToNoPF(newpos)
        }
    }
    sleep(500)
}