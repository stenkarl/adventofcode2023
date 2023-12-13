extends Node2D

var content = FileAccess.open("res://input/day4.txt", FileAccess.READ).get_as_text().split("\n")

# Called when the node enters the scene tree for the first time.
func _ready():
	content.resize(content.size() - 1)
	part2() # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func part1():
	var cards = buildCards()
	#print(cards)
	var sum = 0
	for c in cards:
		var matches = []
		for num in c.numbers:
			if c.winners.has(num):
				matches.append(num)
		var score = 2 ** (matches.size() - 1) if matches.size() > 0 else 0			
		sum += score
		#print("Card ", c.id, ", score:", score, " matches:", matches)
	print("Sum: ", sum)
	
func part2():
	var cards = buildCards()
	print(cards)
	var cardMap = {}
	for c in cards:
		cardMap[c.id] = 1
	print("Processing ", cards.size(), " cards")
	for c in cards:
		#print("Card ", c.id, " has ", c.matches(), " matches")
		#for cardNum in cardMap[c.id]:
			#print("iteration ", cardNum)
		var numMatches = c.matches()
		for n in numMatches:
				#print("c.id:", c.id, " n:", n)
			incMap(c.id + n + 1, cardMap[c.id], cardMap)
	#print(cardMap)
	var sum = 0
	for k in cardMap.keys():
		#print("values card:", k, " has ", cardMap[k])
		sum = sum + cardMap[k]
	print("Sum: ", sum)
		
func incMap(cardId, amt, map):
	#print("Incrementing cardId:", cardId, " by ", amt)
	var sum = map[cardId] + amt
	map[cardId] = sum
	
func buildCards():
	var cards = []
	for c in content:
		cards.append(buildCard(c))
	return cards
	
func buildCard(str):
	var colonSplit = str.split(":")
	var id = int(colonSplit[0].substr(5))
	var split = colonSplit[1].split(" | ")
	var winners = toIntList(split[0])
	var numbers = toIntList(split[1])
	return Card.new(id, winners, numbers)
	
func toIntList(str: String):
	var list = []
	for s in str.strip_edges().split(" "):
		if not s.is_empty():
			list.append(int(s))
	return list
	
class Card:
	var id
	var winners = []
	var numbers = []
	var numMatches = -1
	
	func _init(id, winners, numbers):
		self.id = id
		self.winners = winners
		self.numbers = numbers
		
	func _to_string():
		return "Card " + str(id) + " : " + str(winners) + " | " + str(numbers)
		
	func matches():
		if numMatches == -1:
			numMatches = 0
			for num in numbers:
				if winners.has(num):
					numMatches = numMatches + 1
		return numMatches
