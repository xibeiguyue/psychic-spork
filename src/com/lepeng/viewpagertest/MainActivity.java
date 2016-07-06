package com.lepeng.viewpagertest;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {
	
	private final static String TAG = "VIEWPAGERTEST";
	
	//viewpager����
	private ViewPager viewPager;
	//view�ļ���
	private ArrayList<View> pagerViews;
	//ͼƬ�ؼ�
	private ImageView mImageView;
	//СԲ��ؼ�
	private ImageView imageView;
	//ͼƬ����
	private ImageView[] mImageViews;
	//����СԲ���LinearLayout
	private ViewGroup mViewGroup;
	
	private int[] images;
	
	private int showIndex;
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	         images= new int[]{  //ͼƬ��ԴID
	        		 R.drawable.item05,R.drawable.item01, R.drawable.item02, R.drawable.item03,
				R.drawable.item04, R.drawable.item05,R.drawable.item01 };
		viewPager = (ViewPager) findViewById(R.id.viewpagertest);
		mViewGroup = (ViewGroup) findViewById(R.id.viewgroup);
		pagerViews = new ArrayList<View>();
		
//		LayoutInflater  mLayoutInflater = getLayoutInflater();
		
		for(int i=0;i<images.length;i++){
			//�½�����
			LinearLayout mLinearLayout = new LinearLayout(this);
			//���ֵĿ��
			LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			//ͼƬ�ؼ�
		    mImageView = new ImageView(MainActivity.this);
			mImageView.setScaleType(ScaleType.CENTER_INSIDE);
			mImageView.setPadding(30, 15, 20, 30);
			mImageView.setImageResource(images[i]);
			mLinearLayout.addView(mImageView, mParams);
			pagerViews.add(mLinearLayout);
		}
	
		/**
		 * �м���ͼƬ����ʾ����СԲ��
		 */
		
		//ԭ���ImageViews
		mImageViews = new ImageView[pagerViews.size()-2];
		
		for(int i=0;i<pagerViews.size()-2;i++){
			LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//����СԲ�������ߵļ��
			margin.setMargins(10, 0,0, 0);
			imageView = new ImageView(MainActivity.this);
			//����СԲ��Ŀ��
			imageView.setLayoutParams(new LayoutParams(15,15));
			mImageViews [i] = imageView;
			if(i==0){
				//Ĭ��ѡ�е�һ����Ƭ
				mImageViews [i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else {
				//������Ƭδѡ��״̬
				mImageViews [i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			
			mViewGroup.addView(mImageViews [i], margin);
		}
		
		
		//��viewpager����������
		viewPager.setAdapter(new GuidePagerAdapter());
		//viewpager���ü����¼�
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		//viewpagerĬ�ϵڶ���ͼƬ
		viewPager.setCurrentItem(1,false);
		
		mhHandler.sendEmptyMessageDelayed(1, 3000);
		
	}
	
	//�Զ��л�Handle
	private Handler mhHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Log.i("11111111111111", "handeleee");
				int toItem = showIndex+1;
				if(toItem==6){
					viewPager.setCurrentItem(1, false);
					this.sendEmptyMessageDelayed(1, 3000);
				}else{
					viewPager.setCurrentItem(toItem, false);
					this.sendEmptyMessageDelayed(1, 3000);
				}
				break;

			default:
				break;
			}
		}
	};
	
	//viewpager������
	class GuidePagerAdapter extends PagerAdapter{

		//getCount():����Ҫ������VIew�ĸ���
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagerViews.size();
		}

		//
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		//destroyItem�������ӵ�ǰcontainer��ɾ��ָ��λ�ã�position����View;
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(pagerViews.get(position));
		}

		//instantiateItem()�����������£���һ������ǰ��ͼ��ӵ�container�У��ڶ������ص�ǰView
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(pagerViews.get(position));
			return pagerViews.get(position);
		}
		
	}
	
	// ָ��ҳ������¼�������
		class GuidePageChangeListener implements OnPageChangeListener {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageSelected(int arg0) {
				//���������õ�ǰѡ��ͼƬ�µ�СԲ��������ɫ
				
				// ��ǰҳΪ0ʱ��Ϊ�����ڶ�����Ҳ����ʵ����Ҫ�����һ��view����ǰҳΪ���һ��ʱ��Ϊ�ڶ�������ʵ���ϵĵ�һ��view
				
				Log.i(TAG, "onPageSelected-" + arg0);
				Log.i(TAG, "------------------------------");
			    showIndex = arg0;
				if(arg0==0){
					showIndex = pagerViews.size()-2;
					viewPager.setCurrentItem(pagerViews.size()-2, false);
				}else{
					if(arg0 == pagerViews.size() - 1){
						showIndex = 0;
						viewPager.setCurrentItem(1, false);
					}
				}
				
				for (int i = 0; i < mImageViews.length; i++) {
					mImageViews[showIndex - 1]
					.setBackgroundResource(R.drawable.page_indicator_focused);

					if (showIndex - 1 != i) {
						mImageViews[i]
					.setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
					}
			}
		}
	
}
