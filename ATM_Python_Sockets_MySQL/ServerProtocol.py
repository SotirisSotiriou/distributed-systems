import mysql.connector
import Database

# ERR1: user not found
# ERR2: wrong value
# ERR3: balance less than withdraw price
# ERR4: wrong action
# ERR5: DB error

def processRequest(inmsg):
	parts = str(inmsg).split()
	customer_id = parts[0]
	action = parts[1]
	price = -1.0
	if action == "deposit" or action == "withdraw":
		price = float(parts[2])
		if price < 0.0:
			return "ERR2"

	#connect to database
	try:
		connection = mysql.connector.connect(host="localhost", user="root", passwd="rootroot")
	except:
		print("DB Connection error")
		return "ERR5"
	
	if action == "balance":
		return Database.balance(connection, customer_id)
	elif action == "deposit":
		return Database.deposit(connection, customer_id, price)
	elif action == "withdraw":
		return Database.withdraw(connection, customer_id, price)
	else:
		return "ERR4"
	
	


	