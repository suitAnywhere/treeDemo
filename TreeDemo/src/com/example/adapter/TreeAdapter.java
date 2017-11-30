
package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.tree.Tree;
import com.example.treedemo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
@SuppressLint("InflateParams")
public class TreeAdapter extends BaseAdapter
{

	private Context context;
	private List<Tree> allsCache = new ArrayList<Tree>();
	/**
	 * 用来存放节点�?1 的EventNode
	 * 
	 */
	private List<Tree> EventNodeLevel1 = new ArrayList<Tree>();
	private List<Tree> alls = new ArrayList<Tree>();

	private String EventNodeValues = "";
	private boolean hasCheckBox = true;// 是否拥有复�?�框
	private int expandedIcon = -1;
	private int collapsedIcon = -1;

	private boolean expand = true;
	private boolean setAlign = true;

	public TreeAdapter(Context context, Tree rootEventNode)
	{
		this.context = context;
		addEventNode(rootEventNode);
		findLevelEventNode();
	}

	@Override
	public int getCount()
	{

		return alls.size();
	}

	@Override
	public Object getItem(int position)
	{

		return alls.get(position);
	}

	@Override
	public long getItemId(int position)
	{

		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{

		ViewHolder holder = null;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(R.layout.item_add_attention_tree, null);

			holder = new ViewHolder();
			holder.chbSelect = (CheckBox) view.findViewById(R.id.chbSelect);
			holder.mTextView = (TextView) view.findViewById(R.id.tree_tv);

			holder.ivExEc = (ImageView) view.findViewById(R.id.ivExEc);
			view.setTag(holder);
			// 复�?�框单击事件
		} else
		{
			holder = (ViewHolder) view.getTag();
		}

		// 得到当前节点
		Tree n = alls.get(position);

		if (n != null)
		{
			holder.chbSelect.setTag(n);
			holder.chbSelect.setChecked(n.isChecked());

			// 是否显示复�?�框
			if (n.hasCheckBox() && hasCheckBox)
			{
				holder.chbSelect.setVisibility(View.VISIBLE);
			} else
			{
				holder.chbSelect.setVisibility(View.GONE);
			}

			// 显示文本
			holder.mTextView.setText(n.getText());
			if (setAlign)
			{
				if (n.isLeaf())
				{
					// 是叶节点 不显示展�?和折叠状态图�?
					holder.ivExEc.setVisibility(View.GONE);
				} else
				{
					// 单击时控制子节点展开和折�?,状�?�图标改�?
					holder.ivExEc.setVisibility(View.VISIBLE);
					if (n.isExpanded())
					{
						if (expandedIcon != -1)
							holder.ivExEc.setImageResource(expandedIcon);
					} else
					{
						if (collapsedIcon != -1)
							holder.ivExEc.setImageResource(collapsedIcon);
					}

				}

				// 控制缩进
				view.setPadding(35 * n.getLevel(), 3, 3, 3);
			}
		}
		if (n.isRoot())
		{
			holder.ivExEc.setVisibility(View.GONE);
			holder.chbSelect.setVisibility(View.GONE);
		}

		setCheckBoxListener(holder.chbSelect);

		return view;
	}

