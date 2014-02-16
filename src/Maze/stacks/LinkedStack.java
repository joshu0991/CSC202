//----------------------------------------------------------------------
// LinkedStack.java         by Dale/Joyce/Weems                Chapter 3
//
// Implements UnboundedStackInterface using a linked list 
// to hold the stack elements.
//-----------------------------------------------------------------------

package Maze.stacks;

import stacks.LLNode;

public class LinkedStack<T> implements UnboundedStackInterface<T> {

	protected LLNode<T> top; // reference to the top of this stack
	protected LLNode<T> searcher;
	public int counter;

	public LinkedStack() {
		top = null;
		searcher = null;
	}

	public void push(T element)
	// Places element at the top of this stack.
	{
		LLNode<T> newNode = new LLNode<T>(element);
		newNode.setLink(top);
		top = newNode;
		counter++;
	}

	public void pop()
	// Throws StackUnderflowException if this stack is empty,
	// otherwise removes top element from this stack.

	{
		if (!isEmpty()) {
			top = top.getLink();
			counter--;
		} else
			throw new StackUnderflowException(
					"Pop attempted on an empty stack.");
	}

	public T top()
	// Throws StackUnderflowException if this stack is empty,
	// otherwise returns top element from this stack.
	{
		if (!isEmpty())
			return top.getInfo();
		else		
		
			throw new StackUnderflowException(
					"Top attempted on an empty stack. ");
	}

	public boolean isEmpty()
	// Returns true if this stack is empty, otherwise returns false.
	{
		if (top == null)
			return true;
		else
			return false;
	}

	// checks one element below the top element *not used
	public boolean checkSecound(String s) {

		Object o = top.getLink().getInfo();
		char a = s.charAt(4);
		if (s.equals(o) && a != '-') {

			return true;
		} else {
			return false;
		}
	}

	// checks the entire stack for a particular element returns true if found
	// otherwise returns false
	public boolean contains(String s) {
		boolean state = false;
		if (isEmpty() == false) {
			searcher = top;
			while (searcher != null) {
				Object search = searcher.getInfo();
				searcher = searcher.getLink();
				if (search.equals(s)) {
					state = true;
					break;
				} else {
					state = false;
				}

			}

		} else {
			state = false;
		}
		return state;
	}
}
