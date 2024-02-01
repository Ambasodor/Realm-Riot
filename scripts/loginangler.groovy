account = "x"
password = "x"
characters = ["AnglerBot"]
while (true) {
    player.login(account, password, characters[0])
    sleep(1000)
    player.discordSay("${characters[0]} Loginned")
    return false
}