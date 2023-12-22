extends Node2D

var content = FileAccess.open("res://input/day7sample.txt", FileAccess.READ).get_as_text().split("\n")

# Called when the node enters the scene tree for the first time.
func _ready():
	content.remove_at(content.size() - 1)
	print(content) # Replace with function body.
	part1()

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func part1():
	print("Part 1")
	var hands = []
	for line in content:
		var split = line.split(" ")
		hands.append(Hand.new(split[0], int(split[1])))
	hands.sort_custom(sort)
	print(hands)
	
# return true if first  second
func sort(first: Hand, second: Hand):
	return first.rank < second.rank

	
class Hand:
	var cards: String
	var bid: int
	var rank: int
	
	func _init(c: String, b:int):
		self.cards = c
		self.bid = b
		calculateRank()
		
	func _to_string():
		return "Hand, cards:" + cards + ", bid:" + str(bid)
		
	func calculateRank():
		if isFiveOfKind():
			rank = 5
		elif isFourOfKind():
			rank = 4
		elif isFullHouse():
			rank = 3
		else:
			rank = 0
		
	func isFiveOfKind():
		var firstCard = cards[0]
		for c in cards:
			if c != firstCard:
				return false
		return true
		
	func isFourOfKind():
		# start here!
		pass
		
	func isFullHouse():
		pass
		
	
