using System;
using Thrift.Transport;
using Thrift.Protocol;
using Thrift.Server;
using Thrift.Processor;
using Thrift.Collections;
using Thrift.Transport.Client;


namespace ATMClient
{
    class Program
    {
        private static string[] options = { "(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit" };

        static void Main(string[] args)
        {
            while (true)
            {
                //print menu
                Console.WriteLine("\nMENU");
                foreach (string option in options)
                {
                    Console.WriteLine(option);
                }


                Console.Write("Select an option: ");
                char choice = Console.ReadLine()[0];


                //balance action
                if (choice.ToString().ToUpper() == "B")
                {
                    Console.Write("Give customer id: ");
                    string customer_id = Console.ReadLine();

                    //connect with ATM service
                    TSocketTransport transport = new TSocketTransport(new System.Net.Sockets.TcpClient("localhost", 9092), new Thrift.TConfiguration());
                    transport.OpenAsync(cancellationToken:default);
                    TProtocol protocol = new TBinaryProtocol(transport);
                    ATMService.Client client = new ATMService.Client(protocol);

                    double result = client.balance(customer_id).Result;
                    if (result >= 0.0)
                    {
                        Console.WriteLine("Balance: " + result.ToString());
                    }
                    else if (result == -1.0)
                    {
                        Console.WriteLine("db error");
                    }
                    else if (result == -3.0)
                    {
                        Console.WriteLine("user not found");
                    }

                    transport.Close();
                }
                else if (choice.ToString().ToUpper() == "D")
                {
                    Console.Write("Give customer id: ");
                    string customer_id = Console.ReadLine();

                    double price;
                    while (true)
                    {
                        Console.Write("Give price for deposit: ");
                        string pricetext = Console.ReadLine();

                        if (Double.TryParse(pricetext, out price))
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("not valid price value");
                        }
                    }

                    //connect with ATM service
                    TSocketTransport transport = new TSocketTransport(new System.Net.Sockets.TcpClient("localhost", 9092), new Thrift.TConfiguration());
                    transport.OpenAsync(cancellationToken: default);
                    TProtocol protocol = new TBinaryProtocol(transport);
                    ATMService.Client client = new ATMService.Client(protocol);

                    double result = client.deposit(customer_id, price).Result;
                    if (result >= 0.0)
                    {
                        Console.WriteLine("Balance: " + result.ToString());
                    }
                    else if (result == -1.0)
                    {
                        Console.WriteLine("db error");
                    }
                    else if (result == -3.0)
                    {
                        Console.WriteLine("user not found");
                    }

                    transport.Close();

                }
                else if (choice.ToString().ToUpper() == "W")
                {
                    Console.Write("Give customer id: ");
                    string customer_id = Console.ReadLine();

                    double price;
                    while (true)
                    {
                        Console.Write("Give price for deposit: ");
                        string pricetext = Console.ReadLine();

                        if (Double.TryParse(pricetext, out price))
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("not valid price value");
                        }
                    }

                    //connect with ATM service
                    TSocketTransport transport = new TSocketTransport(new System.Net.Sockets.TcpClient("localhost", 9092), new Thrift.TConfiguration());
                    transport.OpenAsync(cancellationToken: default);
                    TProtocol protocol = new TBinaryProtocol(transport);
                    ATMService.Client client = new ATMService.Client(protocol);

                    double result = client.withdraw(customer_id, price).Result;
                    if (result >= 0.0)
                    {
                        Console.WriteLine("Balance: " + result.ToString());
                    }
                    else if (result == -1.0)
                    {
                        Console.WriteLine("db error");
                    }
                    else if (result == -3.0)
                    {
                        Console.WriteLine("user not found");
                    }

                    transport.Close();
                }
                else if (choice.ToString().ToUpper() == "E")
                {
                    break;
                }
                else
                {
                    Console.WriteLine("not valid choice");
                }
            }
        }
    }
}
