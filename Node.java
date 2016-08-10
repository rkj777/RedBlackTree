
public class Node {
	//Values for each node
	public int key;
	public Node leftChild;
	public Node rightChild;
	public Node parent;
	//Note for color 0 = Black , 1 = Red
	public int color;

	public Node(int nkey, int ncolor, Node nleftChild, Node nrightChild){
		key = nkey;
		color = ncolor;
		leftChild = nleftChild;
		rightChild = nrightChild;
		parent = null;
		//Checking if the children are null
		if(nleftChild != null){
			nleftChild.parent = this;
		}
		
		if(nrightChild != null){
			nrightChild.parent = this;
		}
		
	}
	
	public Node grandparent(){
		//Checking to see if the grandparent exists
		if( parent != null && parent.parent != null){
			return parent.parent;
		}else{
			return null;
		}
	}
	
	public Node sibling () /*throws NodeException*/{
		/*Check to see if the root
		if(parent == null){
			throw new NodeException();
		}*/
		
		//Checking to see what node this is
		if(this == parent.leftChild){
			return parent.rightChild;
		}else{
			return parent.leftChild;
		}
	}
	
	public Node uncle() /*throws NodeException*/{
		//Checking to see if the root or child of the root 
		/*
		if(parent == null || parent.parent == null){
			throw new NodeException();
		} else{
			return parent.sibling();
		}*/
		return parent.sibling();
	}
}

