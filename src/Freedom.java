
/*
 * Victory at all costs, victory in spite of all terror, victory however long and hard the road may be; for without victory, there is no survival.
-Winston Churchill

Inspired by this quote, the students of NSIT plan on attacking the alien warship and putting an end to the oppression, once and for all.

The plan is to attack the aliens from both flanks and kill em all. Two teams are going to be made for this task. The leader of our forces, Ayush, wants the two attack parties led by Arpit and Gaurav to be of similar strengths.

First line of input will contain an integer t, the number of testcases. For each test case first line gives an integer n, the size of the total army, following n lines will contain the strength of each student.

Input:

First line of input will contain an integer t, the number of testcases. For each test case first line gives an integer n, the size of the total army, following n lines will contain the strength of each student.

Output:

You have to print the absolute difference in strengths of the most optimum division of each set into 2 subsets.

1<=t<=50 

0< Total number of students <= 500 and 0< strength <=500

Sample:

Input:
2
5
13
53
19
397
181
8
41
43
47
79
83
443
167
239

Output:
131
4
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.StringTokenizer;

class Freedom {

	private static BufferedReader bufferedReader;
	private static StringTokenizer stringTokenizer;
	private LinkedList<Integer> pilotsSkills;
	private int interceptorNumber;
	
	private Freedom()
	{
		pilotsSkills = new LinkedList<Integer>();
    //  this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    // 	this.stringTokenizer = new StringTokenizer(this.bufferedReader.readLine());
    }
	
	private void readSkills()
	{
		pilotsSkills.clear(); // Reading pilot skill levels
		this.interceptorNumber = Integer.parseInt(stringTokenizer.nextToken());
		while (interceptorNumber-- > 0) // TODO later on create our own insertion sort for fun and training :D
			pilotsSkills.add(Integer.parseInt(stringTokenizer.nextToken()));
	}
	
	private int divideInterceptorsIntoTwoSquadrons()
	{
		int combinedTeamSkill1, combinedTeamSkill2;
		Iterator<Integer> iterator = pilotsSkills.iterator();
		for (combinedTeamSkill1 = iterator.next().intValue(), combinedTeamSkill2 = 0;
			 iterator.hasNext();)
			if (combinedTeamSkill1 <= combinedTeamSkill2) combinedTeamSkill1 += iterator.next().intValue(); 
			else combinedTeamSkill2 += iterator.next().intValue();
		System.out.println("Suma1: " + combinedTeamSkill1 + " Suma2: " + combinedTeamSkill2);
		if (combinedTeamSkill1 >= combinedTeamSkill2) return combinedTeamSkill1 - combinedTeamSkill2;
		else return combinedTeamSkill2 - combinedTeamSkill1;
	}
	
	private void performOneTest()
	{
		readSkills();
		Collections.sort(pilotsSkills);
		Collections.reverse(pilotsSkills);
		System.out.println(divideInterceptorsIntoTwoSquadrons());
	}
	
	public static void main(String[] args) throws IOException 
	{
		try
		{
			Freedom freedom = new Freedom();
	        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	        stringTokenizer = new StringTokenizer(bufferedReader.readLine());
	        int testNumber = Integer.parseInt(stringTokenizer.nextToken());
	        while (testNumber-- > 0)
	        	freedom.performOneTest();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return;		
	}

}
