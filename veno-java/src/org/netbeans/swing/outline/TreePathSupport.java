/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.swing.outline;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.AbstractLayoutCache;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

/** Manages expanded/collapsed paths for the Outline.  Provides services similar
 * to those JTree implements inside its own class body.  Propagates changes
 * in expanded state to the layout cache.
 * <p>
 * Principally what this class does is manage the state of expanded paths which
 * are not visible, or whose parents have been closed/opened.  Whereas the
 * layout cache retains information only about what is visibly expanded, this
 * class manages information about any path that has been expanded at some
 * point in the lifetime of an Outline, so that for example, if A contains B
 * contains C, and A and B and C are expanded, then the user collapses A,
 * and later re-expands A, B and C will retain their expanded state and
 * appear as they did the last time A was expanded.
 * <p>
 * When nodes are removed, the OutlineModel must call removePath() for any
 * defunct paths to avoid memory leaks by the TreePathSupport holding 
 * references to defunct nodes and not allowing them to be garbage collected.
 * <p>
 * Its <code>addTreeWillExpandListener</code> code supports 
 * <code>ExtTreeWillExpandListener</code>, so such a listener may be notified
 * if some other listener vetoes a pending expansion event.
 *
 * @author  Tim Boudreau
 */
public final class TreePathSupport {

    private List<TreeExpansionListener> eListeners = new ArrayList<TreeExpansionListener>();
    private List<TreeWillExpandListener> weListeners = new ArrayList<TreeWillExpandListener>();
    private AbstractLayoutCache layout;
    
    /** Creates a new instance of TreePathSupport */
    public TreePathSupport(OutlineModel mdl, AbstractLayoutCache layout) {
        this.layout = layout;
    }
    
    /** Clear all expanded path data.  This is called if the tree model fires
     * a structural change, and any or all of the nodes it contains may no
     * longer be present. */
    public void clear() {
    }

    /** Expand a path.  Notifies the layout cache of the change,
     * stores the expanded path info (so re-expanding a parent node also re-expands
     * this path if a parent node containing it is later collapsed).  Fires
     * TreeWillExpand and TreeExpansion events.
     * @param path The tree path to expand
     */
    public void expandPath (TreePath path) {
        assert SwingUtilities.isEventDispatchThread();
        if (layout.isExpanded(path)) {
            //It's already expanded, don't waste cycles firing bogus events
            return;
        }
        TreeExpansionEvent e = new TreeExpansionEvent (this, path);
        try {
            fireTreeWillExpand(e, true);
            layout.setExpandedState(path, true);
            fireTreeExpansion(e, true);
        } catch (ExpandVetoException eve) {
            fireTreeExpansionVetoed (e, eve);
        }
    }
    
    /** Collapse a path.  Notifies the layout cache of the change,
     * stores the expanded path info (so re-expanding a parent node also re-expands
     * this path if a parent node containing it is later collapsed).  Fires
     * TreeWillExpand and TreeExpansion events.
     * @param path The tree path to collapse
     */
    public void collapsePath (TreePath path) {
        assert SwingUtilities.isEventDispatchThread();
        if (!layout.isExpanded(path)) {
            //It's already collapsed, don't waste cycles firing bogus events
            return;
        }
        TreeExpansionEvent e = new TreeExpansionEvent (this, path);
        try {
            fireTreeWillExpand(e, false);
            layout.setExpandedState(path, false);
            fireTreeExpansion(e, false);
        } catch (ExpandVetoException eve) {
            fireTreeExpansionVetoed (e, eve);
        }
    }
    
    /** Remove a path's data from the list of known paths.  Called when
     * a tree model deletion event occurs
     * @param path The tree path to remove
     */
    public void removePath (TreePath path) {
    }
    
    private void fireTreeExpansion (TreeExpansionEvent e, boolean expanded) {
        int size = eListeners.size();
        
        TreeExpansionListener[] listeners = new TreeExpansionListener[size];
        synchronized (this) {
            listeners = eListeners.toArray(listeners);
        }
        for (int i=0; i < listeners.length; i++) {
            if (expanded) {
                listeners[i].treeExpanded(e);
            } else {
                listeners[i].treeCollapsed(e);
            }
        }
    }
    
    private void fireTreeWillExpand (TreeExpansionEvent e, boolean expanded) throws ExpandVetoException {
        int size = weListeners.size();
        
        TreeWillExpandListener[] listeners = new TreeWillExpandListener[size];
        synchronized (this) {
            listeners = weListeners.toArray(listeners);
        }
        for (int i=0; i < listeners.length; i++) {
            if (expanded) {
                listeners[i].treeWillExpand(e);
            } else {
                listeners[i].treeWillCollapse(e);
            }
        }
    }
    
