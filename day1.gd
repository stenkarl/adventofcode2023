extends Node2D

var file = FileAccess.open("res://input/day1.txt", FileAccess.READ)
var content = file.get_as_text()
var split = content.split("\n")
var numbers = {"1":1, "2":2, "3":3, "4":4, "5":5, "6":6, "7":7, "8":8, 
	"9":9,"one":1, "two":2, "three":3, "four":4,
 	"five":5, "six":6, "seven":7, "eight":8, "nine":9}

# Called when the node enters the scene tree for the first time.
func _ready():
	part2()

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
	
func part2():
	var sum = 0
	for i in split:
		var first = numbers.get(firstNum(i))
		var last = numbers.get(lastNum(i))
		var num = int((str(first)) + str(last))
		#print(i, ":", num)
		sum += num
	print("sum ", sum)
		
func firstNum(str:String):
	var min:String
	for i in numbers.keys():
		var curIndex = first(str, i)
		#print("i:", i, " curIndex:", curIndex)
		if curIndex == -1:
			continue
		if min.is_empty():
			min = i
			#print("min is now:", min)
		elif not min.is_empty() and curIndex < first(str, min):
			min = i
	return min
	
func lastNum(str:String):
	var max:String
	for i in numbers.keys():
		var curIndex = last(str, i)
		#print("i:", i, " curIndex:", curIndex)
		if curIndex == -1:
			continue
		if max.is_empty():
			max = i
			#print("max is now:", max)
		elif not max.is_empty() and curIndex > last(str, max):
			max = i
	return max
		
	
func first(str:String, which:String):
	return str.find(which)
	
func last(str:String, which:String):
	return str.rfind(which)
	
	
