
package com.ncc.org;


import com.ncc.org.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

class ListAdapterNewsRoom extends BaseAdapter {
		private Activity activity;
		
		public ListAdapterNewsRoom(Activity act) {
			this.activity = act;
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return NewsRoom.title.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.item_newsroom, null);
				holder = new ViewHolder();
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.lytItemFeed = (LinearLayout) convertView.findViewById(R.id.lytItemFeed);
			holder.txtTitle= (TextView) convertView.findViewById(R.id.txtTitle);
			holder.txtPubDate = (TextView) convertView.findViewById(R.id.txtPubDate);
			
			
			if((position%2)!=0){
				holder.lytItemFeed.setBackgroundResource(R.drawable.row_1);
			}else{
				holder.lytItemFeed.setBackgroundResource(R.drawable.row_2);
			}
			
			holder.txtTitle.setText(NewsRoom.title[position]);
			holder.txtPubDate.setText(NewsRoom.pubDate[position]);
			
			
			return convertView;
		}
		
		static class ViewHolder {
			TextView txtTitle, txtPubDate;
			LinearLayout lytItemFeed;
		}
		
		
		
	}