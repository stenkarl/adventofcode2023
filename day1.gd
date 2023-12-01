extends Node2D

var file = FileAccess.open("res://input/day1.txt", FileAccess.READ)
var content = file.get_as_text()
var split = content.split("\n")

# Called when the node enters the scene tree for the first time.
func _ready():
	part1()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func part1():
	print("number of lines: ", split.size())
	var sum = 0
	for i in split:
		var value = i.to_int()
		if (value < 10 and value > 0):
			value = int(str(value) + str(value))
		if (value > 99):
			value = int(str(value)[0] + str(value)[-1])
		#print(value)
		sum += value
	print("sum ", sum)
