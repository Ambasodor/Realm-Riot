import java.text.SimpleDateFormat

Date date = new Date()

def sdf = new SimpleDateFormat("HH:mm:ss")
println sdf.format(date)
def yourFile = new File("CheckLog.txt")
if (yourFile == null) {
yourFile.createNewFile()
}
yourFile << "${sdf.format(date)} - STARTED"

account = "X"
password = "X"
characters = ["BatonShovelBoy"]
def NAMES = ["Thingwall BOI", "EastLake BOI"]

player.login(account, password, characters[0])

log.info("MY NAME -> ${characters[0]} AND I'M ALARM BOT")


while(true) {

    timeout(4)
	
    def unknowns = player.describeUnknowns()
    def unknowns_str = unknowns.join(';')
	def new_unknowns = unknowns_str.replace('[]', '')
	new_unknowns = new_unknowns.replace(';', '')
	new_unknowns = new_unknowns.replace('bronzeplate-kjol', '')
	new_unknowns = new_unknowns.replace('gfx/borka/', '')
	new_unknowns = new_unknowns.replace('caveanglercape-head', '')
	new_unknowns = new_unknowns.replace('gfx/terobjs/mannequin-w1', '')
	new_unknowns = new_unknowns.replace('gfx/fx/knockchirp', '')
	new_unknowns = new_unknowns.replace('sprucecap', '')
	new_unknowns = new_unknowns.replace('bucket-water', '')
	new_unknowns = new_unknowns.replace('gfx/terobjs/mannequin-stand', '')
	new_unknowns = new_unknowns.replace('gfx/terobjs/mannequin-w2', '')
	new_unknowns = new_unknowns.replace('gfx/terobjs/vehicle/coracle', '')
	new_unknowns = new_unknowns.replace('furcoat-body', '')
	new_unknowns = new_unknowns.replace('mammothguard-l', '')
	new_unknowns = new_unknowns.replace('mammotharmor', '')
	new_unknowns = new_unknowns.replace('bronzeplate-kjol', '')
	new_unknowns = new_unknowns.replace('leathercoat-body', '')
	new_unknowns = new_unknowns.replace('leathercoat-kjol', '')
	new_unknowns = new_unknowns.replace('leathercoat-collar', '')
	new_unknowns = new_unknowns.replace('leathercoat-cuffs', '')
	new_unknowns = new_unknowns.replace('bronzeplate-kjol', '')
	new_unknowns = new_unknowns.replace('backpack-birch', '')
	new_unknowns = new_unknowns.replace('spectacles', '')
	new_unknowns = new_unknowns.replace('bearcoat-cuffs', '')
	new_unknowns = new_unknowns.replace('bronzeplate-kjol', '')
	new_unknowns = new_unknowns.replace('bronzeplate-pads', '')
	new_unknowns = new_unknowns.replace('poormansgloves-cuffs', '')
	new_unknowns = new_unknowns.replace('hidecloak-kjol', '')
	new_unknowns = new_unknowns.replace('hidecloak-body', '')
	new_unknowns = new_unknowns.replace('backpack-straps', '')
	new_unknowns = new_unknowns.replace('pipe-clay', '')
	new_unknowns = new_unknowns.replace('-straps', '')
	new_unknowns = new_unknowns.replace('-strap', '')
	new_unknowns = new_unknowns.replace('-leather', '')
	new_unknowns = new_unknowns.replace('-body', '')
	new_unknowns = new_unknowns.replace('furcoatcollar', '')
	new_unknowns = new_unknowns.replace('kjol', '')
	new_unknowns = new_unknowns.replace('-pads', '')
	new_unknowns = new_unknowns.replace('female', '')
	new_unknowns = new_unknowns.replace('-cuffs', '')
	new_unknowns = new_unknowns.replace('-kjol', '')
	new_unknowns = new_unknowns.replace('-collar', '')
	new_unknowns = new_unknowns.replace('male', '')
	new_unknowns = new_unknowns.replace('-kjol', '')
	new_unknowns = new_unknowns.replace('gauzeequ', '')
	new_unknowns = new_unknowns.replace('leatherpants', '')
	new_unknowns = new_unknowns.replace('gauzeequ', '')
	new_unknowns = new_unknowns.replace('wanderersbindle', '')
	new_unknowns = new_unknowns.replace('nettlepants', '')
	new_unknowns = new_unknowns.replace('nettleshirt', '')
	new_unknowns = new_unknowns.replace('leatherboots', '')
	new_unknowns = new_unknowns.replace('beartalman', '')
	new_unknowns = new_unknowns.replace('furcoat', '')
	new_unknowns = new_unknowns.replace('snakeskinbelt', '')
	new_unknowns = new_unknowns.replace('batcape', '')
	new_unknowns = new_unknowns.replace('furboots', '')
	new_unknowns = new_unknowns.replace('backpack', '')
	new_unknowns = new_unknowns.replace('paddle', '')
	new_unknowns = new_unknowns.replace('creel', '')
	new_unknowns = new_unknowns.replace('poormansgloves', '')
	new_unknowns = new_unknowns.replace('bunnyslippers', '')
	new_unknowns = new_unknowns.replace('sealskinpants', '')
	new_unknowns = new_unknowns.replace('moosehidejacket', '')
	new_unknowns = new_unknowns.replace('bearcoat', '')
	new_unknowns = new_unknowns.replace('lynxclawgloves', '')
	new_unknowns = new_unknowns.replace('lynxclawglove', '')
	new_unknowns = new_unknowns.replace('leatherarmor', '')
	new_unknowns = new_unknowns.replace('borewormmask', '')
	new_unknowns = new_unknowns.replace('goatmask', '')
	new_unknowns = new_unknowns.replace('bonegreaves', '')
	new_unknowns = new_unknowns.replace('belt', '')
	new_unknowns = new_unknowns.replace('clogs', '')
	new_unknowns = new_unknowns.replace(',', '@')
	new_unknowns = new_unknowns.replace(',,', '@')
	new_unknowns = new_unknowns.replace(',,,', '@')
	new_unknowns = new_unknowns.replace('\s', '')
	new_unknowns = new_unknowns.replace('@', '-')
	new_unknowns = new_unknowns.replace('--', '-')
	new_unknowns = new_unknowns.replace('---', '-')
	new_unknowns = new_unknowns.replace('--', '-')
	new_unknowns = new_unknowns.replace('-]', ']')
	new_unknowns = new_unknowns.replace('[-', '[')
	new_unknowns = new_unknowns.replace('-]', ']')
	new_unknowns = new_unknowns.replace('[-', '[')
	new_unknowns = new_unknowns.replace('[]', '')
	new_unknowns = new_unknowns.replace('--', '-')
	new_unknowns = new_unknowns.replace('[[', '[')
	new_unknowns = new_unknowns.replace(']]', ']')
	new_unknowns = new_unknowns.replace('[]', '')
	
	def list = [new_unknowns]
	def listLOL = list.join('')
	
    if ((listLOL.isEmpty()) == true)
	{
	// ТУТА НИЧЕГО НЕ ВСТАВЛЯТЬ, ШОБ ДИСКОРД НЕ ЛОЖИЛ МОЙ КОД | DO NOT TOUCH!!
	}
	
	if ((listLOL.isEmpty()) == false)
	{
	yourFile << "\n${sdf.format(date)} - ${listLOL}"
	log.info("${NAMES[1]}: Dots here " + "${unknowns}")
	player.discordSay("${NAMES[1]}: " + "Dots here " + (listLOL))
    }
	
    timeout(4)
}

def timeout(i)
{
sleep(i * 1000)
}