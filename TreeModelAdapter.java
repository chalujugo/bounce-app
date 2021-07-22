/*
 *	===============================================================================
 *	TreeModelAdapter.java : Creates a tree model.
 *	CHALU JUGO CJUG294 6/6/21
 *	=============================================================================== */

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class TreeModelAdapter implements TreeModel {
    
    private Shape nestedShape;
    private ArrayList<TreeModelListener> treeModelListener;
    
    public TreeModelAdapter(NestedShape ns) {
        Shape s = (NestedShape)ns;
        nestedShape = s;
        treeModelListener = new ArrayList<TreeModelListener>();
    }
    
    public Object getRoot() {
        return nestedShape;
    }
    
    public boolean isLeaf(Object node) {
        if(node instanceof NestedShape) {
            return false;
        }
        return true;
    }
    
    public Object getChild(Object parent, int index) {
        if(parent instanceof NestedShape) {
            if(index >= 0 && index < ((NestedShape)parent).getChildren().length) {
             {
                NestedShape ns = (NestedShape) parent;
                Object res = ns.getShapeAt(index);
                return res;
                }
            }
        } return null;
    }
    
    public int getChildCount(Object parent){
        int res = 0;
        if(parent instanceof NestedShape) {
            NestedShape ns = (NestedShape) parent;
            res = ns.getChildren().length;
            return res;
            }
        return res;
    }
    
    public int getIndexOfChild(Object parent, Object child) {
        if(child == null || parent == null)
            return -1;
        else{
            NestedShape parent1 = (NestedShape) parent;
            Shape[] childList = parent1.getChildren();
            for (int x = 0; x < childList.length; x++) {
            if (childList[x] == child)
                return x;
            }
            return -1;
        }
    }
    

    
    public void addTreeModelListener(final TreeModelListener modelListener) {
        treeModelListener.add(modelListener);
    }
	public void removeTreeModelListener(final TreeModelListener modelListener) {
	    if(treeModelListener.contains(modelListener)) {
	        treeModelListener.remove(modelListener);
	    }
	}
	
	public void  fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
	   TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
	   for (TreeModelListener listener: treeModelListener) {
	       listener.treeStructureChanged(e);
	    }
	}
	
	public void fireTreeNodesInserted(Object source, Object[] path,int[] childIndices,Object[] children) {
	    TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (TreeModelListener listener: treeModelListener) {
            listener.treeNodesInserted(e);
        }
	}
	
	public void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
	    TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (TreeModelListener listener: treeModelListener) {
            listener.treeNodesRemoved(e);
        }
	}

    public void addToRoot(Shape s){
        int size = ((NestedShape)nestedShape).getSize();
        s.setParent((NestedShape)nestedShape);
        ((NestedShape)nestedShape).add(s);
        
        int[] childIndices = {size};
        Object[] children = {s};
        Object[] parent_path = {(NestedShape)nestedShape};
        
        
        fireTreeNodesInserted(this, parent_path, childIndices, children);
    }
    
    public boolean addNode(TreePath selectedPath, ShapeType currentShapeType) {
        Object obj = selectedPath.getLastPathComponent();
        
        if(!(obj instanceof NestedShape)) {
            return false;
        }
        
        NestedShape node = (NestedShape) obj;
        int size = node.getSize();
        node.createAddInnerShape(currentShapeType);
        Shape inner_shape = node.getShapeAt(size);
        fireTreeNodesInserted(this, selectedPath.getPath(), new int[] {size}, new Object[] {inner_shape});
        return true;
        
    }
    
    public boolean removeNodeFromParent(TreePath selectedPath) {
        Object obj = selectedPath.getLastPathComponent();
        if(obj == nestedShape) {
            return false;
        }
        
        Shape node = (Shape) obj;
        NestedShape parent = node.getParent();
        int index = parent.indexOf(node);
        ((NestedShape)nestedShape).remove(node);
        fireTreeNodesRemoved(this, Arrays.copyOf(selectedPath.getPath(), selectedPath.getPath().length-1), new int[] {index}, new Object[] {node});
        return true;
    }
        
    
	public void valueForPathChanged(TreePath path, Object newValue) {}
	public void fireTreeNodesChanged(TreeModelEvent e) {}


}