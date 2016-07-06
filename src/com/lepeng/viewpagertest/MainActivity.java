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
	
	//viewpager对象
	private ViewPager viewPager;
	//view的集合
	private ArrayList<View> pagerViews;
	//图片控件
	private ImageView mImageView;
	//小圆点控件
	private ImageView imageView;
	//图片集合
	private ImageView[] mImageViews;
	//包裹小圆点的LinearLayout
	private ViewGroup mViewGroup;
	
	private int[] images;
	
	private int showIndex;
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	         images= new int[]{  //图片资源ID
	        		 R.drawable.item05,R.drawable.item01, R.drawable.item02, R.drawable.item03,
				R.drawable.item04, R.drawable.item05,R.drawable.item01 };
		viewPager = (ViewPager) findViewById(R.id.viewpagertest);
		mViewGroup = (ViewGroup) findViewById(R.id.viewgroup);
		pagerViews = new ArrayList<View>();
		
//		LayoutInflater  mLayoutInflater = getLayoutInflater();
		
		for(int i=0;i<images.length;i++){
			//新建布局
			LinearLayout mLinearLayout = new LinearLayout(this);
			//布局的宽高
			LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			//图片控件
		    mImageView = new ImageView(MainActivity.this);
			mImageView.setScaleType(ScaleType.CENTER_INSIDE);
			mImageView.setPadding(30, 15, 20, 30);
			mImageView.setImageResource(images[i]);
			mLinearLayout.addView(mImageView, mParams);
			pagerViews.add(mLinearLayout);
		}
	
		/**
		 * 有几张图片就显示几个小圆点
		 */
		
		//原点的ImageViews
		mImageViews = new ImageView[pagerViews.size()-2];
		
		for(int i=0;i<pagerViews.size()-2;i++){
			LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//设置小圆点距离左边的间距
			margin.setMargins(10, 0,0, 0);
			imageView = new ImageView(MainActivity.this);
			//设置小圆点的宽高
			imageView.setLayoutParams(new LayoutParams(15,15));
			mImageViews [i] = imageView;
			if(i==0){
				//默认选中第一张照片
				mImageViews [i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else {
				//其他照片未选中状态
				mImageViews [i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			
			mViewGroup.addView(mImageViews [i], margin);
		}
		
		
		//给viewpager设置适配器
		viewPager.setAdapter(new GuidePagerAdapter());
		//viewpager设置监听事件
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		//viewpager默认第二张图片
		viewPager.setCurrentItem(1,false);
		
		mhHandler.sendEmptyMessageDelayed(1, 3000);
		
	}
	
	//自动切换Handle
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
	
	//viewpager适配器
	class GuidePagerAdapter extends PagerAdapter{

		//getCount():返回要滑动的VIew的个数
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

		//destroyItem（）：从当前container中删除指定位置（position）的View;
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(pagerViews.get(position));
		}

		//instantiateItem()：做了两件事，第一：将当前视图添加到container中，第二：返回当前View
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(pagerViews.get(position));
			return pagerViews.get(position);
		}
		
	}
	
	// 指引页面更改事件监听器
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
				//遍历数组让当前选中图片下的小圆点设置颜色
				
				// 当前页为0时改为倒数第二个，也就是实际需要的最后一个view；当前页为最后一个时改为第二个，即实际上的第一个view
				
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
