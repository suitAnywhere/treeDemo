
package com.example.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节�?
 * 
 * @author LongShao
 * 
 */
public class Tree
{
	private Tree parent;// 父节�?
	private List<Tree> children = new ArrayList<Tree>();// 子节�?
	private String text;// 节点显示的文�?
	private String value;// 节点的�??
	private String line;// xaian点的�?
	private int icon = -1;// 是否显示小图�?,-1表示隐藏图标
	private boolean isChecked = false;// 是否处于选中状�??
	private boolean isExpanded = true;// 是否处于展开状�??
	private boolean hasCheckBox = true;// 是否拥有复�?�框

	/**
	 * Node构�?�函�?
	 * 
	 * @param text
	 *            节点显示的文�?
	 * @param value
	 *            节点的�??
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
	 * 设置父节�?
	 * 
	 * @param node
	 */
	public void setParent(Tree node)
	{
		this.parent = node;
	}

	/**
	 * 获得父节�?
	 * 
	 * @return
	 */
	public Tree getParent()
	{
		return this.parent;
	}

	/**
	 * 设置节点文本
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * 获得节点文本
	 * 
	 * @return
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * 设置节点�?
	 * 
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * 获得节点�?
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
	 * 设置节点图标文件
	 * 
	 * @param icon
	 */
	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	/**
	 * 获得图标文件
	 * 
	 * @return
	 */
	public int getIcon()
	{
		return icon;
	}

	/**
	 * 是否根节�?
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null ? true : false;
	}

	/**
	 * 获得子节�?
	 * 
	 * @return
	 */
	public List<Tree> getChildren()
	{
		return this.children;
	}

	/**
	 * 添加子节�?
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
	 * 清除�?有子节点
	 */
	public void clear()
	{
		children.clear();
	}

	/**
	 * 删除�?个子节点
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
	 * 删除指定位置的子节点
	 * 
	 * @param location
	 */
	public void remove(int location)
	{
		children.remove(location);
	}

	/**
	 * 获得节点的级�?,根节点为0
	 * 
	 * @return
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	/**
	 * 设置节点选中状�??
	 * 
	 * @param isChecked
	 */
	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}

	/**
	 * 获得节点选中状�??
	 * 
	 * @return
	 */
	public boolean isChecked()
	{
		return isChecked;
	}

	/**
	 * 设置是否拥有复�?�框
	 * 
	 * @param hasCheckBox
	 */
	public void setCheckBox(boolean hasCheckBox)
	{
		this.hasCheckBox = hasCheckBox;
	}

	/**
	 * 是否拥有复�?�框
	 * 
	 * @return
	 */
	public boolean hasCheckBox()
	{
		return hasCheckBox;
	}

	/**
	 * 是否叶节�?,即没有子节点的节�?
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() < 1 ? true : false;
	}

	/**
	 * 当前节点是否处于展开状�??
	 * 
	 * @return
	 */
	public boolean isExpanded()
	{
		return isExpanded;
	}

	/**
	 * 设置节点展开状�??
	 * 
	 * @return
	 */
	public void setExpanded(boolean isExpanded)
	{
		this.isExpanded = isExpanded;
	}

	/**
	 * 递归判断父节点是否处于折叠状�?,有一个父节点折叠则认为是折叠状�??
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
	 * 递归判断�?给的节点是否当前节点的父节点
	 * 
	 * @param node
	 *            �?给节�?
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
