extends Node2D

var content = FileAccess.open("res://input/day2.txt", FileAccess.READ).get_as_text().split("\n")
var maxReds = 12
var maxGreens = 13
var maxBlues = 14

# Called when the node enters the scene tree for the first time.
func _ready():
	part2() # Replace with function body.

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func part1():
	var sum = 0
	for i in content.size() - 1:
		if processGame(content[i]):
			print("Game ", (i + 1), " is possible")
			sum += i + 1
	print("Sum ", sum)
	
func part2():
	var sum = 0
	for i in content.size() - 1:
		var subStr = content[i].erase(0, content[i].find(":") + 2)
		var grabs = subStr.split("; ")
		var maxGrabPerGame = maxGrabs(grabs)
		var power = maxGrabPerGame["red"] * maxGrabPerGame["green"] * maxGrabPerGame["blue"]
		print("Game ", (i + 1), ", max:", maxGrabPerGame, ", power:", power)
		sum += power
	print("Sum of powers: ", sum)
	
func processGame(line:String):
	var grabs = line.erase(0, 9).split("; ")
	return grabsToMap(grabs)
	#print(grabsTotals)
	#return grabsTotals["red"] <= maxReds and grabsTotals["green"] <= maxGreens and grabsTotals["blue"] <= maxBlues	
	
func grabsToMap(grabs):
	#var grabsMap = {"red": 0, "green": 0, "blue": 0}
	for i in grabs:
		if not grabValid(i):
			#print("Grab not possible: ", i)
			return false
	return true
	

func grabValid(grab):
	var map = {"red": 0, "green": 0, "blue": 0}
	#print(grab)
	for i in grab.split(", "):
		#print(i)
		var j = i.split(" ")
		var num = int(j[0])
		var color = j[1]
		map[color] = num
		#print("num:", num, ", color:", color)
	return map["red"] <= maxReds and map["green"] <= maxGreens and map["blue"] <= maxBlues	

func grabToMap(grab):
	var map = {"red": 0, "green": 0, "blue": 0}
	print("grab:", grab)
	for i in grab.split(", "):
		#print(i)
		var j = i.split(" ")
		var num = int(j[0])
		var color = j[1]
		map[color] = num
	return map
	
func maxGrabs(grabs):
	var grabsMax = {"red": 0, "green": 0, "blue": 0}
	for i in grabs:
		var map = grabToMap(i)
		if grabsMax["red"] < map["red"]:
			grabsMax["red"] = map["red"]
		if grabsMax["green"] < map["green"]:
			grabsMax["green"] = map["green"]
		if grabsMax["blue"] < map["blue"]:
			grabsMax["blue"] = map["blue"]
	return grabsMax
