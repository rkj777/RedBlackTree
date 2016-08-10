import java.util.prefs.BackingStoreException;

import javax.print.attribute.standard.Compression;


public class RedBlackTree {
	
	public static void main(String[] args) throws NodeException {
		RedBlackTree r = new RedBlackTree();
		int[] insert = {1,2,3,4,5,6,7,8};
		
		for(int i : insert){
			r.insert(i);
		}
		
	
		r.print();
		System.out.println();
		
	}

	public Node root;
	public boolean validTree = true;
	private static final int STEP = 4;
	public RedBlackTree() throws NodeException{
		root = null;
		validProperties();
	}
	//Checks if the current tree is valid
	public void validProperties() throws NodeException{
		if(validTree){
			valid1(root);
			valid2(root);
			valid4(root);
			valid5(root);
		}
	}
	
	//Gets the color this makes null leaves black
	private int checkColor (Node n){
		if(n == null){
			return 0;
		}else{
			return n.color;
		}
	}
	
	//Checks rule 1. All nodes are either black or red
	private void valid1(Node n) throws NodeException{
		if(n == null){
			return;
		}
		else if (checkColor(n) != 0 || checkColor(n) != 1){
			throw new NodeException();
		}
		
		valid1(n.leftChild);
		valid1(n.rightChild);
		
	}
	
	//Checks if the root is black
	private void valid2(Node root) throws NodeException{
		if(checkColor(root) != 0){
			throw new NodeException();
		}
	}
	
	//Checks if every red node has a black parent
	private void valid4(Node n) throws NodeException{
		if(checkColor(n) ==  1){
			if(checkColor(n.leftChild) == 1 ||checkColor(n.rightChild) == 1 
				|| checkColor(n.parent) == 1){
				throw new NodeException();
			}
		}
		if(n == null){
			return;
		}
		valid4(n.leftChild);
		valid4(n.rightChild);
	}
	
	//Checks if each path has the same number of black
	private void valid5(Node n) throws NodeException{
		valid5Check(n,0,-1);
	}
	
	//Helps valid 5 with recursion 
	private int valid5Check(Node n, int blackCount, int pathCount) throws NodeException{
		if(checkColor(n) == 0){
			blackCount++;
		}
		
		if(n == null){
			if(pathCount == -1){
				pathCount = blackCount;
			}
			else if (blackCount != pathCount){
				throw new NodeException();
			}
			return pathCount;
		}
		pathCount = valid5Check(n.leftChild, blackCount, pathCount);
		pathCount = valid5Check(n.rightChild, blackCount, pathCount);
		return pathCount;
		
	}
	
	private void rotateLeft(Node n){
		Node r = n.rightChild;
		replaceNode (n,r);
		n.rightChild = r.leftChild;
		
		if(r.leftChild != null){
			r.leftChild.parent = n;
		}
		r.leftChild = n;
		n.parent = r;
	}
	
	private void rotateRight(Node n){
		Node l = n.leftChild;
		replaceNode(n, l);
		n.leftChild = l.rightChild;
		
		if(l.rightChild != null){
			l.rightChild.parent = n;
		}
		
		l.rightChild = n;
		n.parent = l;
		
	}
	
	private void replaceNode(Node oldNode, Node newNode){
		if(oldNode.parent == null){
			root = newNode; 
		}else{
			if(oldNode == oldNode.parent.leftChild){
				oldNode.parent.leftChild = newNode;
			}else{
				oldNode.parent.rightChild = newNode;
			}
			
		}
		if (newNode != null){
			newNode.parent = oldNode.parent;
		}
	}
	public void insert(int key) /*throws NodeException*/{
		Node insertNode = new Node(key,1,null,null);
		
		if(root == null){
			root = insertNode;
		}else {
			Node currentRoot = root;
			
			while(true){
				int comp =  key - currentRoot.key ;
				if(comp == 0){
					return;
				} else if (comp < 0	){
					
					if(currentRoot.leftChild == null){
						currentRoot.leftChild = insertNode;
						break;
						
					} else{
						currentRoot = currentRoot.leftChild;
					}	
				} else {
					if (currentRoot.rightChild == null){
						currentRoot.rightChild = insertNode;
						break;
					} else {
						currentRoot = currentRoot.rightChild;
					}
				}
			}
			insertNode.parent = currentRoot;
		}
		insertCase1(insertNode);
		//validProperties();
	}
	
	private void insertCase1(Node n){
		if (n.parent == null){
			n.color = 0;
		} else{
			insertCase2(n);
		}
	}
	
	private void insertCase2(Node n){
		if(checkColor(n.parent) == 0){
			return;
		}
		else{
			insertCase3(n);
		}
	}
	
	void insertCase3(Node n){
		if(checkColor(n.uncle()) == 1){
			n.parent.color = 0;
			n.uncle().color = 0;
			n.grandparent().color = 1;
			insertCase1(n.grandparent());
		}
		else{
			insertCase4(n);
		}
	}
	
	void insertCase4(Node n){
		if(n == n.parent.rightChild && n.parent == n.grandparent().leftChild){
			rotateLeft(n.parent);
			n = n.leftChild;
		}else if (n == n.parent.leftChild && n.parent == n.grandparent().rightChild){
			rotateRight(n.parent);
			n= n.rightChild;
		}
		insertCase5(n);
	}
	
	void insertCase5(Node n){
		n.parent.color = 0;
		n.grandparent().color = 1;
		if(n == n.parent.leftChild && n.parent == n.grandparent().leftChild){
			rotateRight(n.grandparent());
		} else{
			rotateLeft(n.grandparent());
		}
	}
	
	public void print2(){
		System.out.println(root.key);
		printer(root,0);
		
	}
	
	private static void printer(Node n, int indent){
		if (n == null){
			System.out.println("emmpty tree");
			return;
		}
		
		if( n.rightChild != null){
			printer(n.rightChild, indent + STEP);
		}
		
		for( int i =0; i < indent; i++){
			System.out.print(" ");
		}
		
		if(n.color == 0){
			System.out.println(n.key + " : " + n.color );
		} else {
			System.out.println(n.key + " : " + n.color);
		}
		
		if(n.leftChild != null){
			printer(n.leftChild, indent + STEP);
		}
	}
	
	public void print(){
		printhelp(root);
	}
	public void printhelp(Node n){
		if(n.leftChild != null){
			printhelp(n.leftChild);
		}
		
		if(n.rightChild != null){
			printhelp(n.rightChild);
		}
		
		System.out.print(n.key + " : " + n.color + " ");
	}
}
