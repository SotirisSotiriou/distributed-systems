#Author: Sotiris Sotiriou

import sys

from ATMClient import ATMClient
from ClientProtocol import *

host = "localhost"

options = ["(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"]

def main():
	while True:
		print("\nMENU")
		for option in options:
			print(option)
		
		choice = str(input("Select on option: "))

		if str(choice).upper() == "B":
			id = str(input("Give customer id: "))
			atm = ATMClient()
			message = processRequest(customer_id=id, action="balance")
			response = atm.call(message)
			output = processReply(response)
			print("Balance:", output)
			
		elif str(choice).upper() == "D":
			id = str(input("Give customer id: "))
			price = float(input("Give price for deposit: "))
			atm = ATMClient()
			message = processRequest(customer_id=id, action="deposit", price=price)
			response = atm.call(message)
			output = processReply(response)
			print("Balance:", output)

		elif str(choice).upper() == "W":
			id = str(input("Give customer id: "))
			price = float(input("Give price for withdraw: "))
			atm = ATMClient()
			message = processRequest(customer_id=id, action="withdraw", price=price)
			response = atm.call(message)
			output = processReply(response)
			print("Balance:", output)
			
		elif str(choice).upper() == "E":
			break
		else:
			print("Not valid choice")
		
if __name__ == "__main__":
	main()