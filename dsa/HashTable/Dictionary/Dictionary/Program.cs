using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dictionary
{
    class Program
    {
        static void Main(string[] args)
        {
            // create a list of users
            List<UserLog> ul = new List<UserLog>();
            ul.Add(new UserLog() { date = "20160101", userName = "Nate" });
            ul.Add(new UserLog() { date = "20160102", userName = "Nate" });
            ul.Add(new UserLog() { date = "20160103", userName = "Nate" });
            ul.Add(new UserLog() { date = "20160104", userName = "Nate" });
            ul.Add(new UserLog() { date = "20160101", userName = "Steve" });
            ul.Add(new UserLog() { date = "20160104", userName = "Nieve" });
            ul.Add(new UserLog() { date = "20160102", userName = "Nieve" });
            ul.Add(new UserLog() { date = "20160102", userName = "Kyle" });
            ul.Add(new UserLog() { date = "20160102", userName = "Kyle" });

            Console.WriteLine("Number of repeat users: " + numRepeatUsers(ul));
            Console.Read();
        }

        static int numRepeatUsers(List<UserLog> list)
        {

            // create a dictionary
            Dictionary<string, List<string>> dic = new Dictionary<string, List<string>>();

            // loop through user log list
            for (int i = 0; i < list.Count; i++)
            {
                UserLog il = list[i];
                if (!dic.ContainsKey(il.userName))  // add new user to the directory if name doesn't exist
                {
                    List<string> l = new List<string>();
                    l.Add(il.date);

                    dic.Add(il.userName, l);
                }
                // name already exists, check for different date, if so- add it
                else if (!dic[il.userName].Contains(il.date))
                {
                    dic[il.userName].Add(il.date);
                }

            }

            // count'em up
            int tally = 0;
            foreach (KeyValuePair<string, List<string>> row in dic)
            {
                if (row.Value.Count > 1) tally++;
            }

            return tally;
        }
    }
}
