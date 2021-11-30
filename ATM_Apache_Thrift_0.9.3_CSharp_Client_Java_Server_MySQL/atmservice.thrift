namespace java ATMService
namespace csharp ATMClient

service ATMService{
	double balance(1:string customer_id),
	double deposit(1:string customer_id, 2:double price),
	double withdraw(1:string customer_id, 2:double price),
}