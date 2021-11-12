import mysql.connector


def balance(connection, customer_id):
	try:
		cursor = connection.cursor()
		cursor.execute("use atm")
		cursor.execute("select balance from customer where id = " + customer_id)
		result = cursor.fetchone()
		if len(result) == 0:
			return "ERR1"
	except:
		return "ERR5"
	return str(result[0])
	

def deposit(connection, customer_id, price):
	try:
		#update balance
		cursor = connection.cursor()
		cursor.execute("use atm")
		cursor.execute("update customer set balance = balance + " + str(price) + " where id = " + str(customer_id))
		if cursor.rowcount == 1:
			#get new balance
			cursor.execute("select balance from customer where id = " + str(customer_id))
			newbalance = cursor.fetchone()
			connection.commit()
			return str(newbalance[0])
		else:
			connection.rollback()
			return "ERR1"
	except:
		return "ERR5"



def withdraw(connection, customer_id, price):
	try:
		#check if balance is enough for withdraw
		cursor = connection.cursor()
		cursor.execute("use atm")
		cursor.execute("select balance from customer where id = " + str(customer_id))
		balanceTuple = cursor.fetchone()
		if len(balanceTuple) == 1:
			balance = balanceTuple[0]
			if balance < price:
				return "ERR3"
			
		#update balance
		cursor.execute("update customer set balance = balance - " + str(price) + " where id = " + str(customer_id))
		if cursor.rowcount == 1:
			#get new balance
			cursor.execute("select balance from customer where id = " + str(customer_id))
			newbalance = cursor.fetchone()
			connection.commit()
			return str(newbalance[0])
		else:
			return "ERR1"
	except:
		connection.rollback()
		return "ERR5"