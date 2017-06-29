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
            LinkedListNode<int> p = score.First;

            int prevScore = 0;
            int runningScore = 0;

            for (int i=0; i<n; i++)
            {
                // batter up
                string hit = blocks[i];

                int num;
                bool isNum = int.TryParse(hit, out num);
                if (isNum) // just a number, add score to linked list
                {
                    if (p == null) // no score, just add it
                    {
                        prevScore = runningScore;
                        runningScore = num;
                        score.AddFirst(num);
                        p = score.First;
                    }
                    else
                    {
                        // add to previous score value
                        prevScore = runningScore;
                        runningScore = runningScore + num;
                        score.AddFirst(num);
                        p = score.First;
                    }
                }
                else
                {
                    if (hit.Equals("X")) // double the last score
                    {
                        if (p != null) 
                        {
                            prevScore = runningScore;
                            int thisScore = p.Value * 2;
                            runningScore = runningScore + thisScore;
                            score.AddFirst(thisScore);
                            p = score.First;
                        }
                    }
                    else if (hit.Equals("+")) // sum up the last two scores
                    {
                        if (p == null)
                        {
                            // do nothing
                        }
                        else if (p.Next == null) // we only have one score? just add that to score
                        {
                            prevScore = runningScore;
                            int thisScore = p.Value;
                            runningScore = runningScore + thisScore;
                            score.AddFirst(thisScore);
                            p = score.First;
                        }
                        else if (p.Next != null) // no two full scores, just take last score?
                        {
                            prevScore = runningScore;
                            int thisScore = p.Value + p.Next.Value;
                            runningScore = runningScore + thisScore;
                            score.AddFirst(thisScore);
                            p = score.First;
                        }
                    }
                    else if (hit.Equals("Z")) // the last score never happened
                    {
                        if (p != null) 
                        {
                            runningScore = prevScore;
                            score.RemoveFirst();
                            p = score.First;
                        }
                    }
                    else
                    {
                        // input error, do nothing
                    }
                }
            }

            return runningScore;
        }
    }
}