    private void fireTreeExpansionVetoed (TreeExpansionEvent e, ExpandVetoException ex) {
        int size = weListeners.size();
        
        TreeWillExpandListener[] listeners = new TreeWillExpandListener[size];
        synchronized (this) {
            listeners = weListeners.toArray(listeners);
        }
        for (int i=0; i < listeners.length; i++) {
            if (listeners[i] instanceof ExtTreeWillExpandListener) {
                ((ExtTreeWillExpandListener) listeners[i]).treeExpansionVetoed(e,
                    ex);
            }
        }
    }
    
    /**
     * Test if the tree path is expanded.
     * @param path The tree path to test
     * @return <code>true</code> if the path is expanded, <code>false</code> otherwise.
     */
    public boolean hasBeenExpanded(TreePath path) {
        assert SwingUtilities.isEventDispatchThread();
	return (path != null && layout.isExpanded(path));
    }

    /**
     * Returns true if the node identified by the path is currently expanded,
     * 
     * @param path  the <code>TreePath</code> specifying the node to check
     * @return false if any of the nodes in the node's path are collapsed, 
     *               true if all nodes in the path are expanded
     */
    public boolean isExpanded(TreePath path) {
        assert SwingUtilities.isEventDispatchThread();
	if(path == null)
	    return false;

        if (!layout.isRootVisible() && path.getParentPath() == null) {
            return true; // Invisible root is always expanded
        }

	// Is this node expanded?
	boolean nodeExpanded = layout.isExpanded(path);
        if (!nodeExpanded) {
            return false;
        }

	// It is, make sure its parent is also expanded.
	TreePath parentPath = path.getParentPath();

	if(parentPath != null)
	    return isExpanded(parentPath);
        return true;
    }

    /**
     * Test if the tree path is visible (the parent path is expanded).
     * @param path The tree path to test
     * @return <code>true</code> if the path is visible, <code>false</code> otherwise.
     */
    public boolean isVisible(TreePath path) {
        if(path != null) {
	    TreePath parentPath = path.getParentPath();

	    if(parentPath != null) {
		return isExpanded(parentPath);
            }
	    // Root.
	    return true;
	}
        return false;
    }    

    /**
     * Get all expanded descendants of the given tree path.
     * @param parent Tree path to find the expanded descendants for
     * @return All expanded descendants.
     */
    public TreePath[] getExpandedDescendants(TreePath parent) {
        assert SwingUtilities.isEventDispatchThread();
        TreePath[] result = new TreePath[0];
	if(isExpanded(parent)) {
            TreePath path;
            List<TreePath> results = null;

            Enumeration<TreePath> tpe = layout.getVisiblePathsFrom(parent);
            if (tpe != null) {
                while (tpe.hasMoreElements()) {
                    path = tpe.nextElement();
                    // Add the path if it is expanded, a descendant of parent,
                    // and it is visible (all parents expanded). This is rather
                    // expensive!
                    if (path != parent &&
                        layout.isExpanded(path) &&
                        parent.isDescendant(path)) {
                        
                        if (results == null) {
                            results = new ArrayList<TreePath>();
                        }
                        results.add (path);
                    }
                }
                if (results != null) {
                    result = results.toArray(result);
                }
            }
        }
        return result;
    }    
    
    /** Add a TreeExpansionListener.  If the TreeWillExpandListener implements
     * ExtTreeExpansionListener, it will be notified if another 
     * TreeWillExpandListener vetoes the expansion event
     * @param l The tree expansion listener
     */
    public synchronized void addTreeExpansionListener (TreeExpansionListener l) {
        eListeners.add(l);
    }

    /**
     * Remove a TreeExpansionListener.
     * @param l The tree expansion listener
     */
    public synchronized void removeTreeExpansionListener (TreeExpansionListener l) {
        eListeners.remove(l);
    }

    /**
     * Add a TreeWillExpandListener.
     * @param l The tree will expand listener
     */
    public synchronized void addTreeWillExpandListener (TreeWillExpandListener l) {
        weListeners.add(l);
    }
    
    /**
     * Remove a TreeWillExpandListener.
     * @param l The tree will expand listener
     */
    public synchronized void removeTreeWillExpandListener (TreeWillExpandListener l) {
        weListeners.remove(l);
    }
}
