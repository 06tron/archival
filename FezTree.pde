import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

void setup() {
  size(1200, 800);
  background(255);
  textSize(30);
  fill(0);
  stroke(0);
  int nameNum = 10;
  ArrayList<String> unusedNames = new ArrayList<String>();
  for (int i = 0; i < nameNum; i++) {
    unusedNames.add(Integer.toHexString(i));
  }
  ArrayList<String> usedNames = new ArrayList<String>();
  String firstName = detachRand(unusedNames);
  Tree tree = new Tree(firstName, firstName);
  usedNames.add(firstName);
  while (unusedNames.size() > 0) {
    String name = detachRand(unusedNames);
    tree.addNode(chooseRand(usedNames), name, name, true);
    usedNames.add(name);
  }
  tree.drawTree(this);
  System.out.println(tree.getLongestPath());
}

public static <T> T detachRand(ArrayList<T> list) {
  return list.remove((int) (Math.random() * list.size()));
}

public static <T> T chooseRand(ArrayList<T> list) {
  return list.get((int) (Math.random() * list.size()));
}

public static class Tree {

  private HashMap<String, Node> nodes;

  public Tree(String nodeName, String nodeAbr) {
    nodes = new HashMap<String, Node>();
    Node firstNode = new Node(nodeAbr);
    nodes.put(nodeName, firstNode);
  }

  public void addNode(String parentName, String childName, String childAbr, boolean randomize) {
    Node childNode = new Node(childAbr);
    nodes.put(childName, childNode);
    Node parentNode = nodes.get(parentName);
    parentNode.addChild(childNode, randomize);
    childNode.addChild(parentNode, randomize);
  }
  
  public boolean containsName(String name) {
    return nodes.containsKey(name);
  }

  public ArrayList<ArrayList<Node>> getArrangedNodes(String rootName) {
    ArrayList<ArrayList<Node>> allNodes = new ArrayList<ArrayList<Node>>();
    Node rootNode = nodes.get(rootName);
    rootNode.getNodes(0, null, allNodes);
    return allNodes;
  }
  
  public void drawTree(PApplet p) {
    ArrayList<Node> longestPath = getLongestPath();
    Node centerNode = longestPath.get(longestPath.size()/2);
    ArrayList<ArrayList<Node>> allNodes = new ArrayList<ArrayList<Node>>();
    centerNode.getNodes(0, null, allNodes);
    float depthGap = p.height/(allNodes.size()+1);
    centerNode.drawChildren(0, depthGap, p.width/2, allNodes, p);
  }
  
  public ArrayList<Node> getLongestPath() {
    Node chosenNode = nodes.values().iterator().next();
    Node furthestNode = chosenNode.getFurthestNode(true, null);
    Node fNparent = furthestNode.getChildren().get(0);
    ArrayList<Node> longestPath = fNparent.listBL(furthestNode); // null because furthestNode is leaf
    longestPath.add(furthestNode);
    return longestPath;
  }

}

public static class Node {

  private String abr;

  private ArrayList<Node> children;

  public Node(String abr) {
    this.abr = abr;
    children = new ArrayList<Node>();
  }
  
  public ArrayList<Node> getChildren() {
    return children;
  }

  public void addChild(Node child, boolean randomize) {
    if (randomize) {
      children.add((int) (Math.random() * children.size()), child);
    } else {
      children.add(child);
    }
  }

  public void getNodes(int currDepth, Node parent, ArrayList<ArrayList<Node>> allNodes) {
    if (allNodes.size() == currDepth) {
      allNodes.add(new ArrayList<Node>());
    }
    allNodes.get(currDepth).add(this);
    for (Node child : children) {
      if (currDepth == 0 || !child.equals(parent)) {
        child.getNodes(currDepth+1, this, allNodes);
      }
    }
  }
  
  public boolean isChildOf(Node node) {
    for (Node child : node.children) {
      if (child.equals(this)) return true;
    }
    return false;
  }
  
  public void drawChildren(int currDepth, float depthGap, float horzPos, ArrayList<ArrayList<Node>> allNodes, PApplet p) {
    p.text(toString(), horzPos, depthGap*(currDepth+1));
    if (currDepth == 0 || children.size() > 1) {
      ArrayList<Node> lowerNodes = allNodes.get(currDepth+1);
      float nodeGap = p.width/(lowerNodes.size()+1);
      for (int i = 0; i < lowerNodes.size(); i++) {
        if (lowerNodes.get(i).isChildOf(this)) {
          p.line(horzPos, depthGap*(currDepth+1), nodeGap*(i+1), depthGap*(currDepth+2));
          lowerNodes.get(i).drawChildren(currDepth+1, depthGap, nodeGap*(i+1), allNodes, p);
          // xy of parent: horzPos, depthGap*(currDepth+1)
          // xy of child: nodeGap*(i+1), depthGap*(currDepth+2)
        }
      }
    }
  }
  
  public int getBL(Node parent) {
    int longestBL = 0;
    for (Node child : children) {
      if (!child.equals(parent)) {
        int childBL = child.getBL(this);
        if (childBL > longestBL) {
          longestBL = childBL;
        }
      }
    }
    return longestBL + 1;
  }

  public ArrayList<Node> listBL(Node parent) {
    ArrayList<Node> branch;
    if (children.size() == 1) {
      branch = new ArrayList<Node>();
    } else {
      branch = null; // might stay null?
      int longestListSize = 0;
      for (Node child : children) {
        if (!child.equals(parent)) {
          ArrayList<Node> childBranch = child.listBL(this);
          if (childBranch.size() > longestListSize) {
            branch = childBranch;
            longestListSize = branch.size();
          }
        }
      }
    }
    branch.add(this);
    return branch;
  }
  
  public Node getFurthestNode(boolean noParent, Node parent) {
    int longestLength = 0;
    Node nextNode = null;
    for (Node child : children) {
      if (noParent || !child.equals(parent)) {
        int childLength = child.getBL(this);
        if (childLength > longestLength) {
          longestLength = childLength;
          nextNode = child;
        }
      }
    }
    if (nextNode == null) {
      return this;
    } else {
      return nextNode.getFurthestNode(false, this);
    }
  }

  public String toString() {
    return abr;
  }

}

/*Tree tree = new Tree("root00", "r00");
  tree.addNode("root00", "branch01", "b11");
  tree.addNode("root00", "branch02", "b12");
  tree.addNode("branch01", "branch03", "b21");
  tree.addNode("branch01", "branch04", "b22");
  tree.addNode("branch02", "leaf05", "l23");
  tree.addNode("branch02", "branch06", "b24");
  tree.addNode("branch03", "leaf07", "l31");
  tree.addNode("branch03", "leaf08", "l32");
  tree.addNode("branch04", "leaf09", "l33");
  tree.addNode("branch06", "leaf10", "l34");
  tree.addNode("branch06", "leaf11", "l35");
  tree.drawTree("root00", this);*/
