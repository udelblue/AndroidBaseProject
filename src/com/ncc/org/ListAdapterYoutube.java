
package com.ncc.org;




import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

class ListAdapterYoutube extends BaseAdapter {
		private Activity activity;
		private ImageLoader imageLoader;
		
		public ListAdapterYoutube(Activity act) {
			this.activity = act;
			imageLoader = new ImageLoader(act);
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return Youtube.title.length;
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
				convertView = inflater.inflate(R.layout.item_youtube, null);
				holder = new ViewHolder();
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.lytItemYoutube = (RelativeLayout) convertView.findViewById(R.id.lytItemYoutube);
			holder.imgThumb= (ImageView) convertView.findViewById(R.id.imgThumb);
			holder.txtTitle= (TextView) convertView.findViewById(R.id.txtTitle);
			holder.txtUploader= (TextView) convertView.findViewById(R.id.txtUploader);
			holder.txtViewCount = (TextView) convertView.findViewById(R.id.txtViewCount);
			
			
			if((position%2)!=0){
				holder.lytItemYoutube.setBackgroundResource(R.drawable.row_1);
			}else{
				holder.lytItemYoutube.setBackgroundResource(R.drawable.row_2);
			}
			
			
			
			holder.txtTitle.setText(Youtube.title[position]);
			holder.txtUploader.setText("by "+Youtube.uploader[position]);
			holder.txtViewCount.setText(Youtube.viewCount[position]+" views");
			
			imageLoader.DisplayImage(Youtube.thumbnail[position],
					activity, holder.imgThumb);
			System.out.println("thumbnail: "+ Youtube.thumbnail[position]);
			
			return convertView;
		}
		
		static class ViewHolder {
			TextView txtTitle, txtUploader, txtViewCount;
			ImageView imgThumb;
			RelativeLayout lytItemYoutube;
		}
		
		
		
		
		
	}