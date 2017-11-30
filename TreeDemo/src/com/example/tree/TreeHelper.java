package com.example.tree;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class TreeHelper
{

	private static String TAG = "TreeHelper";

	public static Tree getNodeEventTree(Context context, String fileName, String key)

	{

		Tree NodeEvent = getNodeEvent(getEventArray(getEventObject(getDataFromAssets(context, fileName)), key));
		// 打印方法
		// List<Tree> children = NodeEvent.getChildren();
		// for (int i = 0; i < children.size(); i++)
		// {
		// Mlog.loge(TAG, "NodeEvent=" + children.get(i).getText() + "");
		//
		// List<Tree> children2 = children.get(i).getChildren();
		// for (int j = 0; j < children2.size(); j++)
		// {
		// Mlog.loge(TAG, "---NodeEvent=" + children2.get(j).getText() + "");
		// }
		// }

		return NodeEvent;

	}

	/**
	 * @param context
	 * @param fileName
	 * @return
	 * 
	 * 		获取文件文本
	 */

	public static Tree getNodeEvent(JSONArray companyArr)
	{

		// 创建根节�?
		Tree NodeEvent = new Tree("", "000000");// 单位列表

		for (int i = 0; i < companyArr.length(); i++)
		{

			Tree SecondNodeEvent = SetSecondArray(NodeEvent, i, companyArr);

			NodeEvent.add(SecondNodeEvent);
		}
		// Mlog.loge(TAG, "NodeEvent.getText()=" + NodeEvent.getText());

		return NodeEvent;

	}

	public static String getDataFromAssets(Context context, String fileName)
	{
		try
		{
			InputStream inputStream = context.getAssets().open(fileName);
			int available = inputStream.available();
			byte[] by = new byte[available];

			inputStream.read(by);

			String result = new String(by);
			// Mlog.loge(TAG, "result=" + result);
			return result;
		} catch (Exception e)
		{
		}
		return "";
	}

	/**
	 * @param allData
	 * @param key
	 * @return
	 * 
	 * 
	 * 		得到�?外层数组
	 */
	public static JSONArray getEventArray(JSONObject object, String key)
	{
		try
		{

			JSONArray companyArr = object.getJSONArray(key);
			return companyArr;
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getEventObject(String result)
	{
		try
		{
			JSONObject object = new JSONObject(result);

			return object;
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param firstNodeEvent
	 * @param position
	 * @param companyArr
	 * @return
	 * 
	 * 		获取第二层数�?
	 */
	public static Tree SetSecondArray(Tree parentNodeEvent, int position, JSONArray arr)
	{
		Tree NodeEvent = null;

		try
		{
			JSONObject info = arr.getJSONObject(position);
			String name = "";
			Long id = (long) 0;
			name = info.getString("name");

			NodeEvent = new Tree(name, Long.toHexString(id));
			NodeEvent.setParent(parentNodeEvent);
			if (info.has("child"))
			{
				JSONArray childArr = info.getJSONArray("child");
				for (int i = 0; i < childArr.length(); i++)
				{
					Tree childNodeEvent = SetthirdArray(NodeEvent, position, i, arr);
					NodeEvent.add(childNodeEvent);

				}
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		} // 取出公司列表

		return NodeEvent;
	}

	private static Tree SetthirdArray(Tree parentNodeEvent, int num, int childNum, JSONArray arr)
	{
		Tree childNodeEvent = null;
		try
		{

			JSONObject info = arr.getJSONObject(num);
			JSONArray childArr = info.getJSONArray("child");
			JSONObject childInfo = childArr.getJSONObject(childNum);

			String childName = "";
			Long childId = (long) 0;
			childName = childInfo.getString("name");

			childNodeEvent = new Tree(childName, Long.toHexString(childId));
			childNodeEvent.setParent(parentNodeEvent);
			if (childInfo.has("child"))
			{
				JSONArray lineArr = childInfo.getJSONArray("child");
				for (int i = 0; i < lineArr.length(); i++)
				{
					Tree lineNodeEvent = SetfourthArray(childNodeEvent, num, childNum, i, arr);
					childNodeEvent.add(lineNodeEvent);

				}
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		return childNodeEvent;
	}

	private static Tree SetfourthArray(Tree subsnNodeEvent, int companynum, int subsnnum, int linenum,
			JSONArray companyArr)
	{
		Tree lineNodeEvent = null;
		try
		{

			JSONObject companyinfo = companyArr.getJSONObject(companynum);
			JSONArray subsArr = companyinfo.getJSONArray("child");
			JSONObject subsinfo = subsArr.getJSONObject(subsnnum);
			JSONArray lineArr = subsinfo.getJSONArray("child");
			JSONObject lineinfo = lineArr.getJSONObject(linenum);

			String linename = "";
			Long lineid = (long) 0;
			linename = lineinfo.getString("name");

			lineNodeEvent = new Tree(linename, Long.toHexString(lineid));
			lineNodeEvent.setParent(subsnNodeEvent);
			// lineNodeEvent.setIcon(R.drawable.icon2);

			if (lineinfo.has("child"))
			{
				JSONArray deviceArr = lineinfo.getJSONArray("child");
				for (int i = 0; i < deviceArr.length(); i++)// deviceArr.length()
				{
					String devicename = "";
					Long deviceid = (long) 0;
					try
					{
						JSONObject deviceinfo = deviceArr.getJSONObject(i);
						devicename = deviceinfo.getString("name");

						Tree deviceNodeEvent = new Tree(devicename, Long.toHexString(deviceid));
						deviceNodeEvent.setParent(lineNodeEvent);
						lineNodeEvent.add(deviceNodeEvent);
					} catch (Exception e)
					{
					}
				}
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		} // 取出公司列表
		return lineNodeEvent;
	}

	public static void addNodeEvent(Tree NodeEvent, List<Tree> mNodeEventList)
	{
		mNodeEventList.add(NodeEvent);
		if (NodeEvent.getChildren() != null)
		{
			for (int i = 0; i < NodeEvent.getChildren().size(); i++)
			{
				addNodeEvent(NodeEvent.getChildren().get(i), mNodeEventList);
			}
		}

	}

	/**
	 * @param NodeEvent
	 *            找到level=4的根节点
	 */
	public static Tree findRootParentNodeEvent(Tree NodeEvent)
	{

		int level = NodeEvent.getLevel();

		if (level == 4)
		{
			return NodeEvent.getParent().getParent().getParent();
		} else if (level == 3)
		{
			return NodeEvent.getParent().getParent();
		} else if (level == 2)
		{
			return NodeEvent.getParent();
		}
		return NodeEvent;

	}

	/**
	 * 找到level为n �? NodeEvent
	 */
	public static List<Tree> findLevelNodeEvent(List<Tree> NodeEventList, int n)
	{
		List<Tree> levelNodeEventList = new ArrayList<Tree>();

		for (int i = 0; i < NodeEventList.size(); i++)
		{
			Tree NodeEvent = NodeEventList.get(i);
			if (NodeEvent.getLevel() == n)
			{
				levelNodeEventList.add(NodeEvent);

			}

		}
		return levelNodeEventList;

	}

}
