#first run python -m Pyro4.naming
#run python ATM.py

import Pyro4
import mysql.connector

@Pyro4.expose
#remote object
class ATM(object):
	#-1.0 value -> db error
	#-2.0 value -> not enough balance
	#-3.0 value -> user not found

	def balance(self, customer_id):
		try:
			connection = mysql.connector.connect(host="localhost", user="root", passwd="rootroot")
			cursor = connection.cursor()
			cursor.execute("use atm")
			cursor.execute("select balance from customer where id = " + customer_id)
			result = cursor.fetchone()
			if len(result) == 0:
				return -3.0
		except:
			print("DB Connection error")
			return -1.0
		return float(result[0])
	
	def deposit(self, customer_id, price):
		try:
			connection = mysql.connector.connect(host="localhost", user="root", passwd="rootroot")
			cursor = connection.cursor()
			cursor.execute("use atm")
			cursor.execute("update customer set balance = balance + " + str(price) + " where id = " + str(customer_id))
			if cursor.rowcount == 1:
				#get new balance
				cursor.execute("select balance from customer where id = " + str(customer_id))
				newbalance = cursor.fetchone()
				connection.commit()
				return float(newbalance[0])
			else:
				connection.rollback()
				return -3.0
		except:
			print("DB Connection error")
			return -1.0
		
	def withdraw(self, customer_id, price):
		try:
			connection = mysql.connector.connect(host="localhost", user="root", passwd="rootroot")
			#check if balance is enough for withdraw
			cursor = connection.cursor()
			cursor.execute("use atm")
			cursor.execute("select balance from customer where id = " + str(customer_id))
			balanceTuple = cursor.fetchone()
			if len(balanceTuple) == 1:
				balance = balanceTuple[0]
				if balance < price:
					return -2.0
			#update balance
			cursor.execute("update customer set balance = balance - " + str(price) + " where id = " + str(customer_id))
			if cursor.rowcount == 1:
				#get new balance
				cursor.execute("select balance from customer where id = " + str(customer_id))
				newbalance = cursor.fetchone()
				connection.commit()
				return float(newbalance[0])
			else:
				return -3.0
		except:
			print("DB Connection error")
			return -1.0
		
def main():
	daemon = Pyro4.Daemon()		# make a Pyro daemon
	ns = Pyro4.locateNS()		# find the name server
	uri = daemon.register(ATM)	# register the greeting maker as a Pyro object
	ns.register("ATM", uri)		# register the object with a name in the name server

	print("Ready.")
	daemon.requestLoop()		# start the event loop of the server to wait for calls

if __name__ == "__main__":
	main()