package me.linkcube.taku.ui.setting;

import custom.android.app.CustomFragmentActivity;

//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.ervinwang.bthelper.BTManager;
//import com.ervinwang.bthelper.core.IReceiveData;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.utils.Legend;
//import com.github.mikephil.charting.utils.LimitLine;
//import com.github.mikephil.charting.utils.Legend.LegendForm;
//import com.github.mikephil.charting.utils.LimitLine.LimitLabelPosition;
//
//import custom.android.app.CustomFragmentActivity;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.TextView;
//import me.linkcube.taku.R;
//import me.linkcube.taku.AppConst.GameFrame;
//import me.linkcube.taku.ui.BaseTitleActivity;

public class GraphTestActivity extends CustomFragmentActivity {

//	private LineChart mChart;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.graph_test_activity);
//		mChart = (LineChart) findViewById(R.id.chart1);
//
//		// mChart.setUnit(" $");
//		mChart.setDrawUnitsInChart(true);
//
//		mChart.setStartAtZero(true);
//		
//		mChart.setDrawYValues(false);
//
//		mChart.setDrawBorder(true);
//		mChart.setBorderPositions(new BorderPosition[] { BorderPosition.BOTTOM });
//
//		mChart.setDescription("");
//		mChart.setNoDataTextDescription("You need to provide data for the chart.");
//
//		mChart.setHighlightEnabled(true);
//
//		mChart.setHighlightIndicatorEnabled(false);
//
//		// add data
//		//timer = new Timer();
//		setData(120, 255);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//	}
//
//	private ArrayList<Entry> yVals = new ArrayList<Entry>();
//	private ArrayList<String> xVals = new ArrayList<String>();
//	private LineDataSet set1;
//	//private Timer timer;
//	private int countTemp = 0;
//	private boolean isStart = false;
//
//	LimitLine ll1 = new LimitLine(255f);
//
//	private void setData(final int count, float range) {
//
//		for (int i = 0; i < count; i++) {
//			xVals.add((i) + "");
//		}
//
//		for (int i = 0; i < count; i++) {
//			yVals.add(new Entry(-1, i));
//			countTemp++;
//		}
//
//		BTManager.getInstance().startReceiveData(new IReceiveData() {
//
//			@Override
//			public void receiveData(int bytes, byte[] buffer) {
//
//				byte[] buf_data = new byte[bytes];
//				for (int i = 0; i < bytes; i++) {
//					buf_data[i] = buffer[i];
//				}
//				if (buf_data[0] == GameFrame.PRESS_FRAME[0]
//						&& buf_data[1] == GameFrame.PRESS_FRAME[1]) {
//					if (countTemp >= 120) {
//						if (!isStart) {
//							set1 = new LineDataSet(yVals, "DataSet 1");
//							
//							set1.setColor(Color.BLACK);
//							set1.setCircleColor(Color.BLACK);
//							set1.setLineWidth(0f);
//							set1.setCircleSize(1f);
//							set1.setFillAlpha(0);
//							set1.setFillColor(Color.BLACK);
//
//							ll1.setLineWidth(4f);
//							ll1.enableDashedLine(10f, 10f, 0f);
//							ll1.setDrawValue(true);
//							ll1.setLabelPosition(LimitLabelPosition.RIGHT);
//
//							isStart = true;
//							handlerInit.sendEmptyMessage(0);
//						}
//						for (int j = 1; j < yVals.size(); j++) {
//							yVals.get(j).setXIndex(j - 1);
//						}
//						yVals.remove(0);
//						yVals.add(new Entry(byteToInt(buf_data[2]), 120));
//						xVals.remove(0);
//						countTemp++;
//						xVals.add(countTemp + "");
//						final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//						dataSets.add(set1);
//						LineData data = new LineData(xVals, dataSets);
//
//						data.addLimitLine(ll1);
//
//						mChart.setData(data);
//						handler.sendEmptyMessage(0);
//					} else {
//						yVals.add(new Entry(byteToInt(buf_data[2]), countTemp));
//						System.out.println("val:" + byteToInt(buf_data[2])
//								+ "--countTemp:" + countTemp);
//						countTemp++;
//					}
//				}
//			}
//		});
//
////		timer.schedule(new TimerTask() {
////
////			@Override
////			public void run() {
////
////				if (countTemp >= 120) {
////					if (!isStart) {
////						set1 = new LineDataSet(yVals, "DataSet 1");
////
////						set1.setColor(Color.BLACK);
////						set1.setCircleColor(Color.BLACK);
////						set1.setLineWidth(0f);
////						set1.setCircleSize(1f);
////						set1.setFillAlpha(0);
////						set1.setFillColor(Color.BLACK);
////
////						ll1.setLineWidth(4f);
////						ll1.enableDashedLine(10f, 10f, 0f);
////						ll1.setDrawValue(true);
////						ll1.setLabelPosition(LimitLabelPosition.RIGHT);
////
////						isStart = true;
////						handlerInit.sendEmptyMessage(0);
////					}
////					for (int j = 1; j < yVals.size(); j++) {
////						yVals.get(j).setXIndex(j - 1);
////					}
////					yVals.remove(0);
////					yVals.add(new Entry(getData(), 120));
////					xVals.remove(0);
////					countTemp++;
////					xVals.add(countTemp + "");
////					final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
////					dataSets.add(set1);
////					LineData data = new LineData(xVals, dataSets);
////
////					data.addLimitLine(ll1);
////
////					mChart.setData(data);
////					handler.sendEmptyMessage(0);
////				} else {
////					yVals.add(new Entry(getData(), countTemp));
////					System.out.println("val:" + getData() + "--countTemp:"
////							+ countTemp);
////					countTemp++;
////				}
////
////			}
////		}, 0, 100);
//	}
//
//	private Handler handler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			mChart.invalidate();
//		}
//
//	};
//
//	private Handler handlerInit = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			mChart.animateX(2500);
//			Legend l = mChart.getLegend();
//			l.setForm(LegendForm.LINE);
//		}
//
//	};
//
//	@Override
//	protected void onStop() {
//		super.onStop();
//		System.out.println("-------------------------onStop");
//		// timer.cancel();
//		// timer=null;
//	}
//
//	public static int byteToInt(byte mByte) {
//
//		int addr = mByte & 0xFF;
//
//		return addr;
//	}
//
//	public static float getData() {
//		float val = (float) (Math.random() * 100) + 3;
//		return val;
//	}
}
