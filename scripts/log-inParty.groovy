account = "X"
password = "X"
characters = ["ADpatroler1"]
while (true) {
    player.login(account, password, characters[0])
    sleep(5000)
    player.discordSay("I'm LOG-IN")
	return false
}
