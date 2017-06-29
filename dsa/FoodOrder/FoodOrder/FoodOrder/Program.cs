using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FoodOrder
{
    class Program
    {
        // find out what food everyone wants based on cuisine 
        // input: set of ({food type}, {cuisine}) and set of ({person}, {cuisine})
        // output: set of ({person}, {food type})
        static void Main(string[] args)
        {
            string[,] list1 = new string[,]{ {"Pizza", "Italian"}, {"Pasta", "Italian"}, {"Sushi", "Japanese" }, {"Curry", "Indian"} };
            string[,] list2 = { {"Joe", "Italian"}, {"Sally", "Japanese"}, {"Kyle", "Chinese"}, {"Andrew", "*"} };
            string[,] ret = foodList(list1, list2);

            printList(ret);
            Console.Read();
        }

        static string[,] foodList(string[,] mealTypes, string[,] peopleLikes)
        {
            Dictionary<string, List<string>> d = new Dictionary<string, List<string>>();
            int numOfPeople = 0;
            int numofRows = 0;

            // get a list of all foods of wildcard "*"
            List<string> allFoods = new List<string>();
            for (int i=0; i<mealTypes.GetLength(0); i++)
            {
                allFoods.Add(mealTypes[i, 0]);
            }

            // parse through all the people
            for (int i=0; i<peopleLikes.GetLength(0); i++)
            {
                string name = peopleLikes[i, 0];
                string likes = peopleLikes[i, 1];

                // get the person into the dictionary if they aren't already there
                if (!d.ContainsKey(name))
                {
                    List<string> l = new List<string>();
                    d.Add(name,l);
                    numOfPeople++;
                }

                // do they like everything?
                if (likes.Equals("*"))
                {
                    d[name] = allFoods;
                    numofRows = numofRows + allFoods.Count;
                }
                else
                {
                    // look at all the meals and see if the person like any of them
                    for (int j = 0; j < mealTypes.GetLength(0); j++)
                    {
                        if (mealTypes[j, 1].Equals(likes)) // we found a like!
                        {
                            d[name].Add(mealTypes[j, 0]);
                            numofRows++;
                        }
                    }
                }

            }

            // dictionary to string[,]
            string[,] peopleFoods = new string[numofRows, 2];
            int indexer = 0;
            foreach (KeyValuePair<string, List<string>> record in d)
            {
                string name = record.Key;
                List<string> l = record.Value;
                foreach (string s in l)
                {
                    peopleFoods[indexer, 0] = name;
                    peopleFoods[indexer, 1] = s;
                    indexer++;
                }
            }

            return peopleFoods;
        }

        static void printList(string[,] a)
        {
            int rowLength = a.GetLength(0);
            int colLength = a.GetLength(1);

            for (int i = 0; i < rowLength; i++)
            {
                for (int j = 0; j < colLength; j++)
                {
                    Console.Write(string.Format("{0}\t", a[i, j]));
                }
                Console.Write(Environment.NewLine);
            }
        }
    }
}
