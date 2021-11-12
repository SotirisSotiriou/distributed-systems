
# ERR1: user not found
# ERR2: wrong value
# ERR3: balance less than withdraw price
# ERR4: wrong action
# ERR5: DB error

def processRequest(customer_id, action, price = 0.0):
	outmsg = customer_id + " " + action
	if price > 0.0:
		outmsg += " " + str(price)
	return outmsg

def processReply(inmsg):
	if inmsg == "ERR1":
		return "USER NOT FOUND"
	elif inmsg == "ERR2":
		return "WRONG VALUE"
	elif inmsg == "ERR3":
		return "NOT ENOUGH BALANCE"
	elif inmsg == "ERR4":
		return "WRONG ACTION";
	elif inmsg == "ERR5":
		return "DB ERROR"
	else:
		return inmsg
	
