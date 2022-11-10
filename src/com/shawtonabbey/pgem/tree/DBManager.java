package com.shawtonabbey.pgem.tree;
import javax.swing.*;
import javax.swing.tree.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import javax.swing.event.*;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

@Component
@Scope("prototype")
public class DBManager extends Group<ItemModel> implements TreeModel
{

	private Observable<TreeModelListener> treeModelListeners = new Observable<>(TreeModelListener.class);

	@Autowired
	private EventDispatch dispatch;
	
	public interface Ev extends Add<DBManager> {}
	
	private JTree tree;
	
	public void setTree(JTree tree) {
		this.tree = tree;
		
		addExpandHook();
	}
	
	
	public DBManager()
	{
		super(null, "Database Manager");
		
	}

	public void load() {
		dispatch.find(Ev.class).fire(o->o.added(this, new Event()));
	}
	


	
	private Hashtable<ItemModel, Runnable> expandItems = new Hashtable<>();
	public void AddWillExpandListener(ItemModel node, Runnable toRun) {
		expandItems.put(node, toRun);
	}
	private void addExpandHook() {
		
		tree.addTreeWillExpandListener(new TreeWillExpandListener() {

			@Override
			public void treeWillCollapse(TreeExpansionEvent arg0) throws ExpandVetoException {}

			@Override
			public void treeWillExpand(TreeExpansionEvent arg0) throws ExpandVetoException {
			
				Object t = arg0.getPath().getLastPathComponent();
				if (expandItems.containsKey(t)) {
					var runnable = expandItems.get(t);
					expandItems.remove(t);
					runnable.run();
					
				}
				
			}
			
		});
		
	}
	
	
	public void addWillExpandListener(TreeWillExpandListener listener) {
		if (tree != null)
			tree.addTreeWillExpandListener(listener);
	}
	
//////////////// Fire TreeModel events //////////////////////////////////////////////

	protected void fireTreeNodesChanged(ItemModel node)
	{

		var e = new TreeModelEvent(this,
			new Object[] {node});

		treeModelListeners.fire(o->o.treeNodesChanged(e));
	}

	
	@Override
	protected void fireTreeNodesInserted(ItemModel src, Object[] path, int[] ids, Object[] children)
	{
		var newPath = buildPath(path, this);
		var e = new TreeModelEvent(src, newPath, ids, children);

		treeModelListeners.fire(o->o.treeNodesInserted(e));
	}

	@Override
	protected void fireTreeNodesRemoved(ItemModel src, Object[] path, int[] ids, Object[] children)
	{

		var newPath = buildPath(path, this);
		var e = new TreeModelEvent(src, newPath, ids, children);

		treeModelListeners.fire(o->o.treeNodesRemoved(e));
	}

	
	protected void fireTreeStructureChanged(ItemModel node)
	{

		var e = new TreeModelEvent(this,
			new Object[] {node});

		treeModelListeners.fire(o->o.treeStructureChanged(e));
	}


//////////////// TreeModel interface implementation ///////////////////////


    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
	public void addTreeModelListener(TreeModelListener l)
	{
		treeModelListeners.listen(l);
	}

	public Object getChild(Object parent, int index)
	{
		return ((ItemModel)parent).getChildAt(index);

	}

	public int getChildCount(Object parent)
	{
		return ((ItemModel)parent).getChildCount();
	}

	public int getIndexOfChild(Object parent, Object child)
	{
		return ((ItemModel)parent).getIndex((ItemModel)child);

	}

	public Object getRoot()
	{
		return this;
	}

	public boolean isLeaf(Object node)
	{
		return ((ItemModel)node).isLeaf();
 	}

	public void removeTreeModelListener(TreeModelListener l)
	{
		treeModelListeners.ignore(l);
	}

    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/serverGroup.png"));
	}
	
}
