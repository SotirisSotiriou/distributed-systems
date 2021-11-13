#run ATMClient.py

import Pyro4

options = ["(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"]

def main():
	while True:
		print("\nMENU")
		for option in options:
			print(option)
		
		choice = str(input("Select on option: "))
		
		if str(choice).upper() == "B":
			id = str(input("Give customer id: "))

			atm = Pyro4.Proxy("PYRONAME:ATM@localhost:9090")
			balance = atm.balance(id)
			if balance == -1.0:
				print("db error")
			elif balance == -2.0:
				print("not enough balance")
			elif balance == -3.0:
				print("user not found")
			else:
				print("Balance: " + str(balance))
		
		elif str(choice).upper() == "D":
			id = str(input("Give customer id: "))
			price = float(input("Give price for deposit: "))

			atm = Pyro4.Proxy("PYRONAME:ATM@localhost:9090")
			balance = atm.deposit(id,price)
			if balance == -1.0:
				print("db error")
			elif balance == -2.0:
				print("not enough balance")
			elif balance == -3.0:
				print("user not found")
			else:
				print("Balance: " + str(balance))

		elif str(choice).upper() == "W":
			id = str(input("Give customer id: "))
			price = float(input("Give price for withdraw: "))

			atm = Pyro4.Proxy("PYRONAME:ATM@localhost:9090")
			balance = atm.withdraw(id,price)
			if balance == -1.0:
				print("db error")
			elif balance == -2.0:
				print("not enough balance")
			elif balance == -3.0:
				print("user not found")
			else:
				print("Balance: " + str(balance))
			
		elif str(choice).upper() == "E":
			break
		else:
			print("Not valid choice")
	
if __name__ == "__main__":
	main()