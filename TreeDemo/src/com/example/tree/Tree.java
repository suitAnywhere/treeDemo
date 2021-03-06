
package com.example.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * æ èç?
 * 
 * @author LongShao
 * 
 */
public class Tree
{
	private Tree parent;// ç¶èç?
	private List<Tree> children = new ArrayList<Tree>();// å­èç?
	private String text;// èç¹æ¾ç¤ºçæå­?
	private String value;// èç¹çå??
	private String line;// xaianç¹çå?
	private int icon = -1;// æ¯å¦æ¾ç¤ºå°å¾æ ?,-1è¡¨ç¤ºéèå¾æ 
	private boolean isChecked = false;// æ¯å¦å¤äºéä¸­ç¶æ??
	private boolean isExpanded = true;// æ¯å¦å¤äºå±å¼ç¶æ??
	private boolean hasCheckBox = true;// æ¯å¦æ¥æå¤é?æ¡

	/**
	 * Nodeæé? å½æ?
	 * 
	 * @param text
	 *            èç¹æ¾ç¤ºçæå­?
	 * @param value
	 *            èç¹çå??
	 */
	public Tree(String text, String value)
	{
		this.text = text;
		this.value = value;
	}

	public Tree(String text, String value, String line)
	{
		this.text = text;
		this.value = value;
		this.line = line;
	}

	/**
	 * è®¾ç½®ç¶èç?
	 * 
	 * @param node
	 */
	public void setParent(Tree node)
	{
		this.parent = node;
	}

	/**
	 * è·å¾ç¶èç?
	 * 
	 * @return
	 */
	public Tree getParent()
	{
		return this.parent;
	}

	/**
	 * è®¾ç½®èç¹ææ¬
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * è·å¾èç¹ææ¬
	 * 
	 * @return
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * è®¾ç½®èç¹å?
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * è·å¾èç¹å?
	 * 
	 * @return
	 */
	public String getValue()
	{
		return this.value;
	}

	public void setLine(String line)
	{
		this.line = line;
	}

	public String getLine()
	{
		return this.line;
	}

	/**
	 * è®¾ç½®èç¹å¾æ æä»¶
	 * 
	 * @param icon
	 */
	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	/**
	 * è·å¾å¾æ æä»¶
	 * 
	 * @return
	 */
	public int getIcon()
	{
		return icon;
	}

	/**
	 * æ¯å¦æ ¹èç?
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null ? true : false;
	}

	/**
	 * è·å¾å­èç?
	 * 
	 * @return
	 */
	public List<Tree> getChildren()
	{
		return this.children;
	}

	/**
	 * æ·»å å­èç?
	 * 
	 * @param node
	 */
	public void add(Tree node)
	{
		if (!children.contains(node))
		{
			children.add(node);
		}
	}

	/**
	 * æ¸é¤æ?æå­èç¹
	 */
	public void clear()
	{
		children.clear();
	}

	/**
	 * å é¤ä¸?ä¸ªå­èç¹
	 * 
	 * @param node
	 */
	public void remove(Tree node)
	{
		if (!children.contains(node))
		{
			children.remove(node);
		}
	}

	/**
	 * å é¤æå®ä½ç½®çå­èç¹
	 * 
	 * @param location
	 */
	public void remove(int location)
	{
		children.remove(location);
	}

	/**
	 * è·å¾èç¹ççº§æ?,æ ¹èç¹ä¸º0
	 * 
	 * @return
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	/**
	 * è®¾ç½®èç¹éä¸­ç¶æ??
	 * 
	 * @param isChecked
	 */
	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}

	/**
	 * è·å¾èç¹éä¸­ç¶æ??
	 * 
	 * @return
	 */
	public boolean isChecked()
	{
		return isChecked;
	}

	/**
	 * è®¾ç½®æ¯å¦æ¥æå¤é?æ¡
	 * 
	 * @param hasCheckBox
	 */
	public void setCheckBox(boolean hasCheckBox)
	{
		this.hasCheckBox = hasCheckBox;
	}

	/**
	 * æ¯å¦æ¥æå¤é?æ¡
	 * 
	 * @return
	 */
	public boolean hasCheckBox()
	{
		return hasCheckBox;
	}

	/**
	 * æ¯å¦å¶èç?,å³æ²¡æå­èç¹çèç?
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() < 1 ? true : false;
	}

	/**
	 * å½åèç¹æ¯å¦å¤äºå±å¼ç¶æ??
	 * 
	 * @return
	 */
	public boolean isExpanded()
	{
		return isExpanded;
	}

	/**
	 * è®¾ç½®èç¹å±å¼ç¶æ??
	 * 
	 * @return
	 */
	public void setExpanded(boolean isExpanded)
	{
		this.isExpanded = isExpanded;
	}

	/**
	 * éå½å¤æ­ç¶èç¹æ¯å¦å¤äºæå ç¶æ?,æä¸ä¸ªç¶èç¹æå åè®¤ä¸ºæ¯æå ç¶æ??
	 * 
	 * @return
	 */
	public boolean isParentCollapsed()
	{
		if (parent == null)
			return !isExpanded;
		if (!parent.isExpanded())
			return true;
		return parent.isParentCollapsed();
	}

	@Override
	public String toString()
	{
		return "EventNode [text=" + text + "]";
	}

	/**
	 * éå½å¤æ­æ?ç»çèç¹æ¯å¦å½åèç¹çç¶èç¹
	 * 
	 * @param node
	 *            æ?ç»èç?
	 * @return
	 */
	public boolean isParent(Tree node)
	{
		if (parent == null)
			return false;
		if (node.equals(parent))
			return true;
		return parent.isParent(node);
	}

	
}
