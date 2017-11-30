package com.example.treedemo;

import com.example.adapter.TreeAdapter;
import com.example.tree.Tree;
import com.example.tree.TreeHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity
{

	private ListView mListView;
	private TreeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		getAttentionData();
	}

	private void initView()
	{

		mListView = (ListView) findViewById(R.id.listView);

	}

	private void getAttentionData()
	{

		Tree nodeEventTree = TreeHelper.getNodeEventTree(this, "tree.json", "event");
		adapter = new TreeAdapter(this, nodeEventTree);
		adapter.setCheckBox(true);
		adapter.setExpandedCollapsedIcon(R.drawable.tree_ex, R.drawable.tree_ec);
		adapter.setExpandLevel(1);
		mListView.setAdapter(adapter);
		mListView.setFocusable(false);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				adapter.ExpandOrCollapse(position);

			}
		});

	}

}
