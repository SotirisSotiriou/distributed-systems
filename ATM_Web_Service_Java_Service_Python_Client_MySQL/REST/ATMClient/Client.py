#Author: Sotiris Sotiriou
#Date: 26/12/2021

import requests

# ERR1: user not found
# ERR2: wrong value
# ERR3: balance less than withdraw price
# ERR4: wrong action
# ERR5: DB error

def main():
    options = ["(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"]

    while True:
        print("\nMENU")
        for option in options:
            print(option)
        
        choice = str(input("Select on option: "))

        if str(choice).upper() == "B":
            id = str(input("Give customer id: "))
            response = requests.get('http://localhost:8080/atm/balance/'+id)
            print("Balance: " + response.text)
        elif str(choice).upper() == "D":
            id = str(input("Give customer id: "))
            price = float(input("Give price for deposit: "))
            response = requests.get('http://localhost:8080/atm/deposit/'+id+'/'+str(price))
            print("Balance: " + response.text)
        elif str(choice).upper() == "W":
            id = str(input("Give customer id: "))
            price = float(input("Give price for withdraw: "))
            response = requests.get('http://localhost:8080/atm/withdraw/'+id+'/'+str(price))
            print("Balance: " + response.text)
        elif str(choice).upper() == "E":
            break

        else:
            print("Not Valid choice")

if __name__ == "__main__":
    main()