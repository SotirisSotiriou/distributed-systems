#Author: Sotiris Sotiriou
#Date: 26/12/2021

from zeep import *


def main():
    options = ["(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"]
    wsdl = 'http://localhost:7779/ws/atm?wsdl'
    client = Client(wsdl=wsdl)
    
    while True:
        print("\nMENU")
        for option in options:
            print(option)
        
        choice = str(input("Select on option: "))

        if str(choice).upper() == "B":
            id = str(input("Give customer id: "))
            balance = client.service.balance(id)
            print("Balance: " + balance)

        elif str(choice).upper() == "D":
            id = str(input("Give customer id: "))
            price = float(input("Give price for deposit: "))
            balance = client.service.deposit(id,price)
            print("Balance: " + balance)
        
        elif str(choice).upper() == "W":
            id = str(input("Give customer id: "))
            price = float(input("Give price for withdraw: "))
            balance = client.service.withdraw(id,price)
            print("Balance: " + balance)
        
        elif str(choice).upper() == "E":
            break

        else:
            print("Not Valid choice")
        
if __name__ == "__main__":
    main()

