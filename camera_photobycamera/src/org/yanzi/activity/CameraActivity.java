package org.yanzi.activity;

import org.yanzi.camera.CameraInterface;
import org.yanzi.camera.CameraInterface.CamOpenOverCallback;
import org.yanzi.camera.preview.CameraTextureView;
import org.yanzi.playcamera_v2.R;
import org.yanzi.util.DisplayUtil;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;

public class CameraActivity extends Activity implements CamOpenOverCallback {
	private static final String TAG = "yanzi";
	CameraTextureView textureView = null;
	ImageButton shutterBtn;
	float previewRate = -1f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread openThread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
			}
		};
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		textureView.setAlpha(1.0f);

		shutterBtn.setOnClickListener(new BtnListeners());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	private void initUI() {
		textureView = (CameraTextureView) findViewById(R.id.camera_textureview);
		shutterBtn = (ImageButton) findViewById(R.id.btn_shutter);
	}

	private void initViewParams() {
		LayoutParams params = textureView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		previewRate = DisplayUtil.getScreenRate(this); // Ĭ��ȫ���ı���Ԥ��
		textureView.setLayoutParams(params);

		// �ֶ���������ImageButton�Ĵ�СΪ120dip��120dip,ԭͼƬ��С��64��64
		LayoutParams p2 = shutterBtn.getLayoutParams();
		p2.width = DisplayUtil.dip2px(this, 80);
		p2.height = DisplayUtil.dip2px(this, 80);
		;
		shutterBtn.setLayoutParams(p2);

	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceTexture surface = textureView._getSurfaceTexture();
		CameraInterface.getInstance().doStartPreview(surface, previewRate);
	}

	private class BtnListeners implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_shutter:
				CameraInterface.getInstance().doTakePicture();
				break;
			default:
				break;
			}
		}

	}

}
