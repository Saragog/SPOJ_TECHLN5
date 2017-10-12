
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Freedom {

	private static BufferedReader bufferedReader;
	private static StringTokenizer stringTokenizer;
	private LinkedList<Integer> pilotsSkills;
	private int pilotsNumber;
	private LinkedList<State> foundStates;
	private Map<List<Integer>, Boolean> assignmentAlreadyAnalyzed; // There already was such assignment of pilots so does not need repetition
	private int bestPilotSkill;
	
	private class State
	{
		public int skillSumTeam1;
		public int skillSumTeam2;
		public int pilotsAssigned;
		public List<Integer> pilotsAssignments;
		
		public State(int skillSumTeam1, int skillSumTeam2, List<Integer> pilotsAssignments, int pilotsAssigned)
		{
			this.skillSumTeam1 = skillSumTeam1;
			this.skillSumTeam2 = skillSumTeam2;
			this.pilotsAssignments = pilotsAssignments;
			this.pilotsAssigned = pilotsAssigned;
		}
	}
	
	private Freedom()
	{
		pilotsSkills = new LinkedList<Integer>();
		foundStates = new LinkedList<State>();
		assignmentAlreadyAnalyzed = new HashMap<List<Integer>, Boolean>();
    }
	
	private void readSkills()
	{
		assignmentAlreadyAnalyzed.clear();
		pilotsSkills.clear(); // Reading pilot skill levels
		this.pilotsNumber = Integer.parseInt(stringTokenizer.nextToken());
		boolean bestPilotSkillWasSet = false;
		int readSkill;
		for (int pilotsSkillsToBeRead = pilotsNumber; pilotsSkillsToBeRead > 0; pilotsSkillsToBeRead--) // TODO later on create our own insertion sort for fun and training :D
		{
			readSkill = Integer.parseInt(stringTokenizer.nextToken());
			pilotsSkills.add(readSkill);
			if (!bestPilotSkillWasSet)
			{
				bestPilotSkill = readSkill;
				bestPilotSkillWasSet = true;
			}
			else if (bestPilotSkill < readSkill) bestPilotSkill = readSkill;
		}
	}
	
	private List<Integer> prepareNextPilotsAssignments(List<Integer> pilotsAssignments, int assignedPilotIndex, int pilotsSquadron)
	{
		List<Integer> nextPilotsAssignments = new ArrayList<Integer>(pilotsNumber);
		for (int actualPilotIndex = 0; actualPilotIndex < pilotsNumber; actualPilotIndex++)
		{
			if (actualPilotIndex == assignedPilotIndex) nextPilotsAssignments.add(actualPilotIndex, pilotsSquadron);
			else nextPilotsAssignments.add(actualPilotIndex, pilotsAssignments.get(actualPilotIndex).intValue());
		}
		return nextPilotsAssignments;
	}
	
	private void assignNextPilot(List<Integer> pilotsAssignments, int pilotSkill, int pilotIndex, int skillSumTeam1, int skillSumTeam2, int pilotsAssigned)
	{
		List<Integer> assignmentKey = prepareNextPilotsAssignments(pilotsAssignments, pilotIndex, 1);
		if (!assignmentAlreadyAnalyzed.containsKey(assignmentKey))
		{
			foundStates.add(new State(skillSumTeam1 + pilotSkill, skillSumTeam2, assignmentKey, pilotsAssigned));
			assignmentAlreadyAnalyzed.put(assignmentKey, true);
		}
		assignmentKey = prepareNextPilotsAssignments(pilotsAssignments, pilotIndex, 2);
		if (!assignmentAlreadyAnalyzed.containsKey(assignmentKey))
		{
			foundStates.add(new State(skillSumTeam1, skillSumTeam2 + pilotSkill, assignmentKey, pilotsAssigned));
			assignmentAlreadyAnalyzed.put(assignmentKey, true);
		}
		
		return;
	}
	
	
	
	private void findNewStates(State analyzedState)
	{
		int skillSumTeam1, skillSumTeam2, pilotsAssigned, pilotSkill;
		List<Integer> pilotsAssignments = analyzedState.pilotsAssignments;
		skillSumTeam1 = analyzedState.skillSumTeam1;
		skillSumTeam2 = analyzedState.skillSumTeam2;
		pilotsAssigned = analyzedState.pilotsAssigned;
		Iterator<Integer> iterator = pilotsSkills.iterator();
		for (int pilotIndex = 0; pilotIndex < pilotsNumber; pilotIndex++)
		{
			pilotSkill = iterator.next();
			if (pilotsAssignments.get(pilotIndex).intValue() == 0)
				assignNextPilot(pilotsAssignments, pilotSkill, pilotIndex, skillSumTeam1, skillSumTeam2, pilotsAssigned+1);
		}
	}
	
	private int divideInterceptorsIntoTwoSquadrons()
	{
		int minimum = bestPilotSkill, difference;
		int difference1minus2, difference2minus1;
		State analyzedState;
		foundStates.clear();
		List<Integer> pilotsAssignments = new ArrayList<Integer>(pilotsNumber);
		for (int pilot = 0; pilot < pilotsNumber; pilot++)
			pilotsAssignments.add(pilot, 0);
		foundStates.add(new State(0, 0, pilotsAssignments, 0));
		assignmentAlreadyAnalyzed.put(pilotsAssignments, true);
		while (!foundStates.isEmpty())
		{
			analyzedState = foundStates.removeFirst();
			if (analyzedState.pilotsAssigned == pilotsNumber)
			{
				difference1minus2 = analyzedState.skillSumTeam1 - analyzedState.skillSumTeam2;
				difference2minus1 = analyzedState.skillSumTeam2 - analyzedState.skillSumTeam1;
				difference = (difference1minus2 >= difference2minus1)? difference1minus2 : difference2minus1;
				if (difference < minimum) minimum = difference;
			}
			else findNewStates(analyzedState);
		}
		return minimum;
	}
	
	private void performOneTest()
	{
		readSkills();
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
