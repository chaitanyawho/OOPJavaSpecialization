package textgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private LinkedList<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	boolean isTrained;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		isTrained = false;
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	private int find(LinkedList<ListNode> ll, String word) {
		for(int i = 0;i<ll.size();i++)
			if(ll.get(i).getWord().equalsIgnoreCase(word))
					return i;
		return -1;
	}
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if(isTrained)
			return;
		List<String> src = new ArrayList<String>(Arrays.asList(sourceText.split("[ ]+")));
		starter = src.remove(0);
		String prevWord = starter;
	
		boolean flag;
		for(String w: src) {
			ListNode pW = new ListNode(prevWord);
			flag = true;
			for(int i = 0;i<wordList.size();i++){
				if(wordList.get(i).getWord().equalsIgnoreCase(prevWord)) {
					wordList.get(i).addNextWord(w);
					flag = false;
				}
					
			}if(flag){
				wordList.add(pW);
				wordList.getLast().addNextWord(w);
			}
			prevWord = w;
		}
		//this does not add last word to wordList!
		//adding now:
		flag = true;
		int i;
		for(i = 0;i<wordList.size();i++){
			if(wordList.get(i).getWord().equalsIgnoreCase(prevWord)) {
				flag = false;
				break;
			}
		}
		if(flag) {//did not find lastWord
			wordList.add(new ListNode(prevWord));
			wordList.getLast().addNextWord(starter);
		}
		else//found lastWord in wordList at i
			wordList.get(i).addNextWord(starter);
		isTrained = true;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if(numWords==0) return "";
		if(!isTrained) return "";
		
		String currWord = starter;
		String output = "";
		output+=currWord;
		numWords--;
		while(numWords-->0) {
			String w = wordList.get(find(wordList, currWord)).getRandomNextWord(rnGenerator);
			output+=" "+w;
			currWord = w;
		}
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		isTrained = false;
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		
		
		
		
		
		
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		String t2 = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		//gen.train(t2);
		System.out.println(gen);
		
		System.out.println(gen.generateText(0));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(0));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		String ret = nextWords.get(generator.nextInt(nextWords.size()));
	    return ret;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


