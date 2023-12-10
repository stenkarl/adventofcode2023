extends Node2D

var content = FileAccess.open("res://input/day3.txt", FileAccess.READ).get_as_text().split("\n")
var grid = buildGrid(content)
var digits = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']

# Called when the node enters the scene tree for the first time.
func _ready():
	part2()

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func buildGrid(content):
	content.remove_at(content.size() - 1)
	var g = []
	for row in content:
		var line = []
		for col in row:
			line.append(col)
		g.append(line)
	return g
	
func part1():
	print(digits)
	var numbers = []
	var valid = true
	var numberBuilder = ""
	for row in grid.size():
		for col in grid[row].size():
			var ch = grid[row][col]
			if ch in digits:
				if (not checkValid(row, col)):
					valid = false	
				numberBuilder += ch 
			elif numberBuilder != "":
				if not valid:
					numbers.append(int(numberBuilder))
				numberBuilder = ""
				valid = true
	print(numbers)
	print(sum(numbers))

func sum(numbers):
	var s = 0
	for n in numbers:
		s += n
	return s
	
func checkValid(row, col):
	return isValid(row - 1, col - 1) and isValid(row -1, col) and isValid(row - 1, col + 1) \
	and isValid(row, col - 1) and isValid(row, col + 1) \
	and isValid(row + 1, col -1) and isValid(row + 1, col) and isValid(row + 1, col + 1)

func isValid(row, col):
	#print("touchesSymbol: ", grid[row][col], ", row:", row, ", col:", col)
	if row < 0 or row >= grid.size() or col < 0 or col >= grid[0].size():
		return true
	else:
		return grid[row][col] == '.' or grid[row][col] in digits
		
func part2():
	var map = buildNumberMap()
	findGears(map)
	
func findGears(map):
	var sum:int = 0
	for row in grid.size():
		for col in grid[row].size():
			var ch = grid[row][col]
			if ch == '*':
				var nums = findAdjacentNumbers(map, row, col)
				if (nums.size() == 2):
					sum += int(nums[0]) * int(nums[1])
					print("Found gear at ", row, ", ", col, " with ", nums, ", sum ", sum)	
	print("Sum: ", sum)
	
func findAdjacentNumbers(map, row, col):
	var nums = []
	for key in map.keys():
		for point in map[key]:
			if anyMatch(point, row, col):
				if row == 10 and col == 18:
					print("Found num ", key, " at ", point, ", row ", row, ", col ", col)
				nums.append(key.split(",")[0])
				break
	return nums
			
func anyMatch(point, row, col):
	return point.x == row - 1 and point.y == col - 1 or \
	point.x == row - 1 and point.y == col or \
	point.x == row - 1 and point.y == col + 1 or \
	point.x == row and point.y == col - 1 or \
	point.x == row and point.y == col + 1 or \
	point.x == row + 1 and point.y == col - 1 or \
	point.x == row + 1 and point.y == col or \
	point.x == row + 1 and point.y == col + 1

func buildNumberMap():
	var map = {}
	var curNum = ""
	for row in grid.size():
		for col in grid[row].size():
			var ch = grid[row][col]
			if ch in digits:
				curNum += ch
			if ch not in digits:
				if curNum.length() > 0:
					var endCol = col
					#if col == grid[row].size() - 1:
						#endCol = endCol + 1
					addNumToMap(map, curNum, row, endCol)
				curNum = ""
			if col == grid[row].size() - 1 and curNum.length() > 0:
				addNumToMap(map, curNum, row, col + 1)
				curNum = ""
	print("Map: ", map)
	return map
	
func addNumToMap(map, num, row, col):
	var points = []
	#print("Cur Num: ", num, ", row:", row, ", col:", col)
	for i in num.length():
		points.append(Vector2i(row, (col - num.length()) + i))
	var numStr = "" + num + "," + str(row) + "," + str(col)
	map[numStr] = points
	
	
	
