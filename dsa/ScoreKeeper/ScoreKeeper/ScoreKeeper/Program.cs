using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ScoreKeeper
{
    class Program
    {
        // tally up all hits
        // ints add normally
        // X double the last score
        // + add the last two scores together
        // Z remove the last score like it never happened
        static void Main(string[] args)
        {
            string[] input = { "2", "5", "-1", "X", "2", "2", "+", "1", "1", "Z", "-1", "2" }; // total score of 14
            Console.WriteLine("Total Score: " + totalScore(input, input.Length));
            Console.Read();
        }

        static int totalScore (string[] blocks, int n)
        {
            LinkedList<int> score = new LinkedList<int>();

            for (int i=0; i<n; i++)
            {
                // batter up
                string hit = blocks[i];

                int num;
                bool isNum = int.TryParse(hit, out num);
                if (isNum) // just a number, add score to linked list
                {
                    score.AddFirst(num);
                }
                else
                {
                    if (hit.Equals("X")) // double the last score
                    {
                        if (score.First != null) 
                        {
                            int thisScore = score.First.Value * 2;
                            score.AddFirst(thisScore);
                        } // else... no last score = no score this round!
                    }
                    else if (hit.Equals("+")) // sum up the last two scores
                    {
                        if (score.First == null)
                        {
                            // no previous scores - do nothing
                        }
                        else if (score.First.Next == null) // we only have one score? just add that to score
                        {
                            int thisScore = score.First.Value;
                            score.AddFirst(thisScore);
                        }
                        else if (score.First.Next != null) // we got two previous scores!
                        {
                            int thisScore = score.First.Value + score.First.Next.Value;
                            score.AddFirst(thisScore);
                        }
                    }
                    else if (hit.Equals("Z")) // the last score never happened
                    {
                        if (score.First != null) 
                        {
                            score.RemoveFirst();
                        }
                    }
                    else
                    {
                        // input error, do nothing
                    }
                }
            }

            // count'em up!
            LinkedListNode<int> p = score.First;
            int finalScore = 0;
            while (p != null)
            {
                finalScore = finalScore + p.Value;
                p = p.Next;
            }

            return finalScore;
        }
    }
}
