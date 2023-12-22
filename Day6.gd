extends Node2D

# 7  15   30
# 9  40  200

# 49     78     79     80
# 298   1185   1066   1181

# var races = [Race.new(7, 9), Race.new(15, 40), Race.new(30, 200)]
# var races = [Race.new(49, 298), Race.new(78, 1185), Race.new(79, 1066), Race.new(80, 1181)]
#var races = [Race.new(71530, 940200)]
var races = [Race.new(49787980, 298118510661181)]

# Called when the node enters the scene tree for the first time.
func _ready():
	part1() # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func part1():
	print("Day 6")
	var total = 1
	for r in races:
		total = total * waysToWin(r)
	print("Total:", total)
	
func waysToWin(r: Race):
	var sum = 0
	var time = r.time
	var distance = r.distance
	for t in r.time:
		if t % 1000000 == 0:
			print(t)
		if t * (time - t) > distance:
			sum = sum + 1
	return sum

class Race:
	
	var time:int
	var distance:int
	
	func _init(t:int, d:int):
		self.time = t
		self.distance = d
		
	func _to_string():
		return "Race: time:" + str(time) + ", distance:" + str(distance)
