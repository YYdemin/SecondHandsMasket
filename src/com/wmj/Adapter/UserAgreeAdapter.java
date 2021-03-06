package com.wmj.Adapter;

import java.util.List;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wmj.activity.HavePostActivity;

import com.wmj.bean.Person;
import com.wmj.secondhandmasket.R;
import com.wmj.util.HttpUtil;
/**
 *  管理员界面显示用户的适配器
 * @author Administrator
 *
 */
public class UserAgreeAdapter extends ArrayAdapter<Person> {
	private int resource;
	private List<Person> list;
	private Context context;
	private int listPosition = 0;
	String name;
	private ProgressDialog dialog;
	public UserAgreeAdapter(Context context, int resource, List<Person> list) {
		super(context, resource, list);
		this.resource = resource;
		this.list = list;
		this.context = context;
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Person getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Person person = getItem(position);
		View view;
		ViewHolder holder;
		if (convertView == null) {
		
			view = LayoutInflater.from(context).inflate(resource, null);
			holder = new ViewHolder();
			holder.userName = (TextView) view.findViewById(R.id.user_agree_name);
			holder.userPhone = (TextView) view.findViewById(R.id.user_agree_phone);
			holder.deleteBtn = (Button) view.findViewById(R.id.delete_btn);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.userName.setText("用户名:"+person.getName());
		holder.userPhone.setText("电话号码:"+person.getPhone());
		holder.deleteBtn.setTag(position);
		holder.deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialogDelete(position);
				name = list.get(position).getName();
				listPosition=position;
				
			}
		});
		//为每个item设置监听器,进入已发布帖子界面
		view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,HavePostActivity.class);
					intent.putExtra("person", person);
					context.startActivity(intent);
				}
			});
		return view;
	}

	class ViewHolder {
		TextView userName;
		TextView userPhone;
		Button   deleteBtn;
		int id;
	}

	// 点击删除按钮跳出显示选择对话框，确定删除就使用AsyncTask发送HTTP请求和数据给服务器端

	public void showDialogDelete(int position) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("提醒");
		dialog.setMessage("确定要删除此用户吗？");
		dialog.setCancelable(false);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DeleteUserAsyncTask().execute();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog.show();
	}
	
	/*
	 * 给予异步消息处理机制的AsyncTask，能更方便在子线程处理耗时的操作并且能对UI进行操作
	 * （1）给出一个对话框，在界面上弹出显示"删除中..."的信息 （2）在子线程中将请求和数据拼接传给服务器并进行交互
	 * （3）根据服务器返回的数据判断是否删除成功并以Toast形式告知用户。
	 */
	private class DeleteUserAsyncTask extends
			AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setTitle("");
			dialog.setMessage("删除中...");
			dialog.setCancelable(false); // 设置按下Back键不能取消对话框
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			String datastr;
			try {
				datastr = "Action=deleteagreeuser" + "&name=" + name;
				result = HttpUtil.sendHttpRequests(datastr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		// 根据服务器返回的数据判断是否删除信息成功并以Toast形式告知用户。
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result.trim().length() == 0) {
				Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
				list.remove(listPosition);
				notifyDataSetChanged();
			}
		}
	}


}