	private void setCheckBoxListener(CheckBox checkBox)
	{
		checkBox.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Tree n = (Tree) v.getTag();
				findParentEventNode(n);
				/**
				 * 
				 * 设置每次只能选择�?个公司，如果选择的节点不属于当前的公司的节点，就取消当前公司的所有节点的选中状�??, 并且将它的状态设置为折叠
				 * 
				 *
				 */
				for (int i = 0; i < EventNodeLevel1.size(); i++)
				{

					Tree EventNode = EventNodeLevel1.get(i);

					if (!EventNode.getValue().equals(EventNodeValues))
					{
						checkEventNode(EventNode, false);

						EventNode.setExpanded(false);
						filterEventNode();
					}

				}
				checkEventNode(n, ((CheckBox) v).isChecked());

				notifyDataSetChanged();
				// context.onChange();
				List<Tree> EventNodes = getSeletedEventNodes();
				int num = 0;
				for (int i = 0; i < EventNodes.size(); i++)
				{
					Tree getEventNode = EventNodes.get(i);
					if (getEventNode.isLeaf())
					{
						num++;
						if (num > 5)
						{
							// activity.showTip("查询的表的数量超过最大�??...");
						}
					}
				}

			}

		});

	}

	/**
	 * 
	 * 列表项控件集�?
	 * 
	 */
	public class ViewHolder
	{
		CheckBox chbSelect;// 选中与否
		TextView mTextView;
		ImageView ivExEc;// 展开或折叠标�?">"�?"v"
	}

	private void addEventNode(Tree EventNode)
	{
		alls.add(EventNode);
		allsCache.add(EventNode);
		if (EventNode.isLeaf())
			return;
		for (int i = 0; i < EventNode.getChildren().size(); i++)
		{
			addEventNode(EventNode.getChildren().get(i));
		}
	}

	/**
	 * 找到level�?1 �? EventNode
	 */
	private void findLevelEventNode()
	{

		for (int i = 0; i < alls.size(); i++)
		{
			Tree EventNode = alls.get(i);
			if (EventNode.getLevel() == 1)
			{
				EventNodeLevel1.add(EventNode);

			}

		}

	}
	// 复�?�框联动

	private void checkEventNode(Tree EventNode, boolean isChecked)
	{
		EventNode.setChecked(isChecked);
		// 点击�?个节点的时�?�，将他的子节点设置为ischecked的状�?
		for (int i = 0; i < EventNode.getChildren().size(); i++)
		{
			checkEventNode(EventNode.getChildren().get(i), isChecked);
		}

		// 点击�?个节点的时�?�，将他的父节点设置为ischecked的状�?
		findParentEventNode(EventNode, isChecked);

	}

	/**
	 * @param EventNode
	 * @param isChecked
	 *            找到父节点，并且设置选择状�?�： 选中�?个节点的时�?�，将他的父节点和子节点设置为设置为选中状�?�，与之并列的节点不做处理；
	 *            取消选中的时候，如果有与之并列的节点仍处于�?�中状�?�，则他的父节点就仍然处于�?�中状�?�，
	 *            如果他的父节点下的所有的子节点都处于不�?�中的状态，则他的父节点才设置为不�?�中的状�?
	 */
	public void findParentEventNode(Tree EventNode, boolean isChecked)
	{

		EventNode = EventNode.getParent();
		if (EventNode != null)
		{
			EventNode.setChecked(isChecked);
			for (int i = 0; i < EventNode.getChildren().size(); i++)
			{
				if (EventNode.getChildren().get(i).isChecked())
				{
					EventNode.setChecked(true);
				}

			}

			findParentEventNode(EventNode, isChecked);
		}

	}

	/**
	 * @param EventNode
	 *            找到父节点，并且获取values
	 */
	public void findParentEventNode(Tree EventNode)
	{

		if (EventNode.getLevel() > 1)
		{
			EventNode = EventNode.getParent();
			if (EventNode != null)
			{
				findParentEventNode(EventNode);
				if (EventNode.getLevel() == 1)
				{
					EventNodeValues = EventNode.getValue();

				}
			}
		} else
		{
			EventNodeValues = EventNode.getValue();
		}

	}

	/**
	 * 获得选中节点
	 * 
	 * @return
	 */
	public List<Tree> getSeletedEventNodes()
	{
		List<Tree> EventNodes = new ArrayList<Tree>();
		for (int i = 0; i < allsCache.size(); i++)
		{
			Tree n = allsCache.get(i);
			if (n.isChecked())
			{
				EventNodes.add(n);
			}
		}
		return EventNodes;
	}

	// 控制节点的展�?和折�?
	public void filterEventNode()
	{

		alls.clear();
		for (int i = 0; i < allsCache.size(); i++)
		{
			Tree n = allsCache.get(i);
			if (!n.isParentCollapsed() || n.isRoot())
			{
				alls.add(n);
			}
		}

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
	 * 设置是否展开
	 * 
	 * @param hasCheckBox
	 */
	public void setExpand(boolean expand)
	{
		this.expand = expand;
	}

	/**
	 * 设置是否缩进
	 * 
	 * @param hasCheckBox
	 */
	public void setAlign(boolean setalign)
	{
		this.setAlign = setalign;
	}

	/**
	 * 设置展开和折叠状态图�?
	 * 
	 * @param expandedIcon
	 *            展开时图�?
	 * @param collapsedIcon
	 *            折叠时图�?
	 */
	public void setExpandedCollapsedIcon(int expandedIcon, int collapsedIcon)
	{
		this.expandedIcon = expandedIcon;
		this.collapsedIcon = collapsedIcon;
	}

	/**
	 * 设置展开级别
	 * 
	 * @param level
	 */
	public void setExpandLevel(int level)
	{
		alls.clear();
		for (int i = 0; i < allsCache.size(); i++)
		{
			Tree n = allsCache.get(i);
			if (n.getLevel() <= level)
			{
				if (n.getLevel() < level)
				{// 上层都设置展�?状�??
					n.setExpanded(true);
				} else
				{// �?后一层都设置折叠状�??
					n.setExpanded(false);
				}
				alls.add(n);
			}
		}
		this.notifyDataSetChanged();
	}

	/**
	 * @param position
	 * @return 当节点处于设备时，返回true，用来跳�?
	 */
	public boolean ExpandOrCollapse(int position)
	{
		if (expand)
		{
			Tree n = alls.get(position);
			if (!n.isRoot())
			{
				if (n != null)
				{
					if (!n.isLeaf())
					{
						n.setExpanded(!n.isExpanded());
						filterEventNode();
						this.notifyDataSetChanged();
					} else
					{
						return true;
					}
				}
			}
		}
		return false;
	}

}
