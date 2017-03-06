import csv

allVehicles = []

class vehicle:
	def __init__(self, model, make, fuelType, fuelType1):
		self.model = model.lower()
		self.make = make.lower()
		self.fuelType = fuelType.lower()
		try:
			1/0
			self.fuelType1 = fuelType1.split()[1].lower()
		except:
			self.fuelType1 = fuelType1.lower()
		self.dataCount = 0
	def belongs(self, model, make, fuelType, fuelType1):
		if self.make != make.lower():
			return False
		if self.model != model.lower():
			return False
		if self.fuelType != fuelType.lower():
			return False
		if self.fuelType1 != fuelType1.lower():
			return False
		return True
	def addData(self, carData = None):
		self.dataCount += 1
	def __str__(self):
		return "%s %s %s %i"%(self.model, self.fuelType, self.fuelType1, self.dataCount)


def compareVehicle(v, model, make, fuelType, fuelType1):
	if v.model > model.lower():
		return 1
	elif v.model == model.lower():
		if v.make > make.lower():
			return 1
		elif v.fuelType == fuelType.lower():
			print v.fuelType1, fuelType1
			if v.fuelType > fuelType.lower():
				return 1
			elif v.fuelType1 == fuelType1.lower():
				if v.fuelType1 > fuelType1.lower():
					return 1
				elif v.fuelType1 == fuelType1.lower():
					return 0
				else:
					return -1
			else:
				return -1
		else:
			return -1
	else:
		return -1

vehiclesFieldNames = ("arrels08","barrelsA08","charge120","charge240","city08","city08U","cityA08","cityA08U","cityCD","cityE","cityUF","co2","co2A","co2TailpipeAGpm","co2TailpipeGpm","comb08","comb08U","combA08","combA08U","combE","combinedCD","combinedUF","cylinders","displ","drive","engId","eng_dscr","feScore","fuelCost08","fuelCostA08","fuelType","fuelType1","ghgScore","ghgScoreA","highway08","highway08U","highwayA08","highwayA08U","highwayCD","highwayE","highwayUF","hlv","hpv","id","lv2","lv4","make","model","mpgData","phevBlended","pv2","pv4","range","rangeCity","rangeCityA","rangeHwy","rangeHwyA","trany","UCity","UCityA","UHighway","UHighwayA","VClass","year","youSaveSpend","guzzler","trans_dscr","tCharger","sCharger","atvType","fuelType2","rangeA","evMotor","mfrCode","c240Dscr","charge240b","c240bDscr","createdOn","modifiedOn","startStop","phevCity","phevHwy","phevComb"	)
with open('vehicles.csv', 'rb') as csvfile:
	spamreader = csv.DictReader(csvfile,fieldnames= vehiclesFieldNames, delimiter=',', quotechar='"')
	ignoreFirst = True
	for row in spamreader:
		if ignoreFirst:
			ignoreFirst = False
			continue
		# row['model'], row ['drive'], row['engId'], row['fuelType'], row['fuelType1'], row['model'], row['trany'], row['year']
		foundMatch = False
		#for v in allVehicles: # Make this a binary search
		#	if v.belongs(row ['model'], row['make'], row['fuelType'], row['fuelType1']):
		#		v.addData()
		#		foundMatch = True
		#		break

		lo = 0
		hi = len(allVehicles) - 1
		cp = 1
		while (lo <= hi):
			mid = lo + (hi - lo) / 2
			cp = compareVehicle(allVehicles[mid], row ['model'], row['make'], row['fuelType'], row['fuelType1'])
			if (cp > 0):
				hi = mid - 1
			elif (cp < 0):
				lo = mid + 1
			else:
				break
		mid = lo + (hi - lo) / 2
		if cp == 0:
			allVehicles[mid].addData()
		else:
			newV = vehicle(row ['model'], row['make'], row['fuelType'], row['fuelType1'])
			if cp < 0:
				allVehicles.insert(mid, newV)
			else:
				allVehicles.insert(mid + 1, newV)
		print len(allVehicles)

for v in allVehicles:
	print v