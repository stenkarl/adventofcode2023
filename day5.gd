extends Node2D

var content = FileAccess.open("res://input/day5.txt", FileAccess.READ).get_as_text().split("\n")
var translators: Array[Translator] = []

# Called when the node enters the scene tree for the first time.
func _ready():
	part2() # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func buildSeeds():
	var seedStr = content[0].substr("seeds: ".length()).split(" ")
	var seeds = []
	for s in seedStr:
		seeds.append(int(s))
	return seeds
	
func part2():
	var seeds = buildSeeds()
	print(seeds)
	print("length: ", seeds.size())
	var index = 2
	for i in 7:
		index = buildTranslator(index)
	
	#print(translators)
	var minLoc = -1
	for i in range(0, seeds.size(), 2):
		var start = seeds[i]
		var end = start + seeds[i + 1]
		print(i, " range length: ", (end - start))
		for j in range(start, end):
			var curLoc = findLocation(j)
			#print(j, " location: ", curLoc)
			if curLoc < minLoc or minLoc < 0:
				minLoc = curLoc
	print("min Loc: ", minLoc)
	
func part1():
	var seeds = buildSeeds()
	print(seeds)
	var index = 2
	for i in 7:
		index = buildTranslator(index)
	
	print(translators)
	var minLoc = -1
	for s in seeds:
		var curLoc = findLocation(s)
		print(s, " location: ", curLoc)
		if curLoc < minLoc or minLoc < 0:
			minLoc = curLoc
	print("min Loc: ", minLoc)
	
func findLocation(seed: int):
	var curVal = seed
	#print("Seed:", seed)
	for t in translators:
		var oldVal = curVal
		curVal = t.map(oldVal)
		#print(t.name, " ", oldVal, " -> ", curVal)
	return curVal
	
	
func buildTranslator(start: int):
	#print("buildTranslator:", start)
	var n = content[start].substr(0, content[start].length() - " map:".length())
	var index = start + 1
	var entries: Array[Entry] = []
	while content[index].strip_edges(true, true) != "":
		var nums = content[index].split(" ")
		entries.append(Entry.new(int(nums[1]), int(nums[0]), int(nums[2])))
		index = index + 1
	
	translators.append(Translator.new(n, entries))
	#print("end index:", index, " name:", n)
	return index + 1
	
class Translator:
	
	var name:String
	var entries: Array[Entry]
	
	func _init(name:String, entries:Array[Entry]):
		self.name = name
		self.entries = entries
		
	func _to_string():
		var str = name
		for e in entries:
			str = str + "\n  " + e.to_string()
		return str
		
	func map(value: int):
		for e in entries:
			#var srcEnd = e.source + e.length
			if (value >= e.source and value <= e.srcEnd):
				#print(str(value), " is contained in range: ", e.source, " : ", srcEnd)
				return e.dest + (value - e.source)
		return value

		
class Entry:
	var source:int
	var dest:int
	var length:int
	var srcEnd:int
	
	func _init(source:int, dest:int, length:int):
		self.source = source
		self.dest = dest
		self.length = length
		self.srcEnd = source + length
		
	func _to_string():
		return "src:" + str(source) + ", dest:" + str(dest) + ", len:" + str(length)
